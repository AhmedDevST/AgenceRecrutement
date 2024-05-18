package files.agencerecrutement.DAO;

import files.agencerecrutement.Model.Categorie;
import files.agencerecrutement.Model.Entreprise;
import files.agencerecrutement.Model.Journal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class JournalDAO {

    //ajouterJournal
    public static void AjouterJournal(Journal journal) throws SQLException {
        Connection con = Utilitaire.getConnection();
        PreparedStatement pst = con.prepareStatement(
                "INSERT INTO journal (CodeJr, NomJr, categorie, Periodicite, Langue) " +
                        "VALUES (NULL, ?, ?, ?, ?);");
        //set les parametres
        pst.setString(1,journal.getNomJr());
        pst.setInt(2,journal.getCategorie().getIdcate());
        pst.setString(3,journal.getPeriodicite());
        pst.setString(4,journal.getLangue());
        //excuter
        pst.executeUpdate();
        //fermer
        pst.close();
        con.close();
    }
    //modiferJournal
    public static void ModifierJournal(Journal journal) throws SQLException {
        Connection con = Utilitaire.getConnection();
        PreparedStatement pst = con.prepareStatement(
                "UPDATE journal SET " +
                        " NomJr = ?, categorie = ?, Periodicite = ?, Langue = ? " +
                        " WHERE CodeJr = ?;");
        //set les parametres
        pst.setString(1,journal.getNomJr());
        pst.setInt(2,journal.getCategorie().getIdcate());
        pst.setString(3,journal.getPeriodicite());
        pst.setString(4,journal.getLangue());
        pst.setInt(5,journal.getIdJr());
        //excuter
        pst.executeUpdate();
        //fermer
        pst.close();
        con.close();
    }
    //select journal

    //list des journaux
    public static ObservableList<Journal> afficherJournaux() throws SQLException {
        ObservableList<Journal> journaux = FXCollections.observableArrayList();

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        Statement st = con.createStatement();
        ResultSet res = st.executeQuery("SELECT CodeJr,NomJr,Periodicite,Langue,categorie.IdCat,categorie.Libelle " +
                                           "FROM journal NATURAL JOIN categorie " +
                                          "WHERE categorie = categorie.IdCat;");

        // Remplir la liste
        while (res.next()) {
            journaux.add(new Journal(
                    res.getInt(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    new Categorie(res.getInt(5), res.getString(6) )
            ));
        }

        // Fermer les ressources
        st.close();
        con.close();

        return journaux;
    }
}
