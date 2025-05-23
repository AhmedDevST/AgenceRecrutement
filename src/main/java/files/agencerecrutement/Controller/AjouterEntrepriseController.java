package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.EntrepriseDAO;
import files.agencerecrutement.Model.Entreprise;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class AjouterEntrepriseController {

    @FXML
    private TextField RaisonSocialText ;
    @FXML
    private TextField AdresseTxt ;
    @FXML
    private TextField PhoneTxt ;
    @FXML
    private TextField ActiviteTxt ;
    @FXML
    private  TextField PasswordTxt;
    @FXML
    private TextField UsernameTxt;

   // event sur button ajouter : ajouter entreprise
    @FXML
    private  void AjouterEntrepriseEvent(ActionEvent event){
        try{
            if(CheckInput()){
                // si tous les donnes sont remplir
                if(CheckUniciteUserName()){
                    // si username est unique
                    //afficher un dialog de confirmation
                    if(showConfirmationDialog("Confirmation","Ajouter Entreprise","Vous voullez sur Ajouter cette Entreprise ?")){

                        //ajouter au base de donnne
                        Entreprise entreprise =
                                new Entreprise(0, UsernameTxt.getText(),PasswordTxt.getText(), AdresseTxt.getText(),PhoneTxt.getText(),RaisonSocialText.getText(),ActiviteTxt.getText());
                        EntrepriseDAO.AjouterEntreprise(entreprise);

                        //alert
                        showAlertInfo("Entreprise est bien Ajoute");

                        //fermer la fenetre
                        Node source = (Node) event.getSource();
                        Stage stage = (Stage) source.getScene().getWindow();
                        stage.close();
                    }
                }else{
                    showAlertWarnning(" ce user name deja utiliser  !! ");
                }
            }else{
                showAlertWarnning(" les donnees sont incorrects!! ");
            }
        }catch (Exception ex){
            showAlertWarnning("Probleme lors insertion !!" +ex);
        }
    }
    // event sur button annuler
    @FXML
    private  void AnnulerEntrepriseEvent(ActionEvent event){
        try{
            //fermer le fenetre ou initialisez les donnees dans les inputs
            if(showConfirmationDialog("Confirmation","Ajouter Entreprise","Vous voullez sur annuler cette opeartion ?")){
                //fermer la fenetre
              /*  Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();*/
                //initialisez les donnees dans les inputs
                RaisonSocialText.clear();
                AdresseTxt.clear();
                PhoneTxt.clear();
                ActiviteTxt.clear();
                UsernameTxt.clear();
                PasswordTxt.clear();
            }
        }catch (Exception ex){
              showAlertWarnning("Probleme : " +ex);
        }
    }
    // tester si tous les donnees sont remplir
    private boolean CheckInput(){
        return ( !RaisonSocialText.getText().isEmpty() && !AdresseTxt.getText().isEmpty() &&
                !PhoneTxt.getText().isEmpty() && !ActiviteTxt.getText().isEmpty() &&
                !UsernameTxt.getText().isEmpty() && !PasswordTxt.getText().isEmpty());
    }
    private  boolean CheckUniciteUserName(){
        try {
            // on donner id 0 pour faire test sur tous les entreprise
            return  !EntrepriseDAO.IsUserNameExist(UsernameTxt.getText(),0);
        }catch (Exception ex){
            return  false;
        }
    }
    private  boolean showConfirmationDialog(String title , String Header , String Content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(Header);
        alert.setContentText(Content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }
    public void showAlertWarnning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("title");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void showAlertInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("title");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
