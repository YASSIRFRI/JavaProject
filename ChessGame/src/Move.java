



enum MoveStatus
{
    VALID,INVALID,COMMITED,CHECK,CHECKMATE;
}

class Move {
    private int source;
    private int destination;
    private Piece piece;
    private MoveStatus status;

    public Move(int source, int destination, Piece piece, MoveStatus status) {
        this.source = source;
        this.destination = destination;
        this.piece = piece;
        this.status = status;
    }

    public int getSource() {
        return source;
    }

    public int getDestination() {
        return destination;
    }

    public Piece getPiece() {
        return piece;
    }
    public String getStatus() {
        return this.status.name();
    }



    public void setSource(int source) {
        this.source = source;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setStatus(MoveStatus status) {
        this.status = status;
    }
}