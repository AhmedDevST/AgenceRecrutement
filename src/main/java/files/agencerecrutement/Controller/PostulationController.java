package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.AbonnementDAO;
import files.agencerecrutement.DAO.OffreDAO;
import files.agencerecrutement.DAO.PostulationDAO;
import files.agencerecrutement.DAO.RecrutementDAO;
import files.agencerecrutement.Model.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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
import java.util.Optional;

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

    private User user ;

    //initialiser interface en fonction de user
    public  void initData(User user){
        try{
            this.user = user ;
            ChargerVueTableau();
        }catch (Exception ex){
            showAlertWarnning("probleme:"+ex.getMessage());
        }
    }

    private void ChargerDonnée() {
        try {
            switch (user.getRoleUser()){
                case  1 :
                    //agent : on charger tous les postulations
                    postulations = PostulationDAO.afficherPostulations();
                    break;
                case 2:
                    //entreprise: on charger ses  postulations a ses offres
                    postulations = PostulationDAO.afficherPostulationsOfEntreprise(user.getIdUser());
                    break;
                case  3 :
                    //demandeur: on charger ses  postulations
                    postulations = PostulationDAO.afficherPostulationsOfDemandeur(user.getIdUser());
                    break;
            }
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
                                    Postulation postulation = getTableView().getItems().get(getIndex());
                                    // Ouvrir la fenêtre details offre
                                    OuvrirdetailsOffre(postulation.getAnnonce().getOffre());
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
                                    Postulation postulation = getTableView().getItems().get(getIndex());
                                    // Ouvrir la fenêtre details demandeur
                                    OuvrirdetailsDemandeur(postulation.getPostulant());
                                });

                                // Icone recruter
                                final Image imageRecruter= new Image(Objects.requireNonNull(getClass().getResource("/files/agencerecrutement/Images/recruter.png")).toString());
                                final ImageView RecruterViewImg = new ImageView(imageRecruter);
                                // set invisible
                                RecruterViewImg.setVisible(false);
                                // Style
                                RecruterViewImg.setFitWidth(30);
                                RecruterViewImg.setFitHeight(30);
                                RecruterViewImg.setCursor(Cursor.HAND);
                                Tooltip tooltipRecruter = new Tooltip("Recruter demandeur");
                                Tooltip.install(RecruterViewImg, tooltipRecruter);

                                // Action sur l'icone de info
                                RecruterViewImg.setOnMouseClicked(actionEvent -> {
                                    Postulation postulation = getTableView().getItems().get(getIndex());
                                    RecruterDemandeur(postulation);
                                });

                                // entreprise la seul peut recruter
                                if(user.getRoleUser() == 2) RecruterViewImg.setVisible(true);


                                // Mettre l'icone dans un Hbox
                                HBox Content = new HBox(OffreViewImg,DemandeurViewImg,RecruterViewImg);
                                Content.setStyle("-fx-alignment:CENTER");
                                HBox.setMargin(OffreViewImg, new Insets(2, 7, 7, 3));
                                HBox.setMargin(DemandeurViewImg, new Insets(2, 7, 7, 3));
                                HBox.setMargin(RecruterViewImg, new Insets(2, 7, 7, 3));
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
    private  void  RecruterDemandeur(Postulation postulation){
        try {
            int idDemandeur = postulation.getPostulant().getIdUser();
            int idOffre = postulation.getAnnonce().getOffre().getIdOffre();
            if(!RecrutementDAO.DejaRecruter(idDemandeur,idOffre)){
                //afficher un dialog de confirmation
                if(showConfirmationDialog("Confirmation","Recrutement ","Vous voullez de recruter  ce demandeur ?")){
                    // verifier l etat de offre
                    boolean etatOffre = OffreDAO.getEtatOffre(idOffre);
                    if(etatOffre){
                        //si etat est activer
                        RecrutementDAO.Recruter(idOffre,idDemandeur);
                        showAlertInfo("le demandeur est recruter");
                        //action apres
                    }else{
                        showAlertWarnning("cette offre est desactive");
                        }
                }
            }else
                showAlertWarnning(" vous avez deja recruter ce demadeur au cette offre!");
        } catch (Exception ex) {
            showAlertWarnning(ex.getMessage());
        }
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
    private  boolean showConfirmationDialog(String title , String Header , String Content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(Header);
        alert.setContentText(Content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }
    public void showAlertInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("title");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText(message);
        alert.showAndWait();
    }






}
