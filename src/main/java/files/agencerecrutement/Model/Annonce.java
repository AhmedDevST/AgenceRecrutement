package files.agencerecrutement.Model;

public class Annonce {
    private int idAnnonce ;
    private Offre offre;
    private  Edition edition;

    public Annonce(int idAnnonce, Offre offre, Edition edition) {
        this.idAnnonce = idAnnonce;
        this.offre = offre;
        this.edition = edition;
    }

    public int getIdAnnonce() {
        return idAnnonce;
    }

    public Offre getOffre() {
        return offre;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setIdAnnonce(int idAnnonce) {
        this.idAnnonce = idAnnonce;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }
}
