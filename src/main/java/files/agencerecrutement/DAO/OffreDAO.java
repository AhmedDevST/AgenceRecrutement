package files.agencerecrutement.DAO;

import files.agencerecrutement.Model.Categorie;
import files.agencerecrutement.Model.Entreprise;
import files.agencerecrutement.Model.Journal;
import files.agencerecrutement.Model.Offre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class OffreDAO {
    //ajouterOffre
    //ModiferrOffre
    //activer/deactiver
    //select offre

    //list des offres valides
    public static ObservableList<Offre> ListOffresValides(int IdJr) throws SQLException {
      ObservableList<Offre> offres = FXCollections.observableArrayList();

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        PreparedStatement pst = con.prepareStatement("SELECT e.ID_Offre , o.Titre , est.CodeInterneEs, est.RaisonSocial  " +
                " FROM emission e NATURAL JOIN abonnement abo NATURAL JOIN offre o NATURAL JOIN entreprise est NATURAL JOIN journal j " +
                " WHERE e.ID_Ab = abo.IDAbon " +
                "       AND e.ID_Offre = o.IdOffre " +
                "       AND est.CodeInterneEs = o.Id_Es " +
                "       AND abo.IdJr = j.CodeJr " +
                "       AND abo.EtatAbo = 1 " +
                "       AND o.EtatOffre = 1" +
                "       AND j.CodeJr = ? ;");
        pst.setInt(1,IdJr);
        ResultSet res = pst.executeQuery();
        // Remplir la liste
        while (res.next()) {
            offres.add(new Offre(
                    res.getInt(1),
                    res.getString(2),
                    new Entreprise(res.getInt(3), res.getString(4))
            ));
        }

        // Fermer les ressources
        pst.close();
        con.close();
        return offres;
    }





    //list des offre d un entreprise
    //update nombre de post recruter

}
