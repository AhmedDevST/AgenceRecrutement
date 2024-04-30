module files.agencerecrutement {
    requires javafx.controls;
    requires javafx.fxml;
    requires  mysql.connector.j;
    requires  java.sql;

    opens files.agencerecrutement to javafx.fxml;
    exports files.agencerecrutement;
    exports files.agencerecrutement.Controller;
    opens files.agencerecrutement.Controller to javafx.fxml;
    opens files.agencerecrutement.Model to javafx.base;
}