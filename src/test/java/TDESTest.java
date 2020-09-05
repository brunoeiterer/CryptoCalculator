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
class TDESTests {

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
            stage.setHeight(800);
            stage.setWidth(600);
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    void TTDESECBEncrypt(FxRobot robot) {
        /* select TDES algorithm */
        robot.clickOn("TDES");

        /* select ECB Mode of Operation */
        robot.clickOn("#TDESModeOfOperationComboBox");
        robot.clickOn("ECB");

        /* select noPadding */
        robot.clickOn("#TDESPaddingComboBox");
        robot.clickOn("NoPadding");

        /* write input */
        robot.clickOn("#TDESInputTextArea").write("0000000000000000");

        /* write key */
        robot.clickOn("#TDESKeyTextField").write("0123456789ABCDEFFEDCBA9876543210");

        /* click on Encrypt button */
        robot.clickOn("#TDESEncryptButton");

        /* verify if encryption was successful */
        FxAssert.verifyThat("#TDESOutputTextArea", TextInputControlMatchers.hasText("08d7b4fb629d0885"));
    }

    @Test
    void TDESECBPKCS5Encrypt(FxRobot robot) {
        /* select TDES algorithm */
        robot.clickOn("TDES");

        /* select ECB Mode of Operation */
        robot.clickOn("#TDESModeOfOperationComboBox");
        robot.clickOn("ECB");

        /* select PKCS5 padding */
        robot.clickOn("#TDESPaddingComboBox");
        robot.clickOn("PKCS5Padding");

        /* write input */
        robot.clickOn("#TDESInputTextArea").write("0000000000000000");

        /* write key */
        robot.clickOn("#TDESKeyTextField").write("0123456789ABCDEFFEDCBA9876543210");

        /* click on Encrypt button */
        robot.clickOn("#TDESEncryptButton");

        /* verify if decryption was successful */
        FxAssert.verifyThat("#TDESOutputTextArea", TextInputControlMatchers.hasText("08d7b4fb629d08852e24eeb85aef49ae"));
    }

    @Test
    void TDESECBDecrypt(FxRobot robot) {
        /* select TDES algorithm */
        robot.clickOn("TDES");

        /* select ECB Mode of Operation */
        robot.clickOn("#TDESModeOfOperationComboBox");
        robot.clickOn("ECB");

        /* select noPadding */
        robot.clickOn("#TDESPaddingComboBox");
        robot.clickOn("NoPadding");

        /* write input */
        robot.clickOn("#TDESInputTextArea").write("08d7b4fb629d0885");

        /* write key */
        robot.clickOn("#TDESKeyTextField").write("0123456789ABCDEFFEDCBA9876543210");

        /* click on Encrypt button */
        robot.clickOn("#TDESDecryptButton");

        /* verify if encryption was successful */
        FxAssert.verifyThat("#TDESOutputTextArea", TextInputControlMatchers.hasText("0000000000000000"));
    }

    @Test
    void TDESECBPKCS5Decrypt(FxRobot robot) {
        /* select TDES algorithm */
        robot.clickOn("TDES");

        /* select ECB Mode of Operation */
        robot.clickOn("#TDESModeOfOperationComboBox");
        robot.clickOn("ECB");

        /* select PKCS5 padding */
        robot.clickOn("#TDESPaddingComboBox");
        robot.clickOn("PKCS5Padding");

        /* write input */
        robot.clickOn("#TDESInputTextArea").write("08d7b4fb629d08852e24eeb85aef49ae");

        /* write key */
        robot.clickOn("#TDESKeyTextField").write("0123456789ABCDEFFEDCBA9876543210");

        /* click on Encrypt button */
        robot.clickOn("#TDESDecryptButton");

        /* verify if decryption was successful */
        FxAssert.verifyThat("#TDESOutputTextArea", TextInputControlMatchers.hasText("0000000000000000"));
    }

