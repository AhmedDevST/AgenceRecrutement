package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.PostulationDAO;
import files.agencerecrutement.DAO.RecrutementDAO;
import files.agencerecrutement.Model.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
        private TableColumn<Recrutement,Void> colActions;

        @FXML
        private TextField searchtext;

    private User user ;
    private  int idDemndeur ;

    //initialiser interface en fonction de user
    public  void initData(User user,int idDem){
        try{
            this.user = user ;
            idDemndeur = idDem;
            ChargerVueTableau();
        }catch (Exception ex){
            showAlertWarnning("probleme:"+ex.getMessage());
        }
    }

    private void ChargerDonnée() {
        try {
            switch (user.getRoleUser()){
                case  1 :
                    //agent :
                    if(idDemndeur == 0){
                        // on charger tous les recrutements
                        recrutements = RecrutementDAO.afficherRecrutements();
                    }else{
                        // on charger  les recrutements d un demandeur
                        recrutements = RecrutementDAO.DisplayRecrutementDemandeur(idDemndeur);
                    }
                    break;
                case 2:
                    //entreprise: on charger ses  recrutement a ses offres
                    recrutements = RecrutementDAO.DisplayRecrutementEntreprise(user.getIdUser());
                    break;
                case  3 :
                    //demandeur: on charger ses  recrutetement
                    recrutements = RecrutementDAO.DisplayRecrutementDemandeur(user.getIdUser());
                    break;
            }
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

            // Ajouter une cellule de bouton info
            Callback<TableColumn<Recrutement, Void>, TableCell<Recrutement, Void>> cellFactory =
                    (TableColumn<Recrutement, Void> ActionsCol) -> {
                        final TableCell<Recrutement, Void> cell = new TableCell<>() {
                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {

                                    // Icone de info sur offre
                                    final Image imageOffre = new Image(Objects.requireNonNull(getClass().getResource("/files/agencerecrutement/Images/afficherOffres.png")).toString());
                                    final ImageView OffreViewImg = new ImageView(imageOffre);
                                    // Style
                                    OffreViewImg.setFitWidth(30);
                                    OffreViewImg.setFitHeight(30);
                                    OffreViewImg.setCursor(Cursor.HAND);
                                    Tooltip tooltipOffre = new Tooltip("details Offre");
                                    Tooltip.install(OffreViewImg, tooltipOffre);

                                    // Action sur l'icone de info
                                    OffreViewImg.setOnMouseClicked(actionEvent -> {
                                        Recrutement recrutement = getTableView().getItems().get(getIndex());
                                        // Ouvrir la fenêtre details offre
                                        OuvrirdetailsOffre(recrutement.getOffre());
                                    });

                                    // Icone de info sur demandeur
                                    final Image imageDemandeur= new Image(Objects.requireNonNull(getClass().getResource("/files/agencerecrutement/Images/candidats.png")).toString());
                                    final ImageView DemandeurViewImg = new ImageView(imageDemandeur);
                                    // Style
                                    DemandeurViewImg.setFitWidth(30);
                                    DemandeurViewImg.setFitHeight(30);
                                    DemandeurViewImg.setCursor(Cursor.HAND);
                                    Tooltip tooltipDemandeur = new Tooltip("details demandeur");
                                    Tooltip.install(DemandeurViewImg, tooltipDemandeur);

                                    // Action sur l'icone de info
                                    DemandeurViewImg.setOnMouseClicked(actionEvent -> {
                                        Recrutement recrutement = getTableView().getItems().get(getIndex());
                                        // Ouvrir la  details demandeur
                                        OuvrirdetailsDemandeur(recrutement.getDemandeur());
                                    });

                                    // Mettre l'icone dans un Hbox
                                    HBox Content = new HBox(OffreViewImg,DemandeurViewImg);
                                    Content.setStyle("-fx-alignment:CENTER");
                                    HBox.setMargin(OffreViewImg, new Insets(2, 7, 7, 3));
                                    HBox.setMargin(DemandeurViewImg, new Insets(2, 7, 7, 3));
                                    setGraphic(Content);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    };
            colActions.setCellFactory(cellFactory);
            tableRecrutements.setItems(recrutements);
        }


    private  void OuvrirdetailsOffre(Offre offre){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/DetailsOffre.fxml"));
            Parent parent = fxmlLoader.load();

            //cree instance de controller ModifierEntrepriseController
            DetailsOffreController detailsOffreController = fxmlLoader.getController();
            //passer au controller  l objet entreprise

            detailsOffreController.initData(offre.getIdOffre());

            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Détails Offre ");
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        }catch (Exception e)
        {
            AlertsConfirmationsController.showAlertWarnning(e.getMessage());
        }
    }
    private void OuvrirdetailsDemandeur(Demandeur demandeur){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/DetailsDemandeur.fxml"));
            Parent parent = fxmlLoader.load();

            DetailsDemandeur detailsDemandeurController = fxmlLoader.getController();
            detailsDemandeurController.initData(demandeur.getIdUser(),user);

            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Détails Demandeur ");
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        }catch (Exception e)
        {
            AlertsConfirmationsController.showAlertWarnning(e.getMessage());
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


