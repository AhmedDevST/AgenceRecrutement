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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ModifierJournalController {

    @FXML
    private ComboBox ComboboxCategorie;
    @FXML
    private TextField LangueTxt ;
    @FXML
    private TextField NomJrText ;
    @FXML
    private ComboBox ComboboxPeriodicite ;
    private Journal journal;
    private int indexCategorieSelectionnee = 0; // Champ pour stocker l'index de la catégorie sélectionnée


    //permet de initialiser objet journal  et initiliser les input
    public  void initData(Journal journal){
        try{

            this.journal = journal;

           // initiliser les input
            NomJrText.setText(journal.getNomJr());
            LangueTxt.setText(journal.getLangue());

            //load Combobox Categorie
            ComboboxCategorie.setItems(CategorieDAO.afficherCategories());


            // Sauvegarde de l'index de la catégorie sélectionnée
            for (int i = 0; i < ComboboxCategorie.getItems().size(); i++) {
                // Récupérer l'élément à l'index i
                Categorie categorie =(Categorie) ComboboxCategorie.getItems().get(i);

                // Vérifier la condition pour l'élément
                if (categorie.getIdcate() == journal.getCategorie().getIdcate()) {
                    indexCategorieSelectionnee = i;
                    break;
                }
            }
           //selectionner categorie
            ComboboxCategorie.getSelectionModel().select(indexCategorieSelectionnee);

            //load Combobox periodicte
            ObservableList<String> PeriodiciteList = FXCollections.observableArrayList(
                    "Quotidienne",
                    "Mensuelle",
                    "Hubdomadaire ",
                    "Saisonaire"
            );
            ComboboxPeriodicite.setItems(PeriodiciteList);
            //selectionner Periodicite
            ComboboxPeriodicite.getSelectionModel().select(journal.getPeriodicite());

        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme :" +ex);
        }
    }
    // event sur button rest
    @FXML
    private  void restJournalEvent(){
        try{
            //fermer le fenetre ou initialisez les donnees dans les inputs
            if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Modifier Journal","Vous voullez sur de Réinitialiser  les donnees?")){
                //initialisez les donnees dans les inputs
                NomJrText.setText(journal.getNomJr());
                LangueTxt.setText(journal.getLangue());

                //selectionner categorie
                ComboboxCategorie.getSelectionModel().select(indexCategorieSelectionnee);
                //selectionner Periodicite
                ComboboxPeriodicite.getSelectionModel().select(journal.getPeriodicite());

            }
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme : " +ex);
        }
    }
    // event sur button annuler
    @FXML
    public void AnnulerJournalvent(ActionEvent event) {
        try{
            //fermer le fenetre ou initialisez les donnees dans les inputs
            if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Modifier Journal","Vous voullez sur annuler cette opeartion ?")){
                //fermer la fenetre
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        }catch (Exception ex){
           AlertsConfirmationsController.showAlertWarnning("Probleme : " +ex);
        }
    }
    // event sur button modifier : modifier journal
    @FXML
    public void ModifierJournalEvent(ActionEvent event) {
        try{
            if(CheckInput()){ // si tous les donnes sont remplir
                //afficher un dialog de confirmation
                if(AlertsConfirmationsController.showConfirmationDialog("Confirmation","Modifier Journal","Vous voullez sur Modifier ce Journal ?")){

                    //categorie selectionner
                    Categorie categorie= (Categorie) ComboboxCategorie.getValue();
                    //periodicite selectionner
                    String periodicte = (String) ComboboxPeriodicite.getValue();
                    //modifier au base de donnne
                    Journal journal = new Journal(this.journal.getIdJr(),NomJrText.getText(),periodicte,LangueTxt.getText(),categorie);
                    JournalDAO.ModifierJournal(journal);

                    //alert
                    AlertsConfirmationsController.showAlertInfo("Journal est bien Ajoute");

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
    // tester si tous les donnees sont remplir
    private boolean CheckInput(){
        return ( !NomJrText.getText().isEmpty() && !LangueTxt.getText().isEmpty() );
    }

}
