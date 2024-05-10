package files.agencerecrutement.Controller;
import files.agencerecrutement.DAO.AbonnementDAO;
import files.agencerecrutement.DAO.CategorieDAO;
import files.agencerecrutement.DAO.Utilitaire;
import files.agencerecrutement.Model.*;
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
    private Button ajoutAbon;

    @FXML
    private TableView<Abonnement> tableAbonnements;

    @FXML
    private TableColumn<Abonnement, String> colNomEntreprise;

    @FXML
    private TableColumn<Abonnement, String> colNomJournal;

    @FXML
    private TableColumn<Abonnement, Boolean> colEtatAbonnement;

    @FXML
    private TableColumn<Abonnement, Date> colDateExpiration;

    @FXML
    private TableColumn<Abonnement,Void> ActionOffre;

    @FXML
    private TextField searchtext;

    @FXML
    public void ajouterAbonAction() {
        try {
            // Charger le fichier FXML de l'interface AjoutFollow
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/AjoutAbon.fxml"));

            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Créer une nouvelle fenêtre
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Ajouter un follow");

            // Rendre la fenêtre modale
            stage.initModality(Modality.APPLICATION_MODAL);

            // Afficher la fenêtre
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void initialiser() {
        try {
            ChargerVueTableau(); // Appelez d'abord la méthode pour charger le tableau avec les données
        } catch (Exception ex) {
            showAlertWarnning(ex.getMessage());
        }
    }

    private void ChargerDonnée() {
        try {
            // Obtenir la liste des abonnements à partir de la base de données
            Abonnements = AbonnementDAO.afficherAbonnements();
        } catch (Exception ex) {
            showAlertWarnning(ex.getMessage());
        }
    }

    private void ChargerVueTableau() {
        ChargerDonnée(); // Appelez d'abord la méthode pour charger les données
        // Utiliser PropertyValueFactory avec le même nom d'attribut sur l'objet Entreprise
        colNomEntreprise.setCellValueFactory(new PropertyValueFactory<>("raisonSocial"));
        colNomJournal.setCellValueFactory(new PropertyValueFactory<>("nomJr"));
        colEtatAbonnement.setCellValueFactory(new PropertyValueFactory<>("etatAbo"));
        colDateExpiration.setCellValueFactory(new PropertyValueFactory<>("dateExpiration"));

        // Ajouter une cellule de bouton modifier
        Callback<TableColumn<Abonnement, Void>, TableCell<Abonnement, Void>> cellFactory =
                (TableColumn<Abonnement, Void> ActionOffre) -> {
                    final TableCell<Abonnement, Void> cell = new TableCell<>() {
                        @Override
                        public void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                // Icone de modification
                                final Image imageEdit = new Image(Objects.requireNonNull(getClass().getResource("/files/agencerecrutement/Views/img.png")).toString());
                                final ImageView editEstViewImg = new ImageView(imageEdit);
                                // Style
                                editEstViewImg.setFitWidth(30);
                                editEstViewImg.setFitHeight(30);
                                editEstViewImg.setCursor(Cursor.HAND);
                                Tooltip tooltipEdit = new Tooltip("Ajouter Offre");
                                Tooltip.install(editEstViewImg, tooltipEdit);

                                // Action sur l'icone de modification
                                editEstViewImg.setOnMouseClicked(actionEvent -> {
                                    Abonnement abonnement = getTableView().getItems().get(getIndex());
                                    // Ouvrir la fenêtre de modification de catégorie
                                    // OuvrirModifierCategorie(categorie);
                                });

                                // Mettre l'icone dans un Hbox
                                HBox Content = new HBox(editEstViewImg);
                                Content.setStyle("-fx-alignment:center");
                                setGraphic(Content);
                                setText(null);
                            }
                        }
                    };
                    return cell;
                };
        ActionOffre.setCellFactory(cellFactory);

        tableAbonnements.setItems(Abonnements);
    }

    @FXML
    public void searchAbonnement(ActionEvent event) {
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


