import javafx.css.Size;

enum MoveStatus
{
    VALID,INVALID,COMMITED,CHECK,CHECKMATE;
}

class Move {
    private Square sourceSquare;
    private Square destinationSquare;
    private Piece piece;
    private MoveStatus status;
    private Game game;

    public Move(Square sourceSquare, Square destinationSquare, Piece piece, MoveStatus status, Game game) {
        this.sourceSquare = sourceSquare;
        this.destinationSquare = destinationSquare;
        this.piece = piece;
        this.status = status;
        this.game = game;
    }

    public Move(Square sourceSquare, Square destinationSquare, Piece piece) {
        this.sourceSquare = sourceSquare;
        this.destinationSquare = destinationSquare;
        this.piece = piece;
    }
    
    // Getters and setters
    public Square getSourceSquare() {
        return sourceSquare;
    }

    public Square getDestinationSquare() {
        return destinationSquare;
    }

    public Piece getPiece() {
        return piece;
    }
    public MoveStatus getStatus() {
        return this.status;
    }
    public Game getGame() {
        return game;
    }

    public void setSource(Square sourceSquare) {
        this.sourceSquare = sourceSquare;
    }

    public void setDestination(Square destinationSquare) {
        this.destinationSquare = destinationSquare;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setStatus(MoveStatus status) {
        this.status = status;
    }

    public void setGame(Game game) { this.game = game; }

    public boolean begin(){

        return status == MoveStatus.VALID;


    }
    public boolean equals(Move anotherMove) {
		if(this.getSourceSquare() == anotherMove.getSourceSquare() && this.getDestinationSquare() == anotherMove.getDestinationSquare() ){
			return true;
		}
		return false;
	}
    public void updateStatus(Move move, ChessBoard chessBoard){
//        Square[][] board = new Square[8][8];
        if(piece.validateMove(move, chessBoard))
            status = MoveStatus.VALID;
        else
    
            status = MoveStatus.INVALID;
    }
    
}