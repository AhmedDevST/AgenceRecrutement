package files.agencerecrutement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import files.agencerecrutement.Controller.*;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/files/agencerecrutement/Views/Abonnement.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        primaryStage.setTitle("Hello world left!");
        //scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
        // Commentaire ajouté pour clarifier le code
    }

    public static void main(String[] args) {
        launch(args);
    }
}
