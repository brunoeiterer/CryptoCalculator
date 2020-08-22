import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("DES.fxml"));

            Scene scene = new Scene(root);
            stage.setTitle("CryptoCalculator");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}