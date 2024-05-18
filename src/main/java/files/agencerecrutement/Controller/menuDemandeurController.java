package files.agencerecrutement.Controller;

import files.agencerecrutement.Model.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class menuDemandeurController {



    @FXML
    private HBox  preferences, recrutements, postulations, Annonces , profile;
    @FXML
    private Pane home;

    static boolean hbox_preferences, hbox_postulation, hbox_recrutement,hbox_profile;
    static boolean  hbox_annonces;
    private User user;

    public  void setUser(User user){
        try{
            this.user = user;
            initialise("hbox_annonces");
            set_status(Annonces);
            chargerGestionAnnonce();
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme :" +ex);
        }
    }

    // ---------- DEBUT :  les Events  pour charger les interfaces
    @FXML
    public void profile_clicked(){
        try{
            initialise("hbox_profile");
            set_status(profile);
            chargerProfile();
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme :" +ex);
        }
    }
    @FXML
    public void Annonce_clicked(){
        try{
            initialise("hbox_annonces");
            set_status(Annonces);
            chargerGestionAnnonce();
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme :" +ex);
        }
    }

    //gestion recrutements
    @FXML
    public void recrutements_clicked(){
        try{
            initialise("hbox_recrutement");
            set_status(recrutements);
            chargerGestionRecrutement();
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }
    //gestion postulation
    @FXML
    public void postulation_clicked(){
        try{
            initialise("hbox_postulation");
            set_status(postulations);
            chargerGestionPostulation();
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }
    //prefrences
    @FXML
    public void preferences_clicked(){
        try{
            initialise("hbox_preferences");
            set_status(preferences);
            chargerGestionPreferences();
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }
    //logout
    @FXML
    public void logout(){
        try{
            Platform.exit();
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }

    // ---------- FIN :  les Events  pour charger les interfaces

    // ---------- DEBUT :  les methodes pour charger les interfaces
    //charger profile
    public void chargerProfile( ) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/DetailsDemandeur.fxml"));
            Parent parent = fxmlLoader.load();

            DetailsDemandeur detailsDemandeur = fxmlLoader.getController();

            detailsDemandeur.initData(user.getIdUser(),user);
            home.getChildren().removeAll();
            home.getChildren().setAll(parent);
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }
    //charger Gestion prefrences
    public void chargerGestionPreferences( ) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/PreferencesClient.fxml"));
            Parent parent = fxmlLoader.load();
            //cree instance de controller PostulationController
            PreferencesClientController preferencesClientController = fxmlLoader.getController();
            //passer au controller  l objet entreprise
            preferencesClientController.initData(user.getIdUser(),2);
            home.getChildren().removeAll();
            home.getChildren().setAll(parent);
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }
    //charger Gestion Postulation
    public void chargerGestionPostulation() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/Postulation.fxml"));
            Parent parent = fxmlLoader.load();
            //cree instance de controller PostulationController
            PostulationController postulationController = fxmlLoader.getController();
            //passer au controller  l objet user
            postulationController.initData(user);
            home.getChildren().removeAll();
            home.getChildren().setAll(parent);
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }
    //charger Gestion Recrutement
    public void chargerGestionRecrutement() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/Recrutement.fxml"));
            Parent parent = fxmlLoader.load();
            //cree instance de controller RecrutementController
            RecrutementController recrutementController = fxmlLoader.getController();
            //passer au controller  l objet user
            recrutementController.initData(user,user.getIdUser());
            home.getChildren().removeAll();
            home.getChildren().setAll(parent);
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }
    //charger Gestion Annonce
    public void  chargerGestionAnnonce() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/GestionAnnonces.fxml"));
            Parent parent = fxmlLoader.load();
            //cree instance de controller GestionAnnoncesController
            GestionAnnoncesController gestionAnnoncesController = fxmlLoader.getController();
            //passer au controller  l objet user
            gestionAnnoncesController.initData(user);
            home.getChildren().removeAll();
            home.getChildren().setAll(parent);
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }
    // ---------- FIN :  les methodes pour charger les interfaces

    // ---------- DEBUT :  les Events  pour le style
    public void set_status(HBox h)
    {
        Annonces.setStyle("-fx-background-color : transparent ");
        profile.setStyle("-fx-background-color : transparent ");

        preferences.setStyle("-fx-background-color : transparent");
        recrutements.setStyle("-fx-background-color : transparent");

        postulations.setStyle("-fx-background-color : transparent");
        h.setStyle("-fx-background-color :  #ffffff80");

    }


    public void initialise(String a ){
        hbox_preferences = false;
        hbox_annonces = false;
        hbox_profile = false;
        hbox_postulation = false; hbox_recrutement = false;
        switch (a){
            case "hbox_preferences" : hbox_preferences = true; break;
            case "hbox_annonces" : hbox_annonces = true; break;

            case "hbox_postulation" : hbox_postulation = true; break;
            case "hbox_profile" : hbox_profile = true; break;
            case "hbox_recrutement" : hbox_recrutement = true; break;

        }
    }

    public void dash_entred(){
        Annonces.setStyle("-fx-background-color :  #ffffff80");
    }

    public void preference_entred() {

        preferences.setStyle("-fx-background-color :  #ffffff80");
    }

    public void postulation_entred(){
        postulations.setStyle("-fx-background-color :  #ffffff80");
    }

    public void recrutement_entred(){
        recrutements.setStyle("-fx-background-color : #ffffff80");
    }
    public void profile_entred(){
        profile.setStyle("-fx-background-color : #ffffff80");
    }
    public void dash_exited(){
        if(!hbox_annonces)
            Annonces.setStyle("-fx-background-color : transparent");
    }

    public void preference_exited(){
        if(!hbox_preferences)
            preferences.setStyle("-fx-background-color : transparent");
    }


    public void postulation_exited(){
        if(!hbox_postulation)
            postulations.setStyle("-fx-background-color : transparent");
    }

    public void recrutement_exited(){
        if(!hbox_recrutement)
            recrutements.setStyle("-fx-background-color : transparent");
    }
    public void profile_exited(){
        if(!hbox_profile)
            profile.setStyle("-fx-background-color : transparent");
    }

    // ---------- FIN :  les Events  pour le style
}
