import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



public class Square extends Rectangle {


    private int x;
    private int y;
    public static double squareWidth = Game.windowWidth / 8;
    private Piece placeholder;
    private CheckersPawn placeHolder;


    public Square(int x, int y, Piece placeholder) {
        super(squareWidth,squareWidth,squareWidth,squareWidth);
        this.x = x;
        this.y = y;
        this.placeholder = placeholder;
    }

    public Square(int x, int y, CheckersPawn placeHolder) {
        super(squareWidth,squareWidth,squareWidth,squareWidth);
        this.x = x;
        this.y = y;
        this.placeHolder = placeHolder;

    }

    public Square(int x, int y, Color color) {
        super(squareWidth,squareWidth,squareWidth,squareWidth);
        this.x = x;
        this.y = y;
        this.setFill(color);
        this.placeholder = null;
        this.placeHolder = null;
    }

    // Getters and setters
    public Piece getPlaceholder() {
        return placeholder;
    }
    public CheckersPawn getPlaceHolder() {
        return placeHolder;
    }
    public void setPlaceHolder(CheckersPawn placeHolder) {
        this.placeHolder = placeHolder;
    }
    public int getx() {
        return x;
    }
    public boolean hasPiece() {
        return placeHolder != null;
    }

    public int gety() {
        return y;
    }

    public void setx(int x) {
        this.x = x;
    }

    public void sety(int y) {
        this.y = y;
    }

    public void setPlaceholder(Piece placeholder) {
        this.placeholder = placeholder;
    }

    public boolean isEmpty() {
        return this.getPlaceholder() == null;
    }

    public void resetColor() {
        if ((this.getx() + this.gety()) % 2 != 0)
            this.setFill(Color.BLUE);
        else
            this.setFill(Color.DEEPSKYBLUE);
    }
}
