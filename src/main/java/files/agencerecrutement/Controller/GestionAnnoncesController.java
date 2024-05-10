package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.AnnonceDAO;

import files.agencerecrutement.Model.Annonce;
import files.agencerecrutement.Model.Entreprise;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

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
    private  TableColumn<Annonce,Void> ActionsCol;
    @FXML
    private TextField SearchText;
    @FXML
    public  void initialize(){
        try{
            loadTableView();
        }catch (Exception ex){
            showAlertWarnning("probleme:"+ex.getMessage());
        }
    }
    private void loadData(){
        try{
            //get la list des annonces  a partir de base de donnne
            annonces = AnnonceDAO.ListAnnonces();
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    private void loadTableView(){

        loadData();
        NumeSequentiel.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEdition().getNumSequentiel())));
        Offre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOffre().getTitre()));
        dateParution.setCellValueFactory(cellData -> {
            Date dateParution = cellData.getValue().getEdition().getDateParution();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Format de la date
            return new SimpleStringProperty(dateFormat.format(dateParution));
        });
        Journal.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEdition().getJournal().getNomJr()));

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
                                } );

                                //met  icon dans un Hbox
                                HBox ContentAllbtn = new HBox(postulertViewImg);
                                ContentAllbtn.setStyle("-fx-alignment:center");
                                HBox.setMargin(postulertViewImg, new Insets(2, 7, 7, 3));
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
                loadTableView();
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
                loadTableView();
            });

            stage.showAndWait(); // Use showAndWait() to wait for the window to close before continuing

        }catch (Exception ex){
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
}
