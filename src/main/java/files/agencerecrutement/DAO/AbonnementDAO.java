package files.agencerecrutement.DAO;

import files.agencerecrutement.*;
import java.sql.Connection;
import java.sql.*;
import files.agencerecrutement.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date.*;

public class AbonnementDAO {
    static Connection conn;


    public Abonnement AjouterAbonnement(int IdAbon, Journal Jr, Entreprise Es, boolean EtatAb, Date ab) throws SQLException {
        conn = Utilitaire.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Abonnement (IDAbon, Idjr, IdEs, DateExpiration, EtatAbon) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, IdAbon);
            ps.setInt(2, Jr.getIdJr());
            ps.setInt(3, Es.getIdClient());
            ps.setDate(4, ab);
            ps.setBoolean(5, EtatAb);
            ps.executeUpdate();
            ps.close();
            conn.close();

            return new Abonnement(IdAbon, Es, Jr, EtatAb, ab);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if an exception occurs
    }

    public static ObservableList<Abonnement> afficherAbonnements() {
        ObservableList<Abonnement> Abonnements = FXCollections.observableArrayList();

        try (Connection conn = Utilitaire.getConnection()) {
            // Requête SQL pour sélectionner tous les abonnements
            String query = "SELECT IDAbon,DateExpiration, EtatAbon,CodeInterneEs,RaisonSocial,CodeJr,NomJr FROM Abonnement natural join Entreprise natural join Journal";
            Statement statement = conn.createStatement();

            // Exécution de la requête
            ResultSet resultSet = statement.executeQuery(query);

            // Parcours des résultats et création d'instances Abonnement
            while (resultSet.next()) {
                int idAbon = resultSet.getInt("IDAbon");
                int idJr = resultSet.getInt("CodeJr");
                String nomJr = resultSet.getString("NomJr");
                int idEs = resultSet.getInt("CodeInterneEs");
                String nomEs = resultSet.getString("RaisonSocial");
                Date dateExpiration = resultSet.getDate("DateExpiration");
                boolean etatAbon = resultSet.getBoolean("EtatAbon");

                // Création d'une instance Abonnement
                Abonnement abonnement = new Abonnement(idAbon, new Entreprise(idEs, nomEs), new Journal(idJr, nomJr), etatAbon, dateExpiration);

                // Ajout de l'abonnement à la liste observable
                Abonnements.add(abonnement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Abonnements;
    }

    /*public void affichageTest() throws SQLException {
        conn = Utilitaire.getConnection();
        String query = "SELECT IDAbon,DateExpiration, EtatAbon,CodeInterneEs,RaisonSocial,CodeJr,NomJr FROM Abonnement natural join Entreprise natural join Journal";
        Statement statement = conn.createStatement();

        // Exécution de la requête
        ResultSet resultSet = statement.executeQuery(query);

        // Parcours des résultats et création d'instances Abonnement
        while (resultSet.next()) {
            int idAbon = resultSet.getInt("IDAbon");
            int idJr = resultSet.getInt("CodeJr");
            String nomJr = resultSet.getString("NomJr");
            int idEs = resultSet.getInt("CodeInterneEs");
            String nomEs = resultSet.getString("RaisonSocial");
            Date dateExpiration = resultSet.getDate("DateExpiration");
            boolean etatAbon = resultSet.getBoolean("EtatAbon");
            System.out.println(idAbon + " " + idJr + " " + nomJr + " " + idEs + " " + nomEs);
        }

    }

    public static void main(String[] args) throws SQLException {
        AbonnementDAO a = new AbonnementDAO();
        a.affichageTest();

    }*/
}