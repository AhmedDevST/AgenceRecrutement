package files.agencerecrutement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader( MainApp.class.getResource("Views/TestView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 500);
            primaryStage.setTitle("Hello world!");
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception ex){
            showAlertWarnning("probleme :"+ex.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    public void showAlertWarnning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("title");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
