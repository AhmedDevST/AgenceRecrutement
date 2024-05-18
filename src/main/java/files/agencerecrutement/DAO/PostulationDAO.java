package files.agencerecrutement.DAO;
import files.agencerecrutement.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class PostulationDAO {


    //list des postulation d un demandeur
    public static ObservableList<Postulation> afficherPostulationsOfDemandeur(int idDem) {
        ObservableList<Postulation> postulations = FXCollections.observableArrayList();

        try (Connection conn = Utilitaire.getConnection()) {
            // Requête SQL pour sélectionner tous les Postulations
            String query = " SELECT IdPost,IdOffre,Titre,CodeInterneD,IdAnnonce,IdEd,DateParution,Nom,Prenom " +
                    "   from " +
                    "        postulation natural join annonce " +
                    "        natural join offre natural join " +
                    "        demandeur natural join  editions " +
                    "    where " +
                    "        postulation.IdAnn = annonce.IdAnnonce " +
                    "        and postulation.IdDem = demandeur.CodeInterneD " +
                    "        and Annonce.Id_edition = editions.IdEd " +
                    "        and offre.IdOffre = annonce.Id_offre " +
                    "        AND demandeur.CodeInterneD = ? ;";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,idDem);
            // Exécution de la requête
            ResultSet resultSet = pst.executeQuery();
            // Parcours des résultats et création d'instances postulation
            while (resultSet.next()) {
                int idpost = resultSet.getInt("IdPost");
                int idoffre = resultSet.getInt("IdOffre");
                int Dem = resultSet.getInt("CodeInterneD");
                int idann = resultSet.getInt("IdAnnonce");
                int idEdition = resultSet.getInt("IdEd");
                String nomD = resultSet.getString("Nom");
                String titre = resultSet.getString("Titre");
                String prenomD = resultSet.getString("Prenom");
                Date dateparution = resultSet.getDate("DateParution");


                // Création d'une instance Postulation
                Postulation postulation = new Postulation(
                        idpost,
                        new Annonce(idann,new Offre(idoffre,titre),new Edition(idEdition,dateparution)),
                        new Demandeur(Dem,nomD,prenomD));

                // Ajout de l'abonnement à la liste observable
                postulations.add(postulation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return postulations;
    }

    //afficher tous posulation
    public static ObservableList<Postulation> afficherPostulations() {
        ObservableList<Postulation> postulations = FXCollections.observableArrayList();

        try (Connection conn = Utilitaire.getConnection()) {
            // Requête SQL pour sélectionner tous les Postulations
            String query = "SELECT IdPost,IdOffre,Titre,CodeInterneD,IdAnnonce,IdEd,DateParution,Nom,Prenom from postulation natural join annonce natural join offre natural join demandeur natural join editions where postulation.IdAnn = annonce.IdAnnonce and postulation.IdDem = demandeur.CodeInterneD and Annonce.Id_edition = editions.IdEd and offre.IdOffre = annonce.Id_offre;";
            Statement statement = conn.createStatement();

            // Exécution de la requête
            ResultSet resultSet = statement.executeQuery(query);

            // Parcours des résultats et création d'instances postulation
            while (resultSet.next()) {
                int idpost = resultSet.getInt("IdPost");
                int idoffre = resultSet.getInt("IdOffre");
                int Dem = resultSet.getInt("CodeInterneD");
                int idann = resultSet.getInt("IdAnnonce");
                int idEdition = resultSet.getInt("IdEd");
                String nomD = resultSet.getString("Nom");
                String titre = resultSet.getString("Titre");
                String prenomD = resultSet.getString("Prenom");
                Date dateparution = resultSet.getDate("DateParution");


                // Création d'une instance Postulation
                Postulation postulation = new Postulation(
                        idpost,
                        new Annonce(idann,new Offre(idoffre,titre),new Edition(idEdition,dateparution)),
                        new Demandeur(Dem,nomD,prenomD));

                // Ajout de l'abonnement à la liste observable
                postulations.add(postulation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return postulations;
    }

    //afficher les posulation d un entreprise
    public static ObservableList<Postulation> afficherPostulationsOfEntreprise(int idEst) {
        ObservableList<Postulation> postulations = FXCollections.observableArrayList();

        try (Connection conn = Utilitaire.getConnection()) {
            // Requête SQL pour sélectionner tous les Postulations
            String query = " SELECT IdPost,IdOffre,Titre,CodeInterneD,IdAnnonce,IdEd,DateParution,Nom,Prenom " +
                    "   from " +
                    "        postulation natural join annonce " +
                    "        natural join offre natural join " +
                    "        demandeur natural join  editions " +
                    "    where " +
                    "        postulation.IdAnn = annonce.IdAnnonce " +
                    "        and postulation.IdDem = demandeur.CodeInterneD " +
                    "        and Annonce.Id_edition = editions.IdEd " +
                    "        and offre.IdOffre = annonce.Id_offre " +
                    "        AND offre.Id_Es = ? ;";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,idEst);
            // Exécution de la requête
            ResultSet resultSet = pst.executeQuery();
            // Parcours des résultats et création d'instances postulation
            while (resultSet.next()) {
                int idpost = resultSet.getInt("IdPost");
                int idoffre = resultSet.getInt("IdOffre");
                int Dem = resultSet.getInt("CodeInterneD");
                int idann = resultSet.getInt("IdAnnonce");
                int idEdition = resultSet.getInt("IdEd");
                String nomD = resultSet.getString("Nom");
                String titre = resultSet.getString("Titre");
                String prenomD = resultSet.getString("Prenom");
                Date dateparution = resultSet.getDate("DateParution");


                // Création d'une instance Postulation
                Postulation postulation = new Postulation(
                        idpost,
                        new Annonce(idann,new Offre(idoffre,titre),new Edition(idEdition,dateparution)),
                        new Demandeur(Dem,nomD,prenomD));

                // Ajout de l'abonnement à la liste observable
                postulations.add(postulation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return postulations;
    }


    //ajouterPostulation
    public static void postuler(int idDemandeur, int idAnnonce) throws SQLException {
        Connection conn = Utilitaire.getConnection();
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO postulation (IdPost, IdDem, IdAnn) VALUES (NULL, ?, ?);");
        ps.setInt(1,idDemandeur);
        ps.setInt(2,idAnnonce);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    public static boolean DejaPostuler(int idDemandeur, int idOffre) throws SQLException {
        Connection conn = Utilitaire.getConnection();
        String query = "SELECT COUNT(IdPost) FROM postulation p NATURAL JOIN annonce a NATURAL JOIN offre o " +
                " WHERE p.IdAnn = a.IdAnnonce " +
                "    AND a.Id_offre = o.IdOffre " +
                "    AND p.IdDem = ? " +
                "    AND o.IdOffre = ? ;";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1,idDemandeur);
        ps.setInt(2,idOffre);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int nbr = rs.getInt(1);
        return nbr != 0;
    }
    public static int nombrePostulationOfDemandeur(int idDemandeur) throws SQLException {
        Connection conn = Utilitaire.getConnection();
        String query = " SELECT COUNT(IdPost) FROM postulation p " +
                " WHERE  p.IdDem = ? ; ";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1,idDemandeur);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int nbr = rs.getInt(1);
        return nbr;
    }
}
