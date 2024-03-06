package tn.esprit.yummyfoodfx.services;

import tn.esprit.yummyfoodfx.entities.Commande;
import tn.esprit.yummyfoodfx.entities.EtatLivraison;
import tn.esprit.yummyfoodfx.entities.Livraison;
import tn.esprit.yummyfoodfx.utils.FilterBadWord;
import tn.esprit.yummyfoodfx.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceLivraison implements IService<Livraison>{
    private Connection cnx;
    public ServiceLivraison(){
        cnx= MyDataBase.getInstance().getCnx();
    }
    @Override
    public void ajouter(Livraison livraison) {
        try {
            String query = "INSERT INTO `livraison` (`idCommande`, `idLivreur`, `heureDepart`, `etatLivraison`, `commentairesLivreur`) VALUES (?,?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, livraison.getIdCommande());
            pst.setInt(2, livraison.getIdLivreur());
            pst.setInt(3, livraison.getHeureDepart());
            pst.setString(4, livraison.getEtatLivraison().toString());
            pst.setString(5, FilterBadWord.filter(livraison.getCommentairesLivreur()));
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("erreur: " + e.getMessage());
        }
    }

    @Override
    public void modifier(Livraison livraison) {
        try {
            String query = "UPDATE `livraison` SET `idCommande`=?, `idLivreur`=?, `heureDepart`=?, `etatLivraison`=?, `commentairesLivreur`=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, livraison.getIdCommande());
            pst.setInt(2, livraison.getIdLivreur());
            pst.setInt(3, livraison.getHeureDepart());
            pst.setString(4, livraison.getEtatLivraison().toString());
            pst.setString(5, FilterBadWord.filter(livraison.getCommentairesLivreur()));
            pst.setInt(6, livraison.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("erreur: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String query = "DELETE FROM `livraison` WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("erreur: " + e.getMessage());
        }
    }

    @Override
    public List<Livraison> afficher() {
        List<Livraison> ll = new ArrayList<>();
        try {
            String query = "SELECT * FROM `livraison`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Livraison l = new Livraison();
                l.setId(rs.getInt("id"));
                l.setIdCommande(rs.getInt("idCommande"));
                l.setIdLivreur(rs.getInt("idLivreur"));
                l.setHeureDepart(rs.getInt("heureDepart"));
                l.setEtatLivraison(EtatLivraison.valueOf(rs.getString("etatLivraison")));
                l.setCommentairesLivreur(rs.getString("commentairesLivreur"));
                ll.add(l);
            }
        } catch (SQLException e) {
            System.out.println("erreur: " + e.getMessage());
        }
        return ll;
    }
    public EtatLivraison getEtatLivraisonDuneCommande(int idCommande){
        for(Livraison l:afficher()){
            if(l.getIdCommande()==idCommande){
                return l.getEtatLivraison();
            }
        }
        return null;

       //return afficher().stream().filter(l->l.getIdCommande()==idCommande).findAny().orElse(null).getEtatLivraison();
    }
    public List<Livraison> getLivraisonParLivreur(int idLivreur){
        return afficher().stream().filter(l->l.getIdLivreur()==idLivreur).collect(Collectors.toList());
    }
    ServiceCommande serviceCommande=new ServiceCommande();
    public List<Commande> afficherLesCommandeDunLivreur(int idLivreur){
        List<Livraison> list=getLivraisonParLivreur(idLivreur);
        List<Commande> commandeList=new ArrayList<>();
        for(Livraison livraison:list){
            Commande c=serviceCommande.getCommandeById(livraison.getIdCommande());
            commandeList.add(c);
        }
        return commandeList;
    }
    public Livraison getLivraisonParCommande(int idCommande){
        return afficher().stream().filter(l->l.getIdCommande()==idCommande).findAny().orElse(null);
    }
}
