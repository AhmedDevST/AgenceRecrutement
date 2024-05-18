package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.DemandeurDAO;
import files.agencerecrutement.Model.Demandeur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ModifierDemandeurController {
    private Demandeur demandeur;
    @FXML
    private TextField Nom, Prenom, Telephone, Fax, Adresse, Diplome, Salaire;
    @FXML
    private Spinner NbExper;
    @FXML
    private  TextField PasswordTxt;
    @FXML
    private TextField UsernameTxt;

    @FXML
    public void AnnulerDemandeurEvent(ActionEvent event){
        try{
            //fermer le fenetre ou initialisez les donnees dans les inputs
            if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Modifier Demandeur","Vous voullez sur annuler ces moodifications ?")){
                //fermer la fenetre
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme : " +ex);
        }
    }

    @FXML
    public void restDemandeurEvent(){
        try{
            //fermer le fenetre ou initialisez les donnees dans les inputs
            if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Modifier Demandeur","Vous voullez sur de Réinitialiser les donnees ?")){
                //initialisez les donnees dans les inputs
                Nom.setText(demandeur.getNom());
                Prenom.setText(demandeur.getPrenom());
                Telephone.setText(demandeur.getPhone());
                Fax.setText(demandeur.getFax());
                Adresse.setText(demandeur.getAdresse());
                UsernameTxt.setText(demandeur.getUserName());
                PasswordTxt.setText(demandeur.getPassword());
                Diplome.setText(demandeur.getDiplome());
                Salaire.setText(String.valueOf(demandeur.getSalaire()));
                SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, demandeur.getNbAnneeEx());
                NbExper.setValueFactory(valueFactory1);
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme : " +ex);
        }
    }

   @FXML
    public void ModifierDemandeurEvent(ActionEvent event){
       try{
           if(CheckInput()){
               // si tous les donnes sont remplir
               if(CheckUniciteUserName())
               {
                   // si username est unique
                   if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Modifier Demandeur","Vous voullez sur Modifier ce Demandeur ?")){

                       //modifier au base de donnnee
                       //cree un nouveau objet et meter les donne modifie
                       Demandeur o = new Demandeur(
                               demandeur.getIdUser(),UsernameTxt.getText(),PasswordTxt.getText(),Adresse.getText(),Telephone.getText(),Nom.getText(),Prenom.getText(),
                               Fax.getText(),Integer.parseInt(NbExper.getValue().toString()),Float.parseFloat(Salaire.getText()), Diplome.getText()
                       );
                       DemandeurDAO.ModifierDemandeur(o);
                       AlertsConfirmationsController.showAlertInfo("Demandeur est bien de modifier ");
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
    public void initData(Demandeur demandeur){
        try{
            this.demandeur = demandeur;
            Nom.setText(demandeur.getNom());
            Prenom.setText(demandeur.getPrenom());
            Telephone.setText(demandeur.getPhone());
            Fax.setText(demandeur.getFax());
            Adresse.setText(demandeur.getAdresse());
            UsernameTxt.setText(demandeur.getUserName());
            PasswordTxt.setText(demandeur.getPassword());
            Diplome.setText(demandeur.getDiplome());
            Salaire.setText(String.valueOf(demandeur.getSalaire()));
            SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, demandeur.getNbAnneeEx());
            NbExper.setValueFactory(valueFactory1);
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme :" +ex);
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
            return  !DemandeurDAO.IsUserNameExist(UsernameTxt.getText(),demandeur.getIdUser());
        }catch (Exception ex){
            return  false;
        }
    }
}
