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





    public Game(GameStatus status,GameBoard gameBoard,Move moveHistory[]){
        this.status = status;
        this.gameBoard = gameBoard;
        this.moveHistory = moveHistory;

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

    
}
