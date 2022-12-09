import javax.print.attribute.standard.Destination;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;

import java.lang.reflect.Array;
import java.util.ArrayList;

abstract class Piece {
    
    protected String name;
    protected boolean isWhite;
    protected Square location;
    protected ImageView image;
    protected boolean hasMoved;


    public Piece(String name, boolean isWhite, Square location) {
        this.name = name;
        this.hasMoved = false;
        this.isWhite = isWhite;
        this.location = location;
        if(isWhite){
            this.image = new ImageView(new Image("file:static/White/" + name + ".png"));
        }
        else{
            this.image = new ImageView(new Image("file:static/Black/" + name + ".png"));
        }

        this.image.setFitHeight(Square.squareWidth - 25);
        this.image.setFitWidth(Square.squareWidth - 25);
    }

    public Piece(String name, boolean isWhite) {
        this.name = name;
        this.isWhite = isWhite;
        this.location = null;
        if(isWhite){
            this.image = new ImageView(new Image("file:static/White/" + name + ".png"));
        }
        else{
            this.image = new ImageView(new Image("file:static/Black/" + name + ".png"));
        }

        this.image.setFitHeight(Square.squareWidth - 25);
        this.image.setFitWidth(Square.squareWidth - 25);
    }


    // Getters and setters
    public String getName() {
        return name;
    }
    public boolean getIsWhite() {
        return isWhite;
    }
    public Square getLocation() {
        return location;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setIsWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }
    public void setLocation(Square location) {
        this.location = location;
    }
    public ImageView getImage(){
        return image;
    }
    public void setImage(ImageView image) {
        this.image = image;
    }
    public boolean isHasMoved() { return hasMoved; }
    public void setHasMoved(boolean hasMoved) { this.hasMoved = hasMoved; }

    /**
     * This method returns whether the piece is dead or not (present on the board or not)
     */
    public boolean isDead() {
        return this.location == null;
    }

    public boolean validateMove(Square destination, ChessBoard chessBoard) {
        ArrayList<Square> validMoves = this.getValidMoves(chessBoard);
        int xDest = destination.getx();
        int yDest = destination.gety();
        for (int i=0; i<validMoves.size(); i++) {
            int i_x = validMoves.get(i).getx();
            int i_y = validMoves.get(i).gety();

            if (i_x == xDest && i_y == yDest)       // Checking if the move is in valid moves
                return true;
        }
        return false;
    }


    /**
     * This method returns an arraylist of squares that the piece can move to;
     * @param chessBoard the board of the game;
     * @return an arraylist of all the squares the piece can move to;
     */
    public abstract ArrayList<Square> getValidMoves(ChessBoard chessBoard);
    public ArrayList<Square> getFinalValidMoves(ChessBoard chessBoard) {
        ArrayList<Square> validMoves = this.getValidMoves(chessBoard);
        ArrayList<Square> finalValidMoves = new ArrayList<Square>();

        for (Square validMove : validMoves) {
            if (this.kingWillNotBeThreatened(validMove, chessBoard))
                finalValidMoves.add(validMove);
        }

        // Checking castling if the piece is a king
        if ( this.getName().equals("King") && (! this.isHasMoved())) {
            Square[][] board = chessBoard.getBoard();
            int xSrc = this.getLocation().getx();
            int ySrc = this.getLocation().gety();
            Rook rightRook = (Rook) (this.isWhite ? chessBoard.getBoard()[7][0].getPlaceholder() : chessBoard.getBoard()[7][7].getPlaceholder());
            Rook leftRook = (Rook) (this.isWhite ? chessBoard.getBoard()[0][0].getPlaceholder() : chessBoard.getBoard()[0][7].getPlaceholder());

            // Checking the right side
            if ( (rightRook != null) && (! rightRook.isHasMoved()) && (! chessBoard.isKingInThreat(this.isWhite)) ) {
                if (board[xSrc+1][ySrc].isEmpty() && board[xSrc+2][ySrc].isEmpty() && board[xSrc+3][ySrc].isEmpty()) {
                    if (this.kingWillNotBeThreatened(board[xSrc+1][ySrc], chessBoard) && this.kingWillNotBeThreatened(board[xSrc+2][ySrc], chessBoard))
                        finalValidMoves.add(board[xSrc+2][ySrc]);

                }
            }

            // Checking the left side
            if ( (leftRook != null) && (! leftRook.isHasMoved()) && (!chessBoard.isKingInThreat(this.isWhite)) ) {
                if (board[xSrc-1][ySrc].isEmpty() && board[xSrc-2][ySrc].isEmpty()) {
                    if (this.kingWillNotBeThreatened(board[xSrc-1][ySrc], chessBoard) && this.kingWillNotBeThreatened(board[xSrc-2][ySrc], chessBoard))
                        finalValidMoves.add(board[xSrc-2][ySrc]);
                }
            }
        }

        return finalValidMoves;
    }

