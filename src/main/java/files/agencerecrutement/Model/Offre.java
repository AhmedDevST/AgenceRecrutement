package files.agencerecrutement.Model;

public class Offre {
    private  int idOffre ;
    private String titre ;
    private String Competences ;
    private int nbAnneeEx; //le nombre d’années d’expérience demandées
    private int nbPostOfferts ;// le nombre de postes offerts
    private boolean etatOffre ;
    private Entreprise entreprise;

    public Offre(int idOffre, String titre, String competences, int nbAnneeEx, int nbPostOfferts, boolean etatOffre, Entreprise entreprise) {
        this.idOffre = idOffre;
        this.titre = titre;
        Competences = competences;
        this.nbAnneeEx = nbAnneeEx;
        this.nbPostOfferts = nbPostOfferts;
        this.etatOffre = etatOffre;
        this.entreprise = entreprise;
    }

    public Offre(int idOffre, String titre) {
        this.idOffre = idOffre;
        this.titre = titre;
    }
    public Offre(int idOffre, String titre,Entreprise entreprise) {
        this.idOffre = idOffre;
        this.titre = titre;
        this.entreprise = entreprise;
    }

    public int getIdOffre() {
        return idOffre;
    }

    public String getTitre() {
        return titre;
    }

    public String getCompetences() {
        return Competences;
    }

    public int getNbAnneeEx() {
        return nbAnneeEx;
    }

    public int getNbPostOfferts() {
        return nbPostOfferts;
    }

    public boolean isEtatOffre() {
        return etatOffre;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    @Override
    public String toString() {
        return titre + " - "  + entreprise.getRaisonSocial();
    }

    public  String  ReturnEtatString(){
        if(etatOffre)
            return  "Active";
        return "deactive";
    }
}
