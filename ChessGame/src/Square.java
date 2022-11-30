public class Square {

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Piece getPlaceholder() {
        return placeholder;
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
