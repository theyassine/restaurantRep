package tn.esprit.yummyfoodfx.entities;

public class Livraison {
    private int id;
    private int idCommande;
    private int idLivreur;
    private int heureDepart;
    private EtatLivraison etatLivraison;

    private String commentairesLivreur;

    public Livraison() {
    }

    public Livraison(int idCommande, int idLivreur, int heureDepart, EtatLivraison etatLivraison, String commentairesLivreur) {
        this.idCommande = idCommande;
        this.idLivreur = idLivreur;
        this.heureDepart = heureDepart;
        this.etatLivraison = etatLivraison;
        this.commentairesLivreur = commentairesLivreur;
    }

    public Livraison(int id, int idCommande, int idLivreur, int heureDepart,  EtatLivraison etatLivraison, String commentairesLivreur) {
        this.id = id;
        this.idCommande = idCommande;
        this.idLivreur = idLivreur;
        this.heureDepart = heureDepart;
        this.etatLivraison = etatLivraison;
        this.commentairesLivreur = commentairesLivreur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getIdLivreur() {
        return idLivreur;
    }

    public void setIdLivreur(int idLivreur) {
        this.idLivreur = idLivreur;
    }

    public int getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(int heureDepart) {
        this.heureDepart = heureDepart;
    }



    public EtatLivraison getEtatLivraison() {
        return etatLivraison;
    }

    public void setEtatLivraison(EtatLivraison etatLivraison) {
        this.etatLivraison = etatLivraison;
    }

    public String getCommentairesLivreur() {
        return commentairesLivreur;
    }

    public void setCommentairesLivreur(String commentairesLivreur) {
        this.commentairesLivreur = commentairesLivreur;
    }

    @Override
    public String toString() {
        return "Livraison{" +
                "id=" + id +
                ", idCommande=" + idCommande +
                ", idLivreur=" + idLivreur +
                ", heureDepart=" + heureDepart +
                ", etatLivraison=" + etatLivraison +
                ", commentairesLivreur='" + commentairesLivreur + '\'' +
                '}'+"\n";
    }
}
