package org.example.entities;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public class Panier {
    private int id;
    private int idClient;
    private String nom_produit ;
    private String quantite ;

    private String prix ;

    public Panier(int id, int idClient, String nom_produit, String quantite, String prix) {
        this.id = id;
        this.idClient = idClient;
        this.nom_produit = nom_produit;
        this.quantite = quantite;
        this.prix = prix;
    }

    public Panier() {
    }

    public Panier(int idClient, String nom_produit, String quantite, String prix) {
        this.idClient = idClient;
        this.nom_produit = nom_produit;
        this.quantite = quantite;
        this.prix = prix;
    }
    public StringProperty prixProperty() {
        return new SimpleStringProperty(this.prix);
    }
    public StringProperty quantiteProperty() {
        return new SimpleStringProperty(this.quantite);
    }
    public StringProperty nom_produitProperty() {
        return new SimpleStringProperty(this.nom_produit);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public String getQuantite() {
        return quantite;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "id=" + id +
                ", idClient=" + idClient +
                ", nom_produit='" + nom_produit + '\'' +
                ", quantite='" + quantite + '\'' +
                ", prix='" + prix + '\'' +
                '}';
    }
}
