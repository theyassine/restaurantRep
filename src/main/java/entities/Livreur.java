package entities;

public class Livreur extends User{
    int restaurant_id;
    int user_id;
    int commande_id;
    int livraison_id;
    String statut;
    String localisation;
    int rate;
    int cin;

    public Livreur(int restaurant_id, int user_id, int commande_id, int livraison_id, String statut, String localisation, int rate, int cin) {
        this.restaurant_id = restaurant_id;
        this.user_id = user_id;
        this.commande_id = commande_id;
        this.livraison_id = livraison_id;
        this.statut = statut;
        this.localisation = localisation;
        this.rate = rate;
        this.cin = cin;
    }

    public Livreur() {
    }

    public Livreur(String nom, String prenom, String email, String pwd, entities.role role, int restaurant_id, int user_id, int commande_id, int livraison_id, String statut, String localisation, int rate, int cin) {
        super(nom, prenom, email, pwd, role);
        this.restaurant_id = restaurant_id;
        this.user_id = user_id;
        this.commande_id = commande_id;
        this.livraison_id = livraison_id;
        this.statut = statut;
        this.localisation = localisation;
        this.rate = rate;
        this.cin = cin;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getCommande_id() {
        return commande_id;
    }

    public int getLivraison_id() {
        return livraison_id;
    }

    public String getStatut() {
        return statut;
    }

    public String getLocalisation() {
        return localisation;
    }

    public int getRate() {
        return rate;
    }

    public int getCin() {
        return cin;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setCommande_id(int commande_id) {
        this.commande_id = commande_id;
    }

    public void setLivraison_id(int livraison_id) {
        this.livraison_id = livraison_id;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }
}
