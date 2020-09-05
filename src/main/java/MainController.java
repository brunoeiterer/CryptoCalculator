import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class MainController{

    @FXML
    private ListView<String> algorithmsListView;
    @FXML
    private ScrollPane calculatorScrollPane;

    @FXML
    public void initialize(){
        algorithmsListView.getItems().add("DES");
        algorithmsListView.getItems().add("TDES");

        algorithmsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateCalculatorPane(newValue);
            }
        });
    }
    
    private void updateCalculatorPane(String newPane){
        this.calculatorScrollPane.setContent(null);
        try{
            this.calculatorScrollPane.setContent(FXMLLoader.load(getClass().getResource(newPane + ".fxml")));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        
    }
}