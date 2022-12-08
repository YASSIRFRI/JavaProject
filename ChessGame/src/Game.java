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
    RESIGNATION,
    CHECKMATE,
    CHECK
}   

public class Game extends Application{

    private GameStatus status; 
    private GameBoard gameBoard;
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
        ArrayList<Piece> AllPieces = new  ArrayList<Piece>();
        for (Piece p : wpieces) {
            if (p.getValidMoves(chessBoard).isEmpty()) {
                status = GameStatus.BLACK_WIN;
                System.out.println(" black is the winner");

            }
        for (Piece pi : bpieces) {
            if (pi.getValidMoves(chessBoard).isEmpty()) {
                    status = GameStatus.WHITE_WIN;
                    System.out.println(" white is the winner");

            }
        for (Piece pe : AllPieces) {
            if (pe.isHasMoved() == true) {
                status = GameStatus.ACTIVE;



                }
        
            }


                         
    
                    
                
                    
        }

    }
}
    public boolean play(){
        Move move ;
        ChessBoard chessBoard;
        if( move != null) {
            if(move.getStatus() == MoveStatus.VALID) {
                updatingStatus(chessBoard);
                return true;
            }
            else
                System.out.println(" entered an illegal move, try again please");
        }
        else
            System.out.println( " entered an illegal move format, try again please");

        return false;
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

    
    public void start1(Stage primaryStage){
        CheckersBoard checkersBoard = new CheckersBoard(8);
        checkersBoard.fillBoard();
        Scene scene = new Scene(checkersBoard);
        primaryStage.setTitle("Checkers app");
        primaryStage.setScene(scene);
        primaryStage.show();
        

        
    }
    public static void main(String[] args) {
        launch(args);
    }

    
}
