import java.awt.*;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public abstract class GameBoard extends GridPane {

    protected Square[][] board;

    protected int size;
    public GameBoard(int size) {
        super();
        this.size = size;
        this.board = new Square[size][size];
        int count = 0;

        for (int i = 0; i < size; i++) {

            // Horizontal alignment of images
            ColumnConstraints col = new ColumnConstraints();
            col.setHalignment(HPos.CENTER);
            this.getColumnConstraints().add(col);

            // Vertical alignment of images
            RowConstraints row = new RowConstraints();
            row.setValignment(VPos.CENTER);
            this.getRowConstraints().add(row);

            count++;
            for (int j = 0; j < size; j++) {
                if (count % 2 == 0)
                    board[i][j] = new Square(i, j, Color.BLUE);
                else
                    board[i][j] = new Square(i, j, Color.DEEPSKYBLUE);

//                board[i][j].setStrokeWidth(0.5); //
//                board[i][j].setStroke(Color.BLACK); //

                this.add(board[i][j], i, j);
                count++;
            }
        }
    }
    public abstract void fillBoard();
}


class ChessBoard extends GameBoard implements  EventHandler<javafx.scene.input.MouseEvent> {

    ChessBoard() {
        super(8);
    }

    @Override
    public void fillBoard() {
        for (int i = 0; i < 8; i++) {
            board[i][1].setPlaceholder(new Pawn(true, board[i][1]));
            board[i][6].setPlaceholder(new Pawn(false, board[i][6]));
        }

        board[0][0].setPlaceholder(new Rook(true, board[0][0]));
        board[0][7].setPlaceholder(new Rook(false, board[0][7]));
        board[7][7].setPlaceholder(new Rook(false, board[7][7]));
        board[1][0].setPlaceholder(new Knight(true, board[1][0]));
        board[6][0].setPlaceholder(new Knight(true, board[6][0]));
        board[1][7].setPlaceholder(new Knight(false, board[1][7]));
        board[6][7].setPlaceholder(new Knight(false, board[6][7]));
        board[2][0].setPlaceholder(new Bishop(true, board[2][0]));
        board[5][0].setPlaceholder(new Bishop(true, board[5][0]));
        board[2][7].setPlaceholder(new Bishop(false, board[2][7]));
        board[5][7].setPlaceholder(new Bishop(false, board[5][7]));
        board[3][0].setPlaceholder(new Queen(true, board[3][0]));
        board[3][7].setPlaceholder(new Queen(false, board[3][7]));
        board[4][0].setPlaceholder(new King(true, board[4][0]));
        board[7][7].setPlaceholder(new Rook(false, board[7][7]));
        board[7][0].setPlaceholder(new Rook(true, board[7][0]));
        board[4][7].setPlaceholder(new King(false, board[4][7]));

        for (int i=0;i<8;i++) {
            for (int j=0;j<8;j++) {
                if(board[i][j].getPlaceholder()!=null) {
                    this.add(board[i][j].getPlaceholder().getImage(), i, j);
                }
            }
        }

        this.setGridLinesVisible(true);
        this.setAlignment(Pos.CENTER);
        
    }
    public void handle(javafx.scene.input.MouseEvent event) {
        EventTarget target = event.getTarget();
        if (target instanceof Square) {
            Square square = (Square) target;
            if(square.getPlaceholder()!=null)
            {
                ArrayList<Square> moves = square.getPlaceholder().getValidMoves(board);
                for(Square s: moves)
                {
                    s.setFill(Color.GREEN);
                }
            }

        }
    }

}

//class CheckersBoard extends GameBoard{
//    public CheckersBoard(int size){
//        super(size);
//    }
//
//    @Override
//    void fillBoard(){
//
//        return
//    }
//
//
//}
