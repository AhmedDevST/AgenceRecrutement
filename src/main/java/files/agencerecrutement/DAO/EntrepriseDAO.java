package files.agencerecrutement.DAO;

import files.agencerecrutement.Model.Entreprise;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class EntrepriseDAO {
    //ajouterEntreprise

    public static  void AjouterEntreprise(Entreprise entreprise) throws SQLException {
        Connection con = Utilitaire.getConnection();
        PreparedStatement pst = con.prepareStatement(
                "INSERT INTO entreprise (CodeInterneEs, Adresse, Telephone, RaisonSocial, Activité)" +
                        " VALUES (NULL, ?, ?, ?, ?);");
        //set les parametres
        pst.setString(1,entreprise.getAdresse());
        pst.setString(2,entreprise.getPhone());
        pst.setString(3,entreprise.getRaisonSocial());
        pst.setString(4,entreprise.getActivites());
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
                " Adresse = ?, Telephone = ?, RaisonSocial = ?, Activité = ?" +
                " WHERE CodeInterneEs =  ?;");
        //set les parametres
        pst.setString(1,entreprise.getAdresse());
        pst.setString(2,entreprise.getPhone());
        pst.setString(3,entreprise.getRaisonSocial());
        pst.setString(4,entreprise.getActivites());
        pst.setInt(5,entreprise.getIdClient());
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
}
