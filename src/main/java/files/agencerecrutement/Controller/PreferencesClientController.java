package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.DemandeurDAO;
import files.agencerecrutement.DAO.EntrepriseDAO;
import files.agencerecrutement.DAO.PreferencesClientDAO;
import files.agencerecrutement.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.Objects;
import java.util.Optional;

public class PreferencesClientController {

    //list contient les prefernces de client
    private ObservableList<Categorie> VosPreferences = FXCollections.observableArrayList();
    //list contient les categorie ne sont pas prefere par   client
    private ObservableList<Categorie> NouveauPreferences = FXCollections.observableArrayList();
    //list  va contient les categorie selectionner  par client
    private ObservableList<Categorie> PreferencesSelectionnerClients = FXCollections.observableArrayList();

    private  int idCli ;
    private int TypeClient ;
    // si entreprise = 1;
    // si demandeur = 2;

    @FXML
    private TableView<Categorie> DataVosPreferences ;
    @FXML
    private TableColumn<Categorie,String> LibelleCatVos;
    @FXML
    private  TableColumn<Categorie,Void> ActionkDelete;

    @FXML
    private TableView<Categorie> DataNouveauPreferences ;
    @FXML
    private TableColumn<Categorie,String> LibelleCatNouveau;
    @FXML
    private  TableColumn<Categorie,Void> ActionsCheckAdd;
    @FXML
    private  Label TitreTxt;


    public void initData(int idCli, int TypeClient) {
        try{
            this.TypeClient = TypeClient;
            this.idCli = idCli;
            LoadData();
        }catch (Exception ex){
            showAlertWarnning("Probleme :" +ex);
        }
    }

    // load les list et charger donne au tables au cas de demandeur ou entreprise
    private  void LoadData(){
        try{
            if(TypeClient == 1){
                //si le client est un entreprise
                VosPreferences = PreferencesClientDAO.AfficherPreferencesEntreprise(idCli);
                //return les rest des categorie not PreferencesE
                NouveauPreferences = PreferencesClientDAO.AfficherNotPreferencesEntreprise(idCli);
                //titre
                TitreTxt.setText(" Preferences de Entreprise : "+ EntrepriseDAO.getRaisonSocial(idCli));
            }else{

                // si le client est demandeur
                VosPreferences = PreferencesClientDAO.AfficherPreferencesDemandeur(idCli);
                //return les rest des categorie not PreferencesE
                NouveauPreferences = PreferencesClientDAO.AfficherNotPreferencesDemandeur(idCli);
                //titre
                TitreTxt.setText(" Preferences de demandeur : "+ DemandeurDAO.getFullName(idCli));
            }
            //load les tables
            loadTableViewVosPreferences();
            loadTableViewNouveauPreferences();
        }catch (Exception ex){
            showAlertWarnning("Probleme :" +ex);
        }
    }

    //charger les donne au table de les preferences
    private void loadTableViewVosPreferences(){

        //utiliser  dans PropertyValueFactory  la meme nom de attribut au objet entreprise
        LibelleCatVos.setCellValueFactory(new PropertyValueFactory<>("libelle"));

        //add cell of button edit
        Callback<TableColumn<Categorie,Void> , TableCell<Categorie,Void>> cellFactory=
                (TableColumn<Categorie, Void> param) ->{
                    final  TableCell<Categorie,Void> cell = new  TableCell<Categorie,Void>(){
                        @Override
                        public void updateItem(Void item, boolean empty){
                            super.updateItem(item,empty);
                            if(empty){
                                setGraphic(null);
                                setText(null);
                            }else{
                                //icon supprimer
                                final Image imageDelete = new Image(Objects.requireNonNull(getClass().getResource("/files/agencerecrutement/Images/SupprimerIcon.png")).toString());
                                final ImageView DeleteViewImg = new ImageView(imageDelete);
                                //style
                                DeleteViewImg.setFitWidth(30);
                                DeleteViewImg.setFitHeight(30);
                                DeleteViewImg.setCursor(Cursor.HAND);
                                Tooltip tooltip = new Tooltip("Supprimer preferences");
                                Tooltip.install(DeleteViewImg,tooltip);

                                //event sur icon delete
                                DeleteViewImg.setOnMouseClicked(actionEvent -> {
                                    //get categorie selectionner dans tableview
                                    Categorie categorie = getTableView().getItems().get(getIndex());
                                    SupprimerPreferences(categorie);
                                } );

                                //met icon dans un Hbox
                                HBox Content = new HBox(DeleteViewImg);
                                Content.setStyle("-fx-alignment:center");
                                setGraphic(Content);
                                setText(null);
                            }
                        }
                        //fin methode updateItem
                    };
                    //fin cell
                    return cell;
                };
        ActionkDelete.setCellFactory(cellFactory);

        DataVosPreferences.setItems(VosPreferences);
    }

