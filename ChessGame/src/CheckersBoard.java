import java.io.Console;
import java.util.ArrayList;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CheckersBoard extends GameBoard{
    public CheckersBoard(int size){
        super(size);
    }


 
    @Override
    public void fillBoard(){
        for (int i = 0; i < 8; i++) {
            for (int j= 0; j<8; j++)
            {
                if (i <= 2 && (i+j) % 2 != 0){
                    board[i][j].setPlaceholder(new CheckersPawn(true,board[i][j]));



                }
                if (j >= 5 && (i+j) % 2 != 0){
                    board[i][j].setPlaceholder(new CheckersPawn(false,board[i][j]));
                    


                }

            }
        }

        
    }

    
}
