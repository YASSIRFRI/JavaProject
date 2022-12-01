import javafx.scene.paint.*;

abstract class Piece  {
    protected ImagePattern image;
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

    public abstract boolean validateMove(Move move, Square[][] board);

    /**
     * This method checks if a given square can be occupied by a piece, depending on the piece
     * (it can be occupied only if the square is empty, or is already occupied by a different color piece)
     *
     * @param board array of squares of the board
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
    public boolean validateMove(Move move, Square[][] board) {
        int xSrc = move.getSourceSquare().getX();
        int ySrc = move.getSourceSquare().getY();
        int xDest = move.getDestinationSquare().getX();
        int yDest = move.getDestinationSquare().getY();

        boolean result;
        if (Math.abs(xSrc - xDest) <= 1 && Math.abs(ySrc - yDest) <= 1)
            result = this.canOccupySquare(xDest, yDest, board);
        else
            result = false;


        return result && (move.getStatus() != MoveStatus.CHECK);
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
    public boolean validateMove(Move move, Square[][] board) {
        int xSrc = move.getSourceSquare().getX();
        int ySrc = move.getSourceSquare().getY();
        int xDest = move.getDestinationSquare().getX();
        int yDest = move.getDestinationSquare().getY();
        boolean result, emptyPath=true;

        if (xSrc == xDest) {
            for (int i=Math.min(ySrc, yDest)+1; i<Math.max(ySrc, yDest); i++) {  //looping through squares from source to destination
                if (board[xSrc][i].getPlaceholder() != null) {
                    emptyPath = false;
                    break;
                }
            }
            result = emptyPath && canOccupySquare(xDest, yDest, board);

        } else if (ySrc == yDest) {
            for (int i=Math.min(xSrc, xDest)+1; i<Math.max(xSrc, xDest); i++) {  //looping through squares from source to destination
                if (board[ySrc][i].getPlaceholder() != null) {
                    emptyPath = false;
                    break;
                }
            }
            result = emptyPath && canOccupySquare(xDest, yDest, board);

        } else {
            result = false;
        }

        return result && (move.getStatus() != MoveStatus.CHECK);
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
    public boolean validateMove(Move move, Square[][] board) {
        int xSrc = move.getSourceSquare().getX();
        int ySrc = move.getSourceSquare().getY();
        int xDest = move.getDestinationSquare().getX();
        int yDest = move.getDestinationSquare().getY();
        boolean result, emptyPath=true;

        if (Math.abs(xSrc - xDest) == Math.abs(ySrc - yDest)) {

            if (xDest >= xSrc && yDest >= ySrc) {
                for (int i=xSrc+1, j=ySrc+1; i<xDest && j<yDest; i++, j++) {
                    if (board[i][j].getPlaceholder() != null) {
                        emptyPath = false;
                        break;
                    }
                }
            }

            else if (xDest <= xSrc && yDest >= ySrc) {
                for (int i=xSrc-1, j=ySrc+1; i>xDest && j<yDest; i--, j++) {
                    if (board[i][j].getPlaceholder() != null) {
                        emptyPath = false;
                        break;
                    }
                }
            }
            else if (xDest <= xSrc && yDest <= ySrc) {
                for (int i=xSrc-1, j=ySrc-1; i>xDest && j>yDest; i--, j--) {
                    if (board[i][j].getPlaceholder() != null) {
                        emptyPath = false;
                        break;
                    }
                }
            }
            else if (xDest >= xSrc && yDest <= ySrc) {
                for (int i=xSrc+1, j=yDest-1; i<xDest && j>yDest; i++, j--) {
                    if (board[i][j].getPlaceholder() != null) {
                        emptyPath = false;
                        break;
                    }
                }
            }

            result = emptyPath && canOccupySquare(xDest, yDest, board);
        }
        else
            result = false;

        return result && (move.getStatus() != MoveStatus.CHECK);
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





