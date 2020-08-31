import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class TDESController{

    @FXML
    private TextArea TDESInputTextArea;
    @FXML
    private TextField TDESKeyTextField;
    @FXML 
    private ComboBox<String> TDESModeOfOperationComboBox;
    @FXML
    private ComboBox<String> TDESPaddingComboBox;
    @FXML
    private TextArea TDESOutputTextArea;
    @FXML
    private Button TDESEncryptButton;
    @FXML
    private Button TDESDecryptButton;
    @FXML
    private Label TDESICVLabel;
    @FXML
    private TextField TDESICVTextField;

    private String Padding;

    @FXML
    public void initialize(){

        this.TDESModeOfOperationComboBox.getItems().add("ECB");
        this.TDESModeOfOperationComboBox.getItems().add("CBC");
        this.TDESModeOfOperationComboBox.getItems().add("CFB8");
        this.TDESModeOfOperationComboBox.getItems().add("OFB8");
        this.TDESModeOfOperationComboBox.setValue("ECB");
        this.TDESModeOfOperationComboBox.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                TDESController.this.EncryptionModeChanged(event);
            }
        });

        this.Padding = "/NoPadding";
        this.TDESPaddingComboBox.getItems().add("NoPadding");
        this.TDESPaddingComboBox.getItems().add("PKCS5Padding");
        this.TDESPaddingComboBox.setValue("NoPadding");
        this.TDESPaddingComboBox.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                TDESController.this.PaddingChanged(event);
            }
        });

        this.TDESICVLabel.setVisible(false);
        this.TDESICVLabel.setManaged(false);
        this.TDESICVTextField.setVisible(false);
        this.TDESICVTextField.setManaged(false);

        this.TDESEncryptButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                TDESController.this.EncryptButtonHandler(event);
            }
        });

        this.TDESDecryptButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                TDESController.this.DecryptButtonHandler(event);
            }
        });
    }

    private void EncryptButtonHandler(ActionEvent event){
        StringBuilder errorMessage = new StringBuilder();
        if((this.TDESInputTextArea.getText().trim()).isEmpty()){
            errorMessage.append("The input is invalid.\n");
        }
        else if((this.TDESInputTextArea.getText().trim()).length() % 16 != 0){
            errorMessage.append("The input size must be a multiple of 8 bytes.\n");
        }
        else if(!(this.TDESInputTextArea.getText().trim()).matches("[A-Fa-f0-9]+")){
            errorMessage.append("The input must be an hex string [A-F a-f 0-9].\n");
        }

        if((this.TDESKeyTextField.getText().trim()).isEmpty()){
            errorMessage.append("\nThe key is invalid.\n");
        }
        else if((this.TDESKeyTextField.getText().trim()).length() != 32 &&
                    (this.TDESKeyTextField.getText().trim()).length() != 48){
            errorMessage.append("The key size must be 16 or 24 bytes\n");
        }
        else if(!(this.TDESKeyTextField.getText().trim()).matches("[A-Fa-f0-9]+")){
            errorMessage.append("The key must be an hex string [A-F a-f 0-9].\n");
        }

        String modeOfOperation = this.TDESModeOfOperationComboBox.getValue();
        if(modeOfOperation != "ECB"){
            if(this.TDESICVTextField.getText().trim().isEmpty()){
                errorMessage.append("\nThe ICV is invalid.\n");
            }
            else if(this.TDESICVTextField.getText().trim().length() != 16){
                errorMessage.append("\nThe ICV size must be 8 bytes.\n");
            }
            else if(!(this.TDESICVTextField.getText().trim()).matches("[A-Fa-f0-9]+")){
                errorMessage.append("The ICV must be an hex string [A-F a-f 0-9].\n");
            }
        }

        if(!errorMessage.toString().isEmpty()){
            Alert invalidInputAlert = new Alert(Alert.AlertType.ERROR);
            invalidInputAlert.setHeaderText(null);
            invalidInputAlert.setGraphic(null);
            invalidInputAlert.setTitle("Invalid Input");
            invalidInputAlert.setContentText(errorMessage.toString());
            invalidInputAlert.showAndWait();
        }
        else{
            this.Codec(Cipher.ENCRYPT_MODE, modeOfOperation);
        }
    }

    private void DecryptButtonHandler(ActionEvent event){
        StringBuilder errorMessage = new StringBuilder();
        if((this.TDESInputTextArea.getText().trim()).isEmpty()){
            errorMessage.append("The input is invalid.\n");
        }
        else if((this.TDESInputTextArea.getText().trim()).length() % 16 != 0){
            errorMessage.append("The input size must be a multiple of 8 bytes.\n");
        }
        else if(!(this.TDESInputTextArea.getText().trim()).matches("[A-Fa-f0-9]+")){
            errorMessage.append("The input must be an hex string [A-F a-f 0-9].\n");
        }

        if((this.TDESKeyTextField.getText().trim()).isEmpty()){
            errorMessage.append("\nThe key is invalid.\n");
        }
        else if((this.TDESKeyTextField.getText().trim()).length() != 32 && 
                    (this.TDESKeyTextField.getText().trim()).length() != 48){
            errorMessage.append("The key size must be 16 or 24 bytes\n");
        }
        else if(!(this.TDESKeyTextField.getText().trim()).matches("[A-Fa-f0-9]+")){
            errorMessage.append("The key must be an hex string [A-F a-f 0-9].\n");
        }

        String modeOfOperation = this.TDESModeOfOperationComboBox.getValue();
        if(modeOfOperation != "ECB"){
            if(this.TDESICVTextField.getText().trim().isEmpty()){
                errorMessage.append("\nThe ICV is invalid.\n");
            }
            else if(this.TDESICVTextField.getText().trim().length() != 16){
                errorMessage.append("\nThe ICV size must be 8 bytes.\n");
            }
            else if(!(this.TDESICVTextField.getText().trim()).matches("[A-Fa-f0-9]+")){
                errorMessage.append("The ICV must be an hex string [A-F a-f 0-9].\n");
            }
        }

        if(!errorMessage.toString().isEmpty()){
            Alert invalidInputAlert = new Alert(Alert.AlertType.ERROR);
            invalidInputAlert.setHeaderText(null);
            invalidInputAlert.setGraphic(null);
            invalidInputAlert.setTitle("Invalid Input");
            invalidInputAlert.setContentText(errorMessage.toString());
            invalidInputAlert.showAndWait();
        }
        else{
            Codec(Cipher.DECRYPT_MODE, modeOfOperation);
        }
    }

    private void EncryptionModeChanged(ActionEvent event){
        switch(this.TDESModeOfOperationComboBox.getValue()){
            case "ECB":
                this.TDESICVLabel.setVisible(false);
                this.TDESICVLabel.setManaged(false);
                this.TDESICVTextField.setVisible(false);
                this.TDESICVTextField.setManaged(false);
                break;
            case "CBC":
                this.TDESICVLabel.setVisible(true);
                this.TDESICVLabel.setManaged(true);
                this.TDESICVTextField.setVisible(true);
                this.TDESICVTextField.setManaged(true);
                break;
            case "CFB8":
                this.TDESICVLabel.setVisible(true);
                this.TDESICVLabel.setManaged(true);
                this.TDESICVTextField.setVisible(true);
                this.TDESICVTextField.setManaged(true);
                break;
            case "OFB8":
                this.TDESICVLabel.setVisible(true);
                this.TDESICVLabel.setManaged(true);
                this.TDESICVTextField.setVisible(true);
                this.TDESICVTextField.setManaged(true);
                break;
        }
    }

    private void PaddingChanged(ActionEvent event){
        this.Padding = "/" +  this.TDESPaddingComboBox.getValue();
    }

    private void Codec(int operation, String modeOfOperation){
        SecretKeySpec key = null;
        IvParameterSpec iv = null;
        byte[] input = null;

        try{
            input = Hex.decodeHex(this.TDESInputTextArea.getText().trim().toCharArray());
            String keyString;
            if((this.TDESKeyTextField.getText().trim()).length() == 32) {
                keyString = this.TDESKeyTextField.getText().trim() + this.TDESKeyTextField.getText().trim().substring(0, 16);
            }
            else{
                keyString = this.TDESKeyTextField.getText().trim();
            }
            key = new SecretKeySpec(Hex.decodeHex(keyString.toCharArray()), "DESede");
            if(modeOfOperation != "ECB"){
                iv = new IvParameterSpec(Hex.decodeHex(this.TDESICVTextField.getText().trim().toCharArray()));
            }
        }
        catch(DecoderException e){
            e.printStackTrace();
        }

        try{
            String cipherInstanceName = "DESede/" + modeOfOperation + this.Padding;
            Cipher cipher = Cipher.getInstance(cipherInstanceName);
            cipher.init(operation, key, iv);
            byte ciphertext[] = cipher.doFinal(input);
            this.TDESOutputTextArea.setText(Hex.encodeHexString(ciphertext));
        }
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        catch(NoSuchPaddingException e){
            e.printStackTrace();
        }
        catch(InvalidKeyException e){
            e.printStackTrace();
        }
        catch(IllegalBlockSizeException e){
            e.printStackTrace();
        }
        catch(BadPaddingException e){
            e.printStackTrace();
        }
        catch(InvalidAlgorithmParameterException e){
            e.printStackTrace();
        }
    }
}