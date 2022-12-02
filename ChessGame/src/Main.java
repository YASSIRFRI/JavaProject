import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) {
    GridPane pane = new GridPane();
    int count = 0;
    double s = 100; 
    for (int i = 0; i < 8; i++) {
      count++;
      for (int j = 0; j < 8; j++) {
        Rectangle r = new Rectangle(s, s, s, s);
        if (count % 2 == 0)
          r.setFill(Color.BLACK);
        else 
          r.setFill(Color.WHITE);
        pane.add(r, j, i);
        count++;
      }
    }
    ImageView iv = new ImageView(new Image("file:static/White/Knight.png"));
     pane.add(iv,1,0);
    Scene scene = new Scene(pane,700,700);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
 
}
 