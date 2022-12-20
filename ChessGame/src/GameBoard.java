import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.image.Image;


public abstract class GameBoard extends StackPane {

    protected Square[][] board;
    protected int size;
    protected Color color1;
    protected Color color2;
    protected GridPane Board=new GridPane();
    protected ArrayList<Move> gameHistory;
    protected final ArrayList<Square> highlightedSquares= new ArrayList<Square>();
    protected Label statusLabel=new Label();
    protected Button reverseMove = new Button("Reverse Move");
    protected ImageView playSound = new ImageView(new Image("/static/music.png")); 
    String musicFile = new File("src/static/music.mp3").getAbsolutePath();
    public Media music = new Media(new File(musicFile).toURI().toString());
    protected MediaPlayer mediaPlayer = new MediaPlayer(music);
    boolean soundOn = false;

    public GameBoard(int size, Color color1, Color color2) {
        super();
        this.setStyle("-fx-background-color: lightgray;");
        this.size = size;
        this.board = new Square[size][size];
        this.color1 = color1;
        this.color2 = color2;
        int count = 0;

        for (int i = 0; i < size; i++) {

            // Horizontal alignment of images
            ColumnConstraints col = new ColumnConstraints();
            col.setHalignment(HPos.CENTER);
            this.Board.getColumnConstraints().add(col);

            // Vertical alignment of images
            RowConstraints row = new RowConstraints();
            row.setValignment(VPos.CENTER);
            this.Board.getRowConstraints().add(row);

            count++;
            for (int j = 0; j < size; j++) {
                if (count % 2 == 0)
                    board[i][j] = new Square(i, j, this.color1);
                else
                    board[i][j] = new Square(i, j,  this.color2);

                this.Board.add(board[i][j], i, j);
                count++;
            }
        }
        this.statusLabel = new Label("White's turn");
        statusLabel.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #000000; -fx-border-width: 0px; -fx-border-radius: 0px;");
        statusLabel.setFont(new Font("FiraCode", 20));
        statusLabel.setPadding(new Insets(5, 5, 5, 5));
        statusLabel.setTextFill(Color.BLACK);
        statusLabel.setAlignment(Pos.TOP_RIGHT);
        this.setAlignment(statusLabel, Pos.TOP_RIGHT);
        this.getChildren().add(statusLabel);

        this.gameHistory = new ArrayList<Move>();
        reverseMove.setStyle("-fx-background-color: brown;  -fx-border-width: 5px; -fx-text-fill: white");
        this.Board.add(reverseMove, 12, 7);
        reverseMove.setAlignment(Pos.BOTTOM_CENTER);
        String musicFile = new File("src/static/music.mp3").getAbsolutePath();
        playSound.setStyle(" -fx-background-size: 100% 100%; -fx-background-color: #FFFFFF; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 5px;");
        playSound.setFitHeight(50);
        playSound.setFitWidth(50);
        this.getChildren().add(Board);
        this.setAlignment(Board, Pos.CENTER);
        this.setAlignment(playSound, Pos.BOTTOM_RIGHT);
        playSound.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(soundOn){
                    mediaPlayer.stop();
                    soundOn = false;
                }
                else{
                    mediaPlayer.play();
                    soundOn = true;
                }
                event.consume();
            }
        });
        this.getChildren().add(playSound);

    }

    public Square[][] getBoard() {
        return board;
    }

    public void setBoard(Square[][] board) {
        this.board = board;
    }
    public Square getSquare(int x, int y) {
        return board[x][y];
    }

    public abstract void fillBoard();
}


class ChessBoard extends GameBoard implements EventHandler<MouseEvent> {

    public static Square trigger = null;
    private King blackKing;
    private King whiteKing;
    private ArrayList<Piece> whitePieces;
    private ArrayList<Piece> blackPieces;
    private boolean isWhiteTurn;
    private GridPane piecePicker;
    private Alert alert=new Alert(Alert.AlertType.INFORMATION);
    private Piece promotedPiece;
    private Pawn enPassantThreatedPawn;
 //   public Media sound= new Media("file:/src/static/");


