package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.EntrepriseDAO;
import files.agencerecrutement.Model.Entreprise;
import files.agencerecrutement.Model.Recrutement;
import files.agencerecrutement.Model.User;
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

import java.util.Objects;


public class GestionEntrepriseController {

    private  ObservableList<Entreprise> entreprises = FXCollections.observableArrayList();
    @FXML
    private TableView<Entreprise> DataEntreprises ;
    @FXML
    private TableColumn<Entreprise,Integer> CodeInterne;
    @FXML
    private TableColumn<Entreprise,String> RaisonSocialEst;
    @FXML
    private TableColumn<Entreprise,String> AdresseEst;
    @FXML
    private TableColumn<Entreprise,String> phoneEst;
    @FXML
    private  TableColumn<Entreprise,Void> ActionsCol;
    @FXML
    private TextField SearchText;
    private Menu homeController;


    public  void initData(Menu homeController){
        try{
           this.homeController = homeController;
        }catch (Exception ex){
            showAlertWarnning("probleme:"+ex.getMessage());
        }
    }


    @FXML
    public  void initialize(){
        try{
            loadTableView();
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    private void loadData(){
        try{
            //get la list des entreprises  a partir de base de donnne
            entreprises = EntrepriseDAO.afficherEntreprises();
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    private void loadTableView(){

        loadData();
        //utiliser  dans PropertyValueFactory  la meme nom de attribut au objet entreprise
        CodeInterne.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        RaisonSocialEst.setCellValueFactory(new PropertyValueFactory<>("raisonSocial"));
        AdresseEst.setCellValueFactory(new PropertyValueFactory<>("Adresse"));
        phoneEst.setCellValueFactory(new PropertyValueFactory<>("phone"));

        DataEntreprises.setItems(entreprises);
    }



    //event  sur textField de recherche:touche entrer
   @FXML
    private void searchEst(){
        try{
            if(!SearchText.getText().isEmpty()){
                //cree une autre list =>va contient result de rechreche
                ObservableList<Entreprise> filteredList = FXCollections.observableArrayList();
                for(Entreprise  entreprise : entreprises){
                    //si le raison social,address,phone d  un entreprise  similaire de mot cle on la ajouter
                    if( entreprise.getRaisonSocial().toLowerCase().contains(SearchText.getText().toLowerCase()) ||
                            entreprise.getAdresse().toLowerCase().contains(SearchText.getText().toLowerCase())||
                        entreprise.getPhone().toLowerCase().contains(SearchText.getText().toLowerCase())
                    )
                        filteredList.add(entreprise);
                }
                DataEntreprises.setItems(filteredList);
            }else{
                //si input de recherche vide on refrech la tableView
                loadTableView();
            }
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }

    //event de button Nouvelle entreprise : permet de ouvrir la fenetre d ajouter une entreprise
    @FXML
    private void AjouterEntrepiseEvent(){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource("/files/agencerecrutement/Views/AjouterEntreprise.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Nouvelle Entreprise ");
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            // Wait for the new window to be closed
            stage.setOnHiding(event -> {
                // Reload TableView data after closing the "Ajouter Entreprise" window
                loadTableView();
            });

            stage.showAndWait(); // Use showAndWait() to wait for the window to close before continuing

        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }

    @FXML
    private void  showModifier(){
        try{
            Entreprise entreprise = DataEntreprises.getSelectionModel().getSelectedItem();
            if (entreprise != null) {
                OuvrirModifierEntreprise(entreprise);
            } else {
                AlertsConfirmationsController.showAlertWarnning("Aucune ligne sélectionnée.");
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }
    @FXML
    private void  showDetails(){
        try{
            Entreprise entreprise = DataEntreprises.getSelectionModel().getSelectedItem();
            if (entreprise != null) {
                ouvrirDetails(entreprise);
            } else {
                AlertsConfirmationsController.showAlertWarnning("Aucune ligne sélectionnée.");
            }
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    @FXML
    private void  showPrefernces(){
        try{
            Entreprise entreprise = DataEntreprises.getSelectionModel().getSelectedItem();
            if (entreprise != null) {
                ouvrirPreferences(entreprise);
            } else {
                AlertsConfirmationsController.showAlertWarnning("Aucune ligne sélectionnée.");
            }
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }

    //event sur show abonnemt permet de afficher les abonnment de ce entreprise
    @FXML
    private void  showAbonnement(ActionEvent event){
        try{
            Entreprise entreprise = DataEntreprises.getSelectionModel().getSelectedItem();
            if (entreprise != null) {
               homeController.chargerGestionAbonnement(entreprise.getIdUser(),false); // afficher abonnment par rapport a entreprise
            } else {
                AlertsConfirmationsController.showAlertWarnning("Aucune ligne sélectionnée.");
            }
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    //event sur show abonnemt permet de afficher les abonnment de ce entreprise
    @FXML
    private void  showOffres(ActionEvent event){
        try{
            Entreprise entreprise = DataEntreprises.getSelectionModel().getSelectedItem();
            if (entreprise != null) {
                homeController.chargerGestionOffre(entreprise.getIdUser()); // afficher abonnment par rapport a entreprise
            } else {
                AlertsConfirmationsController.showAlertWarnning("Aucune ligne sélectionnée.");
            }
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    //event sur show abonnemt permet de ajouter un  abonnment a ce entreprise
    @FXML
    private void  AjouterAbo(ActionEvent event){
        try{
            Entreprise entreprise = DataEntreprises.getSelectionModel().getSelectedItem();
            if (entreprise != null) {
                OuvrirAjouterAbonnment(entreprise);
            } else {
                AlertsConfirmationsController.showAlertWarnning("Aucune ligne sélectionnée.");
            }
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    //methode permet de ouvrir le fenetre de modification d un entreprise passer comme argument
    private  void OuvrirAjouterAbonnment(Entreprise entreprise){
        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/AjoutAbon.fxml"));
            Parent parent = fxmlLoader.load();

            //cree instance de controller ModifierEntrepriseController
            AjoutAbonController ajouterAbo = fxmlLoader.getController();
            //passer au controller  l objet entreprise
            ajouterAbo.initData(entreprise);

            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Ajouter Entreprise ");
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            stage.show();
        }catch(Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }

    //methode permet de ouvrir le fenetre de modification d un entreprise passer comme argument
    private  void OuvrirModifierEntreprise(Entreprise entreprise){
        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/ModifierEntreprise.fxml"));
            Parent parent = fxmlLoader.load();

            //cree instance de controller ModifierEntrepriseController
            ModifierEntrepriseController modifierEntrepriseController = fxmlLoader.getController();
            //passer au controller  l objet entreprise
            modifierEntrepriseController.initData(entreprise.getIdUser());

            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Modifier Entreprise ");
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            // Wait for the new window to be closed
            stage.setOnHiding(event -> {
                // Reload TableView data after closing the "modifier Entreprise" window
                loadTableView();
            });

            stage.showAndWait(); // Use showAndWait() to wait for the window to close before continuing

        }catch(Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    private  void ouvrirPreferences(Entreprise entreprise){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/AfficherPrefrencesClients.fxml"));
            Parent parent = fxmlLoader.load();

            AfficherPrefrencesClientsController afficherPrefrencesClientsController = fxmlLoader.getController();
            //passer au controller  l objet entreprise
            afficherPrefrencesClientsController.initData(entreprise.getIdUser(),1);

            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Preferences Entreprise");
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }catch(Exception ex){
             showAlertWarnning("probleme :"+ex.getMessage());
       }
    }

    private  void ouvrirDetails(Entreprise entreprise){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/detailsEntreprise.fxml"));
            Parent parent = fxmlLoader.load();

            DetailsEntrepriseController detailsEntrepriseController = fxmlLoader.getController();
            //passer au controller  l objet entreprise
            detailsEntrepriseController.initData(entreprise.getIdUser());

            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("details Entreprise");
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }catch(Exception ex){
            showAlertWarnning("probleme :"+ex.getMessage());
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
