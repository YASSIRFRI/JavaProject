import java.io.PipedInputStream;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

enum moveType{
    NONE, NORMAL,KILL
}

class Move {
    private Square sourceSquare;
     private Square destinationSquare;
    private moveType type;
    public Piece piece;
    private Piece enemyPiece;
    public ArrayList<Piece> killedPieces=new ArrayList<Piece>();



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

        // Handling EnPassant move
        if (piece.getName().equals("Pawn") && destinationSquare.isEmpty() && (destinationSquare.getx() != sourceSquare.getx())) {
            Pawn killedPiece = chessBoard.getEnPassantThreatedPawn();
            killedPiece.getLocation().setPlaceholder(null);
            chessBoard.Board.getChildren().remove(killedPiece.getImage());
            chessBoard.getEnemyPieces(piece.getIsWhite()).remove(killedPiece);
            killedPiece.setLocation(null);
            this.enemyPiece = killedPiece;

            if (killedPiece.getIsWhite()) {
                ImageView img = new ImageView(new Image("/static/White/Pawn.png"));
                img.setFitWidth(40);
                img.setFitHeight(40);
                chessBoard.getWhiteKilledPiecesPane().getChildren().add(img);
            }
            else {
                ImageView img = new ImageView(new Image("/static/Black/Pawn.png"));
                img.setFitWidth(40);
                img.setFitHeight(40);
                chessBoard.getBlackKilledPiecesPane().getChildren().add(img);
            }
        }


        /////////////////////////////////
        if (!destinationSquare.isEmpty()) {
            Piece killedPiece = destinationSquare.getPlaceholder();
            chessBoard.Board.getChildren().remove(killedPiece.getImage());  // Removing the image of the killed piece
            this.enemyPiece=killedPiece;
            if (killedPiece.getIsWhite()) {
                chessBoard.getWhitePieces().remove(killedPiece);

                ImageView img = new ImageView(new Image("/static/White/" + killedPiece.getName() + ".png"));
                img.setFitWidth(40);
                img.setFitHeight(40);
                chessBoard.getWhiteKilledPiecesPane().getChildren().add(img);
            }
            else {
                chessBoard.getBlackPieces().remove(killedPiece);

                ImageView img = new ImageView(new Image("/static/Black/" + killedPiece.getName() + ".png"));
                img.setFitWidth(40);
                img.setFitHeight(40);
                chessBoard.getBlackKilledPiecesPane().getChildren().add(img);
            }
            destinationSquare.getPlaceholder().setLocation(null);
        }
        destinationSquare.setPlaceholder(piece);
        chessBoard.Board.getChildren().remove(piece.getImage());
        this.piece.setLocation(destinationSquare);
        chessBoard.Board.add(piece.getImage(), destinationSquare.getx(), destinationSquare.gety());
        sourceSquare.setPlaceholder(null);
        this.piece.setHasMoved(true);
        //handling pawn promotion
        chessBoard.gameHistory.add(this);



