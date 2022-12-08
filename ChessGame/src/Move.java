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
    private Piece enemyPiece;

    public Move(Square sourceSquare, Square destinationSquare, Piece piece, MoveStatus status) {
        this.sourceSquare = sourceSquare;
        this.destinationSquare = destinationSquare;
        this.piece = piece;
        this.status = status;
        if(destinationSquare.getPlaceholder()!=null)
        {
            this.enemyPiece=destinationSquare.getPlaceholder();
        }
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
    public Piece getEnemyPiece()
    {
        return enemyPiece;
    }
    public void setEnemyPiece(Piece enemyPiece)
    {
        this.enemyPiece=enemyPiece;
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
    public void reverseMove(GameBoard chessBoard)
    {
        if(this.enemyPiece!=null)
        {
            chessBoard.add(this.enemyPiece.getImage(), this.destinationSquare.getx(), this.destinationSquare.gety());
            sourceSquare.setPlaceholder(piece);
            chessBoard.add(this.piece.getImage(), this.sourceSquare.getx(), this.sourceSquare.gety());
            chessBoard.board[this.destinationSquare.getx()][this.destinationSquare.gety()].setPlaceholder(this.enemyPiece);
            chessBoard.board[this.sourceSquare.getx()][this.sourceSquare.gety()].setPlaceholder(piece);
            this.enemyPiece.setLocation(this.destinationSquare);
            this.piece.setLocation(this.sourceSquare);
            this.piece.setHasMoved(false);
    }
    else
    {
        sourceSquare.setPlaceholder(piece);
        chessBoard.add(this.piece.getImage(), this.sourceSquare.getx(), this.sourceSquare.gety());
        chessBoard.getChildren().remove(this.destinationSquare.getPlaceholder().getImage());
        chessBoard.board[this.sourceSquare.getx()][this.sourceSquare.gety()].setPlaceholder(piece);
        this.piece.setLocation(this.sourceSquare);
        this.piece.setHasMoved(false);
    }
    }
   
}
