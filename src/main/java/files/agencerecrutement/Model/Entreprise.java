package files.agencerecrutement.Model;

import java.util.ArrayList;

public class Entreprise extends  Client{
    private  String raisonSocial ;
    private String Activites ;
    private ArrayList<Offre> Offres;


    public Entreprise(int idUser, String adresse, String phone, String raisonSocial, String activites) {
        super(idUser, adresse, phone);
        this.raisonSocial = raisonSocial;
        Activites = activites;
    }

    public Entreprise(int idUser, String userName, String password, String adresse, String phone, String raisonSocial, String activites) {
        super(idUser, userName, password, adresse, phone);
        this.raisonSocial = raisonSocial;
        Activites = activites;
    }

    public Entreprise(int idUser, String raisonSocial) {
        super(idUser);
        this.raisonSocial = raisonSocial;
    }

    public String getRaisonSocial() {
        return raisonSocial;
    }

    public String getActivites() {
        return Activites;
    }

    public void setRaisonSocial(String raisonSocial) {
        this.raisonSocial = raisonSocial;
    }

    public void setActivites(String activites) {
        Activites = activites;
    }

    @Override
    public String toString() {
        return  raisonSocial ;
    }
}
