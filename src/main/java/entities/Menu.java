package entities;

import javafx.beans.property.*;

public class Menu {
    private final IntegerProperty id_menu = new SimpleIntegerProperty();
    private final StringProperty nom = new SimpleStringProperty();
    private final DoubleProperty prix = new SimpleDoubleProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty categories = new SimpleStringProperty();
    private final IntegerProperty calories = new SimpleIntegerProperty();
    private final StringProperty image = new SimpleStringProperty();
    private final IntegerProperty id_user = new SimpleIntegerProperty();

    public Menu() {
    }

    public Menu(int id_menu, String nom, double prix, String description, String categories, int calories, String image, int id_user) {
        this.id_menu.set(id_menu);
        this.nom.set(nom);
        this.prix.set(prix);
        this.description.set(description);
        this.categories.set(categories);
        this.calories.set(calories);
        this.image.set(image);
        this.id_user.set(id_user);
    }

    public int getId_menu() {
        return id_menu.get();
    }

    public IntegerProperty id_menuProperty() {
        return id_menu;
    }

    public void setId_menu(int id_menu) {
        this.id_menu.set(id_menu);
    }

    public String getNom() {
        return nom.get();
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public double getPrix() {
        return prix.get();
    }

    public DoubleProperty prixProperty() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix.set(prix);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getCategories() {
        return categories.get();
    }

    public StringProperty categoriesProperty() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories.set(categories);
    }

    public int getCalories() {
        return calories.get();
    }

    public IntegerProperty caloriesProperty() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories.set(calories);
    }

    public String getImage() {
        return image.get();
    }

    public StringProperty imageProperty() {
        return image;
    }

    public void setImage(String image) {
        this.image.set(image);
    }

    public int getId_user() {
        return id_user.get();
    }

    public IntegerProperty id_userProperty() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user.set(id_user);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id_menu=" + id_menu.get() +
                ", nom='" + nom.get() + '\'' +
                ", prix=" + prix.get() +
                ", description='" + description.get() + '\'' +
                ", categories='" + categories.get() + '\'' +
                ", calories=" + calories.get() +
                ", image='" + image.get() + '\'' +
                ", id_user=" + id_user.get() +
                '}';
    }
}
