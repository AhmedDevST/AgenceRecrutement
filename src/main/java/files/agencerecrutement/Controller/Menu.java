package files.agencerecrutement.Controller;
import files.agencerecrutement.DAO.Utilitaire;
import files.agencerecrutement.Model.User;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.IOException;


public class Menu {

    @FXML
    private HBox hbox1;
    @FXML
    private Button b;
    @FXML
    private VBox display_client,display_journal;
    @FXML
    private ImageView up, down, up_journal, down_journal;
    @FXML
    private HBox Entreprises, Demandeurs, offres, recrutements, annonces, postulations, journal, categorie, Editions;
    @FXML
    private Pane home;
    @FXML
    private HBox Abonnement;

    static boolean hbox_offre, hbox_dash, hbox_demandeur, hbox_entreprise, hbox_postulation, hbox_recrutement, hbox_cat;
    static boolean hbox_journal, hbox_edition, hbox_annonce,hbox_abonnement;


    private User user;

    public  void setUser(User user){
        try{
            this.user = user;
            initialise("hbox_dash");
            set_status(hbox1);
            chargerDashbord();
        }catch (Exception ex){
            showAlertWarnning("Probleme :" +ex);
        }
    }


    // ---------- DEBUT :  les Events  pour charger les interfaces

    //gestion abonnemet
    @FXML
    public void Abonnement_clicked(){
        try{
            initialise("hbox_abonnement");
            set_status(Abonnement);
            chargerGestionAbonnement(0,true);
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    //gestion categorie
    @FXML
    public void cat_clicked()
    {
        try{
            initialise("hbox_cat");
            set_status(categorie);
            chargerGestionCategorie();
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }

    //dashboard
    @FXML
    public void dashboard_clicked(){
        try{
            initialise("hbox_dash");
            set_status(hbox1);
            chargerDashbord();
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }

    //gestion demandeurs
    @FXML
    public void Demand_clicked(){
        try{
            initialise("hbox_demandeur");
            set_status(Demandeurs);
            chargerGestionDemandeur();
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    //gestion entreprise
    public void Eses_clicked(){
        try{
            initialise("hbox_entreprise");
            set_status(Entreprises);
            chargerGestionEntreprise();
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    //gestion journaux
    @FXML
    public void journal_clicked()
    {
        try{
            initialise("hbox_journal");
            set_status(journal);
            chargerGestionJournal();
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    //gestion editions
    public void edit_clicked()
    {
        try{
            initialise("hbox_edition");
            set_status(Editions);
            chargerGestionEdition();
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }

    //gestion annonces
    @FXML
    public void annonces_clicked(){
        try{
            initialise("hbox_annonce");
            set_status(annonces);
            chargerGestionAnnonce();
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    //gestion recrutements
    @FXML
    public void recrutements_clicked(){
        try{
            initialise("hbox_recrutement");
            set_status(recrutements);
            chargerGestionRecrutement(0);
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    //gestion offres
    public void offres_clicked(){
        try{
            initialise("hbox_offre");
            set_status(offres);
            chargerGestionOffre(0);//afficher tous les offres
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    //gestion postulation
    public void postulation_clicked(){
        try{
            initialise("hbox_postulation");
            set_status(postulations);
            chargerGestionPostulation();
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }

    //logout
    public void logout(){
        Platform.exit();

    }
    // ---------- FIN :  les Events  pour charger les interfaces


    // ---------- DEBUT :  les methodes pour charger les interfaces
    // charger gestion entreprise
    public void chargerGestionEntreprise() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/GestionEntreprise.fxml"));
            Parent parent = fxmlLoader.load();
            //cree instance de controller GestionEntrepriseController
            GestionEntrepriseController gestionEntrepriseController = fxmlLoader.getController();
            //passer au controller  l objet user et le controller home
            gestionEntrepriseController.initData(this);
            home.getChildren().removeAll();
            home.getChildren().setAll(parent);
        }catch (Exception ex){
            showAlert(ex.getMessage());
        }
    }
    // charger gestion demandeur
    public void chargerGestionDemandeur() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/Demandeur.fxml"));
            Parent parent = fxmlLoader.load();
            //cree instance de controller DemandeurController
            DemandeurController demandeurController = fxmlLoader.getController();
            //passer au controller  l objet user et le controller home
            demandeurController.initData(this,user);
            home.getChildren().removeAll();
            home.getChildren().setAll(parent);
        }catch (Exception ex){
            showAlert(ex.getMessage());
        }
    }
    // charger gestion journals
    public void chargerGestionJournal() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/GestionJournaux.fxml"));
            Parent parent = fxmlLoader.load();
            //cree instance de controller GestionJournauxController
            GestionJournauxController gestionJournauxController = fxmlLoader.getController();
            //passer au controller  l objet user et le controller home
            gestionJournauxController.initData(this);
            home.getChildren().removeAll();
            home.getChildren().setAll(parent);
        }catch (Exception ex){
            showAlert(ex.getMessage());
        }
    }
    // charger gestion categorie
    public void chargerGestionCategorie() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/GestionCategorie.fxml"));
            Parent parent = fxmlLoader.load();
            home.getChildren().removeAll();
            home.getChildren().setAll(parent);
        }catch (Exception ex){
            showAlert(ex.getMessage());
        }
    }

    // charger gestion editions
    public void chargerGestionEdition() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/GestionEdition.fxml"));
            Parent parent = fxmlLoader.load();
            home.getChildren().removeAll();
            home.getChildren().setAll(parent);
        }catch (Exception ex){
            showAlert(ex.getMessage());
        }
    }
    // charger dashbord
    public void chargerDashbord() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/Dhasbord.fxml"));
            Parent parent = fxmlLoader.load();
            home.getChildren().removeAll();
            home.getChildren().setAll(parent);
        }catch (Exception ex){
            showAlert(ex.getMessage());
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
            showAlert(ex.getMessage());
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
            showAlert(ex.getMessage());
        }
    }

    //charger Gestion Offre
    public void chargerGestionOffre( int idEst) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/GestionOffre.fxml"));
            Parent parent = fxmlLoader.load();
            //cree instance de controller OffreController
            OffreController offreController = fxmlLoader.getController();
            //passer au controller  l objet user
            offreController.initData(user,idEst);
            home.getChildren().removeAll();
            home.getChildren().setAll(parent);
        }catch (Exception ex){
            showAlert(ex.getMessage());
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
            showAlert(ex.getMessage());
        }
    }
    //charger Gestion Recrutement
    public void chargerGestionRecrutement(int idDem) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/Recrutement.fxml"));
            Parent parent = fxmlLoader.load();
            //cree instance de controller RecrutementController
            RecrutementController recrutementController = fxmlLoader.getController();
            //passer au controller  l objet user
            recrutementController.initData(user,idDem);
            home.getChildren().removeAll();
            home.getChildren().setAll(parent);
        }catch (Exception ex){
            showAlert(ex.getMessage());
        }
    }
    // ---------- FIN :  les methodes pour charger les interfaces




    // ---------- DEBUT :  les Events  pour le style
    @FXML
    public void hbox_test(){

        hbox1.setStyle("-fx-background-color: #b0a7ff;");
        b.setStyle("-fx-background-color: #b0a7ff");
        showAlert("Ok");
    }

    public void set_status(HBox h)
    {
        Abonnement.setStyle("-fx-background-color : transparent ");
        hbox1.setStyle("-fx-background-color : transparent ");
        Entreprises.setStyle("-fx-background-color : transparent");
        Demandeurs.setStyle("-fx-background-color : transparent");;
        offres.setStyle("-fx-background-color : transparent");
        recrutements.setStyle("-fx-background-color : transparent");
        annonces.setStyle("-fx-background-color : transparent");
        postulations.setStyle("-fx-background-color : transparent");
        h.setStyle("-fx-background-color :  #ffffff80");

    }

    public void display_vbox(VBox V, ImageView up, ImageView down)
    {
        up.setVisible(true);
        down.setVisible(false);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), V);
        transition.setFromY(-40); // Déplacer le HBox de -200 (pour qu'il soit hors de la vue)
        transition.setToY(0);
        transition.play();

        V.setManaged(true);
        V.setVisible(true);
    }
    public void display_client(){
        display_vbox(display_client,up,down);
        hide_vbox(display_journal, up_journal, down_journal);
    }

