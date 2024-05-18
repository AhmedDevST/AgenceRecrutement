package files.agencerecrutement.Controller;
import files.agencerecrutement.DAO.*;
import files.agencerecrutement.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class AjoutAbonController {
    @FXML
    private ComboBox ComboboxEntreprise;
    @FXML
    private ComboBox ComboboxJournal;
    @FXML
    private ComboBox ComboboxCategorie;
    @FXML
    private DatePicker DateExp;
    @FXML
    private  Label TypeCatgorieTxt;
    @FXML
    private  Hyperlink VoirTousLink;
    @FXML
    private  Hyperlink VoirPrefrencesLink;
    private  boolean VoirPreferences = true;
    private ObservableList<Journal>journaux = FXCollections.observableArrayList();


    //initialiser la combobox de entreprise par rapport a Parametre + initiliser autres inputs
    public   void initData(Entreprise entreprise){
        try{
            ObservableList<Entreprise> entreprises = FXCollections.observableArrayList();

            if(entreprise != null){
                //affecter a list  un seul entreprise
                entreprises.add(entreprise);
            }else{
                //affecter tous  les  enttreprise
                entreprises= EntrepriseDAO.afficherEntreprises();
            }

            ComboboxEntreprise.setItems(entreprises);
            ComboboxEntreprise.getSelectionModel().selectFirst();

            //initiliser list des journaux
            journaux = JournalDAO.afficherJournaux();

            // Filtrer les Categorie par Entreprise(preferences)
            FiltrerCategorieByEntreprise();

            //filter les journals par categorie
            FiltrerJournalByCategorie();

            //initialiser date
            DateExp.setValue(LocalDate.now());
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    // le filter de categorie va  excuter si on va afficher seulement les prefernces
    @FXML
    private void  FiltrerCategorieByEntreprise(){
        try{

            if(VoirPreferences){
                //initialiser les autres combobox
                ComboboxCategorie.getItems().clear();
                ComboboxJournal.getItems().clear();

                //entreprise selectionner
                Entreprise selectedEntreprise = (Entreprise) ComboboxEntreprise.getValue();
                if(selectedEntreprise != null){
                    ComboboxCategorie.setItems(PreferencesClientDAO.AfficherPreferencesEntreprise(selectedEntreprise.getIdUser()));
                    ComboboxCategorie.getSelectionModel().selectFirst();
                }
            }

        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    @FXML
    private  void  FiltrerJournalByCategorie(){
        try{
            //categorie selectionner
            Categorie selectedCategorie= (Categorie) ComboboxCategorie.getValue();
            if(selectedCategorie != null){
                //cree un list contient les journaux de categorie selectionner
                ObservableList<Journal> filteredJournaux = FXCollections.observableArrayList();
                for(Journal journal:journaux){
                    if(journal.getCategorie().getIdcate() == selectedCategorie.getIdcate())
                        filteredJournaux.add(journal);
                }
                //affectuer au combobox
                ComboboxJournal.setItems(filteredJournaux);
                ComboboxJournal.getSelectionModel().selectFirst();
            }
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }

    // si on va afficher tous les categories  => on charger tous les categorie
    @FXML
    private  void VoirTouscategories(){
        try{

            VoirPreferences = false;
            ComboboxCategorie.setItems(CategorieDAO.afficherCategories());
            ComboboxCategorie.getSelectionModel().selectFirst();
            TypeCatgorieTxt.setText("Tous les Categories");
            VoirTousLink.setVisible(false);
            VoirPrefrencesLink.setVisible(true);
            FiltrerJournalByCategorie();

        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }

    // si on va afficher seulement les prefernces => on filter les categorie par emńtreprise
    @FXML
    private  void VoirLesPrefrences(){
        try{

            VoirPreferences = true;
            TypeCatgorieTxt.setText("les Preferences");
            VoirTousLink.setVisible(true);
            VoirPrefrencesLink.setVisible(false);
            FiltrerCategorieByEntreprise();

        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    public void AjouterAbonnement(ActionEvent event) {
        try{
            if(checkInputs()){ // si tous les donnes sont remplir
                //afficher un dialog de confirmation
                if(showConfirmationDialog("Confirmation","Ajouter Abonnement","Vous voullez sur Ajouter ce Abonnement ?")){
                        //Le journal selectionné
                        Journal j = (Journal) ComboboxJournal.getValue();
                        //L'entreprise selectionné
                        Entreprise e = (Entreprise) ComboboxEntreprise.getValue();
                         //La date selectionné
                        Date d = Date.valueOf(DateExp.getValue());
                         //Vérification si l'entreprise n'a pas des abonnements activé dans ce journal  :
                        if(AbonnementDAO.isCompanyHaveAboInJr(e.getIdUser(), j.getIdJr())){
                            //ajouter au base de donnee
                            AbonnementDAO.AjouterAbonnement(j,e,1,d);
                            showAlertInfo("L'ajout a été effectué ");
                            //fermer la fenetre
                            Node source = (Node) event.getSource();
                            Stage stage = (Stage) source.getScene().getWindow();
                            stage.close();
                        }else{
                                showAlertWarnning("Vous avez déjà un abonnement actif dans ce journal ");
                        }
                }
            }else{
                showAlertWarnning("S'il vous plait, Ajouter vos informations Correct ");
            }
        }
        catch(Exception e){
            showAlertWarnning(e.getMessage());
        }
    }
    //tester si les input ne sont pas vider et la date selectionner est correct
    private  boolean checkInputs(){
        return ComboboxEntreprise.getValue() != null && ComboboxJournal.getValue() != null && DateExp.getValue() != null
                && DateExp.getValue().isAfter(LocalDate.now());
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
    @FXML
    private  void AnnulerAbonnementEvent(ActionEvent event){
        try{
            //fermer le fenetre ou initialisez les donnees dans les inputs
            if(showConfirmationDialog("Confirmation","Annuler opeartion ","Vous êtes sur que vous voullez annuler cette opeartion ?")){
                //fermer la fenetre
               Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        }catch (Exception ex){
            showAlertWarnning("Probleme : " +ex);
        }
    }
}
