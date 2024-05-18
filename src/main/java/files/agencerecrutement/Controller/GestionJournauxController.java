package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.JournalDAO;
import files.agencerecrutement.Model.Categorie;
import files.agencerecrutement.Model.Journal;
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

import java.util.Objects;

public class GestionJournauxController {

    private ObservableList<Journal> journaux = FXCollections.observableArrayList();
    @FXML
    private TableView<Journal> DataJournaux ;
    @FXML
    private TableColumn<Journal,Integer> NumeroJr;
    @FXML
    private TableColumn<Journal,String> NomJr;
    @FXML
    private TableColumn<Journal,String> PeriodiciteJr;
    @FXML
    private TableColumn<Journal,String> LangueJr;
    @FXML
    private TableColumn<Journal, Categorie> CategorieJr;
    @FXML
    private  TableColumn<Journal,Void> ActionsCol;
    @FXML
    private TextField SearchText;

    private Menu homeController;


    public  void initData(Menu homeController){
        try{
            this.homeController = homeController;
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("probleme:"+ex.getMessage());
        }
    }


    @FXML
    public  void initialize(){
        try{
            loadTableView();
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }
    private void loadData(){
        try{
            //get la list des journaux  a partir de base de donnne
            journaux = JournalDAO.afficherJournaux();
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }
    private void loadTableView(){

        loadData();
        //utiliser  dans PropertyValueFactory  la meme nom de attribut au objet entreprise
        NumeroJr.setCellValueFactory(new PropertyValueFactory<>("idJr"));
        NomJr.setCellValueFactory(new PropertyValueFactory<>("nomJr"));
        PeriodiciteJr.setCellValueFactory(new PropertyValueFactory<>("periodicite"));
        LangueJr.setCellValueFactory(new PropertyValueFactory<>("langue"));
        CategorieJr.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        //add cell of button edit
        Callback<TableColumn<Journal,Void> , TableCell<Journal,Void>> cellFactory=
                (TableColumn<Journal, Void> param) ->{
                    final  TableCell<Journal,Void> cell = new  TableCell<Journal,Void>(){
                        @Override
                        public void updateItem(Void item, boolean empty){
                            super.updateItem(item,empty);
                            if(empty){
                                setGraphic(null);
                                setText(null);
                            }else{

                                //icon modifier Journal
                                final Image imageEdit = new Image(Objects.requireNonNull(getClass().getResource("/files/agencerecrutement/Images/editIcon.png")).toString());
                                final ImageView editEstViewImg = new ImageView(imageEdit);
                                //style
                                editEstViewImg.setFitWidth(30);
                                editEstViewImg.setFitHeight(30);
                                editEstViewImg.setCursor(Cursor.HAND);
                                Tooltip tooltipEdit = new Tooltip("Modifier  Journal");
                                Tooltip.install(editEstViewImg,tooltipEdit);

                                //event sur icon modifier
                                editEstViewImg.setOnMouseClicked(actionEvent -> {
                                    //get Journal selectionner dans tableview
                                    Journal journal = getTableView().getItems().get(getIndex());
                                    //ouvrir fenetre de modifier Journal
                                     OuvrirModifierJournal(journal);
                                } );

                                //icon afficher les abonnement de journal
                                final Image imageAbo = new Image(Objects.requireNonNull(getClass().getResource("/files/agencerecrutement/Images/afficherAbo.png")).toString());
                                final ImageView AboJrViewImg = new ImageView(imageAbo);
                                //style
                                AboJrViewImg.setFitWidth(30);
                                AboJrViewImg.setFitHeight(30);
                                AboJrViewImg.setCursor(Cursor.HAND);
                                Tooltip tooltipAbo = new Tooltip("Abonnement du journal");
                                Tooltip.install(AboJrViewImg,tooltipAbo);

                                //event
                                AboJrViewImg.setOnMouseClicked(actionEvent -> {
                                    Journal journal = getTableView().getItems().get(getIndex());
                                    showAbonnement(journal);
                                } );

                                //met les trois icon dans un Hbox
                                HBox ContentAllbtn = new HBox(editEstViewImg,AboJrViewImg);
                                ContentAllbtn.setStyle("-fx-alignment:center");
                                HBox.setMargin(editEstViewImg, new Insets(2, 7, 7, 3));
                                HBox.setMargin(AboJrViewImg, new Insets(2, 7, 7, 2));

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

        DataJournaux.setItems(journaux);
    }

    private void  showAbonnement(Journal journal){
        try{
            homeController.chargerGestionAbonnement(journal.getIdJr(),true); // afficher abonnment par rapport a entreprise
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }

    @FXML
    public void searchJournalEvent() {
        try{
            if(!SearchText.getText().isEmpty()){
                //cree une autre list =>va contient result de rechreche
                ObservableList<Journal> filteredList = FXCollections.observableArrayList();
                for(Journal  journal : journaux){
                    //si le  Nom ,Periodicite , Langue ou Categorie  d un journal  similaire de mot cle on la ajouter
                    if( journal.getNomJr().toLowerCase().contains(SearchText.getText().toLowerCase()) ||
                            journal.getPeriodicite().toLowerCase().contains(SearchText.getText().toLowerCase())||
                            journal.getLangue().toLowerCase().contains(SearchText.getText().toLowerCase()) ||
                            journal.getCategorie().getLibelle().toLowerCase().contains(SearchText.getText().toLowerCase())
                    )
                        filteredList.add(journal);
                }
                DataJournaux.setItems(filteredList);
            }else{
                //si input de recherche vide on refrech la tableView
                loadTableView();
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }

    //event de button nouveau journal : permet de ouvrir la fenetre d ajouter un journal
    @FXML
    public void AjouterJournalEvent() {
        try{
            Parent parent = FXMLLoader.load(getClass().getResource("/files/agencerecrutement/Views/AjouterJournal.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Nouveau Journal ");
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            // Wait for the new window to be closed
            stage.setOnHiding(event -> {
                // Reload TableView data after closing the "Ajouter journal" window
                loadTableView();
            });

            stage.showAndWait(); // Use showAndWait() to wait for the window to close before continuing

        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }
    //methode permet de ouvrir le fenetre de modification d un journal passer comme argument
    private  void OuvrirModifierJournal(Journal journal){
        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/ModifierJournal.fxml"));
            Parent parent = fxmlLoader.load();

            //cree instance de controller ModifierJournalController
            ModifierJournalController modifierJournalController = fxmlLoader.getController();
            //passer au controller  l objet journal
            modifierJournalController.initData(journal);

            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Modifier Journal ");
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            // Wait for the new window to be closed
            stage.setOnHiding(event -> {
                // Reload TableView data after closing the "modifier Journal" window
                loadTableView();
            });

            stage.showAndWait(); // Use showAndWait() to wait for the window to close before continuing

        }catch(Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }
}
