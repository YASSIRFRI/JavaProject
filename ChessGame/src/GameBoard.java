public abstract class GameBoard {

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
                if (i == 0 || i == 1) {
                    this.board[i][j].setPiece(new Piece("Pawn", "White"));
                } else if (i == this.size - 1 || i == this.size - 2) {
                    this.board[i][j].setPiece(new Piece("Pawn", "Black"));
                }
            }
        }
    }
}
