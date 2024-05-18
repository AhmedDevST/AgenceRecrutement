package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.OffreDAO;
import files.agencerecrutement.DAO.RecrutementDAO;
import files.agencerecrutement.Model.Offre;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DetailsOffreController {

    @FXML
    private TextField titre;
    @FXML
    private  TextField nbPosteRecruter;
    @FXML
    private TextField competences, RaisonSocial;
    @FXML
    private TextField nbPoste, anneeEx , EtatOffre;

    public  void initData(int  idOffre){
        try{
            Offre selectOffre = OffreDAO.SelectOffre(idOffre);
            titre.setText(selectOffre.getTitre());
            competences.setText(selectOffre.getCompetences());
            RaisonSocial.setText(selectOffre.getEntreprise().getRaisonSocial());
            nbPoste.setText(selectOffre.getNbPostOfferts()+"");
            anneeEx.setText(selectOffre.getNbAnneeEx()+"");
            EtatOffre.setText(selectOffre.ReturnEtatString());
            nbPosteRecruter.setText(RecrutementDAO.nombrerecrutementOffre(idOffre)+"");
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("Probleme :" +ex);
        }
    }
}
