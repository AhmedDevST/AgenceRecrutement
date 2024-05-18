package files.agencerecrutement.DAO;

import files.agencerecrutement.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class OffreDAO {


    //ajouterOffre
    public static void AjouterOffre(Offre offre) throws SQLException {
        Connection con = Utilitaire.getConnection();
        PreparedStatement pst = con.prepareStatement(
                "INSERT INTO offre (idOffre, id_Es, Titre, Competences, NbAnneeEX, NbPostOffers, EtatOffre)" +
                        " VALUES (NULL, ?, ?, ?, ?, ?, ?);");
        //set les parametres

        pst.setInt(1, offre.getEntreprise().getIdUser());
        pst.setString(2, offre.getTitre());
        pst.setString(3, offre.getCompetences());
        pst.setInt(4, offre.getNbAnneeEx());
        pst.setInt(5, offre.getNbPostOfferts());
        if (offre.isEtatOffre())
            pst.setInt(6, 1);
        else
            pst.setInt(6, 0);
        //excuter
        pst.executeUpdate();

        //fermer
        pst.close();
        con.close();
    }


    //list des offres
    public static ObservableList<Offre> afficherOffre() throws SQLException {
        ObservableList<Offre> offres = FXCollections.observableArrayList();

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        Statement st = con.createStatement();
        ResultSet res = st.executeQuery("SELECT idOffre, CodeInterneEs, RaisonSocial,Titre, Competences, NbAnneeEX, NbPostOffers,EtatOffre " +
                "   FROM " +
                "       offre NATURAL JOIN entreprise " +
                "   WHERE " +
                "       offre.Id_Es = entreprise.CodeInterneEs;");

        // Remplir la liste

        while (res.next()) {
            offres.add(new Offre(
                    res.getInt(1),
                    res.getString(4),
                    res.getString(5),
                    res.getInt(6),

                    res.getInt(7),
                    res.getBoolean(8),
                    new Entreprise(res.getInt(2), res.getString(3))
            ));
        }

        // Fermer les ressources
        st.close();
        con.close();

        return offres;
    }

    //select   offre
    public static Offre SelectOffre(int idOffre) throws SQLException {
        Offre offre = null;

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        PreparedStatement pst = con.prepareStatement("SELECT idOffre, CodeInterneEs, RaisonSocial,Titre, Competences, NbAnneeEX, NbPostOffers,EtatOffre " +
                "   FROM " +
                "       offre NATURAL JOIN entreprise " +
                "   WHERE " +
                "       offre.Id_Es = entreprise.CodeInterneEs " +
                "       AND idOffre = ? ;");
        pst.setInt(1,idOffre);
        ResultSet res = pst.executeQuery();

        // Remplir la liste

        res.next();
        offre = new Offre(
                    res.getInt(1),
                    res.getString(4),
                    res.getString(5),
                    res.getInt(6),
                    res.getInt(7),
                    res.getBoolean(8),
                    new Entreprise(res.getInt(2), res.getString(3))
            );
        // Fermer les ressources
        pst.close();
        con.close();

        return offre;
    }

    //modifier Offre
    public static void ModifierOffre(Offre o) throws SQLException {
            Connection con = Utilitaire.getConnection();
            PreparedStatement pst = con.prepareStatement(
                    " UPDATE offre SET Titre=?, Competences=?, NbAnneeEX=?, NbPostOffers=?  " +
                            " WHERE IdOffre = ?");
            //set les parametres

            pst.setString(1, o.getTitre());
            pst.setString(2, o.getCompetences());
            pst.setInt(3, o.getNbAnneeEx());
            pst.setInt(4, o.getNbPostOfferts());
            pst.setInt(5, o.getIdOffre());

            //excuter
             int nb = 0;
             nb = pst.executeUpdate();
            // update etat  si est execute
            if(nb > 0){
                CallableStatement callableStatement = con.prepareCall("{call UpdateEtatOffres(?)} ");
                callableStatement.setInt(1,o.getIdOffre());
                callableStatement.executeQuery();
                callableStatement.close();
            }
            //fermer
            pst.close();
            con.close();
    }

    //list des offres valides
    public static ObservableList<Offre> ListOffresValides(int IdJr,int NumEdition) throws SQLException {
        AbonnementDAO.UpdateEtatAbonnment();
      ObservableList<Offre> offres = FXCollections.observableArrayList();

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        PreparedStatement pst = con.prepareStatement("SELECT e.ID_Offre , o.Titre , est.CodeInterneEs, est.RaisonSocial  " +
                "      FROM emission e NATURAL JOIN abonnement abo NATURAL JOIN offre o NATURAL JOIN entreprise est NATURAL JOIN journal j " +
                "                 WHERE e.ID_Ab = abo.IDAbon  " +
                "                       AND e.ID_Offre = o.IdOffre  " +
                "                       AND est.CodeInterneEs = o.Id_Es  " +
                "                       AND abo.IdJr = j.CodeJr  " +
                "                       AND abo.EtatAbon = 1 " +
                "                       AND o.EtatOffre = 1 " +
                "                       AND j.CodeJr = ? " +
                "                       AND o.IdOffre NOT IN ( SELECT  Id_offre FROM annonce " +
                "                              WHERE Id_edition = ? ); ;");
        pst.setInt(1,IdJr);
        pst.setInt(2,NumEdition);
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


    public static boolean getEtatOffre(int IdOffre) throws SQLException {
        Connection conn = Utilitaire.getConnection();
        //recherché l'abonnement  active.

        String query = "SELECT EtatOffre from offre WHERE IdOffre = ?;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1,IdOffre);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int etat = rs.getInt(1);
        if (etat == 0) {
            return false;
        }else {
            return true;
        }
    }

    public static int getNbExperience(int idOffre) throws SQLException {
        Connection conn = Utilitaire.getConnection();
        //recherché l'abonnement  active.

        String query = "SELECT NbAnneeEX from offre WHERE IdOffre = ?;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1,idOffre);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return  rs.getInt(1);
    }
    public static int NombreOffreEntreprise(int  idEst) throws SQLException {
        Connection conn = Utilitaire.getConnection();
        String query = "SELECT COUNT(*) as nb FROM offre WHERE Id_Es = ? ;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1,idEst);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("nb");
    }
    //list des offre d un entreprise
    public static ObservableList<Offre> afficherOffreOfEntreprise(int idEst) throws SQLException {
        ObservableList<Offre> offres = FXCollections.observableArrayList();

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        PreparedStatement pst = con.prepareStatement("SELECT idOffre, CodeInterneEs, RaisonSocial,Titre, Competences, NbAnneeEX, NbPostOffers,EtatOffre " +
                "   FROM " +
                "       offre NATURAL JOIN entreprise " +
                "   WHERE " +
                "       offre.Id_Es = entreprise.CodeInterneEs" +
                "        AND Id_Es = ? ;");
        pst.setInt(1,idEst);
        ResultSet res = pst.executeQuery();

        // Remplir la liste

        while (res.next()) {
            offres.add(new Offre(
                    res.getInt(1),
                    res.getString(4),
                    res.getString(5),
                    res.getInt(6),

                    res.getInt(7),
                    res.getBoolean(8),
                    new Entreprise(res.getInt(2), res.getString(3))
            ));
        }

        // Fermer les ressources
        pst.close();
        con.close();

        return offres;
    }
    public static ObservableList<Emission> afficherOffreEmis() throws SQLException {
        ObservableList<Emission> offres_Emis = FXCollections.observableArrayList();

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        Statement st = con.createStatement();
        ResultSet res = st.executeQuery("SELECT idOffre, CodeInterneEs, RaisonSocial,Titre, Competences, NbAnneeEX, NbPostOffers, EtatOffre, NomJr, ID_Ab, DateEmis " +
                "FROM offre NATURAL JOIN entreprise NATURAL JOIN emission NATURAL JOIN abonnement NATURAL JOIN journal " +
                "WHERE offre.Id_Es = entreprise.CodeInterneEs and offre.IdOffre = emission.ID_Offre " +
                "And emission.ID_Ab = abonnement.IDAbon and abonnement.IdJr = journal.CodeJr;");

        // Remplir la liste

        while (res.next()) {

            offres_Emis.add( new Emission(0,res.getDate(11),
                            new Abonnement(
                                    res.getInt(10),
                                    new Entreprise(1,res.getString(3)),
                                    new Journal(
                                            1,res.getString(9),"","",new Categorie(1,""))),
                            new Offre(
                                    res.getInt(1),
                                    res.getString(4),
                                    res.getString(5),
                                    res.getInt(6),

                                    res.getInt(7),
                                    res.getBoolean(8),
                                    new Entreprise(res.getInt(2), res.getString(3))
                            )
                    )
            );
        }
        // Fermer les ressources
        st.close();
        con.close();

        return offres_Emis;
    }

    public static ObservableList<Emission> afficherOffreEmisOfEntreprise(int idEst) throws SQLException {
        ObservableList<Emission> offres_Emis = FXCollections.observableArrayList();

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        PreparedStatement st = con.prepareStatement("SELECT idOffre, CodeInterneEs, RaisonSocial,Titre, Competences, NbAnneeEX, NbPostOffers, EtatOffre, NomJr, ID_Ab, DateEmis " +
                "FROM offre NATURAL JOIN entreprise NATURAL JOIN emission NATURAL JOIN abonnement NATURAL JOIN journal " +
                "WHERE offre.Id_Es = entreprise.CodeInterneEs and offre.IdOffre = emission.ID_Offre " +
                "And emission.ID_Ab = abonnement.IDAbon and abonnement.IdJr = journal.CodeJr" +
                " AND  offre.Id_Es = ?;");
        st.setInt(1,idEst);
        ResultSet res = st.executeQuery();

        // Remplir la liste

        while (res.next()) {

            offres_Emis.add( new Emission(0,res.getDate(11),
                            new Abonnement(
                                    res.getInt(10),
                                    new Entreprise(1,res.getString(3)),
                                    new Journal(
                                            1,res.getString(9),"","",new Categorie(1,""))),
                            new Offre(
                                    res.getInt(1),
                                    res.getString(4),
                                    res.getString(5),
                                    res.getInt(6),

                                    res.getInt(7),
                                    res.getBoolean(8),
                                    new Entreprise(res.getInt(2), res.getString(3))
                            )
                    )
            );
        }
        // Fermer les ressources
        st.close();
        con.close();

        return offres_Emis;
    }
}
