package files.agencerecrutement;

import files.agencerecrutement.Controller.AlertsConfirmationsController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader( MainApp.class.getResource("Views/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 530);

            primaryStage.centerOnScreen();
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            scene.getStylesheets().add(String.valueOf(getClass().getResource("Styles/Style1.css")));

            primaryStage.setTitle("Hello world!");
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception ex){
            AlertsConfirmationsController.showAlertWarnning("probleme :"+ex.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
