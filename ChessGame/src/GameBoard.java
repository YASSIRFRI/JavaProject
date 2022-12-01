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
    abstract void fillBoard();
}


class ChessBoard extends GameBoard {

    ChessBoard() {
        super(8);
    }

    @Override
    void fillBoard() {
        for (int i = 0; i < 8; i++) {
            
            
          }
        
    }
}

class CheckersBoard extends GameBoard{
    public CheckersBoard(int size){
        super(size);
    }

    @Override
    void fillBoard(){
        


    }


}
