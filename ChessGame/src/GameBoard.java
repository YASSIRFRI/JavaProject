import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class GameBoard extends GridPane {

    protected Square[][] board;

    protected int size;
    public GameBoard(int size) {
        this.size = size;
        this.board = new Square[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = new Square(i, j);
            }
        }
    }
    abstract void fillBoard();
}


class ChessBoard extends GameBoard {

    public ChessBoard(int size) {
        super(size);
        
    }

    @Override
    void fillBoard() {
        int count;
        for (int i = 0; i < 8; i++) {
            count++;
            for (int j = 0; j < 8; j++) {
              Rectangle r = new Rectangle(s, s, s, s);
              if (count % 2 == 0)
                r.setFill(Color.BLACK);
              else 
                r.setFill(Color.WHITE);
              pane.add(r, j, i);
              count++;
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
        


    }


}
