package org.example.entities;

public class Categories {
    private int id_categorie;
    private String nom;
    private String description;

    public int getid_categorie() {
        return id_categorie;
    }

    public void setcategory_id(int id) {
        this.id_categorie = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
