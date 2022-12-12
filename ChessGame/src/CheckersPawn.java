/*import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.util.ArrayList;
public class CheckersPawn  {
    protected String name;
    protected boolean isWhite;
    protected Square location;
    protected ImageView image;
    protected boolean hasMoved;

    public CheckersPawn(boolean isWhite , Square location) {
        this.isWhite = isWhite;
        this.location = location;
        if(isWhite){
            this.image = new ImageView(new Image( name + ".png"));
                }
        else{
            this.image = new ImageView(new Image( name + ".png"));
                }

        this.image.setFitHeight(Square.squareWidth - 25);
        this.image.setFitWidth(Square.squareWidth - 25);

    }
    public CheckersPawn(boolean isWhite ) {
            this.isWhite = isWhite;
            this.location = null;



    if(isWhite){
        this.image = new ImageView(new Image( name + ".png"));
            }
    else{
        this.image = new ImageView(new Image(name + ".png"));
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
    /*public boolean isDead() {
        return this.location == null;
    }

    public boolean validateMove(Move move) {
        return true;

        }


    public ArrayList<Square> getValidMoves(CheckersBoard checkersBoard) {
            return null;
        }



}*/

