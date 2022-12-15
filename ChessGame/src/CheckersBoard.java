
import java.util.ArrayList;
import java.io.Console;

import javax.sound.midi.SysexMessage;

import javafx.event.EventTarget;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class CheckersBoard extends GameBoard implements EventHandler<MouseEvent>{
    private ArrayList<CheckersPawn> whitePieces;
    private ArrayList<CheckersPawn> blackPieces;
    public static Square triggerer;
    public ArrayList<Square> highlightedSquares=new ArrayList<Square>();
    public boolean isWhiteTurn=true;
    public Label statusLabel;
    public ArrayList<Move> gameHistory;





  


    public CheckersBoard(){
        super(8, null,null );
    }

    public CheckersBoard(Color[] colors)
    {
        super(8, colors[0], colors[1]);
        
        this.statusLabel = new Label("White's turn");
        statusLabel.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 5px;");
        this.add(statusLabel, 8, 1);
        statusLabel.setFont(new Font("FiraCode", 20));
        statusLabel.setAlignment(Pos.TOP_CENTER);
        statusLabel.setTextFill(Color.BLACK);

        this.gameHistory = new ArrayList<Move>();
        Button reverseMove = new Button("Reverse Move");

        reverseMove.setStyle("-fx-background-color: brown; -fx-font-size: 18px; -fx-border-width: 5px; -fx-text-fill: white");
        this.add(reverseMove, 12, 7);
        reverseMove.setAlignment(Pos.BOTTOM_CENTER);

        reverseMove.setOnAction(e -> {
            if (gameHistory.size() > 0) {
                Move lastMove = gameHistory.get(gameHistory.size() - 1);
                lastMove.reverseMove(this);
                this.gameHistory.remove(lastMove);
            } else {
                System.out.println("Empty History");
            }
        });
        
    }
    public ArrayList<CheckersPawn> getWhitePieces() {
        return whitePieces;
    }

    public void setWhitePieces(ArrayList<CheckersPawn> whitePieces) {
        this.whitePieces = whitePieces;
    }

    public ArrayList<CheckersPawn> getBlackPieces() {
        return blackPieces;
    }

    public void setBlackPieces(ArrayList<CheckersPawn> blackPieces) {
        this.blackPieces = blackPieces;
    }

    @Override
    public void fillBoard(){
        for (int i = 0; i < 8; i++) {
            for (int j= 0; j<8; j++)
            {
                if (j <= 2 && (i+j) % 2 != 0){
                    board[i][j].setPlaceholder(new CheckersPawn(true, board[i][j]));
                    board[i][j].getPlaceholder().setLocation(board[i][j]);
                }
                if (j >= 5 && (i+j) % 2 != 0){
                    board[i][j].setPlaceholder(new CheckersPawn(false, board[i][j]));
                    board[i][j].getPlaceholder().setLocation(board[i][j]);

                }
                if (board[i][j].getPlaceholder() != null ) {
                    this.add(board[i][j].getPlaceholder().getImage(), i, j);
                    board[i][j].getPlaceholder().setLocation(board[i][j]);
                }

            }

            }
        }
    public Square getSquare(int x, int y){
        return board[x][y];
    }

    public void removeHighlights() {

        for (Square s : highlightedSquares)
            s.resetColor(this.color1, this.color2);

        this.highlightedSquares.clear();
    }

   
    public Square getClickedSquare(MouseEvent event) {
        EventTarget target =  event.getTarget();
        Square clickedSquare = null;
        if (target instanceof Square)
            clickedSquare = (Square) target;
        else if (target instanceof ImageView) {
            ImageView image = (ImageView) target;
            int x = GridPane.getColumnIndex(image);
            int y = GridPane.getRowIndex(image);
            clickedSquare = this.board[x][y];
        }
        return clickedSquare;
    }
    public ArrayList<Square> getValidMoves(Piece piece) {
        ArrayList<Square> validMoves = new ArrayList<Square>();
        int x = piece.getLocation().getx();
        int y = piece.getLocation().gety();
        if (piece.getIsWhite()) {
            if (x > 0 && y > 0 && board[x - 1][y - 1].isEmpty())
                validMoves.add(board[x - 1][y - 1]);
            if (x < 7 && y > 0 && board[x + 1][y - 1].isEmpty())
                validMoves.add(board[x + 1][y - 1]);
            if (x > 1 && y > 1 && board[x - 1][y - 1].getPlaceholder().getIsWhite() != piece.getIsWhite()
                    && board[x - 2][y - 2].isEmpty())
                validMoves.add(board[x - 2][y - 2]);
            if (x < 6 && y > 1 && board[x + 1][y - 1].getPlaceholder().getIsWhite() != piece.getIsWhite()
                    && board[x + 2][y - 2].isEmpty())
                validMoves.add(board[x + 2][y - 2]);
        } else {
            if (x > 0 && y < 7 && board[x - 1][y + 1].isEmpty())
                validMoves.add(board[x - 1][y + 1]);
            if (x < 7 && y < 7 && board[x + 1][y + 1].isEmpty())
                validMoves.add(board[x + 1][y + 1]);
            if (x > 1 && y < 6 && board[x - 1][y + 1].getPlaceholder().getIsWhite() != piece.getIsWhite()
                    && board[x - 2][y + 2].isEmpty())
                validMoves.add(board[x - 2][y + 2]);
            if (x < 6 && y < 6 && board[x + 1][y + 1].getPlaceholder().getIsWhite() != piece.getIsWhite()
                    && board[x + 2][y + 2].isEmpty())
                validMoves.add(board[x + 2][y + 2]);
        }
        System.out.println(validMoves);
        return validMoves;

    }
    public void handle(MouseEvent event) {
        Square clickedSquare = getClickedSquare(event);
        if (clickedSquare != null) {
            if (triggerer != null) {
                if (clickedSquare.getFill() == Color.LIMEGREEN || clickedSquare.getFill() == Color.DARKRED) {
                    removeHighlights();
                    Move move = new Move(triggerer, clickedSquare, triggerer.getPlaceholder());
                    System.out.println(move);
                    move.doMove(this);
                    this.switchTurn();
                    this.updateStatusLabel();
                    triggerer = null;
                } else {
                    if (clickedSquare.isEmpty()) {
                        removeHighlights();
                        triggerer = null;
                    } else {
                        if (clickedSquare.getPlaceholder().getIsWhite() == isWhiteTurn) {
                            highlightValidMoves(clickedSquare.getPlaceholder());
                            triggerer = clickedSquare;
                        } else
                            removeHighlights();

                    }
                }


            } else {
                if (clickedSquare.isEmpty())
                    removeHighlights();
                else {
                    // Show valid moves only for same team pieces
                    if (clickedSquare.getPlaceholder().getIsWhite() == isWhiteTurn) {
                        clickedSquare.highlightValidMoves();
                        triggerer = clickedSquare;
                    }
                }

    }
}
    }

    private void switchTurn() {
        this.isWhiteTurn = !this.isWhiteTurn;
    }

    public void highlightValidMoves(Piece piece) {
        this.removeHighlights();
        piece.getLocation().setFill(Color.DARKSLATEBLUE);
        highlightedSquares.add(piece.getLocation());
        ArrayList<Square> moves = piece.getValidMoves(this);
        for (Square s : moves) {
            highlightedSquares.add(s);
            if ((!s.isEmpty()) && piece.isEnemy(s.getPlaceholder()))
                s.setFill(Color.DARKRED);
            else
                s.setFill(Color.LIMEGREEN);
        }
    }
    private void updateStatusLabel() {
        if (this.isWhiteTurn)
            this.statusLabel.setText("White's turn");
        else
            this.statusLabel.setText("Black's turn");


    }


}


