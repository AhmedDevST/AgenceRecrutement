package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.PostulationDAO;
import files.agencerecrutement.DAO.RecrutementDAO;
import files.agencerecrutement.Model.Postulation;
import files.agencerecrutement.Model.Recrutement;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.Objects;

public class RecrutementController {

        private ObservableList<Recrutement> recrutements = FXCollections.observableArrayList();

        @FXML
        private TableView<Recrutement> tableRecrutements;

        @FXML
        private TableColumn<Recrutement, String> colNom;

        @FXML
        private TableColumn<Recrutement, String> colPrenom;

        @FXML
        private TableColumn<Recrutement, String> colOffre;


        @FXML
        private TableColumn<Recrutement, String> colNomEntreprise;


        @FXML
        private TextField searchtext;

        @FXML
        public void initialize() {
            try {
                ChargerVueTableau(); // Appelez d'abord la méthode pour charger le tableau avec les données
            } catch (Exception ex) {
                showAlertWarnning(ex.getMessage());
            }
        }

        private void ChargerDonnée() {
            try {
                // Obtenir la liste des abonnements à partir de la base de données
                recrutements = RecrutementDAO.afficherRecrutements();
            } catch (Exception ex) {
                showAlertWarnning(ex.getMessage());
            }
        }

        private void ChargerVueTableau() {
            ChargerDonnée(); // Appelez d'abord la méthode pour charger les données
            // Utiliser PropertyValueFactory avec le même nom d'attribut sur l'objet recrutement
            colOffre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOffre().getTitre()));
            colNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDemandeur().getNom()));
            colPrenom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDemandeur().getPrenom()));
            colNomEntreprise.setCellValueFactory(CellData -> new SimpleStringProperty(CellData.getValue().getOffre().getEntreprise().getRaisonSocial()));
            tableRecrutements.setItems(recrutements);
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


    public void searchRecrutement(ActionEvent event) {
        try {
            if (searchtext != null && !searchtext.getText().isEmpty()) {
                // Créer une autre liste qui va contenir les résultats de la recherche
                ObservableList<Recrutement> filteredList = FXCollections.observableArrayList();
                for (Recrutement recrutement : recrutements) {
                    // Si le le titre d'offre contient le texte de recherche, on l'ajoute à la liste filtrée
                    if (recrutement.getOffre().getTitre().toLowerCase().contains(searchtext.getText().toLowerCase()))

                    {
                        filteredList.add(recrutement);
                    }
                }
                tableRecrutements.setItems(filteredList);
            } else {
                // Si le champ de recherche est vide, rafraîchir la table avec tous les postulations
                tableRecrutements.setItems(recrutements);
            }

        } catch (Exception ex) {
            showAlertWarnning(ex.getMessage());
        }
    }
}