    @Test
    void TDESCBCEncryptNoPadding(FxRobot robot) {
        /* select TDES algorithm */
        robot.clickOn("TDES");

        /* select ECB Mode of Operation */
        robot.clickOn("#TDESModeOfOperationComboBox");
        robot.clickOn("CBC");

        /* select noPadding */
        robot.clickOn("#TDESPaddingComboBox");
        robot.clickOn("NoPadding");

        /* write input */
        robot.clickOn("#TDESInputTextArea").write("0000000000000000");

        /* write ICV */
        robot.clickOn("#TDESICVTextField").write("0123456789ABCDEF");

        /* write key */
        robot.clickOn("#TDESKeyTextField").write("0123456789ABCDEFFEDCBA9876543210");

        /* click on Encrypt button */
        robot.clickOn("#TDESEncryptButton");

        /* verify if encryption was successful */
        FxAssert.verifyThat("#TDESOutputTextArea", TextInputControlMatchers.hasText("1a4d672dca6cb335"));
    }

    @Test
    void TDESCBCPKCS5Encrypt(FxRobot robot) {
        /* select TDES algorithm */
        robot.clickOn("TDES");

        /* select ECB Mode of Operation */
        robot.clickOn("#TDESModeOfOperationComboBox");
        robot.clickOn("CBC");

        /* select noPadding */
        robot.clickOn("#TDESPaddingComboBox");
        robot.clickOn("PKCS5Padding");

        /* write input */
        robot.clickOn("#TDESInputTextArea").write("0000000000000000");

        /* write ICV */
        robot.clickOn("#TDESICVTextField").write("0123456789ABCDEF");

        /* write key */
        robot.clickOn("#TDESKeyTextField").write("0123456789ABCDEFFEDCBA9876543210");

        /* click on Encrypt button */
        robot.clickOn("#TDESEncryptButton");

        /* verify if encryption was successful */
        FxAssert.verifyThat("#TDESOutputTextArea", TextInputControlMatchers.hasText("1a4d672dca6cb335fce5219cefe9c3ea"));
    }

    @Test
    void TDESCBCDecryptNoPadding(FxRobot robot) {
        /* select TDES algorithm */
        robot.clickOn("TDES");

        /* select ECB Mode of Operation */
        robot.clickOn("#TDESModeOfOperationComboBox");
        robot.clickOn("CBC");

        /* select noPadding */
        robot.clickOn("#TDESPaddingComboBox");
        robot.clickOn("NoPadding");

        /* write input */
        robot.clickOn("#TDESInputTextArea").write("1a4d672dca6cb335");

        /* write ICV */
        robot.clickOn("#TDESICVTextField").write("0123456789ABCDEF");

        /* write key */
        robot.clickOn("#TDESKeyTextField").write("0123456789ABCDEFFEDCBA9876543210");

        /* click on Encrypt button */
        robot.clickOn("#TDESDecryptButton");

        /* verify if decryption was successful */
        FxAssert.verifyThat("#TDESOutputTextArea", TextInputControlMatchers.hasText("0000000000000000"));
    }

    @Test
    void TDESCBCPKCS5Decrypt(FxRobot robot) {
        /* select TDES algorithm */
        robot.clickOn("TDES");

        /* select ECB Mode of Operation */
        robot.clickOn("#TDESModeOfOperationComboBox");
        robot.clickOn("CBC");

        /* select noPadding */
        robot.clickOn("#TDESPaddingComboBox");
        robot.clickOn("PKCS5Padding");

        /* write input */
        robot.clickOn("#TDESInputTextArea").write("1a4d672dca6cb335fce5219cefe9c3ea");

        /* write ICV */
        robot.clickOn("#TDESICVTextField").write("0123456789ABCDEF");

        /* write key */
        robot.clickOn("#TDESKeyTextField").write("0123456789ABCDEFFEDCBA9876543210");

        /* click on Encrypt button */
        robot.clickOn("#TDESDecryptButton");

        /* verify if decryption was successful */
        FxAssert.verifyThat("#TDESOutputTextArea", TextInputControlMatchers.hasText("0000000000000000"));
    }

