import javax.print.attribute.standard.Destination;
import java.lang.reflect.Array;
import java.util.ArrayList;

abstract class Piece {
    protected String name;
    protected boolean isWhite;
    protected Square location;

    public Piece(String name, boolean isWhite, Square location) {
        this.name = name;
        this.isWhite = isWhite;
        this.location = location;
    }

    public Piece(String name, boolean isWhite) {
        this.name = name;
        this.isWhite = isWhite;
        this.location = null;
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

    /**
     * This method returns whether the piece is dead or not (present on the board or not)
     */
    public boolean isDead() {
        return this.location == null;
    }

    public boolean validateMove(Move move, Square[][] board) {
        ArrayList<Square> validMoves = this.getValidMoves(board);
        int xDest = move.getDestinationSquare().getX();
        int yDest = move.getDestinationSquare().getY();
        for (int i=0; i<validMoves.size(); i++) {
            int i_x = validMoves.get(i).getX();
            int i_y = validMoves.get(i).getY();

            if (i_x == xDest && i_y == yDest)       // Checking if the move is in valid moves
                return true;
        }
        return false;
    }

    /**
     * This method returns an arraylist of squares that the piece can move to;
     * @param board the 2d array of squares of the board of the game;
     * @return an arraylist of all the squares the piece can move to;
     */
    public abstract ArrayList<Square> getValidMoves(Square[][] board);

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
    public ArrayList<Square> getValidMoves(Square[][] board) {
        int xSrc = this.getLocation().getX();
        int ySrc = this.getLocation().getY();
        ArrayList<Square> validMoves = new ArrayList<Square>();

        for (int i= xSrc-1; i <= xSrc+1; i++) {
            for (int j=ySrc-1; j <= ySrc+1; j++) {
                if (i != j && 0<=i && i<=7 && 0<=j && j<=7 && this.canOccupySquare(i, j, board))
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
    public boolean validateMove(Move move) {
        // TODO Auto-generated method stub

//        Piece destPiece=move.getPiece();
//        if(destPiece.getColor()==this.getColor())
//        {
//            return false;
//        }
//        else
//        {
//
//        }


        return false;
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
    public ArrayList<Square> getValidMoves(Square[][] board) {
        int xSrc = this.getLocation().getX();
        int ySrc = this.getLocation().getY();
        ArrayList<Square> validMoves = new ArrayList<Square>();

        // Moving to the right
        for (int i=xSrc+1; i<=7; i++) {
            if (this.canOccupySquare(i, ySrc, board)) {
                validMoves.add(board[i][ySrc]);
                if (board[i][ySrc] != null)         // if the square has a piece of different color
                    break;
            }
            else
                break;
        }

        // Moving to the left
        for (int i=xSrc-1; i>=0; i--) {
            if (this.canOccupySquare(i, ySrc, board)) {
                validMoves.add(board[i][ySrc]);
                if (board[i][ySrc] != null)         // if the square has a piece of different color
                    break;
            }
            else
                break;
        }

        // Moving up
        for (int i=ySrc+1; i<=7; i++) {
            if (this.canOccupySquare(xSrc, i, board)) {
                validMoves.add(board[xSrc][i]);
                if (board[xSrc][i] != null)         // if the square has a piece of different color
                    break;
            }
            else
                break;
        }

        //Moving down
        for (int i=ySrc-1; i>=0; i--) {
            if (this.canOccupySquare(xSrc, i, board)) {
                validMoves.add(board[xSrc][i]);
                if (board[xSrc][i] != null)         // if the square has a piece of different color
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
    public ArrayList<Square> getValidMoves(Square[][] board) {
        //
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
    public boolean validateMove(Move move) {
        // TODO Auto-generated method stub
        return false;
    }
    

}

class Pawn extends Piece {
    public Pawn(boolean isWhite) {
        super("Pawn", isWhite);
    }

    public Pawn(boolean isWhite, Square location) {
        super("Pawn", isWhite, location);
    }

    @Override
    public boolean validateMove(Move move) {
        // TODO Auto-generated method stub
        return false;
    }
    

}

class CheckersPawn extends Piece {
    public CheckersPawn(boolean isWhite) {
        super("CheckersPawn", isWhite);
    }

    public CheckersPawn(boolean isWhite, Square location) {
        super("CheckersPawn", isWhite, location);
    }

    @Override
    public boolean validateMove(Move move) {
        // TODO Auto-generated method stub
        return false;
    }

}





