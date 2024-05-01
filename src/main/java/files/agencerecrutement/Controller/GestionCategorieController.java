package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.CategorieDAO;
import files.agencerecrutement.Model.Categorie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class GestionCategorieController {

    private ObservableList<Categorie> categories = FXCollections.observableArrayList();
    @FXML
    private TableView<Categorie> DataCategories ;
    @FXML
    private TableColumn<Categorie,Integer> NumeroCat;
    @FXML
    private TableColumn<Categorie,String> LibelleCat;
    @FXML
    private  TableColumn<Categorie,Void> ActionsCol;
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
            //get la list des categorie  a partir de base de donnne
            categories = CategorieDAO.afficherCategories();
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    private void loadTableView(){

        loadData();
        //utiliser  dans PropertyValueFactory  la meme nom de attribut au objet entreprise
        NumeroCat.setCellValueFactory(new PropertyValueFactory<>("idcate"));
        LibelleCat.setCellValueFactory(new PropertyValueFactory<>("libelle"));

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

                                //icon modifier Categorie
                                final Image imageEdit = new Image(Objects.requireNonNull(getClass().getResource("/files/agencerecrutement/Images/editIcon.png")).toString());
                                final ImageView editEstViewImg = new ImageView(imageEdit);
                                //style
                                editEstViewImg.setFitWidth(30);
                                editEstViewImg.setFitHeight(30);
                                editEstViewImg.setCursor(Cursor.HAND);
                                Tooltip tooltipEdit = new Tooltip("Modifier  categorie");
                                Tooltip.install(editEstViewImg,tooltipEdit);

                                //event sur icon modifier
                                editEstViewImg.setOnMouseClicked(actionEvent -> {
                                    //get categorie selectionner dans tableview
                                    Categorie categorie = getTableView().getItems().get(getIndex());
                                    //ouvrir fenetre de modifier categorie
                                    OuvrirModifierCategorie(categorie);
                                } );

                                //met icon dans un Hbox
                                HBox Content = new HBox(editEstViewImg);
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
        ActionsCol.setCellFactory(cellFactory);

        DataCategories.setItems(categories);
    }

    //event  sur textField de recherche:touche entrer
    @FXML
    public void searchCategorie() {
        try{
            if(!SearchText.getText().isEmpty()){
                //cree une autre list =>va contient result de rechreche
                ObservableList<Categorie> filteredList = FXCollections.observableArrayList();
                for(Categorie  categorie : categories){
                    //si le libelle d un categorue   similaire de mot cle on la ajouter
                    if( categorie.getLibelle().toLowerCase().contains(SearchText.getText().toLowerCase()))
                        filteredList.add(categorie);
                }
                DataCategories.setItems(filteredList);
            }else{
                //si input de recherche vide on refrech la tableView
                loadTableView();
            }
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }

    //event de button Nouvelle categorie : permet de ouvrir la fenetre d ajouter une categorie
    @FXML
    public void AjouterCategorieEvent() {
        try{
            Parent parent = FXMLLoader.load(getClass().getResource("/files/agencerecrutement/Views/AjouterCategorie.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Nouvelle Categorie ");
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            // Wait for the new window to be closed
            stage.setOnHiding(event -> {
                // Reload TableView data after closing the "Ajouter categorie" window
                loadTableView();
            });

            stage.showAndWait(); // Use showAndWait() to wait for the window to close before continuing

        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }


    //methode permet de ouvrir le fenetre de modification d un categorie passer comme argument
    private  void OuvrirModifierCategorie(Categorie categorie){
        try{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/files/agencerecrutement/Views/ModifierCategorie.fxml"));
            Parent parent = fxmlLoader.load();

            //cree instance de controller ModifierCategorieController
            ModifierCategorieController modifierCategorieController = fxmlLoader.getController();
            //passer au controller  l objet categorie
            modifierCategorieController.initData(categorie);

            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Modifier Categorie ");
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            // Wait for the new window to be closed
            stage.setOnHiding(event -> {
                // Reload TableView data after closing the "modifier categorie" window
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
