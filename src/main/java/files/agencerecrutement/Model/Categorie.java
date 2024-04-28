package files.agencerecrutement.Model;

public class Categorie {
    private int idcate ;
    private  String libelle ;

    public Categorie(int idcate, String libelle) {
        this.idcate = idcate;
        this.libelle = libelle;
    }

    public int getIdcate() {
        return idcate;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setIdcate(int idcate) {
        this.idcate = idcate;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
