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
            ps.setInt(2, Es.getIdUser());
            ps.setDate(3, ab);
            ps.setInt(4, EtatAb);
            ps.executeUpdate();
            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Abonnement> afficherAbonnements() throws SQLException {
        UpdateEtatAbonnment();
        ObservableList<Abonnement> Abonnements = FXCollections.observableArrayList();
        Connection conn = Utilitaire.getConnection();
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
        return Abonnements;
    }

    public static boolean isCompanyHaveAboInJr(int id_esn, int id_jr) throws SQLException {
        UpdateEtatAbonnment();
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
        UpdateEtatAbonnment();
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

    public static int NombreAbonnementEntreprise(int  idEst) throws SQLException {
        Connection conn = Utilitaire.getConnection();
        String query = "SELECT COUNT(*) as nb FROM abonnement WHERE IdEs = ?;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1,idEst);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("nb");
    }

    public static ObservableList<Abonnement> afficherAbonnementsOfEntreprise(int idEst) throws SQLException {
        UpdateEtatAbonnment();
        ObservableList<Abonnement> Abonnements = FXCollections.observableArrayList();
        Connection conn = Utilitaire.getConnection();
        PreparedStatement pst = conn.prepareStatement("SELECT Abonnement.IDAbon, DateExpiration, EtatAbon, CodeInterneEs, RaisonSocial, CodeJr, NomJr " +
                "         FROM Abonnement NATURAL JOIN Journal NATURAL JOIN Entreprise " +
                "       WHERE Journal.CodeJr=abonnement.IdJr "  +
                "         AND Entreprise.CodeInterneEs = Abonnement.IdEs " +
                "         AND  IdEs = ? ;" );
        pst.setInt(1,idEst);
        // Exécution de la requête
        ResultSet resultSet = pst.executeQuery();
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
        return Abonnements;
    }

    public static ObservableList<Abonnement> afficherAbonnementsOfJourna(int idJournal) throws SQLException {
        UpdateEtatAbonnment();
        ObservableList<Abonnement> Abonnements = FXCollections.observableArrayList();
        Connection conn = Utilitaire.getConnection();
        PreparedStatement pst = conn.prepareStatement("SELECT Abonnement.IDAbon, DateExpiration, EtatAbon, CodeInterneEs, RaisonSocial, CodeJr, NomJr " +
                "         FROM Abonnement NATURAL JOIN Journal NATURAL JOIN Entreprise " +
                "       WHERE Journal.CodeJr=abonnement.IdJr "  +
                "         AND Entreprise.CodeInterneEs = Abonnement.IdEs " +
                "         AND  IdJr = ? ;" );
        pst.setInt(1,idJournal);
        // Exécution de la requête
        ResultSet resultSet = pst.executeQuery();
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
        return Abonnements;
    }

    public static void UpdateEtatAbonnment() throws SQLException {
        Connection conn = Utilitaire.getConnection();
        try {
            CallableStatement callableStatement = conn.prepareCall("{call update_abonnemnet_etat }");
            callableStatement.executeQuery();
            callableStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Categorie> afficherAbonnementCategorie(int idEntreprise){
        try{
            UpdateEtatAbonnment();
            ObservableList<Categorie> abonnementsCat = FXCollections.observableArrayList();
            Connection conn = Utilitaire.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT(IdCat), Libelle FROM abonnement JOIN journal JOIN categorie ON journal.CodeJr = abonnement.IdJr " +
                    " and journal.categorie = categorie.IdCat WHERE IdEs =? and EtatAbon =1;");
            ps.setInt(1,idEntreprise);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                abonnementsCat.add(new Categorie(
                        rs.getInt(1),rs.getString(2))
                );
            }

            //showAlert(String.valueOf(abonnements.get(0).getIdAbo()));
            return abonnementsCat;
            // PreparedStatement ps = conn.prepareStatement("SELECT ,")
        }catch(Exception e){
            return  null;
        }
    }

    public static ObservableList<Abonnement> afficherAbonnementJournal(int idEntreprise, int idCat){
        try{

            UpdateEtatAbonnment();
            ObservableList<Abonnement> abonnements = FXCollections.observableArrayList();
            Connection conn = Utilitaire.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT IDAbon, IdJr, NomJr,DateExpiration FROM abonnement JOIN journal JOIN categorie ON journal.CodeJr = abonnement.IdJr " +
                    " and journal.categorie = categorie.IdCat WHERE IdEs =? and categorie.IdCat = ? and  EtatAbon =1;");
            ps.setInt(1,idEntreprise);
            ps.setInt(2,idCat);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                abonnements.add(
                        new Abonnement(rs.getInt(1), new Entreprise(idEntreprise,""),
                                new Journal(rs.getInt(2),rs.getString(3),"","",new Categorie(1,"")),true
                                ,rs.getDate(4))
                );
            }

            //showAlert(String.valueOf(abonnements.get(0).getIdAbo()));
            return abonnements;
            // PreparedStatement ps = conn.prepareStatement("SELECT ,")
        }catch(Exception e){
            return  null;
        }
    }

}
