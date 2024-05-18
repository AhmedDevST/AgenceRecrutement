package files.agencerecrutement.Model;

import java.time.LocalDate;
import java.util.Date;

public class Abonnement {
    private int idAbo;
    private Entreprise entreprise;
    private  Journal journal;
    private boolean etatAbo ;
    private Date dateExpiration;

    public Abonnement(int idAbo, Entreprise entreprise, Journal journal, boolean etatAbo, Date dateExpiration) {
        this.idAbo = idAbo;
        this.entreprise = entreprise;
        this.journal = journal;
        this.etatAbo = etatAbo;
        this.dateExpiration = dateExpiration;
    }
    public Abonnement(int idAbo,Entreprise entreprise, Journal journal){
        this.idAbo = idAbo;
        this.entreprise = entreprise;
        this.journal = journal;
    }

    public int getIdAbo() {
        return idAbo;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public Journal getJournal() {
        return journal;
    }

    public boolean isEtatAbo() {
        return etatAbo;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public void setIdAbo(int idAbo) {
        this.idAbo = idAbo;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public void setEtatAbo(boolean etatAbo) {
        this.etatAbo = etatAbo;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public  String ReturnEtatString(){
        if(etatAbo) return "activé";
        return  "desactivé";
    }
    @Override
    public String toString() {
        return this.journal.getNomJr();
    }
}
