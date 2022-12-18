import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class PromotionController {

    @FXML
    void getPromotion(MouseEvent event) {
        ImageView image = (ImageView) event.getSource();
        Image img = image.getImage();
        String name = img.getUrl();
        name = name.substring(name.lastIndexOf('/') + 1);
        name = name.substring(0, name.lastIndexOf('.'));
        System.out.println(name);
    }

}
