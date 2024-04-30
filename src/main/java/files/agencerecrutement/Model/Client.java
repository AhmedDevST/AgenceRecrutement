package files.agencerecrutement.Model;

import java.util.ArrayList;

public class Client {
    protected  int IdClient ;
    protected String Adresse ;
    protected  String phone ;
    protected ArrayList<PreferencesClient> preferencesClients;

    public Client(int idClient, String adresse, String phone) {
        IdClient = idClient;
        Adresse = adresse;
        this.phone = phone;
    }

    public int getIdClient() {
        return IdClient;
    }

    public String getAdresse() {
        return Adresse;
    }

    public String getPhone() {
        return phone;
    }

    public void setIdClient(int idClient) {
        IdClient = idClient;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
