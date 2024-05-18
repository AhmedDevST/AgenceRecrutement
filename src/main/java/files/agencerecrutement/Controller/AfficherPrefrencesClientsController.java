package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.DemandeurDAO;
import files.agencerecrutement.DAO.EntrepriseDAO;
import files.agencerecrutement.DAO.PreferencesClientDAO;
import files.agencerecrutement.Model.Categorie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AfficherPrefrencesClientsController {


    //list contient les prefernces de client
    private ObservableList<Categorie> Preferences = FXCollections.observableArrayList();
    @FXML
    private TableView<Categorie> DataPreferences ;
    @FXML
    private TableColumn<Categorie,String> LibelleCat;
    @FXML
    private Label TitreTxt;
    private  int idCli ;
    private int TypeClient ;
    // si entreprise = 1;
    // si demandeur = 2;
    @FXML
    public void initData(int idCli, int TypeClient) {
        try{
            this.TypeClient = TypeClient;
            this.idCli = idCli;
            LoadData();
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme :" +ex);
        }
    }
    private  void LoadData(){
        try{
            if(TypeClient == 1){
                //si le client est un entreprise
                Preferences = PreferencesClientDAO.AfficherPreferencesEntreprise(idCli);
                //titre
                TitreTxt.setText(" Preferences de Entreprise : "+ EntrepriseDAO.getRaisonSocial(idCli));
            }else{
                // si le client est demandeur
                Preferences = PreferencesClientDAO.AfficherPreferencesDemandeur(idCli);
                //titre
                TitreTxt.setText(" Preferences de demandeur : "+ DemandeurDAO.getFullName(idCli));
            }
            //load le table
            loadTableViewPreferences();
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme :" +ex);
        }
    }

    //charger les donne au table de les preferences
    private void loadTableViewPreferences(){
        LibelleCat.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        DataPreferences.setItems(Preferences);
    }
}
