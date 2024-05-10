package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.CategorieDAO;
import files.agencerecrutement.Model.Categorie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class AjouterCategorieController {
    @FXML
    private TextField LibelleTxt ;

    // event sur button ajouter : ajouter categorie

    public void AjouterCategorieEvent(ActionEvent event) {
        try{
            if(CheckInput()){ // si tous les donnes sont remplir
                //afficher un dialog de confirmation
                if(showConfirmationDialog("Confirmation","Ajouter Ctegorie","Vous voullez sur Ajouter cette Categorie ?")){

                    //ajouter au base de donnne
                    Categorie categorie = new Categorie(0,LibelleTxt.getText());
                    CategorieDAO.AjouterCategorie(categorie);

                    //alert
                    showAlertInfo("Categorie est bien Ajoute");

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

    // event sur button annuler
    @FXML
    private  void AnnulerCategorieEvent(ActionEvent event){
        try{
            //fermer le fenetre ou initialisez les donnees dans les inputs
            if(showConfirmationDialog("Confirmation","Ajouter Categorie","Vous voullez sur annuler cette opeartion ?")){
                //fermer la fenetre
              /*  Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();*/
                //initialisez les donnees dans les inputs
                LibelleTxt.clear();
            }
        }catch (Exception ex){
            showAlertWarnning("Probleme : " +ex);
        }
    }
    // tester si tous les donnees sont remplir
    private boolean CheckInput(){
        return !LibelleTxt.getText().isEmpty() ;
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
