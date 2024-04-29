package files.agencerecrutement.Model;

import java.util.ArrayList;

public class Entreprise extends  Client{
    private String NomEntreprise ;
    private  String raisonSocial ;
    private String Activites ;
    private ArrayList<Offre> Offres;

    public Entreprise(int idClient, String adresse, String phone,String NomEntreprise, String raisonSocial, String activites) {
        super(idClient, adresse, phone);
        this.NomEntreprise = NomEntreprise;
        this.raisonSocial = raisonSocial;
        Activites = activites;
    }
    public String getNomEntreprise() {return NomEntreprise;}

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
