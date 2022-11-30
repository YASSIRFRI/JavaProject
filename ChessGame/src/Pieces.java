import javax.print.attribute.standard.Destination;

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

    public abstract boolean validateMove(Move move);
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
    public boolean validateMove(Move move) {
        // TODO Auto-generated method stub
//        int xDestination= move.getDestination()%8;
//        int yDestination= move.getDestination()/8;
//        if(Math.pow((double)(xDestination-this.x),(double)2)+Math.pow((double)(xDestination-this.x),(double)2)<=2)
//        {
//            return true;
//        }
        return false;
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
    public boolean validateMove(Move move) {
        // TODO Auto-generated method stub
        return false;
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
    public boolean validateMove(Move move) {
        // TODO Auto-generated method stub
        return false;
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





