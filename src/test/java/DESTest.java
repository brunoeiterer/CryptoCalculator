import java.io.IOException;

import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.TextInputControlMatchers;

@ExtendWith(ApplicationExtension.class)
class DESTests {

    @BeforeAll
    private static void setupHeadlessMode() throws Exception{
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
    }

    @Start
    private void start(Stage stage) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

            Scene scene = new Scene(root);
            stage.setTitle("CryptoCalculator");
            stage.setScene(scene);
            stage.setHeight(1024);
            stage.setWidth(768);
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    void DESECBEncrypt(FxRobot robot) {
        /* select ECB Mode of Operation */
        robot.clickOn("#DesModeOfOperationComboBox");
        robot.clickOn("ECB");

        /* select noPadding */
        robot.clickOn("#DesPaddingComboBox");
        robot.clickOn("NoPadding");

        /* write input */
        robot.clickOn("#DESInputTextArea").write("0000000000000000");

        /* write key */
        robot.clickOn("#DESKeyTextField").write("0123456789ABCDEF");

        /* click on Encrypt button */
        robot.clickOn("#DESEncryptButton");

        /* verify if encryption was successful */
        FxAssert.verifyThat("#DesOutputTextArea", TextInputControlMatchers.hasText("d5d44ff720683d0d"));
    }

    @Test
    void DESECBPKCS5Encrypt(FxRobot robot) {
        /* select ECB Mode of Operation */
        robot.clickOn("#DesModeOfOperationComboBox");
        robot.clickOn("ECB");

        /* select PKCS5 padding */
        robot.clickOn("#DesPaddingComboBox");
        robot.clickOn("PKCS5Padding");

        /* write input */
        robot.clickOn("#DESInputTextArea").write("0000000000000000");

        /* write key */
        robot.clickOn("#DESKeyTextField").write("0123456789ABCDEF");

        /* click on Encrypt button */
        robot.clickOn("#DESEncryptButton");

        /* verify if decryption was successful */
        FxAssert.verifyThat("#DesOutputTextArea", TextInputControlMatchers.hasText("d5d44ff720683d0d086f9a1d74c94d4e"));
    }

    @Test
    void DESECBDecrypt(FxRobot robot) {
        /* select ECB Mode of Operation */
        robot.clickOn("#DesModeOfOperationComboBox");
        robot.clickOn("ECB");

        /* select noPadding */
        robot.clickOn("#DesPaddingComboBox");
        robot.clickOn("NoPadding");

        /* write input */
        robot.clickOn("#DESInputTextArea").write("D5D44FF720683D0D");

        /* write key */
        robot.clickOn("#DESKeyTextField").write("0123456789ABCDEF");

        /* click on Encrypt button */
        robot.clickOn("#DESDecryptButton");

        /* verify if encryption was successful */
        FxAssert.verifyThat("#DesOutputTextArea", TextInputControlMatchers.hasText("0000000000000000"));
    }

    @Test
    void DESECBPKCS5Decrypt(FxRobot robot) {
        /* select ECB Mode of Operation */
        robot.clickOn("#DesModeOfOperationComboBox");
        robot.clickOn("ECB");

        /* select PKCS5 padding */
        robot.clickOn("#DesPaddingComboBox");
        robot.clickOn("PKCS5Padding");

        /* write input */
        robot.clickOn("#DESInputTextArea").write("d5d44ff720683d0d086f9a1d74c94d4e");

        /* write key */
        robot.clickOn("#DESKeyTextField").write("0123456789ABCDEF");

        /* click on Encrypt button */
        robot.clickOn("#DESDecryptButton");

        /* verify if decryption was successful */
        FxAssert.verifyThat("#DesOutputTextArea", TextInputControlMatchers.hasText("0000000000000000"));
    }