    public void display_journal(){
        display_vbox(display_journal,up_journal,down_journal);
        hide_vbox(display_client, up, down);
    }

    public void hide_vbox(VBox V, ImageView up, ImageView down)
    {
        up.setVisible(false);
        down.setVisible(true);

        V.setManaged(false);
        V.setVisible(false);
    }

    public void hide_client()
    {
        hide_vbox(display_client, up, down);
    }

    public void hide_journal(){
        hide_vbox(display_journal, up_journal, down_journal);
    }
    public void initialise(String a ){
        hbox_offre = false;
        hbox_abonnement = false;
        hbox_dash = false;
        hbox_demandeur = false; hbox_entreprise = false; hbox_postulation = false; hbox_recrutement = false;  hbox_cat = false;
        hbox_journal = false; hbox_edition = false;
        switch (a){
            case "hbox_offre" : hbox_offre = true; break;
            case "hbox_abonnement" : hbox_abonnement = true; break;
            case "hbox_dash" : hbox_dash = true; break;
            case "hbox_demandeur" : hbox_demandeur = true; break;
            case "hbox_entreprise" : hbox_entreprise = true; break;
            case "hbox_postulation" : hbox_postulation = true; break;
            case "hbox_recrutement" : hbox_recrutement = true; break;
            case "hbox_cat" : hbox_cat = true; break;
            case "hbox_journal" : hbox_journal = true; break;
            case "hbox_edition" : hbox_edition = true; break;
            case "hbox_annonce" : hbox_annonce = true;break;
        }
    }

