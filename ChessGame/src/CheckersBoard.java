
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



    @Override
    public void fillBoard(){
        for (int i = 0; i < 8; i++) {
            for (int j= 0; j<8; j++)
            {
                if (i <= 2 && (i+j) % 2 != 0){
                    board[i][j].setPlaceHolder(new CheckersPawn(true, board[i][j]));
                    
                }
                if (j >= 5 && (i+j) % 2 != 0){
                    board[i][j].setPlaceHolder(new CheckersPawn(false, board[i][j]));

                }
                if (board[i][j].hasPiece() ) {
                    board[i][j].setPlaceHolder(null);
                    this.add(board[i][j].getPlaceHolder().getImage(), i, j);

            }

            }

        }
       

  

        


    


}
    public moveType doMove( CheckersPawn checkersPawn,Square s, Square y) {
        //Move move = new Move(s, y, checkersPawn);
        if (board[y.getx()][y.gety()].hasPiece() || (y.getx()+ y.gety())% 2 ==0){
            //move.setType(moveType.NONE);
            //move.setCheckersPawn(board[y.getx()][y.gety()].getPlaceHolder());
            return moveType.NONE;


 
        }
        return null;

    }
}



