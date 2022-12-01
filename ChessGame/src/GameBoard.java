import javafx.scene.layout.GridPane;

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
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
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
