import java.util.ArrayList;
import java.io.Console;

import javax.sound.midi.SysexMessage;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class CheckersBoard extends GameBoard{

    private ArrayList<CheckersPawn> whitePieces;
    private ArrayList<CheckersPawn> blackPieces;
    public CheckersBoard(){
        super(8);
    }
    public ArrayList<CheckersPawn> getWhitePieces() {
        return whitePieces;
    }

    public void setWhitePieces(ArrayList<CheckersPawn> whitePieces) {
        this.whitePieces = whitePieces;
    }

    public ArrayList<CheckersPawn> getBlackPieces() {
        return blackPieces;
    }

    public void setBlackPieces(ArrayList<CheckersPawn> blackPieces) {
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
    public CheckersPawn makeCheckersPawn(boolean isWhite, Square location){
        CheckersPawn checkersPawn = new CheckersPawn(isWhite,location);
        return checkersPawn; 

    }


 
    @Override
    public void fillBoard(){
        for (int i = 0; i < 8; i++) {
            for (int j= 0; j<8; j++)
            {
                CheckersPawn checkersPawn = null;

                if (i <= 2 && (i+j) % 2 != 0){
                    //board[i][j].setPlaceholder(new CheckersPawn(true,board[i][j]));
                    checkersPawn = makeCheckersPawn(true, board[i][j]);



                }
                if (j >= 5 && (i+j) % 2 != 0){
                    //board[i][j].setPlaceholder(new CheckersPawn(false,board[i][j]));
                    checkersPawn = makeCheckersPawn(false, board[i][j]);

                    


                }

            }

        }
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                if (board[i][j].getPlaceholder() != null) {
                    this.add(board[i][j].getPlaceholder().getImage(), i, j);

            }
        }

        
    }


    
}
