import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;

public class Controller implements Initializable{

    @FXML
    private ChoiceBox<String> gametype;

    @FXML
    private Button start;


    @FXML
    private RadioButton single;


    @FXML
    private RadioButton two;

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {
        gametype.getItems().addAll("Chess", "Checkers");
        gametype.setValue("Chess");
    }
    @FXML
    public String getSelectedGame(ActionEvent event){
        System.out.println(gametype.getValue());
        return gametype.getValue();
    }



    @FXML
    public void printValue(ActionEvent event){
        System.out.println(gametype.getValue());
    }

    @FXML
    public String[] getInfo()
    {
        String[] info = new String[2];
        info[0] = gametype.getValue();
        if(single.isSelected())
        {
            info[1] = "single";
        }
        else if(two.isSelected())
        {
            info[1] = "two";
        }
        return info;
    }
}
