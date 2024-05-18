package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.DemandeurDAO;
import files.agencerecrutement.DAO.EntrepriseDAO;
import files.agencerecrutement.DAO.UserDAO;
import files.agencerecrutement.MainApp;
import files.agencerecrutement.Model.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {

    private  static  final String EMAIL = "admin";
    private  static  final  String MOT_PASSE ="123456";

    @FXML
    private TextField userNameTxt ;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private Button btnAgent;
    @FXML
    private Button btnEntreprise;
    @FXML
    private Button btnDemandeur;
    private  int RoleUser = 0;

    @FXML
    private void loginEvent(ActionEvent event){
        try{
            if( !userNameTxt.getText().isEmpty() &&  !passwordTxt.getText().isEmpty() && RoleUser!= 0 ){
                 switch (RoleUser){
                       case 1:
                           //agent
                           User userADmin= UserDAO.LoginAgent(userNameTxt.getText(),passwordTxt.getText());
                           if(userADmin != null){
                               //afficher acceuil de admin
                               FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/menu.fxml"));
                               Parent parent = fxmlLoader.load();
                               //cree instance de controller Menu
                               Menu menu = fxmlLoader.getController();
                               //passer au controller  l objet user
                               menu.setUser(userADmin);
                               Stage stage = new Stage();
                               Scene scene = new Scene(parent);
                               scene.getStylesheets().add(String.valueOf(getClass().getResource("/files/agencerecrutement/Styles/StyleContextMenu.css")));
                               stage.setScene(scene);
                               stage.setTitle("Home Agent");
                               stage.setMaximized(true);
                               stage.show();
                               Node source = (Node) event.getSource();
                               Stage stageActuelle = (Stage) source.getScene().getWindow();
                               stageActuelle.close();

                           }else
                               showAlertWarnning("User name ou mot de passe sont incorrect !!");
                           break;
                       case 2:
                           //entreprise
                           User userEntreprise= EntrepriseDAO.Login(userNameTxt.getText(),passwordTxt.getText());
                           if(userEntreprise != null){
                               //afficher acceuil de entreprise
                               FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/menuEntreprise.fxml"));
                               Parent parent = fxmlLoader.load();
                               //cree instance de controller Menu
                               menuEntrepriseController menuEntrepriseController = fxmlLoader.getController();
                               //passer au controller  l objet user
                               menuEntrepriseController.setUser(userEntreprise);
                               Stage stage = new Stage();
                               Scene scene = new Scene(parent);
                               scene.getStylesheets().add(String.valueOf(getClass().getResource("/files/agencerecrutement/Styles/StyleContextMenu.css")));
                               stage.setScene(scene);
                               stage.setTitle("Home Entreprise");
                               stage.setMaximized(true);
                               stage.show();
                               Node source = (Node) event.getSource();
                               Stage stageActuelle = (Stage) source.getScene().getWindow();
                               stageActuelle.close();

                           }else
                               showAlertWarnning("User name ou mot de passe sont incorrect !!");
                            break;
                       case 3:
                           //demandeur
                           User userDemandeur= DemandeurDAO.Login(userNameTxt.getText(),passwordTxt.getText());
                           if(userDemandeur != null){
                               //afficher acceuil de demandeur
                               FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/MenuDemandeur.fxml"));
                               Parent parent = fxmlLoader.load();
                               //cree instance de controller demandeurController
                               menuDemandeurController demandeurController = fxmlLoader.getController();
                               //passer au controller  l objet user
                               demandeurController.setUser(userDemandeur);
                               Stage stage = new Stage();
                               Scene scene = new Scene(parent);
                               scene.getStylesheets().add(String.valueOf(getClass().getResource("/files/agencerecrutement/Styles/StyleContextMenu.css")));
                               stage.setScene(scene);
                               stage.setTitle("Home Demandeur");
                               stage.setMaximized(true);
                               stage.show();
                               Node source = (Node) event.getSource();
                               Stage stageActuelle = (Stage) source.getScene().getWindow();
                               stageActuelle.close();

                           }else
                               showAlertWarnning("User name ou mot de passe sont incorrect !!");
                           break;
                 }
            }
            else{
                showAlertWarnning("Vous voullez remplir tous les champs !!");
            }
        }catch (Exception ex){
            showAlertWarnning("Probleme :"+ex.getMessage());
        }
    }


    //click sur button agent
    @FXML
    private  void ClickAgent(){
        try{
            setStyleSelected(btnAgent);
            setStyleNotSelected(btnDemandeur);
            setStyleNotSelected(btnEntreprise);
            RoleUser = 1;
        }catch (Exception ex){
            showAlertWarnning("Probleme :"+ex.getMessage());
        }
    }
    //click sur button entreprise
    @FXML
    private  void ClickEntreprise(){
        try{
            setStyleSelected(btnEntreprise);
            setStyleNotSelected(btnDemandeur);
            setStyleNotSelected(btnAgent);
            RoleUser = 2;
        }catch (Exception ex){
            showAlertWarnning("Probleme :"+ex.getMessage());
        }
    }
    //click sur button Demandeur
    @FXML
    private  void ClickDemandeur(){
        try{
            setStyleSelected(btnDemandeur);
            setStyleNotSelected(btnAgent);
            setStyleNotSelected(btnEntreprise);
            RoleUser = 3;
        }catch (Exception ex){
            showAlertWarnning("Probleme :"+ex.getMessage());
        }
    }
    private void setStyleSelected(Button btn){
        btn.setStyle("-fx-background-color: #B68FF8 ; -fx-text-fill:  white; -fx-border-color : #B68FF8;");
    }
    private void setStyleNotSelected(Button btn){
        btn.setStyle("-fx-background-color: white ; -fx-text-fill:  #8D61DA; -fx-border-color : #8D61DA;");
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
