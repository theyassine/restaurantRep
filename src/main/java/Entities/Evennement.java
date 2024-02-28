package Entities;

import java.time.LocalDateTime;

public class Evennement {
    private int id ;
    private int gerant_id;
    private String nom_event;
    private String desc_event ;
    private String date_debut;
    private String date_fin ;
    private String lieu_evenement;
    private int Nbr_participants;
    private String Time_debut ;
    private String Time_fin ;
    private String NameResto;
    private String image_path;

    public Evennement() {
    }

    public Evennement(int id, int gerant_id, String nom_event, String desc_event, String date_debut, String date_fin, String lieu_evenement, int nbr_participants, String time_debut, String time_fin, String nameResto, String image_path) {
        this.id = id;
        this.gerant_id = gerant_id;
        this.nom_event = nom_event;
        this.desc_event = desc_event;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.lieu_evenement = lieu_evenement;
        Nbr_participants = nbr_participants;
        Time_debut = time_debut;
        Time_fin = time_fin;
        NameResto = nameResto;
        this.image_path = image_path;
    }

    public Evennement(int gerant_id, String nom_event, String desc_event, String date_debut, String date_fin, String lieu_evenement, int nbr_participants, String time_debut, String time_fin, String nameResto, String image_path) {
        this.gerant_id = gerant_id;
        this.nom_event = nom_event;
        this.desc_event = desc_event;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.lieu_evenement = lieu_evenement;
        Nbr_participants = nbr_participants;
        Time_debut = time_debut;
        Time_fin = time_fin;
        NameResto = nameResto;
        this.image_path = image_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGerant_id() {
        return gerant_id;
    }

    public void setGerant_id(int gerant_id) {
        this.gerant_id = gerant_id;
    }

    public String getNom_event() {
        return nom_event;
    }

    public void setNom_event(String nom_event) {
        this.nom_event = nom_event;
    }

    public String getDesc_event() {
        return desc_event;
    }

    public void setDesc_event(String desc_event) {
        this.desc_event = desc_event;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public String getLieu_evenement() {
        return lieu_evenement;
    }

    public void setLieu_evenement(String lieu_evenement) {
        this.lieu_evenement = lieu_evenement;
    }

    public int getNbr_participants() {
        return Nbr_participants;
    }

    public void setNbr_participants(int nbr_participants) {
        Nbr_participants = nbr_participants;
    }

    public String getTime_debut() {
        return Time_debut;
    }

    public void setTime_debut(String time_debut) {
        Time_debut = time_debut;
    }

    public String getTime_fin() {
        return Time_fin;
    }

    public void setTime_fin(String time_fin) {
        Time_fin = time_fin;
    }

    public String getNameResto() {
        return NameResto;
    }

    public void setNameResto(String nameResto) {
        NameResto = nameResto;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    @Override
    public String toString() {
        return "Evennement{" +
                "id=" + id +
                ", gerant_id=" + gerant_id +
                ", nom_event='" + nom_event + '\'' +
                ", desc_event='" + desc_event + '\'' +
                ", date_debut='" + date_debut + '\'' +
                ", date_fin='" + date_fin + '\'' +
                ", lieu_evenement='" + lieu_evenement + '\'' +
                ", Nbr_participants=" + Nbr_participants +
                ", Time_debut='" + Time_debut + '\'' +
                ", Time_fin='" + Time_fin + '\'' +
                ", NameResto='" + NameResto + '\'' +
                ", image_path='" + image_path + '\'' +
                '}';
    }
}
