package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.AbonnementDAO;
import files.agencerecrutement.DAO.EntrepriseDAO;
import files.agencerecrutement.DAO.OffreDAO;
import files.agencerecrutement.DAO.RecrutementDAO;
import files.agencerecrutement.Model.Client;
import files.agencerecrutement.Model.Entreprise;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class DetailsEntrepriseController {
    @FXML
    private TextField RaisonSocialText ;
    @FXML
    private TextField AdresseTxt ;
    @FXML
    private TextField PhoneTxt ;
    @FXML
    private TextField ActiviteTxt ;
    @FXML
    private  TextField NbAbonnement;
    @FXML
    private  TextField NbOffre;
    @FXML
    private  TextField NbRecruteement;
    @FXML
    private  TextField PasswordTxt;
    @FXML
    private TextField UsernameTxt;

    @FXML

    public void initData(int  idEst) {
        try{
            Entreprise entreprise = EntrepriseDAO.SelectEntreprise(idEst);
            if(entreprise != null){
                RaisonSocialText.setText(entreprise.getRaisonSocial());
                AdresseTxt.setText(entreprise.getAdresse());
                PhoneTxt.setText(entreprise.getPhone());
                ActiviteTxt.setText(entreprise.getActivites());
                UsernameTxt.setText(entreprise.getUserName());
                PasswordTxt.setText(entreprise.getPassword());
            }
            NbOffre.setText(""+OffreDAO.NombreOffreEntreprise(idEst));
            NbAbonnement.setText(AbonnementDAO.NombreAbonnementEntreprise(idEst)+"");
            NbRecruteement.setText(RecrutementDAO.NombrerecrutementEntreprise(idEst)+"");
        }catch (Exception ex){
            showAlertWarnning("Probleme :" +ex);
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
