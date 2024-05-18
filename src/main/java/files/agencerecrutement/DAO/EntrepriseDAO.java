package files.agencerecrutement.DAO;

import files.agencerecrutement.Model.Entreprise;
import files.agencerecrutement.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class EntrepriseDAO {
    //ajouterEntreprise

    public static  void AjouterEntreprise(Entreprise entreprise) throws SQLException {
        Connection con = Utilitaire.getConnection();
        PreparedStatement pst = con.prepareStatement(
                "INSERT INTO entreprise (CodeInterneEs, Adresse, Telephone, RaisonSocial, Activité, usernameEst , passwordEst )" +
                        " VALUES (NULL, ?, ?, ?, ?,?,?);");
        //set les parametres
        pst.setString(1,entreprise.getAdresse());
        pst.setString(2,entreprise.getPhone());
        pst.setString(3,entreprise.getRaisonSocial());
        pst.setString(4,entreprise.getActivites());
        pst.setString(5,entreprise.getUserName());
        pst.setString(6,entreprise.getPassword());
        //excuter
        pst.executeUpdate();
        //fermer
        pst.close();
        con.close();
    }

    //modifierEntreprise
    public  static  void ModifierEntreprise(Entreprise entreprise) throws SQLException {
        Connection con = Utilitaire.getConnection();
        PreparedStatement pst = con.prepareStatement("UPDATE entreprise SET " +
                " Adresse = ?, Telephone = ?, RaisonSocial = ?, Activité = ? , usernameEst = ? , passwordEst = ?" +
                " WHERE CodeInterneEs =  ?;");
        //set les parametres
        pst.setString(1,entreprise.getAdresse());
        pst.setString(2,entreprise.getPhone());
        pst.setString(3,entreprise.getRaisonSocial());
        pst.setString(4,entreprise.getActivites());
        pst.setString(5,entreprise.getUserName());
        pst.setString(6,entreprise.getPassword());
        pst.setInt(7,entreprise.getIdUser());
        //excuter
        pst.executeUpdate();
        //fermer
        pst.close();
        con.close();
    }
    //select Entreprise


    //list des Entreprise
    public static ObservableList<Entreprise> afficherEntreprises() throws SQLException {
        ObservableList<Entreprise> entreprises = FXCollections.observableArrayList();

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        Statement st = con.createStatement();
        ResultSet res = st.executeQuery("SELECT CodeInterneEs,Adresse,Telephone,RaisonSocial,Activité FROM entreprise;");

        // Remplir la liste
        while (res.next()) {
            entreprises.add(new Entreprise(
                    res.getInt(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getString(5)
            ));
        }

        // Fermer les ressources
        st.close();
        con.close();

        return entreprises;
    }
    public static Entreprise SelectEntreprise(int idEst) throws SQLException {
        Entreprise entreprise = null;

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        PreparedStatement pst = con.prepareStatement("SELECT CodeInterneEs,Adresse,Telephone,RaisonSocial,Activité,usernameEst,passwordEst " +
                "  FROM entreprise" +
                "  WHERE CodeInterneEs = ? ;");
        pst.setInt(1,idEst);
        ResultSet res = pst.executeQuery();
        // Remplir objet
        res.next();
        entreprise = new Entreprise(
                    res.getInt("CodeInterneEs"),
                    res.getString("usernameEst"),
                    res.getString("passwordEst"),
                    res.getString("Adresse"),
                    res.getString("Telephone"),
                    res.getString("RaisonSocial"),
                    res.getString("Activité")
            );

        // Fermer les ressources
        pst.close();
        con.close();

        return entreprise;
    }

    public static boolean IsUserNameExist(String userName,int idEst) throws SQLException {
        Connection conn = Utilitaire.getConnection();
        //recherché le nombre d'abonnement d'une entreprise dans un journal;
        int nb = 0;
        String query = " SELECT COUNT(*) as nb FROM entreprise " +
                "    WHERE usernameEst = ?" +
                "    AND CodeInterneEs != ?;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1,userName);
        ps.setInt(2,idEst);
        ResultSet rs = ps.executeQuery();
        rs.next();
        nb =rs.getInt(1);
        if(nb == 0) return  false;
        return true;
    }

    public static User Login(String userName, String password) throws SQLException {
        User user = null;

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        PreparedStatement pst = con.prepareStatement("SELECT CodeInterneEs,usernameEst,passwordEst " +
                "   FROM entreprise " +
                "   WHERE usernameEst = ? and passwordEst = ?;");
        pst.setString(1,userName);
        pst.setString(2,password);
        ResultSet res = pst.executeQuery();
        // Remplir objet
        while ( res.next()){
            user = new User(
                    res.getInt("CodeInterneEs"),
                    res.getString("usernameEst"),
                    res.getString("passwordEst"),
                    2
            );
        }

        // Fermer les ressources
        pst.close();
        con.close();

        return user;
    }

    public static String getRaisonSocial(int idCli) throws SQLException {
        Connection conn = Utilitaire.getConnection();
        int nb = 0;
        String query = " SELECT RaisonSocial FROM entreprise " +
                "    WHERE CodeInterneEs = ?;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1,idCli);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString(1);
    }
}
