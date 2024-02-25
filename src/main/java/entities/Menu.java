package entities;

public class Menu {
    private int id_menu;
    private String nom;
    private double prix;
    private String description;
    private String categories;
    private int calories;
    private String image;

    private int id_user;



    public Menu() {
    }

    public Menu(int id_menu, String nom, double prix, String description, String categories, int calories, String image,int id_user ) {
        this.id_menu = id_menu;
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.categories = categories;
        this.calories = calories;
        this.image = image;
        this.id_user=id_user;
    }

    public Menu(String nom, double prix, String description, String categories, int calories, String image) {
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.categories = categories;
        this.calories = calories;
        this.image = image;
    }

    public int getId() {
        return id_menu;
    }

    public void setId(int id_menu) {
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

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
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

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id_menu +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", description='" + description + '\'' +
                ", categories='" + categories + '\'' +
                ", calories=" + calories +
                ", image='" + image + '\'' +
                '}';
    }

    public void setId_menu(int i) {
    }
}

