import java.io.Console;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public abstract class GameBoard extends GridPane {

    protected Square[][] board;

    protected int size;
    public GameBoard(int size) {
        super();
        this.size = size;
        this.board = new Square[size][size];
        int count = 0;
        for (int i = 0; i < size; i++) {
            count++;
            for (int j = 0; j < size; j++) {
                if (count % 2 == 0)
                    board[i][j] = new Square(i, j, Color.BLACK);
                else
                    board[i][j] = new Square(i, j, Color.WHITE);
                this.add(board[i][j], i, j);
                System.out.println(board[i][j].getFill());
                System.out.println(board[i][j]);
                count++;
            }
        }
        System.out.println(this);
    }
    public abstract void fillBoard();
}


class ChessBoard extends GameBoard {

    ChessBoard() {
        super(8);
    }

    @Override
    public void fillBoard() {
        for (int i = 0; i < 8; i++) {
            board[1][i].setPlaceholder(new Pawn(true));
            board[6][i].setPlaceholder(new Pawn(false));
        }
        board[0][0].setPlaceholder(new Rook(true));
        board[0][7].setPlaceholder(new Rook(true));
        board[7][0].setPlaceholder(new Rook(false));
        board[7][7].setPlaceholder(new Rook(false));
        board[0][1].setPlaceholder(new Knight(true));
        board[0][6].setPlaceholder(new Knight(true));
        board[7][1].setPlaceholder(new Knight(false));
        board[7][6].setPlaceholder(new Knight(false));
        board[0][2].setPlaceholder(new Bishop(true));
        board[0][5].setPlaceholder(new Bishop(true));
        board[7][2].setPlaceholder(new Bishop(false));
        board[7][5].setPlaceholder(new Bishop(false));
        board[0][3].setPlaceholder(new Queen(true));
        board[0][4].setPlaceholder(new King(true));
        board[7][3].setPlaceholder(new Queen(false));
        board[7][4].setPlaceholder(new King(false));
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(board[i][j].getPlaceholder()!=null)
                {
                    this.add(board[i][j].getPlaceholder().getImage(), i, j);
                }
            }





            
            
          }
        
    }
}

class CheckersBoard extends GameBoard{
    public CheckersBoard(int size){
        super(size);
    }

    @Override
    void fillBoard(){

        return
    }


}
