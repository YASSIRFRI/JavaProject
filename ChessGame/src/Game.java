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
    
    public static double windowWidth = 700.0;
    // Getters and setters
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
	public static void enemysMoves(GameBoard board) {
		ArrayList<Square> allEnemysMove = new ArrayList<Square>();
        ArrayList<Piece> AllPieces = new ArrayList<Piece>();
		for (Piece p : AllPieces) {
			if (p.getIsWhite() != true) {
                allEnemysMove.add(p.getLocation());
			}
		}
	}
    public void updatingStatus(ChessBoard chessBoard ){
        ArrayList<Piece> wpieces = chessBoard.getWhitePieces();
        ArrayList<Piece> bpieces = chessBoard.getBlackPieces();
        for (Piece p : wpieces) {
            if (p.getValidMoves(chessBoard).isEmpty()) {
                status = GameStatus.BLACK_WIN;
            }
        for (Piece pi : bpieces) {
            if (pi.getValidMoves(chessBoard).isEmpty()) {
                    status = GameStatus.WHITE_WIN;
            }

                         
    
                    
                
                    
        }

    }
}

            
    @Override
    public void start(Stage primaryStage){
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.fillBoard();
        chessBoard.setOnMouseClicked(chessBoard);
        Scene scene = new Scene(chessBoard, windowWidth+4, windowWidth+4);
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }

    
}
