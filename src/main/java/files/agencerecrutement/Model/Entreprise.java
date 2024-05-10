package files.agencerecrutement.Model;

import java.util.ArrayList;

public class Entreprise extends  Client{
    private  String raisonSocial ;
    private String Activites ;
    private ArrayList<Offre> Offres;

    public Entreprise(int idClient, String adresse, String phone, String raisonSocial, String activites) {
        super(idClient, adresse, phone);
        this.raisonSocial = raisonSocial;
        Activites = activites;
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
}
