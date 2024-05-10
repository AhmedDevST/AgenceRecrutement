package files.agencerecrutement.DAO;

import files.agencerecrutement.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            PreparedStatement ps = conn.prepareStatement("SELECT a.IdAnnonce , o.IdOffre , o.Titre , e.IdEd , e.DateParution , " +
                    " j.CodeJr ,j.NomJr " +
                    " FROM annonce a NATURAL JOIN offre o NATURAL JOIN editions e NATURAL JOIN journal j " +
                    " WHERE a.Id_edition = e.IdEd and a.Id_offre = o.IdOffre and e.journalEd = j.CodeJr;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                annonces.add( new Annonce(
                        rs.getInt(1) ,
                        new Offre(rs.getInt(2),rs.getString(3)),
                        new Edition( rs.getInt(4), rs.getDate(5),
                                new Journal(rs.getInt(6),rs.getString(7) )
                        ))
                );
            }
            rs.close();
            ps.close();
            conn.close();
        return annonces;
    }


    // select un annonce
}
