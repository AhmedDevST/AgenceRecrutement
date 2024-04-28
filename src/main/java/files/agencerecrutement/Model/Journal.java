package files.agencerecrutement.Model;

public class Journal {
    private int idJr ;
    private String nomJr ;
    private  String periodicite;
    private String langue ;
    private Categorie categorie;

    public Journal(int idJr, String nomJr, String periodicite, String langue, Categorie categorie) {
        this.idJr = idJr;
        this.nomJr = nomJr;
        this.periodicite = periodicite;
        this.langue = langue;
        this.categorie = categorie;
    }

    public int getIdJr() {
        return idJr;
    }

    public String getNomJr() {
        return nomJr;
    }

    public String getPeriodicite() {
        return periodicite;
    }

    public String getLangue() {
        return langue;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setIdJr(int idJr) {
        this.idJr = idJr;
    }

    public void setNomJr(String nomJr) {
        this.nomJr = nomJr;
    }

    public void setPeriodicite(String periodicite) {
        this.periodicite = periodicite;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}
