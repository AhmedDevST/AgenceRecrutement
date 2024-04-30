package files.agencerecrutement.DAO;

import files.agencerecrutement.Model.Entreprise;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EntrepriseDAO {
    //ajouterEntreprise
    //modifierEntreprise
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
