package files.agencerecrutement.Controller;
import files.agencerecrutement.DAO.AbonnementDAO;
import files.agencerecrutement.DAO.CategorieDAO;
import files.agencerecrutement.DAO.Utilitaire;
import files.agencerecrutement.Model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
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
import java.util.Objects;

public class AbonnementController {

    private ObservableList<Abonnement> Abonnements = FXCollections.observableArrayList();

    @FXML
    private TableView<Abonnement> tableAbonnements;

    @FXML
    private TableColumn<Abonnement, String> colNomEntreprise;

    @FXML
    private TableColumn<Abonnement, String> colNomJournal;

    @FXML
    private TableColumn<Abonnement, String> colEtatAbonnement;

    @FXML
    private TableColumn<Abonnement, Date> colDateExpiration;

    @FXML
    private  Button ajoutAbon;


    @FXML
    private TextField searchtext;


    private  User user ;
    private int Id ;
    // ce variable utilliser dans le cas de user agent : si agent veux voir les abonnment d un entreprise journal
    // Id = 0 => afficher tous abonnment de tous entreprise ou journals
    //sinon => afficher abonnment d un entreprise ou journal
    private boolean ByJournal ;
    // ce variable utilliser dans le cas de user agent : si agent veux voir les abonnment  d un entreprise ou d un journal
    // ByJournal =  true  => afficher tous abonnment de  journal
    //sinon => afficher abonnment d un entreprise


    //initialiser interface en fonction de user
    public  void initData(User user,int Id,boolean ByJournal){
        try{
            this.user = user ;
            this.Id = Id;
            this.ByJournal = ByJournal;
            //set les droit de user
            switch (user.getRoleUser()){
                case  1 :
                    //agent
                    // agent a droit de ajouter des abonnemnt
                    ajoutAbon.setVisible(true);
                    // agent a droit serach par  journal et eaison social
                    searchtext.setPromptText("Recherche par Raison socile  ou journal");
                    break;
                case  2 :
                    //entreprise
                    //demandeur n est pas droit de ajouter abonnemnt
                    ajoutAbon.setVisible(false);
                    // agent a droit serach par  journal
                    searchtext.setPromptText("Recherche par nom de journal");
                    break;
            }
            ChargerVueTableau();
        } catch (Exception ex) {
            showAlertWarnning(ex.getMessage());
        }
    }

    private void ChargerDonnée() {
        try {
            if(user.getRoleUser() == 1){
               // si user est un agent
                if(Id ==  0){
                    // afficher tous  abonnements
                    Abonnements = AbonnementDAO.afficherAbonnements();
                }else{
                    if(ByJournal){
                        // afficher tous  abonnements d un journal
                        Abonnements = AbonnementDAO.afficherAbonnementsOfJourna(Id);
                    }else
                    // afficher tous  abonnements d un entreprise
                        Abonnements = AbonnementDAO.afficherAbonnementsOfEntreprise(Id);
                }

            }else{
                //si user est un entreprise
                // afficher tous  abonnements
                Abonnements = AbonnementDAO.afficherAbonnementsOfEntreprise(user.getIdUser());
            }
        } catch (Exception ex) {
            showAlertWarnning(ex.getMessage());
        }
    }

    private void ChargerVueTableau() {
        ChargerDonnée(); // Appelez d'abord la méthode pour charger les données
        // Utiliser PropertyValueFactory avec le même nom d'attribut sur l'objet abonnement
        colNomJournal.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJournal().getNomJr()));
        colNomEntreprise.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEntreprise().getRaisonSocial()));
        colEtatAbonnement.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().ReturnEtatString()));
        colDateExpiration.setCellValueFactory(new PropertyValueFactory<>("dateExpiration"));

        tableAbonnements.setItems(Abonnements);
    }
    @FXML
    public void ajouterAbonAction() {
        try {
            // Charger le fichier FXML de l'interface AjoutFollow
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/AjoutAbon.fxml"));
            Parent parent = fxmlLoader.load();

            //cree instance de controller AjoutAbonController
            AjoutAbonController ajouterAbo = fxmlLoader.getController();
            //passer au controller  l objet entreprise = null : possbilite de selectionner n import entreprise lors ajouter
            ajouterAbo.initData(null);

            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Ajouter Entreprise ");
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            // Wait for the new window to be closed
            stage.setOnHiding(event -> {
                // Reload TableView data after closing the "Ajouter Abonnement" window
                ChargerVueTableau();
            });

            stage.showAndWait(); // Use showAndWait() to wait for the window to close before continuing

        } catch (Exception ex) {
            showAlertWarnning(ex.getMessage());
        }
    }

    @FXML
    public void searchAbonnement() {
        try {
            if (searchtext != null && !searchtext.getText().isEmpty()) {
                // Créer une autre liste qui va contenir les résultats de la recherche
                ObservableList<Abonnement> filteredList = FXCollections.observableArrayList();
                for (Abonnement abonnement : Abonnements) {
                    // Si le nom de l'entreprise ou le nom du journal contient le texte de recherche, on l'ajoute à la liste filtrée
                    if (abonnement.getEntreprise().getRaisonSocial().toLowerCase().contains(searchtext.getText().toLowerCase())
                            || abonnement.getJournal().getNomJr().toLowerCase().contains(searchtext.getText().toLowerCase())) {
                        filteredList.add(abonnement);
                    }
                }
                tableAbonnements.setItems(filteredList);
            } else {
                // Si le champ de recherche est vide, rafraîchir la table avec tous les abonnements
                tableAbonnements.setItems(Abonnements);
            }
        } catch (Exception ex) {
            showAlertWarnning(ex.getMessage());
        }
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


