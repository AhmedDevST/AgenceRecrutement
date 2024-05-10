package files.agencerecrutement.Model;

import java.time.LocalDate;
import java.util.Date;

public class Edition {
    private int numSequentiel;
    private Date dateParution;
    private  Journal journal;

    public Edition(int numSequentiel, Date dateParution, Journal journal) {
        this.numSequentiel = numSequentiel;
        this.dateParution = dateParution;
        this.journal = journal;
    }

    public int getNumSequentiel() {
        return numSequentiel;
    }

    public Date getDateParution() {
        return dateParution;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setNumSequentiel(int numSequentiel) {
        this.numSequentiel = numSequentiel;
    }

    public void setDateParution(Date dateParution) {
        this.dateParution = dateParution;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    @Override
    public String toString() {
        return  numSequentiel +" / " + dateParution ;
    }
}
