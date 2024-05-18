package files.agencerecrutement.Controller;

import files.agencerecrutement.Model.Client;
import files.agencerecrutement.Model.User;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class menuEntrepriseController {
    @FXML
    private HBox Abonnement;
    @FXML
    private HBox  offres, recrutements, postulations, preferences;
    @FXML
    private Pane home;

    static boolean hbox_offre, hbox_postulation, hbox_recrutement;
    static boolean  hbox_annonce, hbox_preferences, hbox_abonnement;

    private User user;

    public  void setUser(User user){
        try{
            this.user = user;
            initialise("hbox_postulation");
            set_status(postulations);
            chargerGestionPostulation();
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme :" +ex);
        }
    }

    // ---------- DEBUT :  les Events  pour charger les interfaces
    @FXML
    public void Abonnement_clicked(){
        try{
            initialise("hbox_abonnement");
            set_status(Abonnement);
            chargerGestionAbonnement(user.getIdUser(),false);
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
    //gestion offres
    @FXML
    public void offres_clicked(){
        try{
            initialise("hbox_offre");
            set_status(offres);
            chargerGestionOffre();
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
    //charger le profile
    public void chargerProfile(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/detailsEntreprise.fxml"));
            Parent parent = fxmlLoader.load();

            DetailsEntrepriseController detailsEntrepriseController = fxmlLoader.getController();
            //passer au controller  l objet user
            detailsEntrepriseController.initData(user.getIdUser());
            home.getChildren().removeAll();
            home.getChildren().setAll(parent);
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }
    //charger Gestion Abonnement
    public void chargerGestionAbonnement( int idEst,boolean byJournal){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/Abonnement.fxml"));
            Parent parent = fxmlLoader.load();
            //cree instance de controller AbonnementController
            AbonnementController abonnementController = fxmlLoader.getController();
            //passer au controller  l objet user
            abonnementController.initData(user,idEst,byJournal);
            home.getChildren().removeAll();
            home.getChildren().setAll(parent);
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }

    //charger Gestion Offre
    public void chargerGestionOffre( ) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/GestionOffre.fxml"));
            Parent parent = fxmlLoader.load();
            //cree instance de controller OffreController
            OffreController offreController = fxmlLoader.getController();
            //passer au controller  l objet user
            offreController.initData(user, user.getIdUser());
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
            preferencesClientController.initData(user.getIdUser(),1);
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
            recrutementController.initData(user,0);
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
        Abonnement.setStyle("-fx-background-color : transparent ");

        offres.setStyle("-fx-background-color : transparent");
        recrutements.setStyle("-fx-background-color : transparent");
        preferences.setStyle("-fx-background-color : transparent");
        postulations.setStyle("-fx-background-color : transparent");
        h.setStyle("-fx-background-color :  #ffffff80");

    }
    public void initialise(String a ){
        hbox_offre = false;
        hbox_abonnement = false;
          hbox_postulation = false; hbox_recrutement = false;
          hbox_preferences = false;
        switch (a){
            case "hbox_offre" : hbox_offre = true; break;
            case "hbox_abonnement" : hbox_abonnement = true; break;

            case "hbox_postulation" : hbox_postulation = true; break;
            case "hbox_recrutement" : hbox_recrutement = true; break;

            case "hbox_annonce" : hbox_annonce = true;break;
            case "hbox_preferences" : hbox_preferences = true; break;
        }
    }
    public void dash_entred(){
        Abonnement.setStyle("-fx-background-color :  #ffffff80");
    }

    public void offre_entred() {

        offres.setStyle("-fx-background-color :  #ffffff80");
    }

    public void postulation_entred(){
        postulations.setStyle("-fx-background-color :  #ffffff80");
    }

    public void recrutement_entred(){
        recrutements.setStyle("-fx-background-color : #ffffff80");
    }
    public void dash_exited(){
        if(!hbox_abonnement)
            Abonnement.setStyle("-fx-background-color : transparent");
    }

    public void offre_exited(){
        if(!hbox_offre)
            offres.setStyle("-fx-background-color : transparent");
    }



    public void postulation_exited(){
        if(!hbox_postulation)
            postulations.setStyle("-fx-background-color : transparent");
    }

    public void recrutement_exited(){
        if(!hbox_recrutement)
            recrutements.setStyle("-fx-background-color : transparent");
    }

    public  void preference_entred(){
        preferences.setStyle("-fx-background-color :  #ffffff80");
    }
    public void preference_exited(){
        if(!hbox_preferences)
            preferences.setStyle("-fx-background-color : transparent");
    }

}
