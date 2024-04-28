package files.agencerecrutement.Model;

public class Recrutement {
    private Entreprise entreprise;
    private Postulation postulation;

    public Recrutement(Entreprise entreprise, Postulation postulation) {
        this.entreprise = entreprise;
        this.postulation = postulation;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public Postulation getPostulation() {
        return postulation;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public void setPostulation(Postulation postulation) {
        this.postulation = postulation;
    }
}
