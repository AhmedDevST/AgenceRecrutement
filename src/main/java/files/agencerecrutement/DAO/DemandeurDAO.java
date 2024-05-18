package files.agencerecrutement.DAO;

import files.agencerecrutement.Model.User;
import files.agencerecrutement.Model.Demandeur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DemandeurDAO {
    public static int getNbExperience(int idDemandeur) throws SQLException {
        Connection conn = Utilitaire.getConnection();
        //recherché l'abonnement  active.

        String query = "SELECT NbAnneeExp from demandeur WHERE CodeInterneD = ?;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1,idDemandeur);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return  rs.getInt(1);
    }

    public static User Login(String userName, String password) throws SQLException {
        User user = null;

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        PreparedStatement pst = con.prepareStatement("SELECT CodeInterneD,usernameDem,passwordDem " +
                "   FROM demandeur " +
                "   WHERE usernameDem = ? and passwordDem = ?;");
        pst.setString(1,userName);
        pst.setString(2,password);
        ResultSet res = pst.executeQuery();
        // Remplir objet
        while ( res.next()){
            user = new User(
                    res.getInt("CodeInterneD"),
                    res.getString("usernameDem"),
                    res.getString("passwordDem"),
                    3
            );
        }

        // Fermer les ressources
        pst.close();
        con.close();

        return user;
    }

    //list des Demandeur
    public static ObservableList<Demandeur> afficherDemandeur() throws SQLException {
        ObservableList<Demandeur> demandeurs = FXCollections.observableArrayList();

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        Statement st = con.createStatement();
        ResultSet res = st.executeQuery("SELECT  CodeInterneD, Nom, Prenom, Adresse, Telephone, Fax," +
                " NbAnneeExp, Salaire, diplome,usernameDem, passwordDem FROM Demandeur;");

        // Remplir la liste

        while (res.next()) {
            demandeurs.add(new Demandeur(
                    res.getInt(1),
                    res.getString(10),
                    res.getString(11),
                    res.getString(4),
                    res.getString(5),
                    res.getString(2),
                    res.getString(3),
                    res.getString(6),
                    res.getInt(7),
                    res.getFloat(8),
                    res.getString(9)
            ));
        }

        // Fermer les ressources
        st.close();
        con.close();

        return demandeurs;
    }

    //ajouterDemandeur  ( inscription )
    public static void AjouterDemandeur(Demandeur d) {
        try{
            Connection conn = Utilitaire.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO demandeur (CodeInterneD, Nom, Prenom, Adresse, Telephone, Fax," +
                    " NbAnneeExp, Salaire, diplome,usernameDem,passwordDem) VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?,?,?)");
            ps.setString(1,d.getNom());
            ps.setString(2, d.getPrenom());
            ps.setString(3,d.getAdresse());
            ps.setString(4,d.getPhone());
            ps.setString(5, d.getFax());
            ps.setInt(6,d.getNbAnneeEx());
            ps.setFloat(7,d.getSalaire());
            ps.setString(8,d.getDiplome());
            ps.setString(9,d.getUserName());
            ps.setString(10,d.getPassword());
            ps.executeUpdate();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //ModiferrDemandeur
    public static void ModifierDemandeur(Demandeur demandeur) {
        try{
            Connection conn = Utilitaire.getConnection();
            PreparedStatement ps = conn.prepareStatement("Update demandeur SET Nom = ? ,Prenom = ?, Telephone = ?, Fax = ?, Salaire = ?," +
                    "Adresse = ?, NbAnneeExp = ?, Diplome = ? , usernameDem = ? , passwordDem = ?  WHERE CodeInterneD = ? ");
            ps.setString(1, demandeur.getNom());
            ps.setString(2, demandeur.getPrenom());
            ps.setString(3, demandeur.getPhone());
            ps.setString(4, demandeur.getFax());
            ps.setFloat(5,demandeur.getSalaire());
            ps.setString(6, demandeur.getAdresse());
            ps.setInt(7, demandeur.getNbAnneeEx());
            ps.setString(8, demandeur.getDiplome());
            ps.setString(9, demandeur.getUserName());
            ps.setString(10, demandeur.getPassword());
            ps.setInt(11,demandeur.getIdUser());
            ps.executeUpdate();
            ps.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean IsUserNameExist(String username, int idDem) throws SQLException {
        Connection conn = Utilitaire.getConnection();
        //recherché le nombre d'abonnement d'une entreprise dans un journal;
        int nb = 0;
        String query = " SELECT COUNT(*) as nb FROM demandeur " +
                "    WHERE usernameDem = ?" +
                "    AND CodeInterneD  != ?;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1,username);
        ps.setInt(2,idDem);
        ResultSet rs = ps.executeQuery();
        rs.next();
        nb =rs.getInt(1);
        if(nb == 0) return  false;
        return true;
    }

    public static String getFullName(int idCli) throws SQLException {
        Connection conn = Utilitaire.getConnection();
        //recherché le nombre d'abonnement d'une entreprise dans un journal;
        int nb = 0;
        String query = " SELECT Nom , Prenom  FROM demandeur " +
                "    WHERE  CodeInterneD  = ? ;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1,idCli);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return  rs.getString(1) +" " + rs.getString(2);
    }


    //select Demandeur
    public static Demandeur SelectDemandeur(int idDem) throws SQLException {
        Demandeur demandeur = null;

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        PreparedStatement pst = con.prepareStatement("SELECT  CodeInterneD, Nom, Prenom, Adresse, Telephone, Fax," +
                " NbAnneeExp, Salaire, diplome,usernameDem, passwordDem FROM Demandeur" +
                "   WHERE  CodeInterneD = ? ;");
        pst.setInt(1,idDem);
        ResultSet res = pst.executeQuery();

       res.next();
       demandeur = new Demandeur(
                    res.getInt(1),
                    res.getString(10),
                    res.getString(11),
                    res.getString(4),
                    res.getString(5),
                    res.getString(2),
                    res.getString(3),
                    res.getString(6),
                    res.getInt(7),
                    res.getFloat(8),
                    res.getString(9)
            );

        // Fermer les ressources
        pst.close();
        con.close();

        return demandeur;
    }


}
