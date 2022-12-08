import java.util.ArrayList;
import java.io.Console;

import javax.sound.midi.SysexMessage;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class CheckersBoard extends GameBoard{

    private ArrayList<Piece> whitePieces;
    private ArrayList<Piece> blackPieces;
    public CheckersBoard(){
        super(8);
    }
    public ArrayList<Piece> getWhitePieces() {
        return whitePieces;
    }

    public void setWhitePieces(ArrayList<Piece> whitePieces) {
        this.whitePieces = whitePieces;
    }

    public ArrayList<Piece> getBlackPieces() {
        return blackPieces;
    }

    public void setBlackPieces(ArrayList<Piece> blackPieces) {
        this.blackPieces = blackPieces;
    }
    
    @Override
    public void start(Stage primaryStage){
        CheckersBoard checkersBoard = new CheckersBoard();
        checkersBoard.fillBoard();
        Scene scene = new Scene(checkersBoard);
        primaryStage.setTitle("Checkers app");
        primaryStage.setScene(scene);
        primaryStage.show();
        

        
    }


 
    @Override
    public void fillBoard(){
        for (int i = 0; i < 8; i++) {
            for (int j= 0; j<8; j++)
            {
                if (i <= 2 && (i+j) % 2 != 0){
                    board[i][j].setPlaceholder(new CheckersPawn(true,board[i][j]));



                }
                if (j >= 5 && (i+j) % 2 != 0){
                    board[i][j].setPlaceholder(new CheckersPawn(false,board[i][j]));
                    


                }

            }

        }
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                if (board[i][j].getPlaceholder() != null) {
                    this.add(board[i][j].getPlaceholder().getImage(), i, j);

                    if (board[i][j].getPlaceholder().getIsWhite())
                        this.whitePieces.add(board[i][j].getPlaceholder());
                    else
                        this.blackPieces.add(board[i][j].getPlaceholder());
                }
            }
        }

        
    }

    
}
