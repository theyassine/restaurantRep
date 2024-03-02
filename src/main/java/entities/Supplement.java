package entities;

public class Supplement {
    private int id_supp;
    private String nom;
    private double prix;
    private String image;

    public Supplement(int id_supp, String nom, double prix, String image) {
        this.id_supp = id_supp;
        this.nom = nom;
        this.prix = prix;
        this.image = image;
    }

    public Supplement(String nom, double prix, String image) {
        this.nom = nom;
        this.prix = prix;
        this.image = image;
    }

    public Supplement() {

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
