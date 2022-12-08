import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

enum MoveStatus
{
    VALID,INVALID,COMMITED,CHECK,CHECKMATE
}

class Move {
    private Square sourceSquare;
    private Square destinationSquare;
    private Piece piece;
    private MoveStatus status;

    public Move(Square sourceSquare, Square destinationSquare, Piece piece, MoveStatus status) {
        this.sourceSquare = sourceSquare;
        this.destinationSquare = destinationSquare;
        this.piece = piece;
        this.status = status;
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

    public boolean begin(){
        

        return status == MoveStatus.VALID ;


    }
    public void reverse(){
        
    }
    public boolean equals(Move anotherMove) {
		if(this.getSourceSquare() == anotherMove.getSourceSquare() && this.getDestinationSquare() == anotherMove.getDestinationSquare() ){
			return true;
		}
		return false;
	}
    public void updateStatus(Move move, ChessBoard chessBoard){
//        Square[][] board = new Square[8][8];
        if(piece.validateMove(move.getDestinationSquare(), chessBoard))
            status = MoveStatus.VALID;
        else
    
            status = MoveStatus.INVALID;
    }


    public void doMove(ChessBoard chessBoard) {

        if (!destinationSquare.isEmpty()) {
            Piece killedPiece = destinationSquare.getPlaceholder();
            chessBoard.getChildren().remove(killedPiece.getImage());  // Removing the image of the killed piece

            if (killedPiece.getIsWhite())
                chessBoard.getWhitePieces().remove(killedPiece);
            else
                chessBoard.getBlackPieces().remove(killedPiece);

            destinationSquare.getPlaceholder().setLocation(null);
        }

        destinationSquare.setPlaceholder(piece);
        chessBoard.getChildren().remove(piece.getImage());
        this.piece.setLocation(destinationSquare);
        chessBoard.add(piece.getImage(), destinationSquare.getx(), destinationSquare.gety());
        sourceSquare.setPlaceholder(null);


        this.piece.setHasMoved(true);

    }
}