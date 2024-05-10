package files.agencerecrutement.DAO;

import files.agencerecrutement.Model.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import static files.agencerecrutement.DAO.Utilitaire.getConnection;
import files.agencerecrutement.Model.*;
public class AnnonceDAO {
    //ajouterAnnonce
    //list des annoces
    // select un annonce
    static Connection conn;



    public void selectAnnonce(int id) throws SQLException{
        conn = Utilitaire.getConnection();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Annonce WHERE IdAnnonce = ?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int ida = rs.getInt("IdAnnonce");
                int ide = rs.getInt("Id_edition");
                int ido = rs.getInt("Id_offre");

                System.out.println("IdAnnonce : "+ida+", IdEdition : "+ide+", IdOffre : "+ido);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
