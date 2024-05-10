package files.agencerecrutement.Model;

public class Recrutement {
    private Demandeur demandeur;
    private Offre offre;

    public Recrutement(Demandeur demandeur, Offre offre) {
        this.demandeur = demandeur;
        this.offre = offre;
    }

    public Demandeur getDemandeur() {
        return demandeur;
    }

    public Offre getOffre() {
        return offre;
    }

    public void setDemandeur(Demandeur demandeur) {
        this.demandeur = demandeur;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }
}