    @Test
    void TDESCFB8EncryptNoPadding(FxRobot robot) {
        /* select TDES algorithm */
        robot.clickOn("TDES");

        /* select ECB Mode of Operation */
        robot.clickOn("#TDESModeOfOperationComboBox");
        robot.clickOn("CFB8");

        /* select noPadding */
        robot.clickOn("#TDESPaddingComboBox");
        robot.clickOn("NoPadding");

        /* write input */
        robot.clickOn("#TDESInputTextArea").write("0000000000000000");

        /* write ICV */
        robot.clickOn("#TDESICVTextField").write("0123456789ABCDEF");

        /* write key */
        robot.clickOn("#TDESKeyTextField").write("0123456789ABCDEFFEDCBA9876543210");

        /* click on Encrypt button */
        robot.clickOn("#TDESEncryptButton");

        /* verify if encryption was successful */
        FxAssert.verifyThat("#TDESOutputTextArea", TextInputControlMatchers.hasText("1a7e75e736a340fe"));
    }

    @Test
    void TDESCFB8PKCS5Encrypt(FxRobot robot) {
        /* select TDES algorithm */
        robot.clickOn("TDES");

        /* select ECB Mode of Operation */
        robot.clickOn("#TDESModeOfOperationComboBox");
        robot.clickOn("CFB8");

        /* select noPadding */
        robot.clickOn("#TDESPaddingComboBox");
        robot.clickOn("PKCS5Padding");

        /* write input */
        robot.clickOn("#TDESInputTextArea").write("0000000000000000");

        /* write ICV */
        robot.clickOn("#TDESICVTextField").write("0123456789ABCDEF");

        /* write key */
        robot.clickOn("#TDESKeyTextField").write("0123456789ABCDEFFEDCBA9876543210");

        /* click on Encrypt button */
        robot.clickOn("#TDESEncryptButton");

        /* verify if encryption was successful */
        FxAssert.verifyThat("#TDESOutputTextArea", TextInputControlMatchers.hasText("1a7e75e736a340fe3866267ebdfb4f4c"));
    }

    @Test
    void TDESCFB8DecryptNoPadding(FxRobot robot) {
        /* select TDES algorithm */
        robot.clickOn("TDES");

        /* select ECB Mode of Operation */
        robot.clickOn("#TDESModeOfOperationComboBox");
        robot.clickOn("CFB8");

        /* select noPadding */
        robot.clickOn("#TDESPaddingComboBox");
        robot.clickOn("NoPadding");

        /* write input */
        robot.clickOn("#TDESInputTextArea").write("1a7e75e736a340fe");

        /* write ICV */
        robot.clickOn("#TDESICVTextField").write("0123456789ABCDEF");

        /* write key */
        robot.clickOn("#TDESKeyTextField").write("0123456789ABCDEFFEDCBA9876543210");

        /* click on Decrypt button */
        robot.clickOn("#TDESDecryptButton");

        /* verify if decryption was successful */
        FxAssert.verifyThat("#TDESOutputTextArea", TextInputControlMatchers.hasText("0000000000000000"));
    }

    @Test
    void TDESCFB8PKCS5Decrypt(FxRobot robot) {
        /* select TDES algorithm */
        robot.clickOn("TDES");

        /* select ECB Mode of Operation */
        robot.clickOn("#TDESModeOfOperationComboBox");
        robot.clickOn("CFB8");

        /* select noPadding */
        robot.clickOn("#TDESPaddingComboBox");
        robot.clickOn("PKCS5Padding");

        /* write input */
        robot.clickOn("#TDESInputTextArea").write("1a7e75e736a340fe3866267ebdfb4f4c");

        /* write ICV */
        robot.clickOn("#TDESICVTextField").write("0123456789ABCDEF");

        /* write key */
        robot.clickOn("#TDESKeyTextField").write("0123456789ABCDEFFEDCBA9876543210");

        /* click on Decrypt button */
        robot.clickOn("#TDESDecryptButton");

        /* verify if decryption was successful */
        FxAssert.verifyThat("#TDESOutputTextArea", TextInputControlMatchers.hasText("0000000000000000"));
    }

