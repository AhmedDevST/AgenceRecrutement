package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.AnnonceDAO;
import files.agencerecrutement.DAO.OffreDAO;
import files.agencerecrutement.Model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class PublierOffresController {

    @FXML
    private TableView<Offre> DataOffresDisponibles ;
    @FXML
    private TableColumn<Offre,Integer> TitreOffre;
    @FXML
    private TableColumn<Offre,String> RaisonSocialEst;
    @FXML
    private  TableColumn<Offre,Void> ActionsCol;
    @FXML
    private Label NomJournal;
    @FXML
    private  Label NumEdition;

    private ObservableList<Offre> offreSelectionner = FXCollections.observableArrayList();

    private Edition edition;


    public void initData(Edition edition) {
        try{
            this.edition = edition;
            NomJournal.setText(edition.getJournal().getNomJr());
            NumEdition.setText(edition.getNumSequentiel()+"");
            loadTableView();
        }catch (Exception ex){
            showAlertWarnning("Probleme :" +ex);
        }
    }
    private void loadTableView() throws SQLException {

        //utiliser  dans PropertyValueFactory  la meme nom de attribut au objet entreprise
        TitreOffre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        RaisonSocialEst.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEntreprise().getRaisonSocial()));

        //add cell of button edit
        Callback<TableColumn<Offre,Void> , TableCell<Offre,Void>> cellFactory=
                (TableColumn<Offre, Void> param) ->{
                    final  TableCell<Offre,Void> cell = new  TableCell<Offre,Void>(){
                        @Override
                        public void updateItem(Void item, boolean empty){
                            super.updateItem(item,empty);
                            if(empty){
                                setGraphic(null);
                                setText(null);
                            }else{

                                //checkbox
                                final CheckBox checkBox = new CheckBox();
                                //event sur icon modifier
                                checkBox.setOnAction(event -> {
                                    //get offre selectionner dans tableview
                                    Offre offre = getTableView().getItems().get(getIndex());
                                    TraitercheckBox(checkBox.isSelected(),offre);
                                });

                                // icon afficher les offres de est
                                final Image imageOffres = new Image(Objects.requireNonNull(getClass().getResource("/files/agencerecrutement/Images/afficherOffres.png")).toString());
                                final ImageView OffresViewImg = new ImageView(imageOffres);
                                //style
                                OffresViewImg.setFitWidth(30);
                                OffresViewImg.setFitHeight(30);
                                OffresViewImg.setCursor(Cursor.HAND);
                                Tooltip tooltipOffres = new Tooltip("details Offre");
                                Tooltip.install(OffresViewImg,tooltipOffres);

                                //event
                                OffresViewImg.setOnMouseClicked(actionEvent -> {
                                    //get offre selectionner dans tableview
                                    Offre offre = getTableView().getItems().get(getIndex());
                                   OuvrirAfficherOffre(offre);
                                } );

                                //met chcbox icon dans un Hbox
                                HBox ContentAllbtn = new HBox(checkBox,OffresViewImg);
                                ContentAllbtn.setStyle("-fx-alignment:center");
                                HBox.setMargin(checkBox, new Insets(2, 7, 7, 3));
                                HBox.setMargin(OffresViewImg, new Insets(2, 7, 7, 3));
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

        DataOffresDisponibles.setItems(OffreDAO.ListOffresValides(edition.getJournal().getIdJr(),edition.getNumSequentiel()));
    }
    public void AjouterAnnonces(ActionEvent event) {
        try{
            if(!offreSelectionner.isEmpty()){ // si tous au mois un offre selectionner
                //afficher un dialog de confirmation
                if(showConfirmationDialog("Confirmation","Publier Offres","Vous voullez publier ces offres ?")){

                    //publier tous editions (ajouter au annonces)
                    for(Offre offre : offreSelectionner){
                        //on publier si offre n est pas publier deja dans cette edition
                        if(!AnnonceDAO.IsOffreDejaPublier(offre.getIdOffre(),this.edition.getNumSequentiel())){
                            AnnonceDAO.AjouterAnnonce(offre.getIdOffre(),this.edition.getNumSequentiel());
                            showAlertInfo(" l offre :"+offre.getTitre()+" est  publie dans cette edition");
                        }else{
                            showAlertWarnning(" l offre :"+offre.getTitre()+" deja publie dans cette edition");
                        }
                    }
                    //fermer la fenetre
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                }
            }else{
                showAlertWarnning("vous voullez selectionner au moins un offre t ");
            }
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    public void OuvrirAfficherOffre(Offre offre){
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
    private  boolean showConfirmationDialog(String title , String Header , String Content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(Header);
        alert.setContentText(Content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }
    private  void TraitercheckBox(boolean check,Offre offre){
        //si offre selectionner : ajouter au list
        if(check){
            offreSelectionner.add(offre);
        }else{ //sinon
            offreSelectionner.remove(offre);
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
