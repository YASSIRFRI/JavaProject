import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Controller implements Initializable{

    @FXML
    private ChoiceBox<String> gametype;

    @FXML
    public Button start;

    public boolean state=false;
    public String[] info;

    @FXML
    private TextField player1;
    @FXML
    private TextField player2;

    @FXML
    ColorPicker color1;
    @FXML 
    ColorPicker color2;
    @FXML
    private TextField time;
    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {
        gametype.getItems().addAll("Chess", "Checkers");
        gametype.setValue(null);
    }



    public String[] startGame(){
        String[] info = new String[2];
        info[0] = gametype.getValue();
        return info; 
    }

    public Color[] getColors(){
        Color[] colors = new Color[2];
        colors[0] = color1.getValue();
        colors[1] = color2.getValue();
        return colors;
    }

    public String getGameType(){
        return gametype.getValue();
    }
    public  String[] getPlayers(){
        String[] players = new String[2];
        players[0] = player1.getText();
        players[1] = player2.getText();
        return players;
    }
    public String getTime()
    {
        return time.getText();
    }




}
