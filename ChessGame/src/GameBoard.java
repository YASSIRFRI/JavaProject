import java.awt.*;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

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


class ChessBoard extends GameBoard implements  EventHandler<MouseEvent> {

    private ArrayList<Square> highlightedSquares;
    private Piece blackKing;
    private Piece whiteKing;

//    public static Square triggerer;

    ChessBoard() {
        super(8);
        highlightedSquares = new ArrayList<Square>();
    }

    public Piece getBlackKing() {
        return blackKing;
    }

    public void setBlackKing(Piece blackKing) {
        this.blackKing = blackKing;
    }

    public Piece getWhiteKing() {
        return whiteKing;
    }

    public void setWhiteKing(Piece whiteKing) {
        this.whiteKing = whiteKing;
    }

    @Override
    public void fillBoard() {
        for (int i = 0; i < 8; i++) {
            board[i][1].setPlaceholder(new Pawn(true, board[i][1]));
            board[i][6].setPlaceholder(new Pawn(false, board[i][6]));
        }

        board[0][0].setPlaceholder(new Rook(true, board[0][0]));
        board[0][7].setPlaceholder(new Rook(false, board[0][7]));
        board[1][0].setPlaceholder(new Knight(true, board[1][0]));
        board[6][0].setPlaceholder(new Knight(true, board[6][0]));
        board[1][7].setPlaceholder(new Knight(false, board[1][7]));
        board[6][7].setPlaceholder(new Knight(false, board[6][7]));
        board[2][0].setPlaceholder(new Bishop(true, board[2][0]));
        board[5][0].setPlaceholder(new Bishop(true, board[5][0]));
        board[2][7].setPlaceholder(new Bishop(false, board[2][7]));
        board[5][7].setPlaceholder(new Bishop(false, board[5][7]));
        board[3][0].setPlaceholder(new King(true, board[3][0]));
        board[3][7].setPlaceholder(new King(false, board[3][7]));
        board[4][0].setPlaceholder(new Queen(true, board[4][0]));
        board[4][7].setPlaceholder(new Queen(false, board[4][7]));
        board[7][7].setPlaceholder(new Rook(false, board[7][7]));
        board[7][0].setPlaceholder(new Rook(true, board[7][0]));

        this.setBlackKing(board[3][7].getPlaceholder());
        this.setWhiteKing(board[3][0].getPlaceholder());

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

    public void removeHighlights() {

        for (Square s: highlightedSquares) {
            if ((s.getx() + s.gety()) % 2 != 0)
                s.setFill(Color.BLUE);
            else
                s.setFill(Color.DEEPSKYBLUE);
        }

        this.highlightedSquares.clear();
    }

    public void handle(MouseEvent event) {
        EventTarget target = event.getTarget();
//        if (target instanceof Square) {
//            Square square = (Square) target;
//            if(square.getPlaceholder()==null){System.out.println("null");}
//            if(square.getPlaceholder()!=null)
//            {
//                if(triggerer==null)
//                {
//                    triggerer = square;
//                    ArrayList<Square> moves = square.getPlaceholder().getValidMoves(board);
//                    for (Square s: moves) {
//                        highlightedSquares.add(s);
//                        s.setFill(Color.GREEN);
//                    return;
//                }
//                }
//                else
//                {
//                    System.out.println(triggerer.getPlaceholder().getClass().getName());
//                    square.setPlaceholder(triggerer.getPlaceholder());
//                    this.getChildren().remove(triggerer.getPlaceholder().getImage());
//                    this.add(triggerer.getPlaceholder().getImage(), square.getx(), square.gety());
//                    board[triggerer.getx()][triggerer.gety()].setPlaceholder(null);
//                    board[square.getx()][square.gety()].setPlaceholder(square.getPlaceholder());
//                    triggerer = null;
//                }
//                this.removeHighlights();
//
//
//            }
//            else
//            {
//                if(triggerer!=null)
//                {
//                    System.out.println(triggerer.getx()+" "+triggerer.gety());
//                    System.out.println(triggerer.getPlaceholder().getClass().getName());
//                    this.getChildren().remove(triggerer.getPlaceholder().getImage());
//                    this.add(triggerer.getPlaceholder().getImage(), square.getx(), square.gety());
//                    board[square.getx()][square.gety()].setPlaceholder(triggerer.getPlaceholder());
//                    board[triggerer.getx()][triggerer.gety()].setPlaceholder(null);
//                    triggerer=null;
//                }
//                this.removeHighlights();
//            }
//
//        }

        Square clickedSquare = null;

        if (target instanceof Square) {
            clickedSquare = (Square) target;
        }

        else if (target instanceof ImageView) {
            ImageView image = (ImageView) target;
            int x = GridPane.getColumnIndex(image);
            int y = GridPane.getRowIndex(image);
            clickedSquare = board[x][y];
        }

        if (clickedSquare != null) {

            if(clickedSquare.getPlaceholder()!=null)
            {
                this.removeHighlights();

                ArrayList<Square> moves = clickedSquare.getPlaceholder().getValidMoves(board);
                for (Square s: moves) {
                    highlightedSquares.add(s);
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
