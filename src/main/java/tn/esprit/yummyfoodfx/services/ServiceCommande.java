package tn.esprit.yummyfoodfx.services;


import tn.esprit.yummyfoodfx.entities.Commande;
import tn.esprit.yummyfoodfx.entities.EtatCommande;
import tn.esprit.yummyfoodfx.entities.ModePayement;
import tn.esprit.yummyfoodfx.utils.FilterBadWord;
import tn.esprit.yummyfoodfx.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceCommande implements IService<Commande>{
    private Connection cnx;
    public ServiceCommande(){
        cnx= MyDataBase.getInstance().getCnx();
    }
    @Override
    public void ajouter(Commande commande) {
        Date sqlDate=new Date(commande.getDateCommande().getTime());
        try{
            String query="INSERT INTO `commande`" +
                    "(`idClient`, `idMenu`, `adresse`," +
                    " `longitude`, `latitude`, `dateCommande`," +
                    " `etatCommande`, `modePayement`," +
                    " `remarque`) VALUES " +
                    "(?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst=cnx.prepareStatement(query);
            pst.setInt(1,commande.getIdClient());
            pst.setInt(2,commande.getIdMenu());
            pst.setString(3,commande.getAdresse());
            pst.setDouble(4,commande.getLongitude());
            pst.setDouble(5,commande.getLatitude());
            pst.setDate(6,sqlDate);
            pst.setString(7,commande.getEtatCommande().toString());
            pst.setString(8,commande.getModePayement().toString());
            pst.setString(9, FilterBadWord.filter(commande.getRemarque()));
            pst.executeUpdate();
        }catch (SQLException e){
            System.out.println("erreur:"+e.getMessage());
        }


    }

    @Override
    public void modifier(Commande commande) {
        Date sqlDate=new Date(commande.getDateCommande().getTime());
        try{
            String query="UPDATE `commande` SET " +
                    "`idClient`=?,`idMenu`=?," +
                    "`adresse`=?,`longitude`=?," +
                    "`latitude`=?,`dateCommande`=?," +
                    "`etatCommande`=?,`modePayement`=?," +
                    "`remarque`=? WHERE id=?";
            PreparedStatement pst=cnx.prepareStatement(query);
            pst.setInt(1,commande.getIdClient());
            pst.setInt(2,commande.getIdMenu());
            pst.setString(3,commande.getAdresse());
            pst.setDouble(4,commande.getLongitude());
            pst.setDouble(5,commande.getLatitude());
            pst.setDate(6,sqlDate);
            pst.setString(7,commande.getEtatCommande().toString());
            pst.setString(8,commande.getModePayement().toString());
            pst.setString(9, FilterBadWord.filter(commande.getRemarque()));
            pst.setInt(10,commande.getId());
            pst.executeUpdate();
        }catch (SQLException e){
            System.out.println("erreur:"+e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try{
            String query="DELETE FROM `commande` WHERE id=?";
            PreparedStatement pst=cnx.prepareStatement(query);
            pst.setInt(1,id);
            pst.executeUpdate();
        }catch (SQLException e){
            System.out.println("erreur:"+e.getMessage());
        }
    }

    @Override
    public List<Commande> afficher() {
        List<Commande> lc=new ArrayList<>();
        try{
            String query="SELECT * FROM `commande`";
            Statement st= cnx.createStatement();
            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                Commande c=new Commande();
                c.setId(rs.getInt("id"));
                c.setAdresse(rs.getString("adresse"));
                c.setLatitude(rs.getDouble("latitude"));
                c.setIdClient(rs.getInt("idClient"));
                c.setRemarque(rs.getString("remarque"));
                c.setLongitude(rs.getDouble("longitude"));
                c.setDateCommande(rs.getDate("dateCommande"));
                c.setEtatCommande(EtatCommande.valueOf(rs.getString("etatCommande")));
                c.setModePayement(ModePayement.valueOf(rs.getString("modePayement")));
                c.setIdMenu(rs.getInt("idMenu"));
                lc.add(c);
            }
        }catch (SQLException e){
            System.out.println("erreur:"+e.getMessage());
        }

        return lc;
    }
    public Commande getCommandeById(int id){
        return afficher().stream().filter(c1->c1.getId()==id).findAny().orElse(null);

    }
    public List<Commande> getCommandeParIdUser(int idUser){
        return afficher().stream().filter(c->c.getIdClient()==idUser).collect(Collectors.toList());
    }

    public void changeCommandeEtat(EtatCommande etatCommande,int idCommande){
        Commande c=getCommandeById(idCommande);
        if(c!=null){
            c.setEtatCommande(etatCommande);
            modifier(c);
        }
    }
    public List<Commande> triCommandeParCritere(String critere){
        switch (critere){
            case "Adresse":
                return afficher().stream().sorted(Comparator.comparing(Commande::getAdresse)).collect(Collectors.toList());
            case "Etat commande":
                return afficher().stream().sorted(Comparator.comparing(Commande::getEtatCommande)).collect(Collectors.toList());
            case "Date commande":
                return afficher().stream().sorted(Comparator.comparing(Commande::getDateCommande)).collect(Collectors.toList());
        }
        return afficher();
    }
}