    //charger les donne au table de les autre categoirie ne sont pas prefere
    private void loadTableViewNouveauPreferences(){

        //utiliser  dans PropertyValueFactory  la meme nom de attribut au objet entreprise
        LibelleCatNouveau.setCellValueFactory(new PropertyValueFactory<>("libelle"));

        //add cell of button edit
        Callback<TableColumn<Categorie,Void> , TableCell<Categorie,Void>> cellFactory=
                (TableColumn<Categorie, Void> param) ->{
                    final  TableCell<Categorie,Void> cell = new  TableCell<Categorie,Void>(){
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
                                    //get categorie selectionner dans tableview
                                    Categorie categorie = getTableView().getItems().get(getIndex());
                                    TraitercheckBoxAddPreferences(checkBox.isSelected(),categorie);
                                });

                                //met icon dans un Hbox
                                HBox Content = new HBox(checkBox);
                                Content.setStyle("-fx-alignment:center");
                                setGraphic(Content);
                                setText(null);
                            }
                        }
                        //fin methode updateItem
                    };
                    //fin cell
                    return cell;
                };
        ActionsCheckAdd.setCellFactory(cellFactory);

        DataNouveauPreferences.setItems(NouveauPreferences);
    }

    //  ajouter le categorie selectionner au list
    private  void TraitercheckBoxAddPreferences(boolean check,Categorie categorie){
        //si categorie selectionner : ajouter au list
        if(check){
            PreferencesSelectionnerClients.add(categorie);
        }else{ //sinon
            PreferencesSelectionnerClients.remove(categorie);
        }
    }



    //ajouter Preferences selectionnner au  base de donnee
   @FXML
   private void AjouterPreferences(){
        try{
            if(!PreferencesSelectionnerClients.isEmpty()){
                if(showConfirmationDialog("Confirmation","Ajouter Preferences","Vous voullez sur Ajouter ces Preferences ?")){

                    //ajouter au base de donnee
                    for(Categorie categorie : PreferencesSelectionnerClients){
                        if(TypeClient == 1){
                            //si est un entreprise
                            PreferencesClientDAO.AjouterPreferenceEntreprise(idCli,categorie.getIdcate());
                        }else {
                            //si est un demandeur
                            PreferencesClientDAO.AjouterPreferenceDemandeur(idCli,categorie.getIdcate());
                        }
                    }
                    showAlertInfo("operation est termine ");
                    PreferencesSelectionnerClients.clear();
                    LoadData();
                }
            }else showAlertWarnning("selectionner au moins un categorie! ");

        }catch (Exception ex){
            showAlertWarnning("Probleme :" +ex);
        }
   }

   //supprimer un prefernces
   private void SupprimerPreferences(Categorie categorie){
        try{
            if(showConfirmationDialog("Confirmation","Supprimer Preferences","Vous voullez sur supprimer ce Preference ?")){
                //supprimer au bd
                if(TypeClient == 1){
                    //si est un entreprise
                    PreferencesClientDAO.SupprimerPreferenceEntreprise(idCli,categorie.getIdcate());
                }else {
                    //si est un demandeur
                    PreferencesClientDAO.SupprimerPreferenceDemandeur(idCli,categorie.getIdcate());
                }

                showAlertInfo("operation est termine ");
                PreferencesSelectionnerClients.clear();
                LoadData();
            }
        }catch (Exception ex){
            showAlertWarnning("Probleme :" +ex);
        }
   }

   //supprimer tous les preferences
   @FXML
   private  void SupprimerTousPreferences(){
        try{
            if(showConfirmationDialog("Confirmation","Supprimer tout Preferences","Vous voullez sur supprimer tout les  Preference ?")){
                //supprimer au bd
                if(TypeClient == 1){
                    //si est un entreprise
                    PreferencesClientDAO.SupprimerTousPreferenceEntreprise(idCli);
                }else {
                    //si est un demandeur
                    PreferencesClientDAO.SupprimerTousPreferenceDemandeur(idCli);
                }
                showAlertInfo("operation est termine ");
                PreferencesSelectionnerClients.clear();
                LoadData();
            }

        }catch (Exception ex){
            showAlertWarnning("Probleme :" +ex);
        }
   }

    @FXML
    private void AnnulerPreferences(){
        try{
            if(!PreferencesSelectionnerClients.isEmpty()){
                if(showConfirmationDialog("Confirmation","Ajouter Preferences","Vous voullez sur annuler cette opeartion  ?")){
                    PreferencesSelectionnerClients.clear();
                    loadTableViewNouveauPreferences();
                }
            }
        }catch (Exception ex){
            showAlertWarnning("Probleme :" +ex);
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
