package files.agencerecrutement.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class TestController {



    @FXML
    private StackPane Content;

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("title");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void initialize() throws IOException {
       Parent fxml = FXMLLoader.load(getClass().getResource("/files/agencerecrutement/Views/GestionEntreprise.fxml"));
        Content.getChildren().removeAll();
        Content.getChildren().setAll(fxml);
    }
    @FXML
    public void showEst() {
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/files/agencerecrutement/Views/GestionEntreprise.fxml"));
            Content.getChildren().removeAll();
            Content.getChildren().setAll(fxml);
        }catch (Exception ex){
            showAlert(ex.getMessage());
        }

    }
    @FXML
    public void showJournal() {
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/files/agencerecrutement/Views/GestionJournaux.fxml"));
            Content.getChildren().removeAll();
            Content.getChildren().setAll(fxml);
        }catch (Exception ex){
            showAlert(ex.getMessage());
        }
    }
    @FXML
    public void showCat() {
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/files/agencerecrutement/Views/GestionCategorie.fxml"));
            Content.getChildren().removeAll();
            Content.getChildren().setAll(fxml);
        }catch (Exception ex){
            showAlert(ex.getMessage());
        }
    }
    @FXML
    public void showedition() {
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/files/agencerecrutement/Views/GestionEdition.fxml"));
            Content.getChildren().removeAll();
            Content.getChildren().setAll(fxml);
        }catch (Exception ex){
            showAlert(ex.getMessage());
        }
    }
    @FXML
    public void dashbord() {
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/files/agencerecrutement/Views/Dhasbord.fxml"));
            Content.getChildren().removeAll();
            Content.getChildren().setAll(fxml);
        }catch (Exception ex){
            showAlert(ex.getMessage());
        }
    }
    @FXML
    public void showAnnonce() {
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/files/agencerecrutement/Views/GestionAnnonces.fxml"));
            Content.getChildren().removeAll();
            Content.getChildren().setAll(fxml);
        }catch (Exception ex){
            showAlert(ex.getMessage());
        }
    }

}
