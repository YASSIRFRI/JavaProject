import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

enum moveType{
    NONE, NORMAL,KILL
}

class Move {
    private Square sourceSquare;
    private Square destinationSquare;
    private moveType type;
    private Piece piece;
    private Piece enemyPiece;


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
  
    public void setSource(Square sourceSquare) {
        this.sourceSquare = sourceSquare;
    }

    public void setDestination(Square destinationSquare) {
        this.destinationSquare = destinationSquare;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
 

    public boolean equals(Move anotherMove) {
		if(this.getSourceSquare() == anotherMove.getSourceSquare() && this.getDestinationSquare() == anotherMove.getDestinationSquare() ){
			return true;
		}
		return false;
	}


    public void doMove(ChessBoard chessBoard) {

        // Handling castling
        if (piece.getName().equals("King")) {
            int difference = piece.getLocation().getx() - destinationSquare.getx();

            if (difference == 2) {
                Square rookSource = chessBoard.getBoard()[piece.getLocation().getx() - 3][piece.getLocation().gety()];
                Square rookDestination = chessBoard.getBoard()[piece.getLocation().getx() - 1][piece.getLocation().gety()];
                Move rookMove = new Move(rookSource, rookDestination, rookSource.getPlaceholder());
                rookMove.doMove(chessBoard);
            }

            else if (difference == -2) {
                Square rookSource = chessBoard.getBoard()[piece.getLocation().getx() + 4][piece.getLocation().gety()];
                Square rookDestination = chessBoard.getBoard()[piece.getLocation().getx() + 1][piece.getLocation().gety()];
                Move rookMove = new Move(rookSource, rookDestination, rookSource.getPlaceholder());
                rookMove.doMove(chessBoard);
            }
        }

        //Handling pawn promotion
        //////////////////////////////////////////

        if (!destinationSquare.isEmpty()) {
            Piece killedPiece = destinationSquare.getPlaceholder();
            chessBoard.getChildren().remove(killedPiece.getImage());  // Removing the image of the killed piece
            this.enemyPiece=killedPiece;

            if (killedPiece.getIsWhite()) {
                chessBoard.getWhitePieces().remove(killedPiece);
            }
            else {
                chessBoard.getBlackPieces().remove(killedPiece);
            }

            destinationSquare.getPlaceholder().setLocation(null);
        }
        
        destinationSquare.setPlaceholder(piece);
        chessBoard.getChildren().remove(piece.getImage());
        this.piece.setLocation(destinationSquare);
        chessBoard.add(piece.getImage(), destinationSquare.getx(), destinationSquare.gety());
        sourceSquare.setPlaceholder(null);
        this.piece.setHasMoved(true);
        //handling pawn promotion
        if(piece.getName().equals("Pawn") && (destinationSquare.gety()==0 || destinationSquare.gety()==7)){
            chessBoard.getChildren().remove(piece.getImage());
            Piece queen = new Queen(piece.getIsWhite(), destinationSquare);
            chessBoard.add(queen.getImage(), destinationSquare.getx(), destinationSquare.gety());
            destinationSquare.setPlaceholder(queen);
            if(piece.getIsWhite()){
                chessBoard.getWhitePieces().add(queen);
            }
            else{
                chessBoard.getBlackPieces().add(queen);
            }
        }
        chessBoard.gameHistory.add(this);

    }
    public void reverseMove(ChessBoard chessBoard)
    {
        if (this.enemyPiece != null) {
            chessBoard.add(this.enemyPiece.getImage(), this.destinationSquare.getx(), this.destinationSquare.gety());
            sourceSquare.setPlaceholder(piece);
            chessBoard.getChildren().remove(this.piece.getImage());
            chessBoard.add(this.piece.getImage(), this.sourceSquare.getx(), this.sourceSquare.gety());
            chessBoard.board[this.destinationSquare.getx()][this.destinationSquare.gety()].setPlaceholder(this.enemyPiece);
            chessBoard.board[this.sourceSquare.getx()][this.sourceSquare.gety()].setPlaceholder(piece);
            this.enemyPiece.setLocation(this.destinationSquare);
        }

        else {
            sourceSquare.setPlaceholder(piece);
            chessBoard.getChildren().remove(this.piece.getImage());
            chessBoard.add(this.piece.getImage(), this.sourceSquare.getx(), this.sourceSquare.gety());
            chessBoard.board[this.sourceSquare.getx()][this.sourceSquare.gety()].setPlaceholder(piece);
            destinationSquare.setPlaceholder(null);
        }

        this.piece.setLocation(this.sourceSquare);
        if (this.piece.isHasMoved())
            this.piece.setHasMoved(false);

        chessBoard.switchTurn();
        chessBoard.updateStatusLabel();
    }
   
}
