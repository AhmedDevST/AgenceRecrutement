package files.agencerecrutement.Controller;
import  files.agencerecrutement.Model.User;
import files.agencerecrutement.DAO.OffreDAO;
import files.agencerecrutement.Model.Entreprise;
import files.agencerecrutement.Model.Offre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AjouterOffreController {
    @FXML
    private TextField titre;
    @FXML
    private TextField competences;
    @FXML
    private Spinner anneeEx;
    @FXML
    private Spinner nbPoste;

    private  User user ;
    public  void initData(User user){
        try{
            this.user = user ;
        } catch (Exception ex) {
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }


    public void AnnulerOffreEvent(ActionEvent event){
        try{
            //fermer le fenetre ou initialisez les donnees dans les inputs
            if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Ajouter Offre","Vous voullez sur annuler cette opeartion ?")){
                //fermer la fenetre
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme : " +ex);
        }

    }
    public void AjouterOffreEvent(ActionEvent event){

        try{
            if(CheckInput()){
                // si tous les donnes sont remplir
                    //afficher un dialog de confirmation
                    if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Ajouter Offre","Vous voullez sur Ajouter cette offre ?")){

                        Offre o = new Offre(
                                0,titre.getText(),competences.getText(),Integer.parseInt(anneeEx.getValue().toString()),Integer.parseInt(nbPoste.getValue().toString()),true,
                                new Entreprise(user.getIdUser(),"")
                        );
                        OffreDAO.AjouterOffre(o);
                        //alert
                        AlertsConfirmationsController.showAlertInfo("Offre est bien Ajoute");

                        //fermer la fenetre
                        Node source = (Node) event.getSource();
                        Stage stage = (Stage) source.getScene().getWindow();
                        stage.close();
                    }
            }else{
                AlertsConfirmationsController.showAlertWarnning(" les donnees sont incorrects!! ");
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme lors insertion !!" +ex);
        }
    }
    // tester si tous les donnees sont remplir
    private boolean CheckInput(){
        return ( !titre.getText().isEmpty() && !competences.getText().isEmpty());
    }
}
