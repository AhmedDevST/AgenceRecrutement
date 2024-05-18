package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.*;

import files.agencerecrutement.Model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class GestionAnnoncesController {

    private ObservableList<Annonce> annonces = FXCollections.observableArrayList();
    @FXML
    private TableView<Annonce> DataAnnonces;
    @FXML
    private TableColumn<Annonce,String> NumeSequentiel;
    @FXML
    private TableColumn<Annonce,String> Offre;
    @FXML
    private TableColumn<Annonce, String> dateParution;
    @FXML
    private TableColumn<Annonce,String> Journal;
    @FXML
    private TableColumn<Annonce,String> categorieCol;
    @FXML
    private  TableColumn<Annonce,Void> ActionsCol;
    @FXML
    private TextField SearchText;
    @FXML
    private Hyperlink AnnonceByPreferncesLink;
    @FXML
    private Hyperlink TousAnnonceLink;
    @FXML
    private  Button AjouterAnnonceBtn;

    private  User user ;

    //initialiser interface en fonction de user
   public  void initData(User user){
       try{
           this.user = user ;
           //set les droit de user
           switch (user.getRoleUser()){
               case  1 :
                   //agent
                   // agent a droit de ajouter des annonce
                   AjouterAnnonceBtn.setVisible(true);
                   // on n pas prefrences au cas de agent
                   AnnonceByPreferncesLink.setVisible(false);
                   TousAnnonceLink.setVisible(false);
                   //afficher tous les annonces
                   AfficherTousAnnonce();
                   break;
               case  3 :
                   //demandeur n est pas droit de ajouter annonce
                   AjouterAnnonceBtn.setVisible(false);
                   // demandeur  a  des  prefrences
                   AnnonceByPreferncesLink.setVisible(false);
                   TousAnnonceLink.setVisible(true);
                   //afficher  les annonces liee a les Prefernces de demandeur
                   AfficherAnnonceByPrefernces();
                   break;
           }

       }catch (Exception ex){
           showAlertWarnning("probleme:"+ex.getMessage());
       }
   }

    private void loadTableView(){

        NumeSequentiel.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEdition().getNumSequentiel())));
        Offre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOffre().getTitre()));
        dateParution.setCellValueFactory(cellData -> {
            Date dateParution = cellData.getValue().getEdition().getDateParution();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Format de la date
            return new SimpleStringProperty(dateFormat.format(dateParution));
        });
        Journal.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEdition().getJournal().getNomJr()));
        categorieCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEdition().getJournal().getCategorie().getLibelle()));

        //add cell of button edit
        Callback<TableColumn<Annonce,Void> , TableCell<Annonce,Void>> cellFactory =
                (TableColumn<Annonce, Void> param) ->{
                    final  TableCell<Annonce,Void> cell = new  TableCell<Annonce,Void>(){
                        @Override
                        public void updateItem(Void item, boolean empty){
                            super.updateItem(item,empty);
                            if(empty){
                                setGraphic(null);
                                setText(null);
                            }else{

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
                                    Annonce annonce = getTableView().getItems().get(getIndex());
                                    // Ouvrir la fenêtre details offre
                                    OuvrirdetailsOffre(annonce.getOffre());
                                });

                                //icon postuler
                                final Image imagepostuler = new Image(Objects.requireNonNull(getClass().getResource("/files/agencerecrutement/Images/cv.png")).toString());
                                final ImageView postulertViewImg = new ImageView(imagepostuler);
                                //style
                                postulertViewImg.setFitWidth(30);
                                postulertViewImg.setFitHeight(30);
                                postulertViewImg.setCursor(Cursor.HAND);
                                Tooltip tooltipEdit = new Tooltip("Postuler ");
                                Tooltip.install(postulertViewImg,tooltipEdit);

                                //event sur icon postuler
                                postulertViewImg.setOnMouseClicked(actionEvent -> {
                                    //get annonce selectionner dans tableview
                                    Annonce annonce = getTableView().getItems().get(getIndex());
                                    //postuler a une offre
                                    postulerOffre(annonce);
                                } );
                                // la seul demandeur a droit de postuler
                                // dans cas de agnet on set visible false au icon postuler
                                if(user.getRoleUser() == 1) postulertViewImg.setVisible(false);

                                //met  icon dans un Hbox
                                HBox ContentAllbtn = new HBox(postulertViewImg,OffreViewImg);
                                ContentAllbtn.setStyle("-fx-alignment:center");
                                HBox.setMargin(postulertViewImg, new Insets(2, 7, 7, 3));
                                HBox.setMargin(OffreViewImg, new Insets(2, 7, 7, 3));
                                setGraphic(ContentAllbtn);

                                setText(null);

                            }

                        }
                        //fin methode updateItem
                    };
                    //fin cell
                    return cell;
                };
        ActionsCol.setCellFactory(cellFactory);

        DataAnnonces.setItems(annonces);
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
    private void postulerOffre(Annonce annonce){
        try {
                int idOffre = annonce.getOffre().getIdOffre();
                int idDemandeur = user.getIdUser();
                //tester si demadeur n est pas postuler deja a  cette offre
                if(!PostulationDAO.DejaPostuler(idDemandeur,idOffre)){
                    //afficher un dialog de confirmation
                    if(showConfirmationDialog("Confirmation","Postulation ","Vous voullez de postuler a cette offre   ?")){

                        //get nombre anne experience de demandeur
                        int nombreExpDemandeur = DemandeurDAO.getNbExperience(idDemandeur);
                        //get nombre anne experience de offre
                        int nombreExo  = OffreDAO.getNbExperience(idOffre);
                        //tester
                        if(nombreExpDemandeur >= nombreExo){
                            //si nombre annee experience  est verifier
                            //postuler a offre
                            PostulationDAO.postuler(idDemandeur,annonce.getIdAnnonce());
                            showAlertInfo("LA postulation est effectue ");
                            //action apres
                        }else{
                            showAlertWarnning("vous n avez pas le nombre des annnees experiences demande");
                        }
                    }
                }else
                    showAlertWarnning("vous  avez deja postuler a cette offre !");
        } catch (Exception ex) {
            showAlertWarnning(ex.getMessage());
        }
    }
    @FXML
    private void searchAnnonce(){
        try{
            if(!SearchText.getText().isEmpty()){
                //cree une autre list =>va contient result de rechreche
                ObservableList<Annonce> filteredList = FXCollections.observableArrayList();
                for(Annonce  annonce : annonces){
                    //si on a le meme numero de edition
                    if( String.valueOf(annonce.getEdition().getNumSequentiel()).contains(SearchText.getText()))
                        filteredList.add(annonce);
                }
                DataAnnonces.setItems(filteredList);
            }else{
                //si input de recherche vide on refrech la tableView
                AfficherTousAnnonce();
            }
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    @FXML
    private  void AjouterAnnoceEvent(){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource("/files/agencerecrutement/Views/AjouterAnnonce.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Nouvelle Annonce ");
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            // Wait for the new window to be closed
            stage.setOnHiding(event -> {
                // Reload TableView data after closing the "Ajouter annonce" window
                ;
            });

            stage.showAndWait(); // Use showAndWait() to wait for the window to close before continuing
            AfficherTousAnnonce();
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }


    // event lors click sur " afficher annnonce liee a les prefrences  " afficher les annonces par rapport au prefrences de demandeur
    @FXML
    private  void AfficherAnnonceByPrefernces(){
        try{
            LoadAnnoncesPreferes();
            if(user.getRoleUser() == 3){
                AnnonceByPreferncesLink.setVisible(false);
                TousAnnonceLink.setVisible(true);
            }
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    //event lors click sur " afficher  tous les annnonces  "
    @FXML
    private  void AfficherTousAnnonce(){
        try{
            LoadTousAnnonces();
            if(user.getRoleUser() == 3){
                TousAnnonceLink.setVisible(false);
                AnnonceByPreferncesLink.setVisible(true);
            }
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }


    //methode charger dans table tous les annonces
    private  void LoadTousAnnonces(){
        try{
            annonces = AnnonceDAO.ListAnnonces();
            loadTableView();
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    //methode charger dans table  les annonces liee a prefrences de demandeur
    private  void LoadAnnoncesPreferes(){
        try{
            annonces = AnnonceDAO.ListAnnoncesByPreferences(user.getIdUser());
            loadTableView();
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
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
