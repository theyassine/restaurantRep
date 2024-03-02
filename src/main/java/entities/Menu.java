package entities;

public class Menu {
    private int id_menu;
    private String nom;
    private double prix;
    private String description;
    private int calories;
    private String image;

    public Menu(int id_menu, String nom, double prix, String description, int calories, String image) {
        this.id_menu = id_menu;
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.calories = calories;
        this.image = image;
    }

    public Menu(String nom, double prix, String description, int calories, String image) {
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.calories = calories;
        this.image = image;
    }

    public Menu() {

    }



    public int getId_menu() {
        return id_menu;
    }

    public void setId_menu(int id_menu) {
        this.id_menu = id_menu;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id_menu=" + id_menu +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", image='" + image + '\'' +
                '}';
    }
}
