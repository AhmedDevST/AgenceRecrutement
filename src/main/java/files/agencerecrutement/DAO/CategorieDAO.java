package files.agencerecrutement.DAO;

import files.agencerecrutement.Model.Categorie;
import files.agencerecrutement.Model.Entreprise;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class CategorieDAO {


    //Ajouter Categorie
    public static  void AjouterCategorie(Categorie categorie) throws SQLException {
        Connection con = Utilitaire.getConnection();
        PreparedStatement pst = con.prepareStatement(
                "INSERT INTO categorie (IdCat, Libelle)" +
                        " VALUES (NULL, ?);");
        //set les parametres
        pst.setString(1,categorie.getLibelle());
        //excuter
        pst.executeUpdate();
        //fermer
        pst.close();
        con.close();
    }

    //modifier Categorie
    public  static  void ModifierCategorie(Categorie categorie) throws SQLException {
        Connection con = Utilitaire.getConnection();
        PreparedStatement pst = con.prepareStatement("UPDATE categorie SET " +
                                " Libelle = ? " +
                                " WHERE IdCat =  ?;");
        //set les parametres
        pst.setString(1,categorie.getLibelle());
        pst.setInt(2,categorie.getIdcate());
        //excuter
        pst.executeUpdate();
        //fermer
        pst.close();
        con.close();
    }
    //select categories
    public static ObservableList<Categorie> afficherCategories() throws SQLException {
        ObservableList<Categorie> categories = FXCollections.observableArrayList();

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        Statement st = con.createStatement();
        ResultSet res = st.executeQuery("SELECT IdCat , Libelle FROM categorie;");

        // Remplir la liste
        while (res.next()) {
            categories.add(new Categorie(
                    res.getInt(1),
                    res.getString(2)
            ));
        }

        // Fermer les ressources
        st.close();
        con.close();

        return categories;
    }

}
