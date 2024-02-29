package entities;

public class Supplement extends Menu {
    private int id_supp;
    private String nom;
    private double prix;
    private String image;
    private int id_user;

    public Supplement() {
    }

    public Supplement(int id_supp, String nom, double prix, String image, int id_user) {
        this.id_supp = id_supp;
        this.nom = nom;
        this.prix = prix;
        this.image = image;
        this.id_user = id_user;
    }



    public int getId_supp() {
        return id_supp;
    }

    public void setId_supp(int id_supp) {
        this.id_supp = id_supp;
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
        return "Supplement{" +
                "id_supp=" + id_supp +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", image='" + image + '\'' +
                '}';
    }
}
