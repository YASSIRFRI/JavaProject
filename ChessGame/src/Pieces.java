abstract class Piece {
    private String name;
    private Boolean color;
    private int x;
    private int y;
    

    public Piece(String name, Boolean color,int x,int y) {
        this.name = name;
        this.color = color;
        this.x=x;
        this.y=y;
    }

    
    /*
     * This method returns the name of the piece
     */

    public String getName() {
        return name;
    }
    
    /*
     * This method returns the color of the piece
     */

    public Boolean getColor() {
        return color;
    }

    public abstract boolean validateMove()
    

}

/*
 * This class represents a King piece
 */

class King extends Piece {
    public King(Boolean color) {
        super("King", color);
    }
}


class Queen extends Piece {
    public Queen(Boolean color) {
        super("Queen", color);
    }
    

}

class Rook extends Piece {
    public Rook(Boolean color) {
        super("Rook", color);
    }
    

}


class Bishop extends Piece {
    public Bishop(Boolean color) {
        super("Bishop", color);
    }
    

}

class Knight extends Piece {
    public Knight(Boolean color) {
        super("Knight", color);
    }
    

}

class Pawn extends Piece {
    public Pawn(Boolean color) {
        super("Pawn", color);
    }
    

}



