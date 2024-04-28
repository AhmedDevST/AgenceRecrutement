package files.agencerecrutement.Model;

public class PreferencesClient {
    private Client client;
    private  Categorie categorie;

    public PreferencesClient(Client client, Categorie categorie) {
        this.client = client;
        this.categorie = categorie;
    }

    public Client getClient() {
        return client;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}
