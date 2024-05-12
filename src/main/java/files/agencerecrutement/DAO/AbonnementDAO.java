package files.agencerecrutement.DAO;

import files.agencerecrutement.*;
import java.sql.Connection;
import java.sql.*;
import files.agencerecrutement.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date.*;

public class AbonnementDAO {



    public static void AjouterAbonnement(Journal Jr, Entreprise Es, int EtatAb, Date ab) throws SQLException {
        Connection conn = Utilitaire.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Abonnement (IDAbon, Idjr, IdEs, DateExpiration, EtatAbon) VALUES (NULL, ?, ?, ?, ?)");
            ps.setInt(1, Jr.getIdJr());
            ps.setInt(2, Es.getIdClient());
            ps.setDate(3, ab);
            ps.setInt(4, EtatAb);
            ps.executeUpdate();
            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Abonnement> afficherAbonnements() {
        ObservableList<Abonnement> Abonnements = FXCollections.observableArrayList();

        try (Connection conn = Utilitaire.getConnection()) {
            // Requête SQL pour sélectionner tous les abonnements
            String query = "SELECT Abonnement.IDAbon, DateExpiration, EtatAbon, CodeInterneEs, RaisonSocial, CodeJr, NomJr FROM Abonnement NATURAL JOIN Journal NATURAL JOIN Entreprise WHERE Journal.CodeJr=abonnement.IdJr AND Entreprise.CodeInterneEs = Abonnement.IdEs;";
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

    public static boolean isCompanyHaveAboInJr(int id_esn, int id_jr) throws SQLException {
        Connection conn = Utilitaire.getConnection();
        //recherché le nombre d'abonnement d'une entreprise dans un journal;

            String query = "SELECT count(IDAbon) from abonnement where IdEs = ? and IdJr = ? and EtatAbon = 1;";
           PreparedStatement ps = conn.prepareStatement(query);
           ps.setInt(1,id_esn);
            ps.setInt(2,id_jr);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int nbreAbActivé = rs.getInt(1);
            if (nbreAbActivé == 0) {
                return true;
            }else {
                return false;
            }
    }

    public static boolean getEtatAbo(int id) throws SQLException {
        Connection conn = Utilitaire.getConnection();
        //recherché l'abonnement  active.

        String query = "SELECT EtatAbon from abonnement WHERE IDAbon = ?;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int etat = rs.getInt(1);
        if (etat == 0) {
            return false;
        }else {
            return true;
        }
    }

    public static void ModifierAbonnement(int idabo, Date d) throws SQLException {
        Connection conn = Utilitaire.getConnection();

        String query = "UPDATE abonnement SET DateExpiration = ? WHERE IDAbon = ?;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setDate(1,d);
        ps.setInt(2,idabo);
        ps.executeUpdate();
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