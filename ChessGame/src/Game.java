import java.io.Console;
import java.util.ArrayList;

import javax.sound.midi.SysexMessage;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
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
    
    public static double windowWidth = 400.0;
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

    @Override
    public void start(Stage primaryStage){
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.fillBoard();
        chessBoard.setOnMouseClicked(chessBoard);
        Scene scene = new Scene(chessBoard, windowWidth+400, windowWidth+4);
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }


    public static void main(String[] args) {
        launch(args);
    }

    
}
