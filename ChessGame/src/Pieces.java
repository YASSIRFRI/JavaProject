import javax.print.attribute.standard.Destination;

abstract class Piece {
    private String name;
    private Boolean color;
    protected int x;
    protected int y;
   

    public Piece(String name, Boolean color,int x,int y) {
        this.name = name;
        this.color = color;
        this.x=x;
        this.y=y;   
    }

    
    /*
     * This method returns the name of the piece
     */

    public Piece(String string, Boolean color2) {
    }


    public String getName() {
        return name;
    }
    
    /*
     * This method returns the color of the piece
     */

    public Boolean getColor() {
        return color;
    }

    public abstract boolean validateMove(Move move);

/*
 * This class represents a King piece
 */

class King extends Piece {
    public King(Boolean color) {
        super("King", color);
    }

    @Override
    public boolean validateMove(Move move) {
        //if(move.getPiece())
        int xDestination= move.getDestination()%8;
        int yDestination= move.getDestination()/8;
        if(Math.pow((double)(xDestination-this.x),(double)2)+Math.pow((double)(xDestination-this.x),(double)2)<=2)
        {
            return true;
        }
        return false;
    }
}


class Queen extends Piece {
    public Queen(Boolean color) {
        super("Queen", color);
    }

    @Override
    public boolean validateMove(Move move) {
        Piece destPiece=move.getPiece();
        if(destPiece.getColor()==this.getColor())
        {
            return false;
        }
        else
        {
            
        }
        

        return false;
    }
    

}

class Rook extends Piece {
    public Rook(Boolean color) {
        super("Rook", color);
    }

    @Override
    public boolean validateMove() {
        // TODO Auto-generated method stub
        return false;
    }
    

}


class Bishop extends Piece {
    public Bishop(Boolean color) {
        super("Bishop", color);
    }

    @Override
    public boolean validateMove() {
        // TODO Auto-generated method stub
        return false;
        // this is a temp comment
    }
    

}

class Knight extends Piece {
    public Knight(Boolean color) {
        super("Knight", color);
    }

    @Override
    public boolean validateMove() {
        // TODO Auto-generated method stub
        return false;
    }
    

}

class Pawn extends Piece {
    public Pawn(Boolean color) {
        super("Pawn", color);
    }

    @Override
    public boolean validateMove() {
        // TODO Auto-generated method stub
        return false;
    }
    

}

class CheckersPawn extends Piece{
    public CheckersPawn(Boolean color) {
        super("CheckersPawn", color);

    }

    @Override
    public boolean validateMove() {
        // TODO Auto-generated method stub
        return false;
    }

}


}



