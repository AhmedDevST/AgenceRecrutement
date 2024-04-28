package files.agencerecrutement.Model;

public class Edition {
    private int numSequentiel;
    private String dateParution;
    private  Journal journal;

    public Edition(int numSequentiel, String dateParution, Journal journal) {
        this.numSequentiel = numSequentiel;
        this.dateParution = dateParution;
        this.journal = journal;
    }

    public int getNumSequentiel() {
        return numSequentiel;
    }

    public String getDateParution() {
        return dateParution;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setNumSequentiel(int numSequentiel) {
        this.numSequentiel = numSequentiel;
    }

    public void setDateParution(String dateParution) {
        this.dateParution = dateParution;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }
}
