package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.EntrepriseDAO;
import files.agencerecrutement.Model.Entreprise;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ModifierEntrepriseController {

    private Entreprise entreprise ;
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



    //permet de initialiser objet entreprise et initiliser les input
    public  void initData(int idEst){
        try{
            this.entreprise = EntrepriseDAO.SelectEntreprise(idEst);
            if(this.entreprise != null){
                RaisonSocialText.setText(entreprise.getRaisonSocial());
                AdresseTxt.setText(entreprise.getAdresse());
                PhoneTxt.setText(entreprise.getPhone());
                ActiviteTxt.setText(entreprise.getActivites());
                PasswordTxt.setText(entreprise.getPassword());
                UsernameTxt.setText(entreprise.getUserName());
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme :" +ex);
        }
    }
    //event annuler button
    @FXML
    public void AnnulerEntrepriseEvent(ActionEvent event) {
        try{
            //fermer le fenetre ou initialisez les donnees dans les inputs
            if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Modifier Entreprise","Vous voullez sur annuler ces moodifications ?")){
                //fermer la fenetre
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme : " +ex);
        }
    }
    //event rest button
    @FXML
    public void restEntrepiseEvent() {
        try{
            //fermer le fenetre ou initialisez les donnees dans les inputs
            if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Modifier Entreprise","Vous voullez sur de Réinitialiser  les donnees ?")){
                RaisonSocialText.setText(entreprise.getRaisonSocial());
                AdresseTxt.setText(entreprise.getAdresse());
                PhoneTxt.setText(entreprise.getPhone());
                ActiviteTxt.setText(entreprise.getActivites());
                PasswordTxt.setText(entreprise.getPassword());
                UsernameTxt.setText(entreprise.getUserName());
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme : " +ex);
        }
    }

    //event Modifier button
    @FXML
    public void ModifierEntrepriseEvent(ActionEvent event) {
        try{
            if(CheckInput()){
                // si tous les donnes sont remplir
                if(CheckUniciteUserName())
                {
                    // si username est unique
                    if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Modifier Entreprise","Vous voullez sur Modifier cette Entreprise ?")){

                        //modifier au base de donnnee
                        //cree un nouveau objet et meter les donne modifie
                        Entreprise entrepriseModifier =
                                new Entreprise(this.entreprise.getIdUser(), UsernameTxt.getText(),PasswordTxt.getText(), AdresseTxt.getText(),PhoneTxt.getText(),RaisonSocialText.getText(),ActiviteTxt.getText());
                        EntrepriseDAO.ModifierEntreprise(entrepriseModifier);

                        //alert
                        AlertsConfirmationsController.showAlertInfo("Entreprise est bien Modifier");

                        //fermer la fenetre
                        Node source = (Node) event.getSource();
                        Stage stage = (Stage) source.getScene().getWindow();
                        stage.close();
                    }
                }else{
                    AlertsConfirmationsController.showAlertWarnning(" ce user name deja utiliser  !! ");
                }
            }else{
                AlertsConfirmationsController.showAlertWarnning(" les donnees sont incorrects!! ");
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme lors insertion !!" +ex.getMessage());
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
             // on donner id de entreprise pour faire test sur tous les entreprise suaf entreprise veut modifier
             return  !EntrepriseDAO.IsUserNameExist(UsernameTxt.getText(),this.entreprise.getIdUser());
         }catch (Exception ex){
             return  false;
         }
    }
}
