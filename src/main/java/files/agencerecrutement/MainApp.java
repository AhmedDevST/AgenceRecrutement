package files.agencerecrutement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader( MainApp.class.getResource("Views/TestView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 400);
        primaryStage.setTitle("Hello world!");
        primaryStage.setScene(scene);
        primaryStage.show();
        //commment
    }

    public static void main(String[] args) {
        launch(args);
    }


}
