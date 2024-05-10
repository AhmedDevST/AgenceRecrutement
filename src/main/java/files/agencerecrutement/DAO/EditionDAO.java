package files.agencerecrutement.DAO;

import files.agencerecrutement.Model.Categorie;
import files.agencerecrutement.Model.Edition;
import files.agencerecrutement.Model.Journal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class EditionDAO {

    //ajouterEdition
    public static void AjouterEdition(Edition edition) throws SQLException {
        Connection con = Utilitaire.getConnection();
        PreparedStatement pst = con.prepareStatement(
                "INSERT INTO  editions (IdEd, journalEd, DateParution)" +
                        "  VALUES (NULL, ?, ?);");
        //set les parametres
        pst.setInt(1,edition.getJournal().getIdJr());
        pst.setDate(2,new java.sql.Date(edition.getDateParution().getTime()) );
        //excuter
        pst.executeUpdate();
        //fermer
        pst.close();
        con.close();
    }
    //list des editions
    public static ObservableList<Edition> afficherEditions() throws SQLException {
        ObservableList<Edition> editions = FXCollections.observableArrayList();

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        Statement st = con.createStatement();
        ResultSet res = st.executeQuery("SELECT e.IdEd , e.DateParution " +
                                "  , j.CodeJr , j.NomJr , j.Periodicite , j.Langue, " +
                                "    c.IdCat , c.Libelle " +
                                "    FROM editions e  NATURAL JOIN journal j NATURAL JOIN categorie c " +
                                "    WHERE e.journalEd = j.CodeJr and j.categorie = c.IdCat;");

        // Remplir la liste
        while (res.next()) {
            editions.add(new Edition(
                    res.getInt(1),
                    res.getDate(2),
                    new Journal(res.getInt(3), res.getString(4),res.getString(5),res.getString(6),
                              new Categorie(res.getInt(7), res.getString(8)))
            ));
        }

        // Fermer les ressources
        st.close();
        con.close();

        return editions;
    }
    //modifierEdition
    public static void ModifierEdition(Edition editionEdit) throws SQLException {

        Connection con = Utilitaire.getConnection();
        PreparedStatement pst = con.prepareStatement(
                "UPDATE editions SET journalEd = ? , DateParution = ?  WHERE `editions`.`IdEd` = ?;");
        //set les parametres
        pst.setInt(1,editionEdit.getJournal().getIdJr());
        pst.setDate(2,new java.sql.Date(editionEdit.getDateParution().getTime()) );
        pst.setInt(3,editionEdit.getNumSequentiel());
        //excuter
        pst.executeUpdate();

        //fermer
        pst.close();
        con.close();
    }

    //supprimer edition
    public static boolean SupprimerEdition(Edition edition) {
        try{
            Connection con = Utilitaire.getConnection();
            PreparedStatement pst = con.prepareStatement(
                    " DELETE FROM editions WHERE IdEd = ? ;");
            //set les parametres
            pst.setInt(1,edition.getNumSequentiel());
            //excuter
            pst.executeUpdate();
            //fermer
            pst.close();
            con.close();
            return true;
        }catch (Exception ex){
            return  false;
        }
    }
}
