package files.agencerecrutement.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Utilitaire {
   private static final String URL = "jdbc:mysql://localhost:3306/TP6";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    // Méthode pour établir une connexion à la base de données
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}

