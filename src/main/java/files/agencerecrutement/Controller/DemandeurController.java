package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.DemandeurDAO;
import files.agencerecrutement.Model.Demandeur;
import files.agencerecrutement.Model.Entreprise;
import files.agencerecrutement.Model.User;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Objects;

public class DemandeurController {
    @FXML
    private TableView<Demandeur> DataDemandeur;
    private ObservableList<Demandeur> demandeurs = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Demandeur,String> Nom;
    @FXML
    private TableColumn<Demandeur,String> Prenom;
    @FXML
    private TableColumn<Demandeur, String> Adresse;
    @FXML
    private TableColumn<Demandeur, String> Nb_Exper;
    @FXML
    private TableColumn<Demandeur, String> Diplome;
    @FXML
    private TableColumn<Demandeur, String> Salaire;
    @FXML
    private TextField SearchText;

    private Menu homeController;

    private User user;

    public  void initData(Menu homeController,User user){
        try{
            this.user = user;
            this.homeController = homeController;
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("probleme:"+ex.getMessage());
        }
    }


    public void AjouterDemandeurEvent(){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/files/agencerecrutement/Views/AjouterDemandeur.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Nouveau Demandeur ");
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setOnHiding(event -> {
                // Reload TableView data after closing the "Ajouter Entreprise" window
                loadTableView();
            });
            stage.showAndWait();

        }catch (Exception e)
        {
            AlertsConfirmationsController.showAlertWarnning(e.getMessage().toString());
        }
    }
    @FXML
    public void initialize(){
        try{
            loadTableView();
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }
    public void loadTable(){
        try{
            demandeurs = DemandeurDAO.afficherDemandeur();
        }catch(Exception e){
            AlertsConfirmationsController.showAlertWarnning(e.getMessage());
        }
    }

    private void loadTableView(){

        loadTable();

        //utiliser  dans PropertyValueFactory  la meme nom de attribut au objet offre
        Prenom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
        Nom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        //Titre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitre()));
        Adresse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse()));

        Salaire.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getSalaire())));
        Diplome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDiplome()));

        Nb_Exper.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNbAnneeEx())));

        DataDemandeur.setItems(demandeurs);

    }
    public  void OuvrirAfficherDemandeur(Demandeur demandeur1){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/DetailsDemandeur.fxml"));
            Parent parent = fxmlLoader.load();

            DetailsDemandeur detailsDemandeurController = fxmlLoader.getController();
            detailsDemandeurController.initData(demandeur1.getIdUser(),user);

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

    public void OuvrirModifierDemandeur(Demandeur demandeur){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/ModifierDemandeur.fxml"));
            Parent parent = fxmlLoader.load();

            //cree instance de controller ModifierEntrepriseController
            ModifierDemandeurController modifierDemandeurController = fxmlLoader.getController();
            //passer au controller  l objet entreprise

            modifierDemandeurController.initData(demandeur);

            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Modifier Demandeur ");
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setOnHiding(event -> {
                loadTableView();
            });
            stage.showAndWait();

        }catch(Exception e)
        {
            AlertsConfirmationsController.showAlertWarnning(e.getMessage().toString());
        }
    }
    private  void ouvrirPreferences(Demandeur demandeur){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/AfficherPrefrencesClients.fxml"));
            Parent parent = fxmlLoader.load();

            AfficherPrefrencesClientsController afficherPrefrencesClientsController = fxmlLoader.getController();
            //passer au controller  l objet entreprise
            afficherPrefrencesClientsController.initData(demandeur.getIdUser(),2);

            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Preferences Demandeur");
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }catch(Exception ex){
            AlertsConfirmationsController.showAlertWarnning("probleme :"+ex.getMessage());
        }
    }
    //event modifier
    @FXML
    private void  showModifier(){
        try{
            Demandeur demandeur = DataDemandeur.getSelectionModel().getSelectedItem();
            if (demandeur != null) {
                OuvrirModifierDemandeur(demandeur);
            } else {
                System.out.println("Aucune ligne sélectionnée.");
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }
    //event afficher
    @FXML
    private void  showDetails(){
        try{
            Demandeur demandeur = DataDemandeur.getSelectionModel().getSelectedItem();
            if (demandeur != null) {
                OuvrirAfficherDemandeur(demandeur);
            } else {
                AlertsConfirmationsController.showAlertWarnning("Aucune ligne sélectionnée.");
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }
    //event show prefrences
    @FXML
    private void  showPrefernces(){
        try{
            Demandeur demandeur = DataDemandeur.getSelectionModel().getSelectedItem();
            if (demandeur != null) {
                ouvrirPreferences(demandeur);
            } else {
                AlertsConfirmationsController.showAlertWarnning("Aucune ligne sélectionnée.");
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }

    //event recrutement de demandeur
    @FXML
    private void  showRecrutements(ActionEvent event){
        try{
            Demandeur demandeur = DataDemandeur.getSelectionModel().getSelectedItem();
            if (demandeur != null) {
               homeController.chargerGestionRecrutement(demandeur.getIdUser()); // afficher abonnment par rapport a entreprise
            } else {
                AlertsConfirmationsController.showAlertWarnning("Aucune ligne sélectionnée.");
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }
    @FXML
    private void searchDemandeur(){
        try{
            if(!SearchText.getText().isEmpty()){
                //cree une autre list =>va contient result de rechreche
                ObservableList<Demandeur> filteredList = FXCollections.observableArrayList();
                for(Demandeur  demandeur : demandeurs){

                    if( demandeur.getNom().toLowerCase().contains(SearchText.getText().toLowerCase()) ||
                            demandeur.getAdresse().toLowerCase().contains(SearchText.getText().toLowerCase())||
                            demandeur.getDiplome().toLowerCase().contains(SearchText.getText().toLowerCase())||
                            demandeur.getPhone().toLowerCase().contains(SearchText.getText().toLowerCase())
                    )
                        filteredList.add(demandeur);
                }
                DataDemandeur.setItems(filteredList);
            }else{
                //si input de recherche vide on refrech la tableView
                loadTableView();
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }
}
