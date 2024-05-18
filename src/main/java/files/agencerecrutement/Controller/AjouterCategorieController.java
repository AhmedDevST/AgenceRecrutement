package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.CategorieDAO;
import files.agencerecrutement.Model.Categorie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AjouterCategorieController {
    @FXML
    private TextField LibelleTxt ;

    // event sur button ajouter : ajouter categorie

    public void AjouterCategorieEvent(ActionEvent event) {
        try{
            if(CheckInput()){ // si tous les donnes sont remplir
                //afficher un dialog de confirmation
                if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Ajouter Ctegorie","Vous voullez sur Ajouter cette Categorie ?")){

                    //ajouter au base de donnne
                    Categorie categorie = new Categorie(0,LibelleTxt.getText());
                    CategorieDAO.AjouterCategorie(categorie);

                    //alert
                    AlertsConfirmationsController.showAlertInfo("Categorie est bien Ajoute");

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

    // event sur button annuler
    @FXML
    private  void AnnulerCategorieEvent(ActionEvent event){
        try{
            //fermer le fenetre ou initialisez les donnees dans les inputs
            if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Ajouter Categorie","Vous voullez sur annuler cette opeartion ?")){
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
