package org.example.entities;

public class Listecommande {
    private int id;
    private int idClient;
    private String nomrestaurant ;
    private String nomitem ;

    private String nomclient ;

    private String emailclient ;

    private String adresseclient ;

    private ModePayment modePayment;

    public Listecommande() {
    }

    public Listecommande(int id, int idClient, String nomrestaurant, String nomitem, String nomclient, String emailclient, String adresseclient, ModePayment modePayment) {
        this.id = id;
        this.idClient = idClient;
        this.nomrestaurant = nomrestaurant;
        this.nomitem = nomitem;
        this.nomclient = nomclient;
        this.emailclient = emailclient;
        this.adresseclient = adresseclient;
        this.modePayment = modePayment;
    }

    public Listecommande(int idClient, String nomrestaurant, String nomitem, String nomclient, String emailclient, String adresseclient, ModePayment modePayment) {
        this.idClient = idClient;
        this.nomrestaurant = nomrestaurant;
        this.nomitem = nomitem;
        this.nomclient = nomclient;
        this.emailclient = emailclient;
        this.adresseclient = adresseclient;
        this.modePayment = modePayment;
    }

    public Listecommande(String nomrestaurant, String nomitem, String nomclient, String emailclient, String adresseclient, ModePayment modePayment) {
        this.nomrestaurant = nomrestaurant;
        this.nomitem = nomitem;
        this.nomclient = nomclient;
        this.emailclient = emailclient;
        this.adresseclient = adresseclient;
        this.modePayment = modePayment;
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

    public String getNomrestaurant() {
        return nomrestaurant;
    }

    public void setNomrestaurant(String nomrestaurant) {
        this.nomrestaurant = nomrestaurant;
    }

    public String getNomitem() {
        return nomitem;
    }

    public void setNomitem(String nomitem) {
        this.nomitem = nomitem;
    }

    public String getNomclient() {
        return nomclient;
    }

    public void setNomclient(String nomclient) {
        this.nomclient = nomclient;
    }

    public String getEmailclient() {
        return emailclient;
    }

    public void setEmailclient(String emailclient) {
        this.emailclient = emailclient;
    }

    public String getAdresseclient() {
        return adresseclient;
    }

    public void setAdresseclient(String adresseclient) {
        this.adresseclient = adresseclient;
    }

    public ModePayment getModePayment() {
        return modePayment;
    }

    public void setModePayment(ModePayment modePayment) {
        this.modePayment = modePayment;
    }


    @Override
    public String toString() {
        return "Listecommande{" +
                "id=" + id +
                ", idClient=" + idClient +
                ", nomrestaurant='" + nomrestaurant + '\'' +
                ", nomitem='" + nomitem + '\'' +
                ", nomclient='" + nomclient + '\'' +
                ", emailclient='" + emailclient + '\'' +
                ", adresseclient='" + adresseclient + '\'' +
                ", modePayment=" + modePayment +
                '}';
    }
}
