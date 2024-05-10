package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.EditionDAO;
import files.agencerecrutement.DAO.JournalDAO;
import files.agencerecrutement.Model.Edition;
import files.agencerecrutement.Model.Journal;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class GestionEditionController {

    private ObservableList<Edition> editions = FXCollections.observableArrayList();
    @FXML
    private TableView<Edition> DataEdition ;
    @FXML
    private TableColumn<Edition,Integer> NumeroEd;
    @FXML
    private TableColumn<Edition, String> NomJournal;

    @FXML
    private TableColumn<Edition, Date> DateParutionEd;
    @FXML
    private TableColumn<Edition, String> Categorie;
    @FXML
    private  TableColumn<Edition,Void> ActionsCol;
    @FXML
    private ComboBox ComboboxJournal;

    @FXML
    public  void initialize(){
        try{
            loadTableView();
            ComboboxJournal.setItems(JournalDAO.afficherJournaux());
            ComboboxJournal.getSelectionModel().selectFirst();
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    private void loadData(){
        try{
            //get la list des editions  a partir de base de donnne
            editions = EditionDAO.afficherEditions();
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    private void loadTableView(){

        loadData();
        //utiliser  dans PropertyValueFactory  la meme nom de attribut au objet edtions
        NumeroEd.setCellValueFactory(new PropertyValueFactory<>("numSequentiel"));
        DateParutionEd.setCellValueFactory(new PropertyValueFactory<>("dateParution"));
        NomJournal.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJournal().getNomJr()));
        Categorie.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJournal().getCategorie().getLibelle()));
        //add cell of button edit
        Callback<TableColumn<Edition,Void> , TableCell<Edition,Void>> cellFactory=
                (TableColumn<Edition, Void> param) ->{
                    final  TableCell<Edition,Void> cell = new  TableCell<Edition,Void>(){
                        @Override
                        public void updateItem(Void item, boolean empty){
                            super.updateItem(item,empty);
                            if(empty){
                                setGraphic(null);
                                setText(null);
                            }else{

                                //icon supprimer edition
                                final Image imageDelete = new Image(Objects.requireNonNull(getClass().getResource("/files/agencerecrutement/Images/SupprimerIcon.png")).toString());
                                final ImageView deleteEstViewImg = new ImageView(imageDelete);
                                //style
                                deleteEstViewImg.setFitWidth(30);
                                deleteEstViewImg.setFitHeight(30);
                                deleteEstViewImg.setCursor(Cursor.HAND);
                                Tooltip tooltipEdit = new Tooltip("Delete edition");
                                Tooltip.install(deleteEstViewImg,tooltipEdit);

                                //event sur icon suppprimer
                                deleteEstViewImg.setOnMouseClicked(actionEvent -> {
                                    //get edition selectionner dans tableview
                                    Edition edition = getTableView().getItems().get(getIndex());
                                    //supprimer
                                    SupprimerEdition(edition);
                                } );

                                //met  icon dans un Hbox
                                HBox ContentAllbtn = new HBox(deleteEstViewImg);
                                ContentAllbtn.setStyle("-fx-alignment:center");
                                HBox.setMargin(deleteEstViewImg, new Insets(2, 7, 7, 3));

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

        DataEdition.setItems(editions);
    }
    @FXML
    private  void  filterEdition(){
        try{
                ObservableList<Edition> filteredEditions= FXCollections.observableArrayList();
                for(Edition edition : editions){
                    //journal selectionner
                    Journal journal= (Journal) ComboboxJournal.getValue();
                    if(edition.getJournal().getIdJr() == journal.getIdJr())
                        filteredEditions.add(edition);
                }
                if(!filteredEditions.isEmpty()){
                    DataEdition.setItems(filteredEditions);
                }else{
                    loadTableView();
                }
        }catch(Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    @FXML
    private  void refreshData(){
        try  {
             loadTableView();
        }catch(Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    //modifier edidtion
    private void SupprimerEdition(Edition edition) {
        try{
            //fermer le fenetre ou initialisez les donnees dans les inputs
            if(showConfirmationDialog("Confirmation","Supprimer Edition","Vous voullez sur supprimer cette edition ?")){
               if(EditionDAO.SupprimerEdition(edition)){
                   showAlertInfo("Edition est bien supprimer");
                   loadTableView();
                }else
                    showAlertWarnning("Impossible de supprimer cette edition!!");
            }
        }catch(Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }

    //nouvelle edition
    @FXML
    public void AjouterEditionEvent() {
        try{
            Parent parent = FXMLLoader.load(getClass().getResource("/files/agencerecrutement/Views/AjouterEdition.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Nouvelle Edition ");
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            // Wait for the new window to be closed
            stage.setOnHiding(event -> {
                // Reload TableView data after closing the "Ajouter edition" window
                loadTableView();
            });

            stage.showAndWait(); // Use showAndWait() to wait for the window to close before continuing

        }catch (Exception ex){
            showAlertWarnning("probleme :"+ex.getMessage());
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
    public void showAlertInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("title");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void showAlertWarnning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("title");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
