package tn.esprit.yummyfoodfx.entities;

import java.util.Date;

public class Commande {
    private int id;
    private int idClient;
    private int idMenu;
    private String adresse;
    private double longitude;
    private double latitude;
    private Date dateCommande;
    private EtatCommande etatCommande;
    private ModePayement modePayement;
    private String remarque;

    public Commande() {
    }

    public Commande(int idClient, int idMenu, String adresse, double longitude, double latitude, Date dateCommande, EtatCommande etatCommande, ModePayement modePayement, String remarque) {
        this.idClient = idClient;
        this.idMenu = idMenu;
        this.adresse = adresse;
        this.longitude = longitude;
        this.latitude = latitude;
        this.dateCommande = dateCommande;
        this.etatCommande = etatCommande;
        this.modePayement = modePayement;
        this.remarque = remarque;
    }

    public Commande(int id, int idClient, int idMenu, String adresse, double longitude, double latitude, Date dateCommande, EtatCommande etatCommande, ModePayement modePayement, String remarque) {
        this.id = id;
        this.idClient = idClient;
        this.idMenu = idMenu;
        this.adresse = adresse;
        this.longitude = longitude;
        this.latitude = latitude;
        this.dateCommande = dateCommande;
        this.etatCommande = etatCommande;
        this.modePayement = modePayement;
        this.remarque = remarque;
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

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public EtatCommande getEtatCommande() {
        return etatCommande;
    }

    public void setEtatCommande(EtatCommande etatCommande) {
        this.etatCommande = etatCommande;
    }

    public ModePayement getModePayement() {
        return modePayement;
    }

    public void setModePayement(ModePayement modePayement) {
        this.modePayement = modePayement;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", idClient=" + idClient +
                ", idMenu=" + idMenu +
                ", adresse='" + adresse + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", dateCommande=" + dateCommande +
                ", etatCommande=" + etatCommande +
                ", modePayement=" + modePayement +
                ", remarque='" + remarque + '\'' +
                '}'+"\n";
    }
}