    /**
     * This method checks if a given square can be occupied by a piece, depending on the piece
     * (it can be occupied only if the square is empty, or is already occupied by a different color piece)
     * @param board 2D array of squares of the board
     * @param x x coordinate of the square
     * @param y y coordinate of the square
     * @return boolean, whether that square can be occupied be the piece who called this method or not
     */
    public boolean canOccupySquare(int x, int y, Square[][] board) {
        if (board[x][y].getPlaceholder() == null)
            return true;
        else {
            if (board[x][y].getPlaceholder().getIsWhite() == this.isWhite)
                return false;
            else
                return true;
        }
    }

    public static boolean isInRange(int idx) {
        return (0 <= idx && idx <= 7);
    }

    public boolean kingWillNotBeThreatened(Square moveDestinationSquare, ChessBoard chessBoard) {
        Move move = new Move(this.getLocation(), moveDestinationSquare, this);
        return ! chessBoard.kingWillBeInThreat(move);
    }

    public boolean isEnemy(Piece piece) {
        return this.getIsWhite() != piece.getIsWhite();
    }

}

/**
 * This class represents a King piece
 */
class King extends Piece {
    public King(boolean isWhite) {
        super("King", isWhite);
    }
    public King(boolean isWhite, Square location) {
        super("King", isWhite, location);
    }

    @Override
    public ArrayList<Square> getValidMoves(ChessBoard chessBoard) {
        int xSrc = this.getLocation().getx();
        int ySrc = this.getLocation().gety();
        Square[][] board = chessBoard.getBoard();
        ArrayList<Square> validMoves = new ArrayList<Square>();

        for (int i= xSrc-1; i <= xSrc+1; i++) {
            for (int j=ySrc-1; j <= ySrc+1; j++) {
                if (isInRange(i) && isInRange(j) && this.canOccupySquare(i, j, board))
                    validMoves.add(board[i][j]);
            }
        }

        return validMoves;
    }

}


class Queen extends Piece {
    public Queen(boolean isWhite) {
        super("Queen", isWhite);
    }
    public Queen(boolean isWhite, Square location) {
        super("Queen", isWhite, location);
    }

    @Override
    public ArrayList<Square> getValidMoves(ChessBoard chessBoard) {
        // The queen's valid moves are the (bishop + rook) valid moves
        Rook temp_rook = new Rook(this.isWhite, this.location);
        Bishop temp_bishop = new Bishop(this.isWhite, this.location);

        ArrayList<Square> queenValidMoves = new ArrayList<Square>();
        queenValidMoves.addAll(temp_rook.getValidMoves(chessBoard));
        queenValidMoves.addAll(temp_bishop.getValidMoves(chessBoard));

        return queenValidMoves;
    }
}

class Rook extends Piece {
    public Rook(boolean isWhite) {
        super("Rook", isWhite);
    }

    public Rook(boolean isWhite, Square location) {
        super("Rook", isWhite, location);
    }

