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

public class ModifierCategorieController {

    @FXML
    private TextField LibelleTxt ;
    private  Categorie categorie;

    public void initData(Categorie categorie) {
        try{
            this.categorie = categorie;
            LibelleTxt.setText(categorie.getLibelle());
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme :" +ex);
        }
    }
    // event sur button Modifier : Modifier categorie

    public void ModifierCategorieEvent(ActionEvent event) {
        try{
            if(CheckInput()){ // si tous les donnes sont remplir
                //afficher un dialog de confirmation
                if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Modifier Ctegorie","Vous voullez sur Modifier cette Categorie ?")){

                    //modifier au base de donnne
                    Categorie categorie = new Categorie(this.categorie.getIdcate(),LibelleTxt.getText());
                    CategorieDAO.ModifierCategorie(categorie);;

                    //alert
                    AlertsConfirmationsController.showAlertInfo("Categorie est bien Modifier");

                    //fermer la fenetre
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                }
            }else{
                AlertsConfirmationsController.showAlertWarnning("vous voullez remplir tous les donnees ");
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme lors insertion !!" +ex);
        }
    }
    // event sur button rest
    @FXML
    private  void restCategorieEvent(){
        try{
            //fermer le fenetre ou initialisez les donnees dans les inputs
            if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Modifier Categorie","Vous voullez sur de Réinitialiser  les donnees?")){
                //initialisez les donnees dans les inputs
                LibelleTxt.setText(categorie.getLibelle());
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme : " +ex);
        }
    }

    // event sur button annuler
    @FXML
    private  void AnnulerCategorieEvent(ActionEvent event){
        try{
            //fermer le fenetre ou initialisez les donnees dans les inputs
            if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Modifier Categorie","Vous voullez sur annuler cette opeartion ?")){
                //fermer la fenetre
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme : " +ex);
        }
    }

    // tester si tous les donnees sont remplir
    private boolean CheckInput(){
        return !LibelleTxt.getText().isEmpty() ;
    }
}
