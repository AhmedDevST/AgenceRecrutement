package files.agencerecrutement.Model;

public class Offre {
    private  int idOffre ;
    private String titre ;
    private String Competences ;
    private int nbAnneeEx; //le nombre d’années d’expérience demandées
    private int nbPostOfferts ;// le nombre de postes offerts
    private boolean etatOffre ;
    private int nbPostRecruter ; //le nombre de postes recruter
    private Entreprise entreprise;

    public Offre(int idOffre, String titre, String competences, int nbAnneeEx, int nbPostOfferts, boolean etatOffre, int nbPostRecruter, Entreprise entreprise) {
        this.idOffre = idOffre;
        this.titre = titre;
        Competences = competences;
        this.nbAnneeEx = nbAnneeEx;
        this.nbPostOfferts = nbPostOfferts;
        this.etatOffre = etatOffre;
        this.nbPostRecruter = nbPostRecruter;
        this.entreprise = entreprise;
    }

}