        // Handling EnPassant //////////////////////////////////////////////////////////////////////////////////////////
        if (piece.getName().equals("Pawn") && Math.abs(sourceSquare.gety() - destinationSquare.gety()) == 2) {
            chessBoard.setEnPassantThreatedPawn((Pawn) piece);
        }
        else {
            chessBoard.setEnPassantThreatedPawn(null);
        }

    }

    public void doMove(CheckersBoard checkersboard)
    {
        if(Math.abs(this.getDestinationSquare().getx()-this.getSourceSquare().getx())==2)
        {
            Square middleSquare=checkersboard.getBoard()[(this.getDestinationSquare().getx()+this.getSourceSquare().getx())/2][(this.getDestinationSquare().gety()+this.getSourceSquare().gety())/2];
            Piece killedPiece=middleSquare.getPlaceholder();
            checkersboard.Board.getChildren().remove(killedPiece.getImage());
            this.killedPieces.add(killedPiece);
            middleSquare.setPlaceholder(null);
        }
        if(Math.abs(this.getDestinationSquare().gety()-this.getSourceSquare().gety())==4) 
        {
            if(Math.abs(this.getDestinationSquare().getx()-this.getSourceSquare().getx())==0)
            {
            if(this.getSourceSquare().getPlaceholder().isWhite)
            {
            Square middleSquare1=checkersboard.getBoard()[this.getSourceSquare().getx()+1][this.getSourceSquare().gety()+1];
            Square middleSquare2=checkersboard.getBoard()[this.getSourceSquare().getx()+1][this.getSourceSquare().gety()+3];
            Piece killedPiece1=middleSquare1.getPlaceholder();
            Piece killedPiece2=middleSquare2.getPlaceholder();
            checkersboard.Board.getChildren().remove(killedPiece1.getImage());
            checkersboard.Board.getChildren().remove(killedPiece2.getImage());
            this.killedPieces.add(killedPiece1);
            this.killedPieces.add(killedPiece2);
            middleSquare1.setPlaceholder(null);
            middleSquare2.setPlaceholder(null);
            }
            else
            {
                Square middleSquare1=checkersboard.getBoard()[this.getDestinationSquare().getx()+1][this.getDestinationSquare().gety()+1];
                Square middleSquare2=checkersboard.getBoard()[this.getSourceSquare().getx()+1][this.getSourceSquare().gety()-3];
                Piece killedPiece1=middleSquare1.getPlaceholder();
                Piece killedPiece2=middleSquare2.getPlaceholder();
                checkersboard.Board.getChildren().remove(killedPiece1.getImage());
                checkersboard.Board.getChildren().remove(killedPiece2.getImage());
                this.killedPieces.add(killedPiece1);
                this.killedPieces.add(killedPiece2);
                middleSquare1.setPlaceholder(null);
                middleSquare2.setPlaceholder(null);

            }
        }
        else
        {
            if(this.getDestinationSquare().getx()>this.getSourceSquare().getx())
            {
            Square middleSquare1=checkersboard.getBoard()[(this.getDestinationSquare().getx()+this.getSourceSquare().getx())/2+1][(this.getDestinationSquare().gety()+this.getSourceSquare().gety())/2+1];
            Square middleSquare2=checkersboard.getBoard()[(this.getDestinationSquare().getx()+this.getSourceSquare().getx())/2-1][(this.getDestinationSquare().gety()+this.getSourceSquare().gety())/2-1];
            Piece killedPiece2=middleSquare2.getPlaceholder();
            Piece killedPiece1=middleSquare1.getPlaceholder();
            checkersboard.Board.getChildren().remove(killedPiece1.getImage());
            checkersboard.Board.getChildren().remove(killedPiece2.getImage());
            this.killedPieces.add(killedPiece1);
            this.killedPieces.add(killedPiece2);
            middleSquare1.setPlaceholder(null);
            middleSquare2.setPlaceholder(null);
            }
            else
            {
            Square middleSquare1=checkersboard.getBoard()[(this.getDestinationSquare().getx()+this.getSourceSquare().getx())/2+1][(this.getDestinationSquare().gety()+this.getSourceSquare().gety())/2-1];
            Square middleSquare2=checkersboard.getBoard()[(this.getDestinationSquare().getx()+this.getSourceSquare().getx())/2-1][(this.getDestinationSquare().gety()+this.getSourceSquare().gety())/2+1];
            Piece killedPiece2=middleSquare2.getPlaceholder();
            Piece killedPiece1=middleSquare1.getPlaceholder();
            checkersboard.Board.getChildren().remove(killedPiece1.getImage());
            checkersboard.Board.getChildren().remove(killedPiece2.getImage());
            this.killedPieces.add(killedPiece1);
            this.killedPieces.add(killedPiece2);
            middleSquare1.setPlaceholder(null);
            middleSquare2.setPlaceholder(null);
            }
        }
    }
        destinationSquare.setPlaceholder(piece);
        checkersboard.Board.getChildren().remove(piece.getImage());
        this.piece.setLocation(this.getDestinationSquare());
        System.out.println(destinationSquare.getx() + " " + destinationSquare.gety());
        System.out.println("Piece location: " + piece.getLocation().getx() + " " + piece.getLocation().gety());
        checkersboard.Board.add(piece.getImage(), destinationSquare.getx(), destinationSquare.gety());
        sourceSquare.setPlaceholder(null);
        checkersboard.gameHistory.add(this);
        if(this.getDestinationSquare().getPlaceholder().isWhite && this.killedPieces.size()>0)
        {
            checkersboard.getWhitePieces().removeAll(this.killedPieces);
        }
        else if(this.killedPieces.size()>0)
        {
            checkersboard.getBlackPieces().removeAll(this.killedPieces);
        }
    }
    
    public void reverseMove(ChessBoard chessBoard)
    {
        if (this.enemyPiece != null) {
            chessBoard.Board.add(this.enemyPiece.getImage(), this.destinationSquare.getx(), this.destinationSquare.gety());
            sourceSquare.setPlaceholder(piece);
            chessBoard.Board.getChildren().remove(this.piece.getImage());
            chessBoard.Board.add(this.piece.getImage(), this.sourceSquare.getx(), this.sourceSquare.gety());
            chessBoard.board[this.destinationSquare.getx()][this.destinationSquare.gety()].setPlaceholder(this.enemyPiece);
            chessBoard.board[this.sourceSquare.getx()][this.sourceSquare.gety()].setPlaceholder(piece);
            this.enemyPiece.setLocation(this.destinationSquare);

            // Deleting the last element of showed killed pieces
            if (enemyPiece.getIsWhite()) {
                chessBoard.getWhiteKilledPiecesPane().getChildren().remove(chessBoard.getWhiteKilledPiecesPane().getChildren().size()-1);
            }
            else {
                chessBoard.getBlackKilledPiecesPane().getChildren().remove(chessBoard.getBlackKilledPiecesPane().getChildren().size()-1);
            }
        }

        else {
            sourceSquare.setPlaceholder(piece);
            chessBoard.Board.getChildren().remove(this.piece.getImage());
            chessBoard.Board.add(this.piece.getImage(), this.sourceSquare.getx(), this.sourceSquare.gety());
            chessBoard.board[this.sourceSquare.getx()][this.sourceSquare.gety()].setPlaceholder(piece);
            destinationSquare.setPlaceholder(null);
        }

        this.piece.setLocation(this.sourceSquare);
        if (this.piece.isHasMoved())
            this.piece.setHasMoved(false);

        chessBoard.switchTurn();
        chessBoard.updateStatusLabel();
    }
    public void reverseMove(CheckersBoard checkersBoard) {
        if(this.killedPieces.size()>0)
        {
            for(Piece killedPiece:this.killedPieces)
            {
                checkersBoard.Board.add(killedPiece.getImage(), killedPiece.getLocation().getx(), killedPiece.getLocation().gety());
                checkersBoard.board[killedPiece.getLocation().getx()][killedPiece.getLocation().gety()].setPlaceholder(killedPiece);
                if(killedPiece.isWhite)
                {
                    checkersBoard.getWhitePieces().add((CheckersPawn)killedPiece);
                }
                else
                {
                    checkersBoard.getBlackPieces().add((CheckersPawn)killedPiece);
                }
            }
        }
        sourceSquare.setPlaceholder(piece);
        checkersBoard.Board.getChildren().remove(this.piece.getImage());
        checkersBoard.Board.add(this.piece.getImage(), this.sourceSquare.getx(), this.sourceSquare.gety());
        checkersBoard.board[this.sourceSquare.getx()][this.sourceSquare.gety()].setPlaceholder(piece);
        destinationSquare.setPlaceholder(null);
        this.piece.setLocation(this.sourceSquare);
        checkersBoard.switchTurn();
        checkersBoard.updateStatusLabel();
    }
   
}

