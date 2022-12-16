import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



public class Square extends Rectangle {


    private int x;
    private int y;
    public static double squareWidth = Game.windowWidth / 8;
    private Piece placeholder;
    Color color;


    public Square(int x, int y, Piece placeholder, Color color) {
        super(squareWidth,squareWidth,squareWidth,squareWidth);
        this.x = x;
        this.y = y;
        this.color = color;
        this.setFill(color);
        this.placeholder = placeholder;
    }


    public Square(int x, int y, Color color) {
        super(squareWidth,squareWidth,squareWidth,squareWidth);
        this.x = x;
        this.y = y;
        this.setFill(color);
        this.placeholder = null;
    }

    // Getters and setters
    public Piece getPlaceholder() {
        return placeholder;
    }
   
    public int getx() {
        return x;
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

    public void resetColor(Color color1, Color color2) {
        if ((this.getx() + this.gety()) % 2 != 0)
            this.setFill(color1);
        else
            this.setFill(color2);
    }
    public boolean isEmpty() {
        return this.placeholder == null;
    }


}
