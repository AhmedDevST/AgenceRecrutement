package files.agencerecrutement.DAO;
import files.agencerecrutement.Model.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import static files.agencerecrutement.DAO.Utilitaire.getConnection;
import files.agencerecrutement.Model.*;

public class PostulationDAO {
    //ajouterPostulation
    //select postulation
    //list des postulation
    //list des postulation d un demandeur
    static Connection conn;

    public void AjouterPostulation(int idp, Annonce an, Demandeur post) throws SQLException{
        conn = Utilitaire.getConnection();
        try{
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Postulation(IdPost,IdDem,IdAnn) INTO VALUES (?,?,?)");
            ps.setInt(1,idp);
            ps.setInt(2,an.getIdAnnonce());
            ps.setInt(3,post.getIdClient());
            ps.executeUpdate();
            ps.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void selectPostulation(int id) throws SQLException{
        conn = Utilitaire.getConnection();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Postulation WHERE IdPost = ?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int idp = rs.getInt("IdPost");
                int idd = rs.getInt("IdDem");
                int idan = rs.getInt("IdAnn");

            System.out.println("IdPostulant : "+idp+", IdDemandeur : "+idd+", IDAnnonce : "+idan);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public void ListPostulation()throws SQLException{
            conn = Utilitaire.getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM Postulation");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                   int idp = rs.getInt("IdPost");
                   int idd = rs.getInt("IdDem");
                   int ida = rs.getInt("IdAnn");

                    System.out.println("IdPostulant : "+idp+", IdDemandeur : "+idd+", IDAnnonce : "+ida);
                }
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    public void ListPostulationParDemandeur(int idD)throws SQLException{
        conn = Utilitaire.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Postulation WHERE idDem = ?");
            ps.setInt(1,idD);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idp = rs.getInt("IdPost");
                int idd = rs.getInt("IdDem");
                int ida = rs.getInt("IdAnn");

                System.out.println("IdPostulant : "+idp+", IdDemandeur : "+idd+", IDAnnonce : "+ida);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ListPostulationParAnnonce(int idA)throws SQLException{
        conn = Utilitaire.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Postulation WHERE idAnn = ?");
            ps.setInt(1,idA);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idp = rs.getInt("IdPost");
                int idd = rs.getInt("IdDem");
                int ida = rs.getInt("IdAnn");

                System.out.println("IdPostulant : "+idp+", IdDemandeur : "+idd+", IDAnnonce : "+ida);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
