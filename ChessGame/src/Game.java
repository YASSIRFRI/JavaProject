import java.io.Console;

import javax.sound.midi.SysexMessage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

enum GameStatus {
    ACTIVE,
    BLACK_WIN,
    WHITE_WIN,
    FORFEIT,
    STALEMATE,
    RESIGNATION
}   

public class Game extends Application{

    private GameStatus status; 
    private GameBoard gameBoard;
    private Move[] moveHistory;
    private Player[] players;


    public GameStatus getStatus()
    {
        return status;
    }
  
    public void setStatus(GameStatus status)
    {
        this.status = status;
    }
   


    public Move[] getMoveHistory() {
        return moveHistory;
    }
    public void setMoveHistory(Move[] moveHistory) {
        this.moveHistory = moveHistory;
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
        Scene scene = new Scene(chessBoard,700,700);
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }

    
}
