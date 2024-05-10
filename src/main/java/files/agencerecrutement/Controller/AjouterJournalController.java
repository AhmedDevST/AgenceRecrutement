package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.CategorieDAO;
import files.agencerecrutement.DAO.JournalDAO;
import files.agencerecrutement.Model.Categorie;
import files.agencerecrutement.Model.Journal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class AjouterJournalController {

    @FXML
    private ComboBox ComboboxCategorie;
    @FXML
    private TextField LangueTxt ;
    @FXML
    private TextField NomJrText ;
    @FXML
    private ComboBox ComboboxPeriodicite ;
    @FXML
    public  void initialize(){
        try{
            ComboboxCategorie.setItems(CategorieDAO.afficherCategories());
            ComboboxCategorie.getSelectionModel().select(0);
            LoadComboboxPeriodicite();
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    private  void LoadComboboxPeriodicite(){
        ObservableList<String> PeriodiciteList = FXCollections.observableArrayList(
                "Quotidienne",
                "Mensuelle",
                "Hubdomadaire ",
                "Saisonaire"
        );
        ComboboxPeriodicite.setItems(PeriodiciteList);
        ComboboxPeriodicite.getSelectionModel().select(0);
    }

    // event sur button annuler
    @FXML
    public void AnnulerJournalvent(ActionEvent event) {
        try{
            //fermer le fenetre ou initialisez les donnees dans les inputs
            if(showConfirmationDialog("Confirmation","Ajouter Journal","Vous voullez sur annuler cette opeartion ?")){
                //fermer la fenetre
              /*  Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();*/
                //initialisez les donnees dans les inputs
                NomJrText.clear();
                LangueTxt.clear();
            }
        }catch (Exception ex){
            showAlertWarnning("Probleme : " +ex);
        }
    }
    // event sur button ajouter : ajouter journal
    @FXML
    public void AjouterJournalEvent(ActionEvent event) {
        try{
            if(CheckInput()){ // si tous les donnes sont remplir
                //afficher un dialog de confirmation
                if(showConfirmationDialog("Confirmation","Ajouter Journal","Vous voullez sur Ajouter ce Journal ?")){

                    //categorie selectionner
                     Categorie categorie= (Categorie) ComboboxCategorie.getValue();
                    //periodicite selectionner
                    String periodicte = (String) ComboboxPeriodicite.getValue();
                    //ajouter au base de donnne
                    Journal journal = new Journal(0,NomJrText.getText(),periodicte,LangueTxt.getText(),categorie);
                    JournalDAO.AjouterJournal(journal);

                    //alert
                    showAlertInfo("Journal est bien Ajoute");

                    //fermer la fenetre
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                }
            }else{
                showAlertWarnning("vous voullez remplir tous les donnees ");
            }
        }catch (Exception ex){
            showAlertWarnning("Probleme lors insertion !!" +ex);
        }
    }

    // tester si tous les donnees sont remplir
    private boolean CheckInput(){

        return ( !NomJrText.getText().isEmpty() && !LangueTxt.getText().isEmpty() && (ComboboxCategorie.getValue()!=null) );
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