///You must keep the code abstract, and not add a new class each time
// class PromotionCheckers extends Move {
//     public CheckersKing promotedPiece;
//     public Piece killedPiece;
//     public PromotionCheckers(Square sourceSquare, Square destinationSquare, Piece piece, CheckersKing promotedPiece) {
//         super(sourceSquare, destinationSquare, piece);
//         this.promotedPiece=promotedPiece;
//     }
//     public Piece getPromotedPiece()
//     {
//         return promotedPiece;
//     }
//     public void setPromotedPiece(CheckersKing promotedPiece)
//     {
//         this.promotedPiece=promotedPiece;
//     }
    

// }


class Promotion extends Move {
    public Piece promotedPiece;
    public Piece killedPiece;
    public Promotion(Square sourceSquare, Square destinationSquare, Piece piece, Piece promotedPiece) {
        super(sourceSquare, destinationSquare, piece);
        this.promotedPiece=promotedPiece;
    }

    public Piece getPromotedPiece()
    {
        return promotedPiece;
    }
    public void setPromotedPiece(Piece promotedPiece)
    {
        this.promotedPiece=promotedPiece;
    }


    @Override
    public void doMove(ChessBoard chessBoard) {
        if(this.getDestinationSquare().getPlaceholder()!=null)
        {
            this.killedPiece=this.getDestinationSquare().getPlaceholder();
            this.getDestinationSquare().setPlaceholder(null);
            chessBoard.Board.getChildren().remove(this.killedPiece.getImage());
            this.killedPiece.setLocation(null);
        }
        chessBoard.Board.getChildren().remove(this.piece.getImage());
        chessBoard.Board.add(this.promotedPiece.getImage(), getDestinationSquare().getx(), getDestinationSquare().gety());
        chessBoard.board[getDestinationSquare().getx()][getDestinationSquare().gety()].setPlaceholder(promotedPiece);
        chessBoard.board[getSourceSquare().getx()][getSourceSquare().gety()].setPlaceholder(null);
        getDestinationSquare().setPlaceholder(promotedPiece);
        promotedPiece.setLocation(getDestinationSquare());
        getSourceSquare().setPlaceholder(null);
        this.promotedPiece.setLocation(getDestinationSquare());
        if (!this.piece.isHasMoved())
            this.piece.setHasMoved(true);
        if(piece.getIsWhite())
        {
            chessBoard.getWhitePieces().remove(piece);
            chessBoard.getWhitePieces().add(promotedPiece);
        }
        else{
            chessBoard.getBlackPieces().remove(piece);
            chessBoard.getBlackPieces().add(promotedPiece);
        }
        if(this.killedPiece!=null)
        {
            if(this.killedPiece.getIsWhite())
                chessBoard.getWhitePieces().remove(this.killedPiece);
            else
                chessBoard.getBlackPieces().remove(this.killedPiece);
        }
        chessBoard.gameHistory.add(this);
    }
    @Override
    public void reverseMove(ChessBoard chessBoard) {
        System.out.println("reversing promotion");
        chessBoard.Board.getChildren().remove(this.promotedPiece.getImage());
        chessBoard.Board.getChildren().remove(this.piece.getImage());
        chessBoard.Board.add(this.piece.getImage(), getSourceSquare().getx(), getSourceSquare().gety());
        chessBoard.board[getSourceSquare().getx()][getSourceSquare().gety()].setPlaceholder(piece);
        getSourceSquare().setPlaceholder(piece);
        this.piece.setLocation(getSourceSquare());
        if(piece.getIsWhite())
        {
            chessBoard.getWhitePieces().remove(promotedPiece);
            chessBoard.getWhitePieces().add(piece);
        }
        else{
            chessBoard.getBlackPieces().remove(promotedPiece);
            chessBoard.getBlackPieces().add(piece);
        }
        if(this.killedPiece!=null)
        {
            if(this.killedPiece.getIsWhite())
                chessBoard.getWhitePieces().add(this.killedPiece);
            else
                chessBoard.getBlackPieces().add(this.killedPiece);
            chessBoard.Board.getChildren().add(this.killedPiece.getImage());
            this.getDestinationSquare().setPlaceholder(this.killedPiece);
            this.killedPiece.setLocation(this.getDestinationSquare());
        }
        else
        {
            this.getDestinationSquare().setPlaceholder(null);
        }
        chessBoard.switchTurn();
        chessBoard.updateStatusLabel();
    }
    @Override
    public void doMove(CheckersBoard checkersBoard) {
        if(checkersBoard.board[(this.getDestinationSquare().getx()+this.getSourceSquare().getx())/2][(this.getDestinationSquare().gety()+this.getSourceSquare().gety())/2].getPlaceholder()!=null)
        {
            this.killedPiece= checkersBoard.board[(this.getDestinationSquare().getx()+this.getSourceSquare().getx())/2][(this.getDestinationSquare().gety()+this.getSourceSquare().gety())/2].getPlaceholder();
            checkersBoard.Board.getChildren().remove(this.killedPiece.getImage());
            this.killedPiece.setLocation(null);
        }
        getSourceSquare().setPlaceholder(null);
        checkersBoard.Board.getChildren().remove(this.piece.getImage());
        checkersBoard.Board.add(this.promotedPiece.getImage(), getDestinationSquare().getx(), getDestinationSquare().gety());
        checkersBoard.board[getDestinationSquare().getx()][getDestinationSquare().gety()].setPlaceholder(promotedPiece);
        getDestinationSquare().setPlaceholder(promotedPiece);
        this.promotedPiece.setLocation(getDestinationSquare());
        if(piece.getIsWhite())
        {
            checkersBoard.getWhitePieces().remove(piece);
            checkersBoard.getWhitePieces().add((CheckersPawn) promotedPiece);
        }
        else{
            checkersBoard.getBlackPieces().remove(piece);
            checkersBoard.getBlackPieces().add((CheckersPawn) promotedPiece);
        }
        if(this.killedPiece!=null)
        {
            if(this.killedPiece.getIsWhite())
                checkersBoard.getWhitePieces().remove(this.killedPiece);
            else
                checkersBoard.getBlackPieces().remove(this.killedPiece);
        }
        checkersBoard.gameHistory.add(this);

}
    @Override
    public void reverseMove(CheckersBoard checkersBoard) {
        getSourceSquare().setPlaceholder(piece);
        checkersBoard.Board.getChildren().remove(this.promotedPiece.getImage());
        checkersBoard.Board.add(this.piece.getImage(), getSourceSquare().getx(), getSourceSquare().gety());
        checkersBoard.board[getDestinationSquare().getx()][getDestinationSquare().gety()].setPlaceholder(piece);
        getDestinationSquare().setPlaceholder(null);
        this.piece.setLocation(getSourceSquare());
        if(piece.getIsWhite())
        {
            checkersBoard.getWhitePieces().remove(promotedPiece);
            checkersBoard.getWhitePieces().add((CheckersPawn) piece);
        }
        else{
            checkersBoard.getBlackPieces().remove(promotedPiece);
            checkersBoard.getBlackPieces().add((CheckersPawn) piece);
        }
        if(this.killedPiece!=null)
        {
            if(this.killedPiece.getIsWhite())
                checkersBoard.getWhitePieces().add((CheckersPawn) this.killedPiece);
            else
                checkersBoard.getBlackPieces().add((CheckersPawn) this.killedPiece);
        }
        checkersBoard.switchTurn();
        checkersBoard.updateStatusLabel();
    }

}


