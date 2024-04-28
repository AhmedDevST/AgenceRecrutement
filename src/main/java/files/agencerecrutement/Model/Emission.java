package files.agencerecrutement.Model;

import java.util.Date;

public class Emission {
    private int idEmission;
    private Date dateEmission ;
    private Abonnement aboUtilise;
    private Offre offreEmis;

    public Emission(int idEmission, Date dateEmission, Abonnement aboUtilise, Offre offreEmis) {
        this.idEmission = idEmission;
        this.dateEmission = dateEmission;
        this.aboUtilise = aboUtilise;
        this.offreEmis = offreEmis;
    }

    public int getIdEmission() {
        return idEmission;
    }

    public Date getDateEmission() {
        return dateEmission;
    }

    public Abonnement getAboUtilise() {
        return aboUtilise;
    }

    public Offre getOffreEmis() {
        return offreEmis;
    }

    public void setIdEmission(int idEmission) {
        this.idEmission = idEmission;
    }

    public void setDateEmission(Date dateEmission) {
        this.dateEmission = dateEmission;
    }

    public void setAboUtilise(Abonnement aboUtilise) {
        this.aboUtilise = aboUtilise;
    }

    public void setOffreEmis(Offre offreEmis) {
        this.offreEmis = offreEmis;
    }
}
