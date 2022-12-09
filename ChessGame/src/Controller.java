import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;

public class Controller implements Initializable{

    @FXML
    private ChoiceBox<String> gametype;

    @FXML
    public Button start;

    public boolean state=false;
    public String[] info;

    @FXML
    private RadioButton single;


    @FXML
    private RadioButton two;

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {
        gametype.getItems().addAll("Chess", "Checkers");
        gametype.setValue(null);
    }

    public String[] startGame(){
        String[] info = new String[2];
        info[0] = gametype.getValue();
        if(single.isSelected()){
            info[1] = "single";
        }
        else if(two.isSelected()){
            info[1] = "two";
        }
        return info; 
    }



}
