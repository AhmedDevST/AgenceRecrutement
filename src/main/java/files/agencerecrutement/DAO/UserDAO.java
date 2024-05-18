package files.agencerecrutement.DAO;

import files.agencerecrutement.Model.Entreprise;
import files.agencerecrutement.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public static User LoginAgent(String userName, String password) throws SQLException {
        User user = null;

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        PreparedStatement pst = con.prepareStatement("SELECT idAdmin,UserName,passwordUser " +
                "   FROM admin " +
                "   WHERE UserName = ? and passwordUser = ?;");
        pst.setString(1,userName);
        pst.setString(2,password);
        ResultSet res = pst.executeQuery();
        // Remplir objet
        while ( res.next()){
            user = new User(
                    res.getInt("idAdmin"),
                    res.getString("UserName"),
                    res.getString("passwordUser"),
                    1
            );
        }

        // Fermer les ressources
        pst.close();
        con.close();

        return user;
    }
}
