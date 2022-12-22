import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;


import javax.sound.midi.SysexMessage;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

enum GameStatus {
    ACTIVE,
    STALEMATE,
    CHECKMATE,
    CHECK
}   

public class Game extends Application{

    private GameStatus status; 
    private GameBoard gameBoard;
    private Player[] players;

    public static double windowWidth = 600.0;
    // Getters and setters
    public GameStatus getStatus()
    {
        return status;
    }
  
    public void setStatus(GameStatus status)
    {
        this.status = status;
    }
   

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }
    public void startGame(MouseEvent event,Color[] colors,String game,String time,String[] players) throws IOException{
        if(game=="Chess" && players[0]!="" && players[1]!=""){
            int timeInt = 5;
            if(time==""){
                return;
            }
            else{
                timeInt = Integer.parseInt(time);
                if(timeInt<0){
                    return;
                }
            }
            ChessBoard chessBoard = new ChessBoard(colors,timeInt);
            chessBoard.fillBoard();
            chessBoard.setWhitePlayer(players[0]);
            chessBoard.setWhitePlayer(players[1]);
            chessBoard.setOnMouseClicked(chessBoard);
            Scene scene = new Scene(chessBoard, windowWidth+400, windowWidth+20);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        else if(game=="Checkers"){
            CheckersBoard checkersBoard = new CheckersBoard(colors,2);
            checkersBoard.fillBoard();
            checkersBoard.setOnMouseClicked(checkersBoard);
            Scene scene = new Scene(checkersBoard, windowWidth+400, windowWidth+20);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("menu.fxml"));
        Controller controller = new Controller();
        loader.setController(controller);
        Scene scene1 = new Scene(loader.load());
        primaryStage.setScene(scene1);
        primaryStage.setTitle("ChessGame");
        primaryStage.setResizable(false);
        primaryStage.show();
        controller.start.addEventHandler(MouseEvent.MOUSE_CLICKED, arg0 -> {
            try {
                startGame(arg0,controller.getColors(),controller.getGameType(),controller.getTime(),controller.getPlayers());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    
}
