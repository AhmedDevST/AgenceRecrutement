package files.agencerecrutement.DAO;
import files.agencerecrutement.Model.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import static files.agencerecrutement.DAO.Utilitaire.getConnection;

import files.agencerecrutement.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class RecrutementDAO {

    public static ObservableList<Recrutement> afficherRecrutements() {
        ObservableList<Recrutement> recrutements = FXCollections.observableArrayList();

        try (Connection conn = Utilitaire.getConnection()) {
            // Requête SQL pour sélectionner tous les Recrutements
            String query = "SELECT Titre,Nom,Prenom,IdOffre,CodeInterneD,CodeInterneEs,RaisonSocial FROM recrutement NATURAL JOIN demandeur NATURAL JOIN offre JOIN entreprise ON offre.Id_Es = entreprise.CodeInterneEs where recrutement.IdDemd = demandeur.CodeInterneD and recrutement.IdOff = offre.IdOffre;";
            Statement statement = conn.createStatement();

            // Exécution de la requête
            ResultSet resultSet = statement.executeQuery(query);

            // Parcours des résultats et création d'instances recrutement
            while (resultSet.next()) {
                int idoffre = resultSet.getInt("IdOffre");
                int Dem = resultSet.getInt("CodeInterneD");
                int idEs = resultSet.getInt("CodeInterneEs");
                String nomD = resultSet.getString("Nom");
                String titre = resultSet.getString("Titre");
                String prenomD = resultSet.getString("Prenom");
                String NomEs = resultSet.getString("RaisonSocial");



                // Création d'une instance Recrutement
                Recrutement recrutement = new Recrutement(new Demandeur(Dem,nomD,prenomD),new Offre(idoffre,titre,new Entreprise(idEs,NomEs)));

                // Ajout de l'abonnement à la liste observable
                recrutements.add(recrutement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recrutements;
    }


    public static ObservableList<Recrutement> DisplayRecrutementDemandeur(int Id) {
        ObservableList<Recrutement> recrutements = FXCollections.observableArrayList();

        try (Connection conn = Utilitaire.getConnection()) {
            // Requête SQL pour sélectionner tous les Recrutements
            String query = "SELECT select Titre,Nom,Prenom,IdOffre,CodeInterneD,CodeInterneEs,RaisonSocial FROM recrutement NATURAL JOIN demandeur NATURAL JOIN offre JOIN entreprise ON offre.Id_Es = entreprise.CodeInterneEs where recrutement.IdDemd = demandeur.CodeInterneD and recrutement.IdOff = offre.IdOffre and IdDemd = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,Id);

            // Exécution de la requête
            ResultSet resultSet = ps.executeQuery(query);

            // Parcours des résultats et création d'instances recrutement
            while (resultSet.next()) {
                int idoffre = resultSet.getInt("IdOffre");
                int Dem = resultSet.getInt("CodeInterneD");
                int idEs = resultSet.getInt("CodeInterneEs");
                String nomD = resultSet.getString("Nom");
                String titre = resultSet.getString("Titre");
                String prenomD = resultSet.getString("Prenom");
                String NomEs = resultSet.getString("RaisonSocial");



                // Création d'une instance Recrutement
                Recrutement recrutement = new Recrutement(new Demandeur(Dem,nomD,prenomD),new Offre(idoffre,titre,new Entreprise(idEs,NomEs)));

                // Ajout de l'abonnement à la liste observable
                recrutements.add(recrutement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recrutements;
    }
}