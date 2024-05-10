package files.agencerecrutement.DAO;
import files.agencerecrutement.Model.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import static files.agencerecrutement.DAO.Utilitaire.getConnection;

import files.agencerecrutement.Model.*;

import java.sql.SQLException;

public class RecrutementDAO {
    //AjouterRecrutement
    //select recrutement
    //list des recrutement
    //list des recrutement d un entreprise
    static Connection conn ;

    public void AjouterRecrutement(Demandeur d,Offre o) throws SQLException {
        conn = getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Recrutement(IdDemd,IdOff) INTO VALUES (?,?)");
            ps.setInt(2,d.getIdClient());
            ps.setInt(3,o.getIdOffre());
            ps.executeUpdate();
            ps.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void selectRecrutementparDemandeur(int id) throws SQLException{
        conn = Utilitaire.getConnection();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Recrutement WHERE IdDemd = ?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int idd = rs.getInt("IdDemd");
                int ido = rs.getInt("IdOff");

                System.out.println("IdDemandeur : "+idd+", IDOffre : "+ido);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectRecrutementparOffre(int id) throws SQLException{
        conn = Utilitaire.getConnection();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Recrutement WHERE IdOff = ?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int idd = rs.getInt("IdDemd");
                int ido = rs.getInt("IdOff");

                System.out.println("IdDemandeur : "+idd+", IDOffre : "+ido);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ListRecrutement()throws SQLException{
        conn = Utilitaire.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Recrutement");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int idd = rs.getInt("IdDemd");
                int ido = rs.getInt("IdOff");

                System.out.println("IdDemandeur : "+idd+", IDOffre : "+ido);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ListRecrutementParEntreprise(int Id) throws SQLException{
            conn = Utilitaire.getConnection();
            try{
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM Recrutement NATURAL JOIN Offre NATURAL JOIN Entreprise WHERE CodeInterneEs = ? ");
                ps.setInt(1,Id);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int idd = rs.getInt("IdDemd");
                    int ido = rs.getInt("IdOff");

                    System.out.println("IdDemandeur : "+idd+", IDOffre : "+ido);
                }
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}



