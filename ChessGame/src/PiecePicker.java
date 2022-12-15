import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class PiecePicker extends GridPane {

    public Piece selectedPiece = null;

    PiecePicker()
    {
        Square rook= new Square(0,0,new Rook(true),Color.WHITE);
        Square knight= new Square(0,0,new Knight(true),Color.WHITE);
        Square bishop= new Square(0,0,new Bishop(true),Color.WHITE);
        Square queen= new Square(0,0,new Queen(true),Color.WHITE);
        this.add(queen, 0, 0);
        this.add(rook, 1, 0);
        this.add(knight, 2, 0);
        this.add(bishop, 3, 0);
    }

    public void handle(MouseEvent event) {
        Square square = (Square) event.getSource();
        if (square.getPlaceholder() != null && selectedPiece == null) {
            selectedPiece = square.getPlaceholder();
        }
    }
    
}
