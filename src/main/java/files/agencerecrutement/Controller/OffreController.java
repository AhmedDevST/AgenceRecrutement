package files.agencerecrutement.Controller;
import files.agencerecrutement.DAO.AbonnementDAO;
import files.agencerecrutement.Model.Emission;
import files.agencerecrutement.Model.Offre;
import files.agencerecrutement.DAO.OffreDAO;
import files.agencerecrutement.Model.User;
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

import java.util.Objects;


public class OffreController {
    private ObservableList<Offre> offres = FXCollections.observableArrayList();
    private ObservableList<Emission> offresEmis = FXCollections.observableArrayList();

    @FXML
    private TableView<Offre> DataOffre;
    @FXML
    private TableView<Emission> DataOffreEmis ;
    @FXML
    private TableColumn<Offre,String> Titre;
    @FXML
    private TableColumn<Offre,String> RaisonSocialEst;
    @FXML
    private TableColumn<Offre,String> Experience;
    @FXML
    private TableColumn<Offre,String> nb_poste;
    @FXML
    private TableColumn<Offre,String> etat;
    @FXML
    private  TableColumn<Offre,Void> ActionsCol;
    @FXML
    private TableColumn<Emission,String> Titre1, RaisonSocialEst1, Experience1, nb_poste1, etat1,
            DateEmission, NomJr;
    @FXML
    private TextField SearchText, SearchText1;



    @FXML
    private Button AjouterOffreEvent;



    private User user ;
    private int IdEst ;
    // ce variable utilliser dans le cas de user agent : si agent veux voir les abonnment d un entreprise
    // IdEst = 0 => afficher tous offres de tous entreprise
    //sinon => afficher offres d un entreprise

