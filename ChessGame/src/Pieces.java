abstract class Piece {
    private String name;
    private Boolean color;

    public Piece(String name, Boolean color) {
        this.name = name;
        this.color = color;
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
    

}

/*
 * This class represents a King piece
 */

class King extends Piece {
    public King(Boolean color) {
        super("King", color);
    }



}

