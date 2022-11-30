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

    public Square getSourceSquare() {
        return sourceSquare;
    }

    public Square getDestinationSquare() {
        return destinationSquare;
    }

    public Piece getPiece() {
        return piece;
    }
    public String getStatus() {
        return this.status.name();
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
}