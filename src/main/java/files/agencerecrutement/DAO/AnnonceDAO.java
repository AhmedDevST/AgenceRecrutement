package files.agencerecrutement.DAO;

import files.agencerecrutement.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class AnnonceDAO {
    //ajouterAnnonce
    public static void AjouterAnnonce(int IDOffre , int NumEdition) throws SQLException {
        Connection conn = Utilitaire.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO annonce (IdAnnonce, Id_edition, Id_offre) VALUES (NULL, ?, ?);");
            ps.setInt(1,NumEdition);
            ps.setInt(2,IDOffre);
            ps.executeUpdate();
            ps.close();
            conn.close();
    }


    //list des annoces
    public static ObservableList<Annonce> ListAnnonces() throws SQLException {
        ObservableList<Annonce> annonces = FXCollections.observableArrayList();
        Connection conn = Utilitaire.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs  = st.executeQuery("SELECT " +
                "   a.IdAnnonce , o.IdOffre , o.Titre , e.IdEd , e.DateParution , j.CodeJr ,j.NomJr , c.IdCat, c.Libelle " +
                "   FROM  " +
                "       annonce a NATURAL JOIN offre o" +
                "        NATURAL JOIN editions e " +
                "       NATURAL JOIN journal j" +
                "        NATURAL JOIN categorie c" +
                "  WHERE " +
                "       a.Id_edition = e.IdEd " +
                "       AND a.Id_offre = o.IdOffre " +
                "       AND e.journalEd = j.CodeJr " +
                "       AND j.categorie = c.IdCat ;");

            while (rs.next()) {
                annonces.add( new Annonce(
                        rs.getInt(1) ,
                        new Offre(rs.getInt(2),rs.getString(3)),
                        new Edition( rs.getInt(4), rs.getDate(5),
                                new Journal(rs.getInt(6),rs.getString(7),
                                        new Categorie(rs.getInt(8),rs.getString(9)) )
                        ))
                );
            }
            rs.close();
            st.close();
            conn.close();
        return annonces;
    }
    public static ObservableList<Annonce> ListAnnoncesByPreferences(int idDemandeur) throws SQLException {
        ObservableList<Annonce> annonces = FXCollections.observableArrayList();
        Connection conn = Utilitaire.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT " +
                "   a.IdAnnonce , o.IdOffre , o.Titre , e.IdEd , e.DateParution , j.CodeJr ,j.NomJr , c.IdCat, c.Libelle " +
                "   FROM  " +
                "       annonce a NATURAL JOIN offre o" +
                "        NATURAL JOIN editions e " +
                "       NATURAL JOIN journal j" +
                "        NATURAL JOIN categorie c" +
                "  WHERE " +
                "       a.Id_edition = e.IdEd " +
                "       AND a.Id_offre = o.IdOffre " +
                "       AND e.journalEd = j.CodeJr " +
                "       AND j.categorie = c.IdCat " +
                "       AND c.IdCat IN (SELECT ID_Cat FROM pref_demandeur WHERE ID_Dem = ?);");
        ps.setInt(1,idDemandeur);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            annonces.add( new Annonce(
                    rs.getInt(1) ,
                    new Offre(rs.getInt(2),rs.getString(3)),
                    new Edition( rs.getInt(4), rs.getDate(5),
                            new Journal(rs.getInt(6),rs.getString(7),
                                    new Categorie(rs.getInt(8),rs.getString(9)) )
                    ))
            );
        }
        rs.close();
        ps.close();
        conn.close();
        return annonces;
    }

    public static boolean IsOffreDejaPublier(int idOffre, int numSequentiel) throws SQLException {
        Connection conn = Utilitaire.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT COUNT(IdAnnonce) as nb FROM annonce " +
                             " WHERE Id_edition = ? and Id_offre = ? ;");
        ps.setInt(1,numSequentiel);
        ps.setInt(2,idOffre);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int nb = rs.getInt("nb");
        return nb != 0;
    }


    // select un annonce
}
