package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.EntrepriseDAO;
import files.agencerecrutement.Model.Entreprise;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;


public class GestionEntrepriseController {

    private  ObservableList<Entreprise> entreprises;
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

    public GestionEntrepriseController(){
        try{
            entreprises = EntrepriseDAO.afficherEntreprises();
        }catch (Exception ex){
            showAlert(ex.getMessage());
        }
    }
    @FXML
    public  void initialize(){
        try{
            loadData();
        }catch (Exception ex){
            showAlert(ex.getMessage());
        }
    }
    private void loadData(){

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
                            final Image imageEdit = new Image(getClass().getResource("/files/agencerecrutement/Images/editIcon.png").toString());
                            final ImageView editEstViewImg = new ImageView(imageEdit);
                            //style
                            editEstViewImg.setFitWidth(30);
                            editEstViewImg.setFitHeight(30);
                            editEstViewImg.setCursor(Cursor.HAND);
                            Tooltip tooltipEdit = new Tooltip("Modifier l entreprise");
                            Tooltip.install(editEstViewImg,tooltipEdit);

                             //event
                            editEstViewImg.setOnMouseClicked(actionEvent -> {
                                Entreprise entreprise = getTableView().getItems().get(getIndex());
                                showAlert( "Modifier entreprise : "+entreprise.getRaisonSocial());
                            } );

                            // icon afficher les offres de est
                            final Image imageOffres = new Image(getClass().getResource("/files/agencerecrutement/Images/afficherOffres.png").toString());
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
                                showAlert("les offres de entrprise :"+entreprise.getRaisonSocial());
                            } );

                            //icon afficher les abonnement de est
                            final Image imageAbo = new Image(getClass().getResource("/files/agencerecrutement/Images/afficherAbo.png").toString());
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
                                showAlert("les abonnement de entreprise :"+entreprise.getRaisonSocial());
                            } );

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



    //event text change sur textField de recherche
   @FXML
    private void searchEst(){
        try{
            if(!SearchText.getText().isEmpty()){
                ObservableList<Entreprise> filteredList = FXCollections.observableArrayList();
                for(Entreprise  entreprise : entreprises){
                    if( entreprise.getRaisonSocial().toLowerCase().contains(SearchText.getText().toLowerCase()) ||
                            entreprise.getAdresse().toLowerCase().contains(SearchText.getText().toLowerCase())||
                        entreprise.getPhone().toLowerCase().contains(SearchText.getText().toLowerCase())
                    )
                        filteredList.add(entreprise);
                }
                DataEntreprises.setItems(filteredList);
            }else{
                loadData();
            }
        }catch (Exception ex){
            showAlert(ex.getMessage());
        }
    }

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("title");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
