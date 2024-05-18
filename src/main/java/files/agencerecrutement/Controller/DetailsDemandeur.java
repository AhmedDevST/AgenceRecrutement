package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.DemandeurDAO;
import files.agencerecrutement.DAO.PostulationDAO;
import files.agencerecrutement.DAO.RecrutementDAO;
import files.agencerecrutement.Model.Demandeur;
import files.agencerecrutement.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class DetailsDemandeur {
    @FXML
    private TextField Nom, Adresse;
    @FXML
    private TextField Prenom, Telephone,Fax, Salaire, Diplome;
    @FXML
    private TextField  NbExper;
    @FXML
    private  TextField PasswordTxt;
    @FXML
    private TextField UsernameTxt;
    @FXML
    private  TextField nbRecrutementTxt;
    @FXML
    private TextField nbPostulationTxt;
    @FXML
    private VBox VboxRecrutement,VboxNbPostulation,VboxPassword,VboxUsername;

    public  void initData(int idDem, User user ){
        try{
            //si est un entreprise
            if(user.getRoleUser() == 2){
                VboxRecrutement.setVisible(false);
                VboxNbPostulation.setVisible(false);
                VboxPassword.setVisible(false);
                VboxUsername.setVisible(false);
            }
            Demandeur SelectDemandeur  = DemandeurDAO.SelectDemandeur(idDem);
            Nom.setText(SelectDemandeur.getNom());
            Prenom.setText(SelectDemandeur.getPrenom());
            Adresse.setText(SelectDemandeur.getAdresse());
            UsernameTxt.setText(SelectDemandeur.getUserName());
            PasswordTxt.setText(SelectDemandeur.getPassword());
            Diplome.setText(SelectDemandeur.getDiplome());
            Salaire.setText(String.valueOf(SelectDemandeur.getSalaire()));
            Telephone.setText(SelectDemandeur.getPhone());
            Fax.setText(SelectDemandeur.getFax());
            nbPostulationTxt.setText(PostulationDAO.nombrePostulationOfDemandeur(idDem)+"");
            nbRecrutementTxt.setText(RecrutementDAO.nombrerecrutementDemandeur(idDem)+"");
            NbExper.setText(SelectDemandeur.getNbAnneeEx()+"");
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme :" +ex);
        }
    }
}
