package files.agencerecrutement.Model;

public class Demandeur extends Client {

    private String nom ;
    private  String prenom;
    private  String fax;
    private  int nbAnneeEx ;//le nombre d’années d’expérience
    private  float salaire ;
    private  String diplome ;

    public Demandeur(int idClient, String adresse, String phone, String nom, String prenom, String fax, int nbAnneeEx, float salaire, String diplome) {
        super(idClient, adresse, phone);
        this.nom = nom;
        this.prenom = prenom;
        this.fax = fax;
        this.nbAnneeEx = nbAnneeEx;
        this.salaire = salaire;
        this.diplome = diplome;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getFax() {
        return fax;
    }

    public int getNbAnneeEx() {
        return nbAnneeEx;
    }

    public float getSalaire() {
        return salaire;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setNbAnneeEx(int nbAnneeEx) {
        this.nbAnneeEx = nbAnneeEx;
    }

    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }
}
