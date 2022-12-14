
import java.util.ArrayList;
import java.io.Console;

import javax.sound.midi.SysexMessage;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class CheckersBoard extends GameBoard implements EventHandler<MouseEvent>{
    private ArrayList<CheckersPawn> whitePieces;
    private ArrayList<CheckersPawn> blackPieces;

    public CheckersBoard(){
        super(8, null,null );
    }

    public CheckersBoard(Color[] colors)
    {
        super(8, colors[0], colors[1]);
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
    public void fillBoard(){
        for (int i = 0; i < 8; i++) {
            for (int j= 0; j<8; j++)
            {
                if (j <= 2 && (i+j) % 2 != 0){
                    board[i][j].setPlaceholder(new CheckersPawn(true, board[i][j]));

                }
                if (j >= 5 && (i+j) % 2 != 0){
                    board[i][j].setPlaceholder(new CheckersPawn(false, board[i][j]));

                }
                if (board[i][j].getPlaceholder() != null ) {
                    this.add(board[i][j].getPlaceholder().getImage(), i, j);
                }

            }

            }
        }
    public Square getSquare(int x, int y){
        return board[x][y];
    }
    public void handle(MouseEvent event) {
        // TODO Auto-generated method stub

    }
}



