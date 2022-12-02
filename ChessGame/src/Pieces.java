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


    public Piece(String name, boolean isWhite, Square location) {
        this.name = name;

        this.isWhite = isWhite;
        this.location = location;
        if(isWhite){
            this.image = new ImageView(new Image("file:static/White/" + name + ".png"));
        }
        else{
            this.image = new ImageView(new Image("file:static/Black/" + name + ".png"));
        }
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

    /**
     * This method returns whether the piece is dead or not (present on the board or not)
     */
    public boolean isDead() {
        return this.location == null;
    }

    public boolean validateMove(Move move, Square[][] board) {
        ArrayList<Square> validMoves = this.getValidMoves(board);
        int xDest = move.getDestinationSquare().getx();
        int yDest = move.getDestinationSquare().gety();
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
        int xSrc = this.getLocation().getx();
        int ySrc = this.getLocation().gety();
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
    public ArrayList<Square> getValidMoves(Square[][] board) {
        // The queen's valid moves are the (bishop + rook) valid moves
        Rook temp_rook = new Rook(this.isWhite, this.location);
        Bishop temp_bishop = new Bishop(this.isWhite, this.location);

        ArrayList<Square> queenValidMoves = new ArrayList<Square>();
        queenValidMoves.addAll(temp_rook.getValidMoves(board));
        queenValidMoves.addAll(temp_bishop.getValidMoves(board));

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
    public ArrayList<Square> getValidMoves(Square[][] board) {
        int xSrc = this.getLocation().getx();
        int ySrc = this.getLocation().gety();
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

        // Moving up
        for (int i=ySrc+1; i<=7; i++) {
            if (this.canOccupySquare(xSrc, i, board)) {
                validMoves.add(board[xSrc][i]);
                if (board[xSrc][i].getPlaceholder() != null)         // if the square has a piece of different color
                    break;
            }
            else
                break;
        }

        //Moving down
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
    public ArrayList<Square> getValidMoves(Square[][] board) {
        int xSrc = this.getLocation().getx();
        int ySrc = this.getLocation().gety();
        ArrayList<Square> validMoves = new ArrayList<Square>();

        //Moving top-right
        for (int i=xSrc+1, j=ySrc+1; i<=7 && j<=7; i++, j++) {
            if (this.canOccupySquare(i, j, board)) {
                validMoves.add(board[i][j]);
                if (board[i][j].getPlaceholder() != null)
                    break;
            }
            else
                break;
        }

        //Moving top-left
        for (int i=xSrc-1, j=ySrc+1; i>=0 && j<=7; i--, j++) {
            if (this.canOccupySquare(i, j, board)) {
                validMoves.add(board[i][j]);
                if (board[i][j].getPlaceholder() != null)
                    break;
            }
            else
                break;
        }

        //Moving bottom-right
        for (int i=xSrc+1, j=ySrc-1; i<=7 && j>=0; i++, j--) {
            if (this.canOccupySquare(i, j, board)) {
                validMoves.add(board[i][j]);
                if (board[i][j].getPlaceholder() != null)
                    break;
            }
            else
                break;
        }

        //Moving bottom-left
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
    public ArrayList<Square> getValidMoves(Square[][] board) {
        int xSrc = this.getLocation().getx();
        int ySrc = this.getLocation().gety();
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
            if (0<=x && x<=7 && 0<=y && y<=7 && this.canOccupySquare(x, y, board))
                validMoves.add(board[x][y]);
        }

        return validMoves;
    }
}

class Pawn extends Piece {

    private boolean hasMoved;

    public Pawn(boolean isWhite) {
        super("Pawn", isWhite);
        this.hasMoved = false;
    }

    public Pawn(boolean isWhite, Square location) {
        super("Pawn", isWhite, location);
        this.hasMoved = false;
    }

    @Override
    public ArrayList<Square> getValidMoves(Square[][] board) {
        int xSrc = this.getLocation().getx();
        int ySrc = this.getLocation().gety();
        ArrayList<Square> validMoves = new ArrayList<Square>();

        // Checking the square forward
        if ((ySrc+1)<=7 && board[xSrc][ySrc+1].getPlaceholder() == null)
            validMoves.add(board[xSrc][ySrc+1]);

        // Checking the diagonals
        if ((xSrc+1)<=7 && (ySrc+1)<=7 && board[xSrc+1][ySrc+1].getPlaceholder() != null) {
            if (board[xSrc+1][ySrc+1].getPlaceholder().getIsWhite() != this.isWhite)
                validMoves.add(board[xSrc+1][ySrc+1]);
        }

        if ((xSrc-1)>=0 && (ySrc+1)<=7 && board[xSrc-1][ySrc+1].getPlaceholder() != null) {
            if (board[xSrc-1][ySrc+1].getPlaceholder().getIsWhite() != this.isWhite)
                validMoves.add(board[xSrc-1][ySrc+1]);
        }

        // Checking the second square forward if the pawn never moved
        if (! this.hasMoved) {
            if ((ySrc+2)<=7 && board[xSrc][ySrc+2].getPlaceholder() == null && board[xSrc][ySrc+1].getPlaceholder() == null)
                validMoves.add(board[xSrc][ySrc+2]);
        }

        return validMoves;
    }
}

//class CheckersPawn extends Piece {
//    public CheckersPawn(boolean isWhite) {
//        super("CheckersPawn", isWhite);
//    }
//
//    public CheckersPawn(boolean isWhite, Square location) {
//        super("CheckersPawn", isWhite, location);
//    }
//
//    @Override
//    public boolean validateMove(Move move) {
//        // TODO Auto-generated method stub
//        return false;
//    }
//
//}