    public void dash_entred(){
        hbox1.setStyle("-fx-background-color :  #ffffff80");
    }
    public void offre_entred() {

            offres.setStyle("-fx-background-color :  #ffffff80");
    }
    public void annonce_entred(){
        annonces.setStyle("-fx-background-color :  #ffffff80");
    }
    public void Abonnement_entred(){
        Abonnement.setStyle("-fx-background-color :  #ffffff80");
    }

    public void postulation_entred(){
        postulations.setStyle("-fx-background-color :  #ffffff80");
    }
    public void recrutement_entred(){
        recrutements.setStyle("-fx-background-color : #ffffff80");
    }
    public void dash_exited(){
        hbox1.setStyle("-fx-background-color : transparent");
    }
    public void Abonnement_exited(){
        Abonnement.setStyle("-fx-background-color : transparent");
    }

    public void offre_exited(){
        if(!hbox_offre)
            offres.setStyle("-fx-background-color : transparent");
    }
    public void annonce_exited(){
        if(!hbox_annonce)
            annonces.setStyle("-fx-background-color : transparent");
    }
    public void postulation_exited(){
        if(!hbox_postulation)
            postulations.setStyle("-fx-background-color : transparent");
    }
    public void recrutement_exited(){
        if(!hbox_recrutement)
            recrutements.setStyle("-fx-background-color : transparent");
    }

    public void journal_entred(){
        journal.setStyle("-fx-background-color : #ffffff80");
    }

    public void journal_exited(){
        journal.setStyle("-fx-background-color : transparent");
    }

    public void cat_entred(){
        categorie.setStyle("-fx-background-color : #ffffff80");
    }

    public void cat_exited(){
        categorie.setStyle("-fx-background-color : transparnt");
    }

    public void edit_entred(){
        Editions.setStyle("-fx-background-color : #ffffff80");
    }

    public void edit_exited(){
        Editions.setStyle("-fx-background-color : transparnt");
    }

    public void eses_entred(){
        Entreprises.setStyle("-fx-background-color : #ffffff80");
    }

    public void eses_exited(){
        Entreprises.setStyle("-fx-background-color : transparnt");
    }

    public void demandeur_entred(){
        Editions.setStyle("-fx-background-color : #ffffff80");
    }

    public void demandeur_exited(){
        Editions.setStyle("-fx-background-color : transparnt");
    }


    // ---------- FIN :  les Events  pour le style
    public void showAlertWarnning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("title");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showAlert(String ok) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("title");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText(ok);
        alert.showAndWait();
    }
}
