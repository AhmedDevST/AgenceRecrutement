package files.agencerecrutement.DAO;

import files.agencerecrutement.Model.Categorie;
import files.agencerecrutement.Model.Client;
import files.agencerecrutement.Model.PreferencesClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class PreferencesClientDAO {
    public static ObservableList<Categorie> AfficherPreferencesEntreprise(int idClient) throws SQLException {
        ObservableList<Categorie> Preferences = FXCollections.observableArrayList();

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        PreparedStatement st = con.prepareStatement(" SELECT  ID_Cat , Libelle " +
                " FROM pref_entreprise NATURAL JOIN categorie  " +
                " WHERE pref_entreprise.ID_Cat = categorie.IdCat " +
                " AND ID_Es = ?; ");
        st.setInt(1,idClient);
        ResultSet res = st.executeQuery();

        // Remplir la liste
        while (res.next()) {
            Preferences.add(
                    new Categorie(
                           res.getInt(1) , res.getString(2)
                    ));
        }
        // Fermer les ressources
        st.close();
        con.close();

        return Preferences;
    }

    public static ObservableList<Categorie> AfficherNotPreferencesEntreprise(int idClient) throws SQLException {
        ObservableList<Categorie> Preferences = FXCollections.observableArrayList();

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        PreparedStatement st = con.prepareStatement(" SELECT IdCat,Libelle " +
                " FROM categorie" +
                " WHERE IdCat not in (SELECT ID_Cat FROM pref_entreprise WHERE  ID_Es = ?   ); ");
        st.setInt(1,idClient);
        ResultSet res = st.executeQuery();

        // Remplir la liste
        while (res.next()) {
            Preferences.add(
                    new Categorie(
                            res.getInt(1) , res.getString(2)
                    ));
        }
        // Fermer les ressources
        st.close();
        con.close();

        return Preferences;
    }

    public static ObservableList<Categorie> AfficherPreferencesDemandeur(int idClient) throws SQLException {
        ObservableList<Categorie> Preferences = FXCollections.observableArrayList();

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        PreparedStatement st = con.prepareStatement(" SELECT  ID_Cat , Libelle " +
                " FROM pref_demandeur NATURAL JOIN categorie  " +
                " WHERE pref_demandeur.ID_Cat = categorie.IdCat " +
                " AND ID_Dem = ?; ");
        st.setInt(1,idClient);
        ResultSet res = st.executeQuery();

        // Remplir la liste
        while (res.next()) {
            Preferences.add(
                    new Categorie(
                            res.getInt(1) , res.getString(2)
                    ));
        }
        // Fermer les ressources
        st.close();
        con.close();

        return Preferences;
    }

    public static ObservableList<Categorie> AfficherNotPreferencesDemandeur(int idClient) throws SQLException {
        ObservableList<Categorie> Preferences = FXCollections.observableArrayList();

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        PreparedStatement st = con.prepareStatement(" SELECT IdCat,Libelle " +
                " FROM categorie" +
                " WHERE IdCat not in (SELECT ID_Cat FROM pref_demandeur WHERE  ID_Dem = ?   ); ");
        st.setInt(1,idClient);
        ResultSet res = st.executeQuery();

        // Remplir la liste
        while (res.next()) {
            Preferences.add(
                    new Categorie(
                            res.getInt(1) , res.getString(2)
                    ));
        }
        // Fermer les ressources
        st.close();
        con.close();

        return Preferences;
    }

    public static void AjouterPreferenceEntreprise(int idCli , int idCat) throws SQLException {
        Connection conn = Utilitaire.getConnection();
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO pref_entreprise (ID_Es, ID_Cat) VALUES (?, ?);");
        ps.setInt(1,idCli);
        ps.setInt(2,idCat);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }
    public static void AjouterPreferenceDemandeur(int idCli , int idCat) throws SQLException {
        Connection conn = Utilitaire.getConnection();
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO pref_demandeur (ID_Dem, ID_Cat) VALUES (?, ?);");
        ps.setInt(1,idCli);
        ps.setInt(2,idCat);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }
    public static void SupprimerPreferenceEntreprise(int idClient , int idCat) throws SQLException {
        Connection conn = Utilitaire.getConnection();
        PreparedStatement ps = conn.prepareStatement(
                " DELETE FROM pref_entreprise WHERE ID_Es = ? and ID_Cat = ?;");
        ps.setInt(1,idClient);
        ps.setInt(2,idCat);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }
    public static void SupprimerPreferenceDemandeur(int idClient , int idCat) throws SQLException {
        Connection conn = Utilitaire.getConnection();
        PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM pref_demandeur WHERE ID_Dem = ? and ID_Cat = ?;");
        ps.setInt(1,idClient);
        ps.setInt(2,idCat);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    public static void SupprimerTousPreferenceEntreprise(int idClient) throws SQLException {
        Connection conn = Utilitaire.getConnection();
        PreparedStatement ps = conn.prepareStatement(
                " DELETE FROM pref_entreprise WHERE ID_Es = ? ;");
        ps.setInt(1,idClient);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }
    public static void SupprimerTousPreferenceDemandeur(int idClient) throws SQLException {
        Connection conn = Utilitaire.getConnection();
        PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM pref_demandeur WHERE ID_Dem = ? ;");
        ps.setInt(1,idClient);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }
}