    @Override
    public ArrayList<Square> getValidMoves(ChessBoard chessBoard) {
        int xSrc = this.getLocation().getx();
        int ySrc = this.getLocation().gety();
        Square[][] board = chessBoard.getBoard();
        ArrayList<Square> validMoves = new ArrayList<Square>();

        // Moving to the right
        for (int i=xSrc+1; i<=7; i++) {
            if (this.canOccupySquare(i, ySrc, board)) {
                validMoves.add(board[i][ySrc]);
                if (board[i][ySrc].getPlaceholder() != null)         // if the square has a piece of different color
                    break;
            }
            else
                break;
        }

        // Moving to the left
        for (int i=xSrc-1; i>=0; i--) {
            if (this.canOccupySquare(i, ySrc, board)) {
                validMoves.add(board[i][ySrc]);
                if (board[i][ySrc].getPlaceholder() != null)         // if the square has a piece of different color
                    break;
            }
            else
                break;
        }

        // Moving down
        for (int i=ySrc+1; i<=7; i++) {
            if (this.canOccupySquare(xSrc, i, board)) {
                validMoves.add(board[xSrc][i]);
                if (board[xSrc][i].getPlaceholder() != null)         // if the square has a piece of different color
                    break;
            }
            else
                break;
        }

        //Moving up
        for (int i=ySrc-1; i>=0; i--) {
            if (this.canOccupySquare(xSrc, i, board)) {
                validMoves.add(board[xSrc][i]);
                if (board[xSrc][i].getPlaceholder() != null)         // if the square has a piece of different color
                    break;
            }
            else
                break;
        }
        return validMoves;
    }
}


class Bishop extends Piece {
    public Bishop(boolean isWhite) {
        super("Bishop", isWhite);
    }

    public Bishop(boolean isWhite, Square location) {
        super("Bishop", isWhite, location);
    }

    @Override
    public ArrayList<Square> getValidMoves(ChessBoard chessBoard) {
        int xSrc = this.getLocation().getx();
        int ySrc = this.getLocation().gety();
        Square[][] board = chessBoard.getBoard();
        ArrayList<Square> validMoves = new ArrayList<Square>();

        //Moving bottom-right
        for (int i=xSrc+1, j=ySrc+1; i<=7 && j<=7; i++, j++) {
            if (this.canOccupySquare(i, j, board)) {
                validMoves.add(board[i][j]);
                if (board[i][j].getPlaceholder() != null)
                    break;
            }
            else
                break;
        }

        //Moving bottom-left
        for (int i=xSrc-1, j=ySrc+1; i>=0 && j<=7; i--, j++) {
            if (this.canOccupySquare(i, j, board)) {
                validMoves.add(board[i][j]);
                if (board[i][j].getPlaceholder() != null)
                    break;
            }
            else
                break;
        }

        //Moving top-right
        for (int i=xSrc+1, j=ySrc-1; i<=7 && j>=0; i++, j--) {
            if (this.canOccupySquare(i, j, board)) {
                validMoves.add(board[i][j]);
                if (board[i][j].getPlaceholder() != null)
                    break;
            }
            else
                break;
        }

        //Moving top-left
        for (int i=xSrc-1, j=ySrc-1; i>=0 && j>=0; i--, j--) {
            if (this.canOccupySquare(i, j, board)) {
                validMoves.add(board[i][j]);
                if (board[i][j].getPlaceholder() != null)
                    break;
            }
            else
                break;
        }

        return validMoves;
    }
}

class Knight extends Piece {
    public Knight(boolean isWhite) {
        super("Knight", isWhite);
    }

    public Knight(boolean isWhite, Square location) {
        super("Knight", isWhite, location);
    }

