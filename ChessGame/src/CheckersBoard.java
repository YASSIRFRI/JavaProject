
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


public class CheckersBoard extends GameBoard implements EventHandler<MouseEvent>{
    private ArrayList<CheckersPawn> whitePieces;
    private ArrayList<CheckersPawn> blackPieces;
    public static Square triggerer;
    public ArrayList<Square> highlightedSquares=new ArrayList<Square>();
    public boolean isWhiteTurn=true;

    public CheckersBoard(){
        super(8, null,null );
    }

    public CheckersBoard(Color[] colors)
    {
        super(8, colors[0], colors[1]);
        reverseMove.setOnAction(e -> {
            if (gameHistory.size() > 0) {
                Move lastMove = gameHistory.get(gameHistory.size() - 1);
                lastMove.reverseMove(this);
                this.gameHistory.remove(lastMove);
            } else {
                System.out.println("Empty History");
            }
        });
        this.gameHistory = new ArrayList<Move>();
        
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
                    this.Board.add(board[i][j].getPlaceholder().getImage(), i, j);
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
    public static boolean inBoard(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }
    public ArrayList<Square> getValidMoves(CheckersPawn piece) {
        ArrayList<Square> validMoves = new ArrayList<Square>();
        int x = piece.getLocation().getx();
        System.out.println(x);
        int y = piece.getLocation().gety();
        System.out.println(y);
        if (piece.getIsWhite()) {
            if (inBoard(x+1, y+1) && board[x + 1][y + 1].isEmpty())
                validMoves.add(board[x + 1][y + 1]);
            if(inBoard(x-1, y+1) && board[x - 1][y + 1].isEmpty())
                validMoves.add(board[x - 1][y + 1]);
            if(inBoard(x+2, y+2) && board[x + 2][y + 2].isEmpty() && board[x + 1][y + 1].getPlaceholder() != null && board[x + 1][y + 1].getPlaceholder().getIsWhite() == false)
            {
                System.out.println("test");
                validMoves.add(board[x + 2][y + 2]);
            }
            if(inBoard(x-2, y+2) && board[x - 2][y + 2].isEmpty() && board[x - 1][y + 1].getPlaceholder() != null && board[x - 1][y + 1].getPlaceholder().getIsWhite() == false)
                validMoves.add(board[x - 2][y + 2]);
            if(inBoard(x+4, y+4) && board[x + 4][y + 4].isEmpty()&& board[x+2][y+2].isEmpty() && board[x + 1][y + 1].getPlaceholder() != null && board[x + 1][y + 1].getPlaceholder().getIsWhite() == false && board[x+3][y +3].getPlaceholder() != null && board[x + 3][y + 3].getPlaceholder().getIsWhite() == false)
                validMoves.add(board[x + 4][y + 4]);
            if(inBoard(x-4, y+4) && board[x - 4][y + 4].isEmpty()&&board[x-2][y+2].isEmpty() && board[x - 1][y + 1].getPlaceholder() != null && board[x - 1][y + 1].getPlaceholder().getIsWhite() == false && board[x-3][y +3].getPlaceholder() != null && board[x - 3][y + 3].getPlaceholder().getIsWhite() == false)
                validMoves.add(board[x - 4][y + 4]);
            
        } 
        else {
            if (inBoard(x-1, y-1) && board[x - 1][y - 1].isEmpty())
            {
                validMoves.add(board[x - 1][y - 1]);
            }
            if (inBoard(x+1, y-1) && board[x + 1][y - 1].isEmpty())
            {
                validMoves.add(board[x + 1][y - 1]);
            }
            if(inBoard(x-2, y-2) && board[x - 2][y - 2].isEmpty() && board[x - 1][y - 1].getPlaceholder() != null && board[x - 1][y - 1].getPlaceholder().getIsWhite() == true)
                validMoves.add(board[x - 2][y - 2]);
            if(inBoard(x+2, y-2) && board[x + 2][y - 2].isEmpty() && board[x + 1][y - 1].getPlaceholder() != null && board[x + 1][y - 1].getPlaceholder().getIsWhite() == true)
                validMoves.add(board[x + 2][y - 2]);
            if(inBoard(x-4, y-4) && board[x - 4][y - 4].isEmpty()&&board[x-2][y-2].isEmpty() && board[x - 1][y - 1].getPlaceholder() != null && board[x - 1][y - 1].getPlaceholder().getIsWhite() == true && board[x-3][y -3].getPlaceholder() != null && board[x - 3][y - 3].getPlaceholder().getIsWhite() == true)
                validMoves.add(board[x - 4][y - 4]);
            if(inBoard(x+4, y-4) && board[x + 4][y - 4].isEmpty()&&board[x+2][y-2].isEmpty() && board[x + 1][y - 1].getPlaceholder() != null && board[x + 1][y - 1].getPlaceholder().getIsWhite() == true && board[x+3][y -3].getPlaceholder() != null && board[x + 3][y - 3].getPlaceholder().getIsWhite() == true)
                validMoves.add(board[x + 4][y - 4]);
            
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
                    if(clickedSquare.gety()==0 || clickedSquare.gety()==7 )
                    {
                        Promotion p=new Promotion(triggerer, clickedSquare, triggerer.getPlaceholder(),new CheckersKing(isWhiteTurn));
                        p.doMove(this);
                        this.switchTurn();
                        this.updateStatusLabel();
                        triggerer = null;
                    }
                    else
                    {
                    Move move = new Move(triggerer, clickedSquare, triggerer.getPlaceholder());
                    move.doMove(this);
                    this.switchTurn();
                    this.updateStatusLabel();
                    triggerer = null;
                    }

                } else {
                    if (clickedSquare.isEmpty()) {
                        removeHighlights();
                        triggerer = null;
                    }

                     else {
                        if (clickedSquare.getPlaceholder().getIsWhite() == isWhiteTurn) {
                            highlightValidMoves((CheckersPawn) clickedSquare.getPlaceholder());
                            triggerer = clickedSquare;
                        } else
                            removeHighlights();
                    }
                }
            } 
            else 
            {
                System.out.println("triggerer is null");
                if (clickedSquare.isEmpty())
                    removeHighlights();
                else {
                    System.out.println("clicked square is not empty");
                    System.out.println("clicked square is white: " + clickedSquare.getPlaceholder().getIsWhite());
                    if (clickedSquare.getPlaceholder().getIsWhite() == isWhiteTurn) {
                        System.out.println("highlighting valid moves");
                        highlightValidMoves((CheckersPawn) clickedSquare.getPlaceholder());
                        triggerer = clickedSquare;
                    }
                }

    }
}
    }

    public void switchTurn() {
        this.isWhiteTurn = !this.isWhiteTurn;
    }

    public void highlightValidMoves(CheckersPawn piece) {
        System.out.println("highlighting valid moves");
        this.removeHighlights();
        piece.getLocation().setFill(Color.DARKSLATEBLUE);
        highlightedSquares.add(piece.getLocation());
        ArrayList<Square> moves = this.getValidMoves(piece);
        System.out.println("valid moves: ");
        System.out.println(moves);
        for (Square s : moves) {
            highlightedSquares.add(s);
            if ((!s.isEmpty()) && piece.isEnemy(s.getPlaceholder()))
                s.setFill(Color.DARKRED);
            else
                s.setFill(Color.LIMEGREEN);
        }
    }
    public void updateStatusLabel() {
        if (this.isWhiteTurn)
            this.statusLabel.setText("White's turn");
        else
            this.statusLabel.setText("Black's turn");


    }


}


