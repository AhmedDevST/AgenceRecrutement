package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.AbonnementDAO;
import files.agencerecrutement.DAO.PostulationDAO;
import files.agencerecrutement.Model.Abonnement;
import files.agencerecrutement.Model.Postulation;
import javafx.beans.property.SimpleIntegerProperty;
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

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class PostulationController {
    private ObservableList<Postulation> postulations = FXCollections.observableArrayList();

    @FXML
    private TableView<Postulation> tablePostulations;

    @FXML
    private TableColumn<Postulation, String> colNom;

    @FXML
    private TableColumn<Postulation, String> colPrenom;

    @FXML
    private TableColumn<Postulation, String> colOffre;


    @FXML
    private TableColumn<Postulation, String> colEdition;

    @FXML
    private TableColumn<Postulation, String> coldateparution;

    @FXML
    private TableColumn<Postulation,Void> ActionsCol;

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
            postulations = PostulationDAO.afficherPostulations();
        } catch (Exception ex) {
            showAlertWarnning(ex.getMessage());
        }
    }

    private void ChargerVueTableau() {
        ChargerDonnée(); // Appelez d'abord la méthode pour charger les données
        // Utiliser PropertyValueFactory avec le même nom d'attribut sur l'objet abonnement
        colNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPostulant().getNom()));
        colPrenom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPostulant().getPrenom()));
        colOffre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAnnonce().getOffre().getTitre()));
        colEdition.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAnnonce().getEdition().getNumSequentiel())));
        coldateparution.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAnnonce().getEdition().getDateParution())));

        // Ajouter une cellule de bouton info
        Callback<TableColumn<Postulation, Void>, TableCell<Postulation, Void>> cellFactory =
                (TableColumn<Postulation, Void> ActionsCol) -> {
                    final TableCell<Postulation, Void> cell = new TableCell<>() {
                        @Override
                        public void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                // Icone de info
                                final Image imageEdit = new Image(Objects.requireNonNull(getClass().getResource("/files/agencerecrutement/Images/addAbo.png")).toString());
                                final ImageView editEstViewImg = new ImageView(imageEdit);
                                // Style
                                editEstViewImg.setFitWidth(30);
                                editEstViewImg.setFitHeight(30);
                                editEstViewImg.setCursor(Cursor.HAND);
                                Tooltip tooltipEdit = new Tooltip("Info");
                                Tooltip.install(editEstViewImg, tooltipEdit);

                                // Action sur l'icone de info
                                editEstViewImg.setOnMouseClicked(actionEvent -> {
                                    Postulation psotulation = getTableView().getItems().get(getIndex());
                                    // Ouvrir la fenêtre de modification de l'abonnement
                                    //OuvrirModifierAbonnement(abonnement);
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
        ActionsCol.setCellFactory(cellFactory);

        tablePostulations.setItems(postulations);
    }

    @FXML
    public void searchPostulants(ActionEvent event) {
        try {
            if (searchtext != null && !searchtext.getText().isEmpty()) {
                // Créer une autre liste qui va contenir les résultats de la recherche
                ObservableList<Postulation> filteredList = FXCollections.observableArrayList();
                for (Postulation postulation : postulations) {
                    // Si le le titre d'offre contient le texte de recherche, on l'ajoute à la liste filtrée
                    if (postulation.getAnnonce().getOffre().getTitre().toLowerCase().contains(searchtext.getText().toLowerCase()))

                    {
                        filteredList.add(postulation);
                    }
                }
                tablePostulations.setItems(filteredList);
            } else {
                // Si le champ de recherche est vide, rafraîchir la table avec tous les postulations
                tablePostulations.setItems(postulations);
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



    //methode permet de ouvrir le fenetre de modification d un abonnement passer comme argument
    private  void OuvrirModifierAbonnement(Abonnement abonnement){
        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/ModifierAbonnement.fxml"));
            Parent parent = fxmlLoader.load();

            //cree instance de controller
            ModifierAbonnementController modifierAbonnementController = fxmlLoader.getController();
            //passer au controller  l objet abonnement
            modifierAbonnementController.initData(abonnement);

            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Modifier abonnement ");
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            // Wait for the new window to be closed
            stage.setOnHiding(event -> {
                // Reload TableView data after closing the "modifier abonnement" window
                ChargerVueTableau();
            });

            stage.showAndWait(); // Use showAndWait() to wait for the window to close before continuing

        }catch(Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }


}