    //initialiser interface en fonction de user
    public  void initData(User user,int IdEst){
        try{
            this.user = user ;
            this.IdEst = IdEst;
            //set les droit de user
            switch (user.getRoleUser()){
                case  1 :
                    //agent
                    // agent n a  pas le droit de ajouter des offres
                    AjouterOffreEvent.setVisible(false);
                    // agent a droit serach par  titre et eaison social
                    SearchText.setPromptText("Recherche par Raison socile  ou Titre d ' offre");
                    SearchText1.setPromptText("Recherche par Raison socile  ou Titre d ' offre");
                    break;
                case  2 :
                    //entreprise
                    //entreprise a le  droit de ajouter offres
                    AjouterOffreEvent.setVisible(true);
                    // agent a droit serach par  tite offre
                    SearchText.setPromptText("Recherche par Titre d ' offre ");
                    SearchText1.setPromptText("Recherche par Titre d ' offre ");
                    break;
            }
            loadTableView();
            loadTableOffreEmis();
        } catch (Exception ex) {
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }



    private void loadOffres(){
        try{
            if(user.getRoleUser() == 1){
                // si user est un agent
                if(IdEst == 0){
                    // afficher tous  offres
                    offres = OffreDAO.afficherOffre();
                }else
                    //afficher les offres d un entrprise
                    offres = OffreDAO.afficherOffreOfEntreprise(IdEst);
            }else{
                //si user est un entreprise
                // afficher ses offres
                offres = OffreDAO.afficherOffreOfEntreprise(user.getIdUser());
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());
        }
    }
    private void loadOffreEmis(){
        try{
            if(user.getRoleUser() == 1){
                // si user est un agent
                if(IdEst == 0){
                    // afficher tous  offres emis
                    offresEmis = OffreDAO.afficherOffreEmis();
                }else
                    //afficher les offres  emis d un entrprise
                    offresEmis = OffreDAO.afficherOffreEmisOfEntreprise(IdEst);
            }else{
                //si user est un entreprise
                // afficher ses offres
                offresEmis = OffreDAO.afficherOffreEmisOfEntreprise(user.getIdUser());
            }
        }catch (Exception e)
        {
            AlertsConfirmationsController.showAlertWarnning(e.getMessage());
        }
    }


        private void loadTableView(){
        try {
            loadOffres();

            //utiliser  dans PropertyValueFactory  la meme nom de attribut au objet offre
            Titre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitre()));
            RaisonSocialEst.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEntreprise().getRaisonSocial()));

            Experience.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNbAnneeEx())));
            nb_poste.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNbPostOfferts())));

            etat.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().ReturnEtatString()));

           // showAlert("testetetete");
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

                                    //icon modifier offre
                                    final Image imageEdit = new Image(Objects.requireNonNull(getClass().getResource("/files/agencerecrutement/Images/editIcon.png")).toString());
                                    final ImageView editEstViewImg = new ImageView(imageEdit);
                                    //style
                                    editEstViewImg.setFitWidth(30);
                                    editEstViewImg.setFitHeight(30);
                                    editEstViewImg.setCursor(Cursor.HAND);
                                    Tooltip tooltipEdit = new Tooltip("Modifier l offre");
                                    Tooltip.install(editEstViewImg,tooltipEdit);

                                    //event sur icon modifier
                                    editEstViewImg.setOnMouseClicked(actionEvent -> {
                                        //get entreprise selectionner dans tableview
                                        Offre offre = getTableView().getItems().get(getIndex());
                                        //ouvrir fenetre de modifier offre
                                        OuvrirModifierOffre(offre);

                                    } );
                                    // entreprise seul peut modifier a offre
                                    if(user.getRoleUser() != 2) editEstViewImg.setVisible(false);

                                    //icon info offre
                                    final Image imageDisp = new Image(Objects.requireNonNull(getClass().getResource("/files/agencerecrutement/Images/iconInfo.png")).toString());
                                    final ImageView dispImage = new ImageView(imageDisp);
                                    //style
                                    dispImage.setFitWidth(30);
                                    dispImage.setFitHeight(30);
                                    dispImage.setCursor(Cursor.HAND);
                                    Tooltip tooltipDisplay = new Tooltip("Afficher l offre");
                                    Tooltip.install(dispImage,tooltipDisplay);

                                    //event sur icon modifier
                                    dispImage.setOnMouseClicked(actionEvent -> {
                                                //get entreprise selectionner dans tableview
                                                Offre offre = getTableView().getItems().get(getIndex());
                                                //ouvrir fenetre de modifier offre
                                                OuvrirAfficherOffre(offre);
                                            });

                                    //event sur icon emis offres
                                    final Image imageEmis = new Image(Objects.requireNonNull(getClass().getResource("/files/agencerecrutement/Images/crm.png")).toString());
                                    final ImageView emiImg = new ImageView(imageEmis);
                                    //style
                                    emiImg.setFitWidth(30);
                                    emiImg.setFitHeight(30);
                                    emiImg.setCursor(Cursor.HAND);
                                    Tooltip tooltipEmi = new Tooltip("Emies l offre");
                                    Tooltip.install(emiImg,tooltipEmi);

                                    // entreprise seul peut modifier a offre
                                    if(user.getRoleUser() != 2) emiImg.setVisible(false);

                                    //event sur icon modifier
                                    emiImg.setOnMouseClicked(actionEvent -> {
                                        //get entreprise selectionner dans tableview
                                        Offre offre = getTableView().getItems().get(getIndex());
                                        //ouvrir fenetre de modifier offre
                                        OuvrirEmisOffre(offre);
                                    } );


                                    //met les trois icon dans un Hbox
                                    HBox ContentAllbtn = new HBox(editEstViewImg,dispImage,emiImg);
                                    ContentAllbtn.setStyle("-fx-alignment: CENTER ");
                                    ContentAllbtn.setSpacing(10);
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

            DataOffre.setItems(offres);
        }catch (Exception e)
        {
           AlertsConfirmationsController.showAlertWarnning(e.getMessage());
        }
    }
    private void loadTableOffreEmis(){
        try {
            loadOffreEmis();

            //utiliser  dans PropertyValueFactory  la meme nom de attribut au objet offre
            Titre1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOffreEmis().getTitre()));
            RaisonSocialEst1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOffreEmis().getEntreprise().getRaisonSocial()));

            Experience1.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getOffreEmis().getNbAnneeEx())));
            nb_poste1.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getOffreEmis().getNbPostOfferts())));

            etat1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOffreEmis().ReturnEtatString()));
            DateEmission.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDateEmission())));
            NomJr.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAboUtilise().getJournal().getNomJr())));

            DataOffreEmis.setItems(offresEmis);
        }catch (Exception e){
            AlertsConfirmationsController.showAlertWarnning(e.getMessage());
        }
    }
    public void OuvrirModifierOffre(Offre offre){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/ModifierOffre.fxml"));
            Parent parent = fxmlLoader.load();

            //cree instance de controller ModifierEntrepriseController
            ModifierOffreController modifierOffreController = fxmlLoader.getController();
            //passer au controller  l objet entreprise

            modifierOffreController.initData(offre);

            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Modifier Offre ");
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setOnHiding(event -> {
                // Reload TableView data after closing the "Ajouter Entreprise" window
                loadTableView();
                loadOffreEmis();
            });
            stage.showAndWait();

        }catch (Exception e)
        {
           AlertsConfirmationsController.showAlertWarnning(e.getMessage());
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
    public void AjouterOffreEvent(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/AjouterOffre.fxml"));
            Parent parent = fxmlLoader.load();

            //cree instance de controller AjouterOffreController
            AjouterOffreController ajouterOffreController = fxmlLoader.getController();
            //passer au controller  l objet entreprise

            ajouterOffreController.initData(user);
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Nouvelle Offre ");
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
    public void searchOffre()
    {

        try{
            if(!SearchText.getText().isEmpty()){
                //cree une autre list =>va contient result de rechreche
                ObservableList<Offre> filteredList = FXCollections.observableArrayList();
                for(Offre  offre : offres){
                    //si le raison social,address,phone d  un entreprise  similaire de mot cle on la ajouter
                    if( offre.getTitre().toLowerCase().contains(SearchText.getText().toLowerCase()) ||
                            offre.getEntreprise().getRaisonSocial().toLowerCase().contains(SearchText.getText().toLowerCase()))

                        filteredList.add(offre);
                        //showAlert(String.valueOf("testetet"));
                }

                DataOffre.setItems(filteredList);

            }else{
                //si input de recherche vide on refrech la tableView
                loadTableView();
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage().toString());

        }
    }
    public void searchOffreEmis()
    {
        try{
            if(!SearchText1.getText().isEmpty()){
                //cree une autre list =>va contient result de rechreche
                ObservableList<Emission> filteredList = FXCollections.observableArrayList();
                for(Emission  emission : offresEmis){
                    //si le raison social,address,phone d  un entreprise  similaire de mot cle on la ajouter
                    if( emission.getAboUtilise().getJournal().getNomJr().toLowerCase().contains(SearchText1.getText().toLowerCase()) ||
                            emission.getOffreEmis().getEntreprise().getRaisonSocial().toLowerCase().contains(SearchText1.getText().toLowerCase()) ||
                            emission.getOffreEmis().getTitre().toLowerCase().contains(SearchText1.getText().toLowerCase()))

                        filteredList.add(emission);
                }
                DataOffreEmis.setItems(filteredList);

            }else{
                //si input de recherche vide on refrech la tableView
                loadTableOffreEmis();
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning(ex.getMessage());

        }
    }

    public void OuvrirEmisOffre(Offre offre){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/OffreEmis.fxml"));
            Parent parent = fxmlLoader.load();

            //cree instance de controller ModifierEntrepriseController
            EmisOffreController emisOffreController = fxmlLoader.getController();
            //passer au controller  l objet entreprise

            emisOffreController.initData(offre);

            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Emis Offre ");
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setOnHiding(event -> {
                // Reload TableView data after closing the "Ajouter Entreprise" window
                loadTableOffreEmis();
            });
            stage.showAndWait();
        }catch (Exception e)
        {
            AlertsConfirmationsController.showAlertWarnning(e.getMessage());
        }
    }
}

