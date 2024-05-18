package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.OffreDAO;
import files.agencerecrutement.Model.Entreprise;
import files.agencerecrutement.Model.Offre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ModifierOffreController {
    private Offre offre;
    @FXML
    private TextField titre;
    @FXML
    private TextField competences;
    @FXML
    private Spinner nbPoste, anneeEx;

    public  void initData(Offre offre){
        try{
            this.offre = offre;
            titre.setText(offre.getTitre());
            competences.setText(offre.getCompetences());
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, offre.getNbPostOfferts());
            nbPoste.setValueFactory(valueFactory);
            SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, offre.getNbAnneeEx());
            anneeEx.setValueFactory(valueFactory1);
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme :" +ex);
        }
    }

    @FXML
    public void restOffreEvent(){
        try{
            //fermer le fenetre ou initialisez les donnees dans les inputs
            if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Modifier Offre","Vous voullez sur de Réinitialiser  les donnees?")){
                titre.setText(offre.getTitre());
                competences.setText(offre.getCompetences());
                SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, offre.getNbPostOfferts());
                nbPoste.setValueFactory(valueFactory);
                SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, offre.getNbAnneeEx());
                anneeEx.setValueFactory(valueFactory1);
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme : " +ex);
        }
    }

    @FXML
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
    public void ModifierOffreEvent(ActionEvent event){
        try{
            if(CheckInput()){
                // si tous les donnes sont remplir
                //afficher un dialog de confirmation
                if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Modifier Offre","Vous voullez sur Modifier cette offre ?")){

                    Offre o = new Offre(
                            offre.getIdOffre(),titre.getText(),competences.getText(),Integer.parseInt(anneeEx.getValue().toString()),Integer.parseInt(nbPoste.getValue().toString()),true,
                            new Entreprise(offre.getEntreprise().getIdUser(),"")
                    );
                    OffreDAO.ModifierOffre(o);
                    //alert
                    AlertsConfirmationsController.showAlertInfo("Offre est bien Modifier");

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
