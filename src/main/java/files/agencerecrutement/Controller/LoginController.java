package files.agencerecrutement.Controller;

import files.agencerecrutement.MainApp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {

    private  static  final String EMAIL = "admin";
    private  static  final  String MOT_PASSE ="123456";

    @FXML
    private TextField emailTxt ;
    @FXML
    private PasswordField passwordTxt;

    @FXML
    private void loginEvent(ActionEvent event){
        try{
            if( !emailTxt.getText().isEmpty() &&  !passwordTxt.getText().isEmpty()){
               if( emailTxt.getText().equals(EMAIL) && passwordTxt.getText().equals(MOT_PASSE) ){
                   showAlertWarnning("ok");
                   Parent parent = FXMLLoader.load(getClass().getResource("/files/agencerecrutement/Views/TestView.fxml"));
                   Stage stage = new Stage();
                   stage.setScene(new Scene(parent));
                   stage.setTitle("Home ");
                   stage.setMaximized(true);
                   stage.show();

                   Node source = (Node) event.getSource();
                   Stage stageActuelle = (Stage) source.getScene().getWindow();
                   stageActuelle.close();

               }else{
                   showAlertWarnning("l' Email ou le Mot de passe sont incorrect !!");
               }
            }else{
                showAlertWarnning("Vous voullez remplir tous les champs !!");
            }
        }catch (Exception ex){
            showAlertWarnning("Probleme :"+ex.getMessage());
        }
    }

    @FXML
    public void CloseApp() {
        Platform.exit();
    }
    public void showAlertWarnning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("title");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
