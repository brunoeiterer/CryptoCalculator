import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
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

public class DESController{

    @FXML
    private TextArea DESInputTextArea;
    @FXML
    private TextField DESKeyTextField;
    @FXML 
    private ComboBox<String> DesModeOfOperationComboBox;
    @FXML
    private TextArea DesOutputTextArea;
    @FXML
    private Button DESEncryptButton;
    @FXML
    private Button DESDecryptButton;
    @FXML
    private Label DESICVLabel;
    @FXML
    private TextField DESICVTextField;

    @FXML
    public void initialize(){

        this.DesModeOfOperationComboBox.getItems().add("ECB");
        this.DesModeOfOperationComboBox.getItems().add("CBC");
        this.DesModeOfOperationComboBox.getItems().add("CFB");
        this.DesModeOfOperationComboBox.getItems().add("OFB");
        this.DesModeOfOperationComboBox.setValue("ECB");
        this.DesModeOfOperationComboBox.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                DESController.this.EncryptionModeChanged(event);
            }
        });

        this.DESICVLabel.setVisible(false);
        this.DESICVLabel.setManaged(false);
        this.DESICVTextField.setVisible(false);
        this.DESICVTextField.setManaged(false);

        this.DESEncryptButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                DESController.this.EncryptButtonHandler(event);
            }
        });

        this.DESDecryptButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                DESController.this.DecryptButtonHandler(event);
            }
        });
    }

    private void EncryptButtonHandler(ActionEvent event){
        StringBuilder errorMessage = new StringBuilder();
        if((this.DESInputTextArea.getText().trim()).isEmpty()){
            errorMessage.append("The input is invalid.\n");
        }
        else if((this.DESInputTextArea.getText().trim()).length() % 16 != 0){
            errorMessage.append("The input size must be a multiple of 8 bytes.\n");
        }
        else if(!(this.DESInputTextArea.getText().trim()).matches("[A-Fa-f0-9]+")){
            errorMessage.append("The input must be an hex string [A-F a-f 0-9].\n");
        }

        if((this.DESKeyTextField.getText().trim()).isEmpty()){
            errorMessage.append("\nThe key is invalid.\n");
        }
        else if((this.DESKeyTextField.getText().trim()).length() != 16){
            errorMessage.append("The key size must be 8 bytes\n");
        }
        else if(!(this.DESKeyTextField.getText().trim()).matches("[A-Fa-f0-9]+")){
            errorMessage.append("The key must be an hex string [A-F a-f 0-9].\n");
        }

        String modeOfOperation = this.DesModeOfOperationComboBox.getValue();
        if(modeOfOperation != "ECB"){
            if(this.DESICVTextField.getText().trim().isEmpty()){
                errorMessage.append("\nThe ICV is invalid.\n");
            }
            else if(this.DESICVTextField.getText().trim().length() != 16){
                errorMessage.append("\nThe ICV size must be 8 bytes.\n");
            }
            else if(!(this.DESICVTextField.getText().trim()).matches("[A-Fa-f0-9]+")){
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
        if((this.DESInputTextArea.getText().trim()).isEmpty()){
            errorMessage.append("The input is invalid.\n");
        }
        else if((this.DESInputTextArea.getText().trim()).length() % 16 != 0){
            errorMessage.append("The input size must be a multiple of 8 bytes.\n");
        }
        else if(!(this.DESInputTextArea.getText().trim()).matches("[A-Fa-f0-9]+")){
            errorMessage.append("The input must be an hex string [A-F a-f 0-9].\n");
        }

        if((this.DESKeyTextField.getText().trim()).isEmpty()){
            errorMessage.append("\nThe key is invalid.\n");
        }
        else if((this.DESKeyTextField.getText().trim()).length() != 16){
            errorMessage.append("The key size must be 8 bytes\n");
        }
        else if(!(this.DESKeyTextField.getText().trim()).matches("[A-Fa-f0-9]+")){
            errorMessage.append("The key must be an hex string [A-F a-f 0-9].\n");
        }

        String modeOfOperation = this.DesModeOfOperationComboBox.getValue();
        if(modeOfOperation != "ECB"){
            if(this.DESICVTextField.getText().trim().isEmpty()){
                errorMessage.append("\nThe ICV is invalid.\n");
            }
            else if(this.DESICVTextField.getText().trim().length() != 16){
                errorMessage.append("\nThe ICV size must be 8 bytes.\n");
            }
            else if(!(this.DESICVTextField.getText().trim()).matches("[A-Fa-f0-9]+")){
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
        switch(this.DesModeOfOperationComboBox.getValue()){
            case "ECB":
                this.DESICVLabel.setVisible(false);
                this.DESICVLabel.setManaged(false);
                this.DESICVTextField.setVisible(false);
                this.DESICVTextField.setManaged(false);
                break;
            case "CBC":
                this.DESICVLabel.setVisible(true);
                this.DESICVLabel.setManaged(true);
                this.DESICVTextField.setVisible(true);
                this.DESICVTextField.setManaged(true);
                break;
            case "CFB":
                this.DESICVLabel.setVisible(true);
                this.DESICVLabel.setManaged(true);
                this.DESICVTextField.setVisible(true);
                this.DESICVTextField.setManaged(true);
                break;
            case "OFB":
                this.DESICVLabel.setVisible(true);
                this.DESICVLabel.setManaged(true);
                this.DESICVTextField.setVisible(true);
                this.DESICVTextField.setManaged(true);
                break;
        }
    }

    private void Codec(int operation, String modeOfOperation){
        SecretKeySpec key = null;
        IvParameterSpec iv = null;
        byte[] input = null;

        try{
            input = Hex.decodeHex(this.DESInputTextArea.getText().trim().toCharArray());
            key = new SecretKeySpec(Hex.decodeHex(this.DESKeyTextField.getText().trim().toCharArray()), "DES");
            if(modeOfOperation != "ECB"){
                iv = new IvParameterSpec(Hex.decodeHex(this.DESICVTextField.getText().trim().toCharArray()));
            }
        }
        catch(DecoderException e){
            e.printStackTrace();
        }

        try{
            String cipherInstanceName = "DES/" + modeOfOperation + "/NoPadding";
            Cipher cipher = Cipher.getInstance(cipherInstanceName);
            cipher.init(operation, key, iv);
            byte ciphertext[] = cipher.doFinal(input);
            this.DesOutputTextArea.setText(Hex.encodeHexString(ciphertext));
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