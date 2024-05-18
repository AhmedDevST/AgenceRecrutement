package files.agencerecrutement.Model;

import java.util.ArrayList;

public class Client extends  User {
    protected String Adresse ;
    protected  String phone ;
    protected ArrayList<PreferencesClient> preferencesClients;

    public Client(int idUser, String adresse, String phone) {
        super(idUser);
        Adresse = adresse;
        this.phone = phone;
    }

    public Client(int idUser, String userName, String password, int roleUser, String adresse, String phone) {
        super(idUser, userName, password, roleUser);
        Adresse = adresse;
        this.phone = phone;
    }
    public Client(int idUser, String userName, String password, String adresse, String phone) {
        super(idUser, userName, password);
        Adresse = adresse;
        this.phone = phone;
    }
    public Client(int idUser) {
        super(idUser);
    }
    public String getAdresse() {
        return Adresse;
    }

    public String getPhone() {
        return phone;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
