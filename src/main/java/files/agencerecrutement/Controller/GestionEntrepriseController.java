package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.EntrepriseDAO;
import files.agencerecrutement.Model.Entreprise;
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
    private TableColumn<Entreprise,String> ActivitesEst;
    @FXML
    private  TableColumn<Entreprise,Void> ActionsCol;
    @FXML
    private TextField SearchText;

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
        CodeInterne.setCellValueFactory(new PropertyValueFactory<>("IdClient"));
        RaisonSocialEst.setCellValueFactory(new PropertyValueFactory<>("raisonSocial"));
        AdresseEst.setCellValueFactory(new PropertyValueFactory<>("Adresse"));
        phoneEst.setCellValueFactory(new PropertyValueFactory<>("phone"));
        ActivitesEst.setCellValueFactory(new PropertyValueFactory<>("Activites"));

        //add cell of button edit
        Callback<TableColumn<Entreprise,Void> , TableCell<Entreprise,Void>> cellFactory=
                (TableColumn<Entreprise, Void> param) ->{
                final  TableCell<Entreprise,Void> cell = new  TableCell<Entreprise,Void>(){
                    @Override
                    public void updateItem(Void item, boolean empty){
                        super.updateItem(item,empty);
                        if(empty){
                            setGraphic(null);
                            setText(null);
                        }else{

                            //icon modifier entreprise
                            final Image imageEdit = new Image(Objects.requireNonNull(getClass().getResource("/files/agencerecrutement/Images/editIcon.png")).toString());
                            final ImageView editEstViewImg = new ImageView(imageEdit);
                            //style
                            editEstViewImg.setFitWidth(30);
                            editEstViewImg.setFitHeight(30);
                            editEstViewImg.setCursor(Cursor.HAND);
                            Tooltip tooltipEdit = new Tooltip("Modifier l entreprise");
                            Tooltip.install(editEstViewImg,tooltipEdit);

                             //event sur icon modifier
                            editEstViewImg.setOnMouseClicked(actionEvent -> {
                                //get entreprise selectionner dans tableview
                                Entreprise entreprise = getTableView().getItems().get(getIndex());
                                //ouvrir fenetre de modifier entreprise
                                OuvrirModifierEntreprise(entreprise);
                            } );

                            // icon afficher les offres de est
                            final Image imageOffres = new Image(Objects.requireNonNull(getClass().getResource("/files/agencerecrutement/Images/afficherOffres.png")).toString());
                            final ImageView OffresViewImg = new ImageView(imageOffres);
                            //style
                            OffresViewImg.setFitWidth(30);
                            OffresViewImg.setFitHeight(30);
                            OffresViewImg.setCursor(Cursor.HAND);
                            Tooltip tooltipOffres = new Tooltip("Offres d ' entrprise");
                            Tooltip.install(OffresViewImg,tooltipOffres);

                            //event
                            OffresViewImg.setOnMouseClicked(actionEvent -> {
                                Entreprise entreprise = getTableView().getItems().get(getIndex());
                                showAlertInfo("les offres de entrprise :"+entreprise.getRaisonSocial());
                            } );

                            //icon afficher les abonnement de est
                            final Image imageAbo = new Image(Objects.requireNonNull(getClass().getResource("/files/agencerecrutement/Images/afficherAbo.png")).toString());
                            final ImageView AboEstViewImg = new ImageView(imageAbo);
                            //style
                            AboEstViewImg.setFitWidth(30);
                            AboEstViewImg.setFitHeight(30);
                            AboEstViewImg.setCursor(Cursor.HAND);
                            Tooltip tooltipAbo = new Tooltip("Abonnement d ' entrprise");
                            Tooltip.install(AboEstViewImg,tooltipAbo);

                             //event
                            AboEstViewImg.setOnMouseClicked(actionEvent -> {
                                Entreprise entreprise = getTableView().getItems().get(getIndex());
                                showAlertInfo("les abonnement de entreprise :"+entreprise.getRaisonSocial());
                            } );

                            //met les trois icon dans un Hbox
                            HBox ContentAllbtn = new HBox(editEstViewImg,OffresViewImg,AboEstViewImg);
                            ContentAllbtn.setStyle("-fx-alignment:center");
                            HBox.setMargin(editEstViewImg, new Insets(2, 7, 7, 3));
                            HBox.setMargin(OffresViewImg, new Insets(2, 7, 7, 2));
                            HBox.setMargin(AboEstViewImg, new Insets(2, 7, 7, 2));

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

    //methode permet de ouvrir le fenetre de modification d un entreprise passer comme argument
    private  void OuvrirModifierEntreprise(Entreprise entreprise){
        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/ModifierEntreprise.fxml"));
            Parent parent = fxmlLoader.load();

            //cree instance de controller ModifierEntrepriseController
            ModifierEntrepriseController modifierEntrepriseController = fxmlLoader.getController();
            //passer au controller  l objet entreprise
            modifierEntrepriseController.initData(entreprise);

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
