import javafx.scene.shape.Rectangle;



public class Square extends Rectangle {
    
    private int x;
    private int y;
    private Piece placeholder;

    public Square(int x, int y, Piece placeholder) {
        this.x = x;
        this.y = y;
        this.placeholder = placeholder;
    }

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
        this.placeholder = null;
    }


    public Piece getPlaceholder() {
        return placeholder;
    }


    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPlaceholder(Piece placeholder) {
        this.placeholder = placeholder;
    }
}
