package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.CategorieDAO;
import files.agencerecrutement.DAO.EditionDAO;
import files.agencerecrutement.DAO.JournalDAO;
import files.agencerecrutement.Model.Categorie;
import files.agencerecrutement.Model.Edition;
import files.agencerecrutement.Model.Journal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;

public class AjouterEditionController {

    @FXML
    private ComboBox ComboboxCategorie;
    @FXML
    private ComboBox ComboboxJournal;
    @FXML
    private DatePicker DateParution;
    private ObservableList<Journal>journaux = FXCollections.observableArrayList();


    @FXML
    public  void initialize(){
        try{
            //initiliser list des journaux
            journaux = JournalDAO.afficherJournaux();

            //initialiser la combobox de categorie
            ComboboxCategorie.setItems(CategorieDAO.afficherCategories());
            ComboboxCategorie.getSelectionModel().selectFirst();
            //filter les journals par categorie
            FiltrerJournalByCategorie();
            //initiliser datepiker par date actuelle
            DateParution.setValue(LocalDate.now());

        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }


    //event sur combobox categorie lors seletionner un item
    @FXML
    private  void  FiltrerJournalByCategorie(){
        try{
            //categorie selectionner
            Categorie selectedCategorie= (Categorie) ComboboxCategorie.getValue();

            //cree un list contient les journaux de categorie selectionner
            ObservableList<Journal> filteredJournaux = FXCollections.observableArrayList();

            if(selectedCategorie!=null){
                for(Journal journal:journaux){
                    if(journal.getCategorie().getIdcate() == selectedCategorie.getIdcate())
                        filteredJournaux.add(journal);
                }
            }

            //affectuer au combobox
            ComboboxJournal.setItems(filteredJournaux);
            ComboboxJournal.getSelectionModel().selectFirst();
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }

    @FXML
    private  void AjouterEdition(ActionEvent event){
        try{
            if(DateParution.getValue()!= null && ComboboxCategorie.getValue()!=null && ComboboxJournal.getValue()!=null) { // si tous les donnes sont remplir
                //afficher un dialog de confirmation
                if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Ajouter Edition","Vous voullez sur Ajouter  cette Edition ?")){

                    //journal selectionner
                    Journal journal= (Journal) ComboboxJournal.getValue();
                    //ajouter au base de donnne
                    Edition editionInsert = new Edition(0, Date.valueOf(DateParution.getValue()),journal);
                    EditionDAO.AjouterEdition(editionInsert);
                    //alert
                    AlertsConfirmationsController.showAlertInfo("Edition est bien Ajouter" );
                    //fermer la fenetre
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                }
            }else{
                AlertsConfirmationsController.showAlertWarnning("vous voullez remplir tous les donnees ");
            }

        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }
    // event sur button annuler
    @FXML
    private  void AnnulerEditionvent(ActionEvent event){
        try{
            //fermer le fenetre ou initialisez les donnees dans les inputs
            if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Ajouter Edition","Vous voullez sur annuler cette opeartion ?")){
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