    @Test
    void TDESOFB8EncryptNoPadding(FxRobot robot) {
        /* select TDES algorithm */
        robot.clickOn("TDES");

        /* select ECB Mode of Operation */
        robot.clickOn("#TDESModeOfOperationComboBox");
        robot.clickOn("OFB8");

        /* select noPadding */
        robot.clickOn("#TDESPaddingComboBox");
        robot.clickOn("NoPadding");

        /* write input */
        robot.clickOn("#TDESInputTextArea").write("0000000000000000");

        /* write ICV */
        robot.clickOn("#TDESICVTextField").write("0123456789ABCDEF");

        /* write key */
        robot.clickOn("#TDESKeyTextField").write("0123456789ABCDEFFEDCBA9876543210");

        /* click on Encrypt button */
        robot.clickOn("#TDESEncryptButton");

        /* verify if encryption was successful */
        FxAssert.verifyThat("#TDESOutputTextArea", TextInputControlMatchers.hasText("1a7e75e736a340fe"));
    }

    @Test
    void TDESOFB8PKCS5Encrypt(FxRobot robot) {
        /* select TDES algorithm */
        robot.clickOn("TDES");

        /* select ECB Mode of Operation */
        robot.clickOn("#TDESModeOfOperationComboBox");
        robot.clickOn("OFB8");

        /* select noPadding */
        robot.clickOn("#TDESPaddingComboBox");
        robot.clickOn("PKCS5Padding");

        /* write input */
        robot.clickOn("#TDESInputTextArea").write("0000000000000000");

        /* write ICV */
        robot.clickOn("#TDESICVTextField").write("0123456789ABCDEF");

        /* write key */
        robot.clickOn("#TDESKeyTextField").write("0123456789ABCDEFFEDCBA9876543210");

        /* click on Encrypt button */
        robot.clickOn("#TDESEncryptButton");

        /* verify if encryption was successful */
        FxAssert.verifyThat("#TDESOutputTextArea", TextInputControlMatchers.hasText("1a7e75e736a340fe38a7afab1f25d8b4"));
    }

    @Test
    void TDESOFB8DecryptNoPadding(FxRobot robot) {
        /* select TDES algorithm */
        robot.clickOn("TDES");

        /* select ECB Mode of Operation */
        robot.clickOn("#TDESModeOfOperationComboBox");
        robot.clickOn("OFB8");

        /* select noPadding */
        robot.clickOn("#TDESPaddingComboBox");
        robot.clickOn("NoPadding");

        /* write input */
        robot.clickOn("#TDESInputTextArea").write("1a7e75e736a340fe");

        /* write ICV */
        robot.clickOn("#TDESICVTextField").write("0123456789ABCDEF");

        /* write key */
        robot.clickOn("#TDESKeyTextField").write("0123456789ABCDEFFEDCBA9876543210");

        /* click on Decrypt button */
        robot.clickOn("#TDESDecryptButton");

        /* verify if decryption was successful */
        FxAssert.verifyThat("#TDESOutputTextArea", TextInputControlMatchers.hasText("0000000000000000"));
    }

    @Test
    void TDESOFB8PKCS5Decrypt(FxRobot robot) {
        /* select TDES algorithm */
        robot.clickOn("TDES");

        /* select ECB Mode of Operation */
        robot.clickOn("#TDESModeOfOperationComboBox");
        robot.clickOn("OFB8");

        /* select noPadding */
        robot.clickOn("#TDESPaddingComboBox");
        robot.clickOn("PKCS5Padding");

        /* write input */
        robot.clickOn("#TDESInputTextArea").write("1a7e75e736a340fe38a7afab1f25d8b4");

        /* write ICV */
        robot.clickOn("#TDESICVTextField").write("0123456789ABCDEF");

        /* write key */
        robot.clickOn("#TDESKeyTextField").write("0123456789ABCDEFFEDCBA9876543210");

        /* click on Decrypt button */
        robot.clickOn("#TDESDecryptButton");

        /* verify if decryption was successful */
        FxAssert.verifyThat("#TDESOutputTextArea", TextInputControlMatchers.hasText("0000000000000000"));
    }
}