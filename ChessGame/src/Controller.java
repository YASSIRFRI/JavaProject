import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class Controller implements Initializable{

    @FXML
    private Label label;

    @FXML
    private ChoiceBox<String> gametype;

    public String[] games={"Chess","Checkers"};


    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
        gametype.getItems().addAll(games);
        gametype.setOnAction(this::getSelectedGame);
    }
    public String getSelectedGame(ActionEvent event){
        System.out.println(gametype.getValue());
        return gametype.getValue();

    }
    

    
}
