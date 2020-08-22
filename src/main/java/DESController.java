import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.spec.SecretKeySpec;
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
    public void initialize(){

        this.DesModeOfOperationComboBox.getItems().add("ECB");
        this.DesModeOfOperationComboBox.getItems().add("CBC");
        this.DesModeOfOperationComboBox.getItems().add("CFB");
        this.DesModeOfOperationComboBox.getItems().add("OFB");
        this.DesModeOfOperationComboBox.setValue("ECB");

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

        if(!errorMessage.toString().isEmpty()){
            Alert invalidInputAlert = new Alert(Alert.AlertType.ERROR);
            invalidInputAlert.setHeaderText(null);
            invalidInputAlert.setGraphic(null);
            invalidInputAlert.setTitle("Invalid Input");
            invalidInputAlert.setContentText(errorMessage.toString());
            invalidInputAlert.showAndWait();
        }
        else{
            try{
                Cipher cipher = Cipher.getInstance("DES");
                try{
                    SecretKeySpec key = new SecretKeySpec(Hex.decodeHex(this.DESKeyTextField.getText().trim().toCharArray()), "DES");
                    cipher.init(Cipher.ENCRYPT_MODE, key);
                    byte ciphertext[] = cipher.doFinal(Hex.decodeHex(this.DESInputTextArea.getText().trim().toCharArray()));
                    this.DesOutputTextArea.setText(Hex.encodeHexString(ciphertext));
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
                catch(DecoderException e){
                    e.printStackTrace();
                }
            }
            catch(NoSuchAlgorithmException e){
                e.printStackTrace();
            }
            catch(NoSuchPaddingException e){
                e.printStackTrace();
            }
        }
    }

    private void DecryptButtonHandler(ActionEvent event){

    }
}