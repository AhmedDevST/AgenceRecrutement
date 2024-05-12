package files.agencerecrutement.DAO;
import files.agencerecrutement.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class PostulationDAO {
    //ajouterPostulation
    //select postulation
    //list des postulation
    //list des postulation d un demandeur


    public static void AjouterPostulation(int idp, Annonce an, Demandeur post) throws SQLException{
        Connection conn = Utilitaire.getConnection();
        try{
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Postulation(IdPost,IdDem,IdAnn) INTO VALUES (NULL,?,?)");
            ps.setInt(1,idp);
            ps.setInt(2,an.getIdAnnonce());
            ps.setInt(3,post.getIdClient());
            ps.executeUpdate();
            ps.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


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
    public boolean isOffreActivé(Postulation p){
        if(p.getAnnonce().getOffre().isEtatOffre()){
            return true;
        }else{
            return false;
        }
    }



}
