package files.agencerecrutement.Controller;
import  files.agencerecrutement.DAO.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TestController {

    @FXML
    TextArea result ;

    public  void testMethode(){
        try{
           // Utilitaire.getConnection();
           // showAlert("ok");
            //result.setText("ddd");
           Image image = new Image(getClass().getResource("/files/agencerecrutement/Images/editIcon.png").toString());
           ImageView imageView = new ImageView(image);
        }catch (Exception ex){
            showAlert("exception :"+ex.getMessage());

        }
    }
    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("title");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