    @Override
    public ArrayList<Square> getValidMoves(ChessBoard chessBoard) {
        int xSrc = this.getLocation().getx();
        int ySrc = this.getLocation().gety();
        Square[][] board = chessBoard.getBoard();
        ArrayList<Square> validMoves = new ArrayList<Square>();

        int[][] standardPossibleSquares = {
                {xSrc+2, ySrc+1},
                {xSrc+2, ySrc-1},
                {xSrc+1, ySrc+2},
                {xSrc+1, ySrc-2},
                {xSrc-2, ySrc+1},
                {xSrc-2, ySrc-1},
                {xSrc-1, ySrc+2},
                {xSrc-1, ySrc-2}
        };

        for (int i=0; i<8; i++) {
            int x = standardPossibleSquares[i][0];
            int y = standardPossibleSquares[i][1];
            if (isInRange(x) && isInRange(y) && this.canOccupySquare(x, y, board))
                validMoves.add(board[x][y]);
        }

        return validMoves;
    }
}

class Pawn extends Piece {

    private int pawnOrientation;

    public Pawn(boolean isWhite) {
        super("Pawn", isWhite);
        if (isWhite)
            this.pawnOrientation = 1;
        else
            this.pawnOrientation = -1;
    }

    public Pawn(boolean isWhite, Square location) {
        super("Pawn", isWhite, location);
        if (isWhite)
            this.pawnOrientation = 1;
        else
            this.pawnOrientation = -1;
    }

    public int getPawnOrientation() {
        return pawnOrientation;
    }

    public void setPawnOrientation(int pawnOrientation) {
        this.pawnOrientation = pawnOrientation;
    }

    @Override
    public ArrayList<Square> getValidMoves(ChessBoard chessBoard) {
        int xSrc = this.getLocation().getx();
        int ySrc = this.getLocation().gety();
        Square[][] board = chessBoard.getBoard();
        ArrayList<Square> validMoves = new ArrayList<Square>();


        // Checking the square forward
        if (isInRange(ySrc+pawnOrientation) && board[xSrc][ySrc+pawnOrientation].getPlaceholder() == null)
            validMoves.add(board[xSrc][ySrc+pawnOrientation]);

        // Checking the diagonals
        if (isInRange(xSrc+pawnOrientation) && isInRange(ySrc+pawnOrientation) && board[xSrc+pawnOrientation][ySrc+pawnOrientation].getPlaceholder() != null) {
            if (board[xSrc+pawnOrientation][ySrc+pawnOrientation].getPlaceholder().getIsWhite() != this.isWhite)
                validMoves.add(board[xSrc+pawnOrientation][ySrc+pawnOrientation]);
        }

        if (isInRange(xSrc-pawnOrientation) && isInRange(ySrc+pawnOrientation) && board[xSrc-pawnOrientation][ySrc+pawnOrientation].getPlaceholder() != null) {
            if (board[xSrc-pawnOrientation][ySrc+pawnOrientation].getPlaceholder().getIsWhite() != this.isWhite)
                validMoves.add(board[xSrc-pawnOrientation][ySrc+pawnOrientation]);
        }

        // Checking the second square forward if the pawn never moved
        if (! this.isHasMoved()) {
            if (board[xSrc][ySrc+2*pawnOrientation].getPlaceholder() == null && board[xSrc][ySrc+pawnOrientation].getPlaceholder() == null)
                validMoves.add(board[xSrc][ySrc+2*pawnOrientation]);
        }

        return validMoves;
    }
}

<<<<<<< HEAD

=======
//class CheckersPawn extends Piece {
//    public CheckersPawn(boolean isWhite) {
//        super("CheckersPawn", isWhite);
//    }
//
//    public CheckersPawn(boolean isWhite, Square location) {
//        super("CheckersPawn", isWhite, location);
//    }
//
//    public boolean validateMove(Move move) {
//        public boolean validateMove(Square destination, ChessBoard chessBoard) {
//            ArrayList<Square> validMoves = this.getValidMoves(chessBoard);
//            int xDest = destination.getx();
//            int yDest = destination.gety();
//            for (int i=0; i<validMoves.size(); i++) {
//                int i_x = validMoves.get(i).getx();
//                int i_y = validMoves.get(i).gety();
//
//                if (i_x == xDest && i_y == yDest)       // Checking if the move is in valid moves
//                    return true;
//            }
//            return false;
//        }
//    }
//
//}
>>>>>>> ead387b84b76dd4cbd02590e7f48aed03150f4ee





