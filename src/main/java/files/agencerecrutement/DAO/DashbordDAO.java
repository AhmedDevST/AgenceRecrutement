package files.agencerecrutement.DAO;


import files.agencerecrutement.Model.Categorie;
import files.agencerecrutement.Model.Edition;
import files.agencerecrutement.Model.Journal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DashbordDAO {

    //Nombre Total Entreprise
    public static  int NombreTotalEntreprise(){
        try{
            int nb = 0;
            Connection con = Utilitaire.getConnection();
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery("SELECT COUNT(CodeInterneEs) as CountEst FROM entreprise;");
           res.next();
           nb = res.getInt("CountEst");
           return  nb;
        }catch (Exception ex){
            return 0;
        }
    }
    //Nombre Total Demandeur
    public static  int NombreTotalDemandeur(){
        try{
            int nb = 0;
            Connection con = Utilitaire.getConnection();
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery("SELECT COUNT(CodeInterneD) as CountD FROM demandeur;");
            res.next();
            nb = res.getInt("CountD");
            return  nb;
        }catch (Exception ex){
            return 0;
        }
    }
    //Nombre Total Journal
    public static  int NombreTotalJournal(){
        try{
            int nb = 0;
            Connection con = Utilitaire.getConnection();
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery("SELECT COUNT(CodeJr) as CountJr FROM journal;");
            res.next();
            nb = res.getInt("CountJr");
            return  nb;
        }catch (Exception ex){
            return 0;
        }
    }
    //Nombre Total categorie
    public static  int NombreTotalCategorie(){
        try{
            int nb = 0;
            Connection con = Utilitaire.getConnection();
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery("SELECT COUNT(IdCat) as CountCat FROM categorie;");
            res.next();
            nb = res.getInt("CountCat");
            return  nb;
        }catch (Exception ex){
            return 0;
        }
    }

    //Nombre Total postulation
    public static  int NombreTotalPostulation(){
        try{
            int nb = 0;
            Connection con = Utilitaire.getConnection();
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery("SELECT COUNT(IdPost) as CountPost FROM postulation;");
            res.next();
            nb = res.getInt("CountPost");
            return  nb;
        }catch (Exception ex){
            return 0;
        }
    }

    //Nombre Total recrutemnt
    public static  int NombreTotalRecrutement(){
        try{
            int nb = 0;
            Connection con = Utilitaire.getConnection();
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery("SELECT COUNT(IdOff) as CountRect FROM recrutement;");
            res.next();
            nb = res.getInt("CountRect");
            return  nb;
        }catch (Exception ex){
            return 0;
        }
    }

    //Nombre Total offre
    public static  int NombreTotalOffre(){
        try{
            int nb = 0;
            Connection con = Utilitaire.getConnection();
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery("SELECT COUNT(IdOffre) as CountOffre FROM offre;");
            res.next();
            nb = res.getInt("CountOffre");
            return  nb;
        }catch (Exception ex){
            return 0;
        }
    }

    //Nombre Total abonnement
    public static  int NombreTotalAbonnement(){
        try{
            int nb = 0;
            Connection con = Utilitaire.getConnection();
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery("SELECT COUNT(IDAbon) as CountAbo FROM abonnement;");
            res.next();
            nb = res.getInt("CountAbo");
            return  nb;
        }catch (Exception ex){
            return 0;
        }
    }

    //le 5 plus entreprise abonnee
    public static ObservableList<PieChart.Data> Top5EntrepriseAbonne() throws SQLException {
        ObservableList<PieChart.Data> pieChartData  = FXCollections.observableArrayList();

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        Statement st = con.createStatement();
        ResultSet res = st.executeQuery("SELECT RaisonSocial, COUNT(abonnement.IDAbon) AS NombreAbonnements " +
                " FROM entreprise " +
                " JOIN abonnement ON entreprise.CodeInterneEs = abonnement.IdEs " +
                " GROUP BY entreprise.RaisonSocial " +
                " ORDER BY NombreAbonnements DESC " +
                " LIMIT 5;");

        // Remplir la liste
        while (res.next()) {
            PieChart.Data data = new PieChart.Data(res.getString("RaisonSocial"),res.getInt("NombreAbonnements"));
            pieChartData.add(data);
        }

        // Fermer les ressources
        st.close();
        con.close();

        return pieChartData;
    }

    //le 5 plus entreprise recruter
    public static ObservableList<PieChart.Data> Top5EntrepriseRecruter() throws SQLException {
        ObservableList<PieChart.Data> pieChartData  = FXCollections.observableArrayList();

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        Statement st = con.createStatement();
        ResultSet res = st.executeQuery("SELECT RaisonSocial, COUNT(recrutement.IdOff) AS NombreRecrutements " +
                "                    FROM entreprise  " +
                "                    JOIN offre ON entreprise.CodeInterneEs = offre.Id_Es  " +
                "                    JOIN recrutement ON offre.IdOffre = recrutement.IdOff  " +
                "                    GROUP BY RaisonSocial  " +
                "                    ORDER BY NombreRecrutements DESC  " +
                "                    LIMIT 5;");

        // Remplir la liste
        while (res.next()) {
            PieChart.Data data = new PieChart.Data(res.getString("RaisonSocial"),res.getInt("NombreRecrutements"));
            pieChartData.add(data);
        }

        // Fermer les ressources
        st.close();
        con.close();

        return pieChartData;
    }

    //le 5 plus demandeur postuler
    public static ObservableList<PieChart.Data> Top5DEmandeurPostuler() throws SQLException {
        ObservableList<PieChart.Data> pieChartData  = FXCollections.observableArrayList();

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        Statement st = con.createStatement();
        ResultSet res = st.executeQuery("SELECT Nom, Prenom, COUNT(IdPost) AS NombrePostulations " +
                "        FROM demandeur " +
                "        JOIN postulation ON CodeInterneD = IdDem  " +
                "        GROUP BY Nom , Prenom " +
                "        ORDER BY NombrePostulations DESC " +
                "        LIMIT 5;");

        // Remplir la liste
        while (res.next()) {
            String NomComplet = res.getString(1) +" " + res.getString(2);
            PieChart.Data data = new PieChart.Data(NomComplet,res.getInt("NombrePostulations"));
            pieChartData.add(data);
        }

        // Fermer les ressources
        st.close();
        con.close();

        return pieChartData;
    }

    //le 5 plus Journal abonnee
    public static ObservableList<PieChart.Data> Top5JournalAbonne() throws SQLException {
        ObservableList<PieChart.Data> pieChartData  = FXCollections.observableArrayList();

        // Obtenir la connexion
        Connection con = Utilitaire.getConnection();

        // Créer le statement
        Statement st = con.createStatement();
        ResultSet res = st.executeQuery("SELECT NomJr, COUNT(abonnement.IDAbon) AS NombreAbonnements " +
                " FROM journal " +
                " JOIN abonnement ON CodeJr = IdJr " +
                " GROUP BY NomJr " +
                " ORDER BY NombreAbonnements DESC " +
                " LIMIT 5;");

        // Remplir la liste
        while (res.next()) {
            PieChart.Data data = new PieChart.Data(res.getString("NomJr"),res.getInt("NombreAbonnements"));
            pieChartData.add(data);
        }

        // Fermer les ressources
        st.close();
        con.close();

        return pieChartData;
    }

}
