package entities;

public class Gerant extends User {
   int restaurant_id;
   int cin;
   int num_telephone;

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public int getCin() {
        return cin;
    }

    public int getNum_telephone() {
        return num_telephone;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public void setNum_telephone(int num_telephone) {
        this.num_telephone = num_telephone;
    }

    public Gerant(int id, String nom, String prenom, String email, String pwd, entities.role role, int restaurant_id, int cin, int num_telephone) {
        super(id, nom, prenom, email, pwd, role);
        this.restaurant_id = restaurant_id;
        this.cin = cin;
        this.num_telephone = num_telephone;
    }

    public Gerant(int restaurant_id, int cin, int num_telephone) {
        this.restaurant_id = restaurant_id;
        this.cin = cin;
        this.num_telephone = num_telephone;
    }

    public Gerant(String nom, String prenom, String email, String pwd, entities.role role, int restaurant_id, int cin, int num_telephone) {
        super(nom, prenom, email, pwd, role);
        this.restaurant_id = restaurant_id;
        this.cin = cin;
        this.num_telephone = num_telephone;
    }

}
