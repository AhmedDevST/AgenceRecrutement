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


    public Client(int idClient){
        IdClient = idClient;
    }
    public int getIdClient(){
        return IdClient;
    }
}