    @Test
    void DESCBCEncryptNoPadding(FxRobot robot) {
        /* select ECB Mode of Operation */
        robot.clickOn("#DesModeOfOperationComboBox");
        robot.clickOn("CBC");

        /* select noPadding */
        robot.clickOn("#DesPaddingComboBox");
        robot.clickOn("NoPadding");

        /* write input */
        robot.clickOn("#DESInputTextArea").write("0000000000000000");

        /* write ICV */
        robot.clickOn("#DESICVTextField").write("0123456789ABCDEF");

        /* write key */
        robot.clickOn("#DESKeyTextField").write("0123456789ABCDEF");

        /* click on Encrypt button */
        robot.clickOn("#DESEncryptButton");

        /* verify if encryption was successful */
        FxAssert.verifyThat("#DesOutputTextArea", TextInputControlMatchers.hasText("56cc09e7cfdc4cef"));
    }

    @Test
    void DESCBCPKCS5Encrypt(FxRobot robot) {
        /* select ECB Mode of Operation */
        robot.clickOn("#DesModeOfOperationComboBox");
        robot.clickOn("CBC");

        /* select noPadding */
        robot.clickOn("#DesPaddingComboBox");
        robot.clickOn("PKCS5Padding");

        /* write input */
        robot.clickOn("#DESInputTextArea").write("0000000000000000");

        /* write ICV */
        robot.clickOn("#DESICVTextField").write("0123456789ABCDEF");

        /* write key */
        robot.clickOn("#DESKeyTextField").write("0123456789ABCDEF");

        /* click on Encrypt button */
        robot.clickOn("#DESEncryptButton");

        /* verify if encryption was successful */
        FxAssert.verifyThat("#DesOutputTextArea", TextInputControlMatchers.hasText("56cc09e7cfdc4cef9c9434b86c531b57"));
    }

    @Test
    void DESCBCDecryptNoPadding(FxRobot robot) {
        /* select ECB Mode of Operation */
        robot.clickOn("#DesModeOfOperationComboBox");
        robot.clickOn("CBC");

        /* select noPadding */
        robot.clickOn("#DesPaddingComboBox");
        robot.clickOn("NoPadding");

        /* write input */
        robot.clickOn("#DESInputTextArea").write("56cc09e7cfdc4cef");

        /* write ICV */
        robot.clickOn("#DESICVTextField").write("0123456789ABCDEF");

        /* write key */
        robot.clickOn("#DESKeyTextField").write("0123456789ABCDEF");

        /* click on Encrypt button */
        robot.clickOn("#DESDecryptButton");

        /* verify if decryption was successful */
        FxAssert.verifyThat("#DesOutputTextArea", TextInputControlMatchers.hasText("0000000000000000"));
    }

    @Test
    void DESCBCPKCS5Decrypt(FxRobot robot) {
        /* select ECB Mode of Operation */
        robot.clickOn("#DesModeOfOperationComboBox");
        robot.clickOn("CBC");

        /* select noPadding */
        robot.clickOn("#DesPaddingComboBox");
        robot.clickOn("PKCS5Padding");

        /* write input */
        robot.clickOn("#DESInputTextArea").write("56cc09e7cfdc4cef9c9434b86c531b57");

        /* write ICV */
        robot.clickOn("#DESICVTextField").write("0123456789ABCDEF");

        /* write key */
        robot.clickOn("#DESKeyTextField").write("0123456789ABCDEF");

        /* click on Encrypt button */
        robot.clickOn("#DESDecryptButton");

        /* verify if decryption was successful */
        FxAssert.verifyThat("#DesOutputTextArea", TextInputControlMatchers.hasText("0000000000000000"));
    }

    @Test
    void DESCFB8EncryptNoPadding(FxRobot robot) {
        /* select ECB Mode of Operation */
        robot.clickOn("#DesModeOfOperationComboBox");
        robot.clickOn("CFB8");

        /* select noPadding */
        robot.clickOn("#DesPaddingComboBox");
        robot.clickOn("NoPadding");

        /* write input */
        robot.clickOn("#DESInputTextArea").write("0000000000000000");

        /* write ICV */
        robot.clickOn("#DESICVTextField").write("0123456789ABCDEF");

        /* write key */
        robot.clickOn("#DESKeyTextField").write("0123456789ABCDEF");

        /* click on Encrypt button */
        robot.clickOn("#DESEncryptButton");

        /* verify if encryption was successful */
        FxAssert.verifyThat("#DesOutputTextArea", TextInputControlMatchers.hasText("5679535718144520"));
    }
}