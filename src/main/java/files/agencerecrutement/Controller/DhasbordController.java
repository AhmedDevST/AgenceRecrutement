package files.agencerecrutement.Controller;

import files.agencerecrutement.DAO.DashbordDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

import java.io.IOException;
import java.sql.SQLException;

public class DhasbordController {
    @FXML
    private Label NombreTotalEstTxt;
    @FXML
    private  Label NombreTotalDemandeurTxt;
    @FXML
    private  Label NombreTotalJournalTxt;
    @FXML
    private  Label NombreTotalCategorieTxt;
    @FXML
    private  Label NombreTotalAbonnementTxt;
    @FXML
    private  Label NombreTotalOffresTxt;
    @FXML
    private  Label NombreTotalRecrutementTxt;
    @FXML
    private  Label NombreTotalPostulationTxt;
    @FXML
    private PieChart PieChart_PlusEstAbonne;
    @FXML
    private  PieChart PieChart_PlusJournalAbonne;
    @FXML
    private  PieChart PieChart_PlusEstRecruter;
    @FXML
    private  PieChart PieChart_PlusDemandeurPostuler;

    @FXML
    public void initialize() {
       try{
           NombreTotalEstTxt.setText(DashbordDAO.NombreTotalEntreprise()+"");
           NombreTotalDemandeurTxt.setText(DashbordDAO.NombreTotalDemandeur()+"");
           NombreTotalJournalTxt.setText(DashbordDAO.NombreTotalJournal()+"");
           NombreTotalCategorieTxt.setText(DashbordDAO.NombreTotalCategorie()+"");
           NombreTotalAbonnementTxt.setText(DashbordDAO.NombreTotalAbonnement()+"");
           NombreTotalOffresTxt.setText(DashbordDAO.NombreTotalOffre()+"");
           NombreTotalPostulationTxt.setText(DashbordDAO.NombreTotalPostulation()+"");
           NombreTotalRecrutementTxt.setText(DashbordDAO.NombreTotalRecrutement()+"");
           LoadPieChart();

       }catch (Exception ex){
           showAlertWarnning(ex.getMessage());
       }
    }

    private  void LoadPieChart() throws SQLException {

        // les 5 entreprise plus abonnee
        PieChart_PlusEstAbonne.setData(DashbordDAO.Top5EntrepriseAbonne());
        PieChart_PlusEstAbonne.getData().forEach(data -> {
            String value = data.getPieValue()+"";
            Tooltip tooltip = new Tooltip(value);
            Tooltip.install(data.getNode(),tooltip);
        });
        // les 5 journal a  plus  des abonnee
        PieChart_PlusJournalAbonne.setData(DashbordDAO.Top5JournalAbonne());
        PieChart_PlusJournalAbonne.getData().forEach(data -> {
            String value = data.getPieValue()+"";
            Tooltip tooltip = new Tooltip(value);
            Tooltip.install(data.getNode(),tooltip);
        });
        // les 5 entreprise   plus  recruter
        PieChart_PlusEstRecruter.setData(DashbordDAO.Top5EntrepriseRecruter());
        PieChart_PlusEstRecruter.getData().forEach(data -> {
            String value = data.getPieValue()+"";
            Tooltip tooltip = new Tooltip(value);
            Tooltip.install(data.getNode(),tooltip);
        });
        // les 5 demandeur    plus  postuler
        PieChart_PlusDemandeurPostuler.setData(DashbordDAO.Top5DEmandeurPostuler());
        PieChart_PlusDemandeurPostuler.getData().forEach(data -> {
            String value = data.getPieValue()+"";
            Tooltip tooltip = new Tooltip(value);
            Tooltip.install(data.getNode(),tooltip);
        });
    }
    public void showAlertWarnning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("title");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
