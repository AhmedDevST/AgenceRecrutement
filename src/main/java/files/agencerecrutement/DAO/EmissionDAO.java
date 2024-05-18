package files.agencerecrutement.DAO;

import java.sql.*;
public class EmissionDAO {
    public static boolean verifierEmission(int idAbon, Date date,int idOffre){
        try{
            Connection conn = Utilitaire.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM emission WHERE ID_Ab = ? and ID_Offre = ?" +
                    " and DateEmis = ?");
            ps.setInt(1,idAbon);
            ps.setInt(2,idOffre);
            ps.setDate(3,date);

            ResultSet rs = ps.executeQuery();

            if(rs.next())
                return false;
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static void ajouterEmission(int idAbo, Date dateExpiration, int idOffre) throws SQLException {
            Connection conn = Utilitaire.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO emission (IdEmis, ID_Ab, ID_Offre, DateEmis) VALUES (NULL, ?, ?, ?)");
            ps.setInt(1,idAbo);
            ps.setInt(2,idOffre);
            ps.setDate(3, dateExpiration);
            ps.executeUpdate();
    }
}

