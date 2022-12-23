import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.ArrayList;
public class CheckersPawn extends Piece {
    protected String name="Checkers";
    protected boolean isWhite;
    protected ImageView image;
    protected boolean hasMoved;

    public CheckersPawn(boolean isWhite ) {
        super("Checkers", isWhite);
    if(isWhite){
        this.image = new ImageView(new Image("/static/White/"+this.name+ ".png"));
            }
    else{
        this.image = new ImageView(new Image("/static/Black/"+this.name+".png"));
            }

    this.image.setFitHeight(Square.squareWidth - 25);
    this.image.setFitWidth(Square.squareWidth - 25);
    }
    public CheckersPawn(boolean isWhite, Square location) {
        super("Checkers", isWhite, location);
    if(isWhite){
        System.out.println("/static/White/"+this.name+ ".png");
        this.image = new ImageView(new Image("/static/White/"+this.name+ ".png"));
            }
    else{
        this.image = new ImageView(new Image("/static/Black/"+this.name+".png"));
            }
    this.image.setFitHeight(Square.squareWidth - 25);
    this.image.setFitWidth(Square.squareWidth - 25);
    }


    public void setName(String name) {
        this.name = name;
    }
    public void setIsWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }
    public ImageView getImage(){
        return this.image;
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

    public boolean validateMove(Move move) {
        return true;

        }

    @Override
    public ArrayList<Square> getValidMoves(GameBoard chekersBoard) {
        ArrayList<Square> validMoves = new ArrayList<Square>();
        int x = this.location.getx();
        int y = this.location.gety();
        if (this.isWhite) {
            if (x + 1 < 8 && y + 1 < 8 && chekersBoard.getSquare(x + 1, y + 1).isEmpty()) {
                validMoves.add(chekersBoard.getSquare(x + 1, y + 1));
            }
            if (x - 1 >= 0 && y + 1 < 8 && chekersBoard.getSquare(x - 1, y + 1).isEmpty()) {
                validMoves.add(chekersBoard.getSquare(x - 1, y + 1));
            }
        } else {
            if (x + 1 < 8 && y + 1 >= 0 && chekersBoard.getSquare(x + 1, y + 1).isEmpty()) {
                validMoves.add(chekersBoard.getSquare(x + 1, y + 1));
            }
            if (x - 1 >= 0 && y + 1 >= 0 && chekersBoard.getSquare(x - 1, y + 1).isEmpty()) {
                validMoves.add(chekersBoard.getSquare(x - 1, y + 1));
            }


        }
        return validMoves;
}
}

class CheckersKing extends CheckersPawn
{

    public CheckersKing(boolean isWhite) {
        super(isWhite);
        this.name = "CheckersKing";
        if(isWhite){
            this.image = new ImageView(new Image("/static/White/" +this.name+ ".png"));
        }
        else{
            this.image = new ImageView(new Image("/static/Black/" +this.name+ ".png"));
        }
        this.image.setFitHeight(Square.squareWidth - 25);
        this.image.setFitWidth(Square.squareWidth - 25);
    }

    @Override
    public ArrayList<Square> getValidMoves(GameBoard chekersBoard) {
        ArrayList<Square> validMoves = new ArrayList<Square>();
        validMoves.addAll(super.getValidMoves(chekersBoard));
        CheckersPawn tmp = new CheckersPawn(!this.isWhite);
        tmp.setLocation(this.location);
        this.getLocation().setPlaceholder(tmp);
        validMoves.addAll(tmp.getValidMoves(chekersBoard));
        this.getLocation().setPlaceholder(this);
        System.out.println("King valid moves: " + validMoves.size());
        return validMoves;
}
}