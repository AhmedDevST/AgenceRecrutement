package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.DemandeurDAO;
import files.agencerecrutement.Model.Demandeur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AjouterDemandeurController {
    @FXML
    private TextField Nom, Prenom, Adresse, Fax, Telephone, Salaire,Diplome;
    @FXML
    private Spinner NbExper;
    @FXML
    private  TextField PasswordTxt;
    @FXML
    private TextField UsernameTxt;

    @FXML
    public void AjouterDemandeurEvent(ActionEvent event){
        try{
            if(CheckInput()){
                // si tous les donnes sont remplir
                if(CheckUniciteUserName()){
                    // si username est unique
                    //afficher un dialog de confirmation
                    if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Ajouter Demandeur","Vous voullez sur Ajouter ce Demandeur ?")){

                        //ajouter au base de donnne
                                Demandeur d = new Demandeur(
                                0,UsernameTxt.getText(),PasswordTxt.getText(), Adresse.getText(),Telephone.getText(),Nom.getText(),Prenom.getText(),
                                        Fax.getText(),Integer.parseInt(NbExper.getValue().toString()),Float.parseFloat(Salaire.getText()), Diplome.getText()
                                );
                        DemandeurDAO.AjouterDemandeur(d);
                        //alert
                        AlertsConfirmationsController.showAlertInfo("Demandeur est bien Ajoute");

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
            AlertsConfirmationsController.showAlertWarnning("Probleme lors insertion !!" +ex);
        }
    }


    @FXML
    public void AnnulerDemandeurEvent(ActionEvent event){
        try{
            //fermer le fenetre ou initialisez les donnees dans les inputs
            if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Ajouter Entreprise","Vous voullez sur annuler cette opeartion ?")){
                //fermer la fenetre
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme : " +ex);
        }
    }
    // tester si tous les donnees sont remplir
    private boolean CheckInput(){
        return ( !Nom.getText().isEmpty() && !Prenom.getText().isEmpty() &&
                !Adresse.getText().isEmpty() && !Telephone.getText().isEmpty() &&
                !Salaire.getText().isEmpty() && !Diplome.getText().isEmpty() &&
                !UsernameTxt.getText().isEmpty() && !PasswordTxt.getText().isEmpty());
    }
    private  boolean CheckUniciteUserName(){
        try {
            // on donner id 0 pour faire test sur tous les demandeur
            return  !DemandeurDAO.IsUserNameExist(UsernameTxt.getText(),0);
        }catch (Exception ex){
            return  false;
        }
    }
}
