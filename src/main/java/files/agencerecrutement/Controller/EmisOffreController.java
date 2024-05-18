package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.AbonnementDAO;
import files.agencerecrutement.DAO.EmissionDAO;
import files.agencerecrutement.Model.Abonnement;
import files.agencerecrutement.Model.Categorie;
import files.agencerecrutement.Model.Offre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;

public class EmisOffreController {

    private  Offre offre;
    @FXML
    private TextField titre;
    @FXML
    private ComboBox<Abonnement> ComboboxAbonnement;
    @FXML
    private ComboBox<Categorie> ComboboxAbonnementJr;

    public  void  initData(Offre offre){
        try{
            this.offre = offre;
            this.titre.setText(offre.getTitre());
            // initialiser categories
            ComboboxAbonnementJr.setItems(AbonnementDAO.afficherAbonnementCategorie(offre.getEntreprise().getIdUser()));// done
            ComboboxAbonnementJr.getSelectionModel().selectFirst();

            //filter les journaux par categories
            filtrerJournaux();

        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme :" +ex);
        }
    }

    @FXML
    private  void  filtrerJournaux(){
        try{
            Categorie selectCat =  ComboboxAbonnementJr.getValue();
            if(selectCat != null){
                ComboboxAbonnement.setItems(AbonnementDAO.afficherAbonnementJournal(offre.getEntreprise().getIdUser(), selectCat.getIdcate()));
                ComboboxAbonnement.getSelectionModel().selectFirst();
            }

        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }

    public void AjouterEmission(ActionEvent event){
        try{
            if(ComboboxAbonnement.getValue() != null && ComboboxAbonnementJr.getValue() != null){
                if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Emission offre","Vous voullez sur emis cette offre  ?")){
                    // si offre n est pas emis dans cette abonnment dans meme date emission
                    if( EmissionDAO.verifierEmission(ComboboxAbonnement.getValue().getIdAbo(), Date.valueOf(LocalDate.now()),offre.getIdOffre())){
                        //emis offre
                        EmissionDAO.ajouterEmission(ComboboxAbonnement.getValue().getIdAbo(),Date.valueOf(LocalDate.now()),offre.getIdOffre());
                        //alert
                        AlertsConfirmationsController.showAlertInfo("Offre est bien emis" );
                        //fermer la fenetre
                        Node source = (Node) event.getSource();
                        Stage stage = (Stage) source.getScene().getWindow();
                        stage.close();
                    }
                    else
                        AlertsConfirmationsController.showAlertWarnning("Offre déja emis dans le même journal à la même date!!!");
                }
            }else AlertsConfirmationsController.showAlertWarnning("les donnees sont incorrects !! ");

        }catch(Exception e){
            AlertsConfirmationsController.showAlertWarnning(e.getMessage());
        }
    }
    // event sur button annuler
    @FXML
    private  void AnnulerEditionvent(ActionEvent event){
        try{
            //fermer le fenetre ou initialisez les donnees dans les inputs
            if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Emission offre","Vous voullez sur annuler cette opeartion ?")){
                //fermer la fenetre
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme : " +ex);
        }
    }
}
