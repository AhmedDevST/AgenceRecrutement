package files.agencerecrutement.Model;

public class Postulation {
    private int idPostulation;
    private Annonce annonce;
    private  Demandeur postulant ;

    public Postulation(int idPostulation, Annonce annonce, Demandeur postulant) {
        this.idPostulation = idPostulation;
        this.annonce = annonce;
        this.postulant = postulant;
    }

    public int getIdPostulation() {
        return idPostulation;
    }

    public Annonce getAnnonce() {
        return annonce;
    }

    public Demandeur getPostulant() {
        return postulant;
    }

    public void setIdPostulation(int idPostulation) {
        this.idPostulation = idPostulation;
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }

    public void setPostulant(Demandeur postulant) {
        this.postulant = postulant;
    }
}
