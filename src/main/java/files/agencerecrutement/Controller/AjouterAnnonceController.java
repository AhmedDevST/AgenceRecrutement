package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.*;
import files.agencerecrutement.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Optional;

public class AjouterAnnonceController {

    private ObservableList<Journal> journaux = FXCollections.observableArrayList();
    private  ObservableList<Edition> editions = FXCollections.observableArrayList();
    @FXML
    private ComboBox ComboboxCategorie;
    @FXML
    private ComboBox ComboboxJournal;
    @FXML
    private ComboBox ComboboxEdition;
    @FXML
    private ComboBox ComboboxOffre;

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

            //initiliser list des edition
            editions = EditionDAO.afficherEditions();
            //filter les editions  et les offres par journal
            FiltrerEditionOffreByJournal();
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
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
            for(Journal journal:journaux){
                if(journal.getCategorie().getIdcate() == selectedCategorie.getIdcate())
                    filteredJournaux.add(journal);
            }

            //affectuer au combobox
            ComboboxJournal.setItems(filteredJournaux);
            ComboboxJournal.getSelectionModel().selectFirst();

        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }

    @FXML
    private  void  FiltrerEditionOffreByJournal(){
        try{
            //Journal selectionner
            Journal selectedJournal= (Journal) ComboboxJournal.getValue();
            if(selectedJournal!=null){

                //cree un list contient les edition de Journal selectionner
                ObservableList<Edition> filteredEditions = FXCollections.observableArrayList();
                for(Edition edition:editions){
                    if(edition.getJournal().getIdJr() == selectedJournal.getIdJr())
                        filteredEditions.add(edition);
                }
                //affectuer au combobox editions
                ComboboxEdition.setItems(filteredEditions);
                ComboboxEdition.getSelectionModel().selectFirst();

                //cree un list contient les offre de Journal selectionner
                ObservableList<Offre> filteredOffres = FXCollections.observableArrayList();
                //affectuer au combobox offre
                ComboboxOffre.setItems(OffreDAO.ListOffresValides(selectedJournal.getIdJr()));
                ComboboxOffre.getSelectionModel().selectFirst();
            }
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }

    @FXML
    private void AjouterAnnonceEvent(ActionEvent event){
        try{
            if(CheckInput()){ // si tous les donnes sont remplir
                //afficher un dialog de confirmation
                if(showConfirmationDialog("Confirmation","Ajouter annonce","Vous voullez sur Ajouter cette annonce ?")){

                    //Edition selectionner
                    Edition edition= (Edition) ComboboxEdition.getValue();
                    //offre selectionner
                    Offre offre = (Offre) ComboboxOffre.getValue();
                    //ajouter au base de donnne
                    AnnonceDAO.AjouterAnnonce(offre.getIdOffre(),edition.getNumSequentiel());

                    //alert
                    showAlertInfo("Annonce est bien Ajoute");

                    //fermer la fenetre
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                }
            }else{
                showAlertWarnning("vous voullez remplir tous les donnees ");
            }
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }

    private boolean CheckInput() {
        return ComboboxCategorie.getValue() != null && ComboboxJournal.getValue() != null
                && ComboboxEdition.getValue() != null && ComboboxOffre.getValue() != null;
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
