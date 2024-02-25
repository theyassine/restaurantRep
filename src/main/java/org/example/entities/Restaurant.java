package org.example.entities;

import org.example.Service.RestaurantService;

public class Restaurant extends Categories {
    private int id;
    private int id_categorie;
    private String nom;
    private String Speciality;
    private String telephone;

    private String Description;
    private String Place;

    private String Rate;
    private String image;
    RestaurantService RestaurantService = new RestaurantService();

    public Restaurant() {
    }

    public Restaurant(int id_categorie, String nom, String speciality, String telephone, String description, String place, String rate, String image) {
        this.id_categorie = id_categorie;
        this.nom = nom;
        Speciality = speciality;
        this.telephone = telephone;
        Description = description;
        Place = place;
        Rate = rate;
        // this.image = image;
    }

    public Restaurant(int id, int id_categorie, String nom, String speciality, String telephone, String description, String place, String rate) {
        this.id = id;
        this.id_categorie = id_categorie;
        this.nom = nom;
        Speciality = speciality;
        this.telephone = telephone;
        Description = description;
        Place = place;
        Rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSpeciality() {
        return Speciality;
    }

    public void setSpeciality(String speciality) {
        Speciality = speciality;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }


    public String getRate() {
        return Rate;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public int getid_categorie() {
        return id_categorie;
    }

    public void setid_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;


    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", id_categorie=" + id_categorie +
                ", nom='" + nom + '\'' +
                ", Speciality='" + Speciality + '\'' +
                ", telephone='" + telephone + '\'' +
                ", Description='" + Description + '\'' +
                ", Place='" + Place + '\'' +
                ", Rate='" + Rate + '\'' +
                ", image='" + image + '\'' +
                ", RestaurantService=" + RestaurantService +
                '}';
    }
}