    ChessBoard(Color[] colors) {
        super(8, colors[0], colors[1]);
        this.whitePieces = new ArrayList<Piece>();
        this.blackPieces = new ArrayList<Piece>();
        this.isWhiteTurn = true;
        this.reverseMove.setOnAction(e -> {
            if (gameHistory.size() > 0) {
                Move lastMove = gameHistory.get(gameHistory.size() - 1);
                lastMove.reverseMove(this);
                this.gameHistory.remove(lastMove);
            } else {
                System.out.println("Empty History");
            }
        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource("promotion.fxml"));
        try {
            PromotionController controller = new PromotionController();
            loader.setController(controller);
            piecePicker = loader.load();
            for (Node node : piecePicker.getChildren()) {
                if (node instanceof ImageView) {
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        ImageView piece = (ImageView) e.getSource();
                        System.out.println(piece.getId());
                        System.out.println("Clicked");
                        switch (piece.getId()) {
                            case "Queen":
                                promotedPiece = new Queen(isWhiteTurn);
                                break;
                            case "Rook":
                                promotedPiece = new Rook(isWhiteTurn);
                                break;
                            case "Bishop":
                                promotedPiece = new Bishop(isWhiteTurn);
                                break;
                            case "Knight":
                                promotedPiece = new Knight(isWhiteTurn);
                                break;
                        }
                        System.out.println(promotedPiece);
                        e.consume();
                    });
                }
            }
            this.setAlignment(piecePicker, Pos.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.alert.setContentText("Choose a piece to promote to");
        this.alert.setTitle("Promotion");
        this.alert.getDialogPane().setContent(piecePicker);
    }

    public Pawn getEnPassantThreatedPawn() {
        return enPassantThreatedPawn;
    }

    public void setEnPassantThreatedPawn(Pawn enPassantThreatedPawn) {
        this.enPassantThreatedPawn = enPassantThreatedPawn;
    }

    public ArrayList<Piece> getWhitePieces() {
        return whitePieces;
    }

    public void setWhitePieces(ArrayList<Piece> whitePieces) {
        this.whitePieces = whitePieces;
    }

    public ArrayList<Piece> getBlackPieces() {
        return blackPieces;
    }

    public void setBlackPieces(ArrayList<Piece> blackPieces) {
        this.blackPieces = blackPieces;
    }

    public King getBlackKing() {
        return blackKing;
    }

    public void setBlackKing(King blackKing) {
        this.blackKing = blackKing;
    }

    public King getWhiteKing() {
        return whiteKing;
    }

    public void setWhiteKing(King whiteKing) {
        this.whiteKing = whiteKing;
    }

    public ArrayList<Piece> getEnemyPieces(boolean teamIsWhite) {
        if (teamIsWhite)
            return this.getBlackPieces();
        else
            return this.getWhitePieces();
    }

    public ArrayList<Piece> getAlliesPieces(boolean teamIsWhite) {
        return teamIsWhite ? this.getWhitePieces() : this.getBlackPieces();
    }

    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    public void setWhiteTurn(boolean whiteTurn) {
        isWhiteTurn = whiteTurn;
    }

    public boolean isKingInThreat(boolean teamIsWhite) {
        King king;
        if (teamIsWhite)
            king = this.getWhiteKing();
        else
            king = this.getBlackKing();

        ///////////////////////////// Yassir's algorithm ////////////////////////////////////////////

        // Iterating over enemy pieces to see if they can kill him
        for (Piece enemy : getEnemyPieces(teamIsWhite)) {
            if (enemy.validateMove(king.getLocation(), this))
                return true;
        }

        ///////////////////////////// Ilyas's algorithm /////////////////////////////////////////////

//        int x = king.getLocation().getx();
//        int y = king.getLocation().gety();
//
//        // Checking threat by an enemy Pawn
//        Pawn tempPawn = new Pawn(king.getIsWhite(), this.getBoard()[x][y]);
//        for (Square s: tempPawn.getValidMoves(this)) {
//            if (s.getPlaceholder() != null && s.getPlaceholder().getName() == "Pawn")
//                return true;
//        }
//
//        // Checking threat by an enemy Knight
//        Knight tempKnight = new Knight(king.getIsWhite(), this.getBoard()[x][y]);
//        for (Square s: tempKnight.getValidMoves(this)) {
//            if (s.getPlaceholder() != null && s.getPlaceholder().getName() == "Knight")
//                return true;
//        }
//
//        // Checking threat by an enemy Bishop
//        Bishop tempBishop = new Bishop(king.getIsWhite(), this.getBoard()[x][y]);
//        for (Square s: tempBishop.getValidMoves(this)) {
//            if (s.getPlaceholder() != null && s.getPlaceholder().getName() == "Bishop")
//                return true;
//        }
//
//        // Checking threat by an enemy Rook
//        Rook tempRook = new Rook(king.getIsWhite(), this.getBoard()[x][y]);
//        for (Square s: tempRook.getValidMoves(this)) {
//            if (s.getPlaceholder() != null && s.getPlaceholder().getName() == "Rook")
//                return true;
//        }
//
//        // Checking threat by an enemy King
//        King tempKing = new King(king.getIsWhite(), this.getBoard()[x][y]);
//        for (Square s: tempKing.getValidMoves(this)) {
//            if (s.getPlaceholder() != null && s.getPlaceholder().getName() == "King")
//                return true;
//        }
//
//        // Checking threat by an enemy Queen
//        Queen tempQueen = new Queen(king.getIsWhite(), this.getBoard()[x][y]);
//        for (Square s: tempQueen.getValidMoves(this)) {
//            if (s.getPlaceholder() != null && s.getPlaceholder().getName() == "Queen")
//                return true;
//        }

        return false;

    }

    public boolean kingWillBeInThreat(Move move) {
        Piece piece = move.getPiece();
        int x_src = move.getSourceSquare().getx();
        int y_src = move.getSourceSquare().gety();
        int x_dest = move.getDestinationSquare().getx();
        int y_dest = move.getDestinationSquare().gety();

        // Simulating the move (to check if king will be in threat)
        this.getBoard()[x_src][y_src].setPlaceholder(null);
        Piece killedPiece = board[x_dest][y_dest].getPlaceholder();
        if (killedPiece != null)
            this.getEnemyPieces(piece.getIsWhite()).remove(killedPiece);
        this.getBoard()[x_dest][y_dest].setPlaceholder(piece);
        piece.setLocation(board[x_dest][y_dest]);


        // Retrieving the result
        boolean result = this.isKingInThreat(piece.getIsWhite());

        // Resetting the board to initial state
        this.getBoard()[x_src][y_src].setPlaceholder(piece);
        piece.setLocation(board[x_src][y_src]);
        this.getBoard()[x_dest][y_dest].setPlaceholder(killedPiece);
        if (killedPiece != null)
            this.getEnemyPieces(piece.getIsWhite()).add(killedPiece);

        return result;
    }

    public Label getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(Label statusLabel) {
        this.statusLabel = statusLabel;
    }


    @Override
    public void fillBoard() {
        for (int i = 0; i < 8; i++) {
            board[i][1].setPlaceholder(new Pawn(true, board[i][1]));
            board[i][6].setPlaceholder(new Pawn(false, board[i][6]));
        }

        board[0][0].setPlaceholder(new Rook(true, board[0][0]));
        board[0][7].setPlaceholder(new Rook(false, board[0][7]));
        board[1][0].setPlaceholder(new Knight(true, board[1][0]));
        board[6][0].setPlaceholder(new Knight(true, board[6][0]));
        board[1][7].setPlaceholder(new Knight(false, board[1][7]));
        board[6][7].setPlaceholder(new Knight(false, board[6][7]));
        board[2][0].setPlaceholder(new Bishop(true, board[2][0]));
        board[5][0].setPlaceholder(new Bishop(true, board[5][0]));
        board[2][7].setPlaceholder(new Bishop(false, board[2][7]));
        board[5][7].setPlaceholder(new Bishop(false, board[5][7]));
        board[3][0].setPlaceholder(new King(true, board[3][0]));
        board[3][7].setPlaceholder(new King(false, board[3][7]));
        board[4][0].setPlaceholder(new Queen(true, board[4][0]));
        board[4][7].setPlaceholder(new Queen(false, board[4][7]));
        board[7][7].setPlaceholder(new Rook(false, board[7][7]));
        board[7][0].setPlaceholder(new Rook(true, board[7][0]));

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].getPlaceholder() != null) {
                    this.Board.add(board[i][j].getPlaceholder().getImage(), i, j);

                    if (board[i][j].getPlaceholder().getIsWhite())
                        this.whitePieces.add(board[i][j].getPlaceholder());
                    else
                        this.blackPieces.add(board[i][j].getPlaceholder());
                }
            }
        }

        this.Board.setHgap(2);
        this.Board.setVgap(2);
        this.setBlackKing((King) board[3][7].getPlaceholder());
        this.setWhiteKing((King) board[3][0].getPlaceholder());

        this.setAlignment(Pos.BASELINE_LEFT);
    }

    public void removeHighlights() {

        for (Square s : highlightedSquares)
            s.resetColor(this.color1, this.color2);

        this.highlightedSquares.clear();
    }

    public Square getClickedSquare(MouseEvent event) {
        EventTarget target = event.getTarget();
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

    public void highlightValidMoves(Piece piece) {
        this.removeHighlights();
        piece.getLocation().setFill(Color.DARKSLATEBLUE);
        highlightedSquares.add(piece.getLocation());
        ArrayList<Square> moves = piece.getFinalValidMoves(this);
        for (Square s : moves) {
            highlightedSquares.add(s);
            if ((!s.isEmpty()) && piece.isEnemy(s.getPlaceholder()))
                s.setFill(Color.DARKRED);
            else
                s.setFill(Color.LIMEGREEN);
        }
    }

    public void switchTurn() {
        this.isWhiteTurn = !this.isWhiteTurn();
        this.statusLabel.setStyle("-fx-background-color: " + (this.isWhiteTurn() ? "white" : "black") + "; -fx-text-fill: " + (this.isWhiteTurn() ? "black" : "white") + ";");
    }

    public GameStatus getBoardStatus() {

        for (Piece piece : this.getAlliesPieces(isWhiteTurn)) {
            if (!piece.getFinalValidMoves(this).isEmpty())
                return isKingInThreat(isWhiteTurn) ? GameStatus.CHECK : GameStatus.ACTIVE;
        }
        return isKingInThreat(isWhiteTurn) ? GameStatus.CHECKMATE : GameStatus.STALEMATE; // If all pieces don't have any legal moves
    }

    public void updateStatusLabel() {
        String text = "";

        switch (this.getBoardStatus()) {
            case ACTIVE:
                text = (this.isWhiteTurn() ? "White" : "Black") + "'s turn";
                break;

            case CHECKMATE:
                text = (this.isWhiteTurn() ? "White" : "Black") + " is checkmated !";
                break;

            case CHECK:
                text = (this.isWhiteTurn() ? "White" : "Black") + " king is checked !";
                break;

            case STALEMATE:
                text = "Stalemate !";
                break;
        }

        this.getStatusLabel().setText(text);
    }

    public void handle(MouseEvent event) {
        Square clickedSquare = getClickedSquare(event);
        if (clickedSquare != null) {
            if (trigger != null) {
                if (clickedSquare.getFill() == Color.LIMEGREEN || clickedSquare.getFill() == Color.DARKRED) {
                    removeHighlights();
                if((clickedSquare.gety()==0 || clickedSquare.gety()==7)&& trigger.getPlaceholder() instanceof Pawn){
                    this.setAlignment(piecePicker, Pos.CENTER);
                    this.alert.showAndWait();
                    this.promotedPiece.setIsWhite(isWhiteTurn);
                   Promotion p= new Promotion(clickedSquare, clickedSquare, trigger.getPlaceholder(),this.promotedPiece);
                   p.doMove(this);
                   this.promotedPiece=null;
                    this.switchTurn();
                    this.updateStatusLabel();
                    trigger = null;
                    return;
                }
                else{
                    Move move = new Move(trigger, clickedSquare, trigger.getPlaceholder());
                    move.doMove(this);
                    this.switchTurn();
                    this.updateStatusLabel();
                    trigger = null;
                }
                } else {
                    if (clickedSquare.isEmpty()) {
                        removeHighlights();
                        trigger = null;
                    } else {
                        if (clickedSquare.getPlaceholder().getIsWhite() == isWhiteTurn()) {
                            highlightValidMoves(clickedSquare.getPlaceholder());
                            trigger = clickedSquare;
                        } else
                            removeHighlights();
                    }
                }


            } else {
                if (clickedSquare.isEmpty())
                    removeHighlights();
                else {
                    // Show valid moves only for same team pieces
                    if (clickedSquare.getPlaceholder().getIsWhite() == isWhiteTurn()) {
                        highlightValidMoves(clickedSquare.getPlaceholder());
                        trigger = clickedSquare;
                    }

                }
            }
        }


    }

}


