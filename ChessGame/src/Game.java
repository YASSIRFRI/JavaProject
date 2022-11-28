public class Game {
    public enum GameStatus {
        ACTIVE,
        BLACK_WIN,
        WHITE_WIN,
        FORFEIT,
        STALEMATE,
        RESIGNATION
    }   
    private GameStatus status; 
    private GameBoard gameBoard;
    private Move[] moveHistory;
    private Player[] players;





    public Game(GameStatus status,GameBoard gameBoard,Move[] moveHistory,Player[] players){
        this.status = status;
        this.gameBoard = gameBoard;
        this.moveHistory = moveHistory;
        this.players = players;

    }

    public GameStatus getStatus()
    {
        return status;
    }
  
    public void setStatus(GameStatus status)
    {
        this.status = status;
    }
   


    public Move[] getMoveHistory() {
        return moveHistory;
    }
    public void setMoveHistory(Move[] moveHistory) {
        this.moveHistory = moveHistory;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    
}
