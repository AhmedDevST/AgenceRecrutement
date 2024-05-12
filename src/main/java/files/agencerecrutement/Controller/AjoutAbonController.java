package files.agencerecrutement.Controller;
import files.agencerecrutement.DAO.*;
import files.agencerecrutement.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
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

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class AjoutAbonController {
    @FXML
    private ComboBox ComboboxEntreprise;
    @FXML
    private ComboBox ComboboxJournal;
    @FXML
    private DatePicker DateExp;

    @FXML
    public  void initialize(){
        try{
            ComboboxEntreprise.setItems(EntrepriseDAO.afficherEntreprises());
            ComboboxEntreprise.getSelectionModel().selectFirst();
            ComboboxJournal.setItems(JournalDAO.afficherJournaux());
            ComboboxJournal.getSelectionModel().selectFirst();
            DateExp.setValue(LocalDate.now());
        }catch (Exception ex){
            showAlertWarnning(ex.getMessage());
        }
    }
    public void AjouterAbonnement(ActionEvent event) {
        try{
            //tester si les valeurs sont different de nulle
            if(ComboboxEntreprise.getValue()!=null &&ComboboxJournal.getValue()!=null &&DateExp.getValue()!=null){
                //Le journal selectionné
                    Journal j = (Journal) ComboboxJournal.getValue();
                //L'entreprise selectionné
                    Entreprise e = (Entreprise) ComboboxEntreprise.getValue();
                //La date selectionné
                    Date d = Date.valueOf(DateExp.getValue());
                //Vérification si l'entreprise n'a pas des abonnements activé dans ce journal  :
                    if(AbonnementDAO.isCompanyHaveAboInJr(e.getIdClient(), j.getIdJr())){
                        AbonnementDAO.AjouterAbonnement(j,e,1,d);
                        showAlertInfo("L'ajout a été effectué ");
                        //fermer la fenetre
                        Node source = (Node) event.getSource();
                        Stage stage = (Stage) source.getScene().getWindow();
                        stage.close();
                }else{
                        showAlertWarnning("Vous avez déjà un abonnement actif dans ce journal ");
                    }

                //Ajouter Abonnement:
                //AbonnementDAO.AjouterAbonnement(j,e,etat,d);



            }else{
                showAlertWarnning("S'il vous plait, Ajouter vos informations ");

            }
        }
        catch(Exception e){
            showAlertWarnning(e.getMessage());
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
    @FXML
    private  void AnnulerAbonnementEvent(ActionEvent event){
        try{
            //fermer le fenetre ou initialisez les donnees dans les inputs
            if(showConfirmationDialog("Confirmation","Ajouter Abonnement","Vous êtes sur que vous voullez annuler cette opeartion ?")){
                //fermer la fenetre
               Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();

            }
        }catch (Exception ex){
            showAlertWarnning("Probleme : " +ex);
        }
    }

}
