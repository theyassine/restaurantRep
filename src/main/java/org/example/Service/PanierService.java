package org.example.Service;

import org.example.Utils.Data;
import org.example.entities.ISevice;
import org.example.entities.Panier;
import org.example.entities.Restaurant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PanierService implements ISevice<Panier> {
    private Connection connexion;
    private PreparedStatement pst;
    private Statement st;
    public PanierService() {
        connexion= Data.getInstance().getCnx();

    }

    @Override
    public void add(Panier panier) {
        try{
            String query="INSERT INTO `panier`" +
                    "(`id_user`,`nom_produit`,`quantite`," +
                    " `prix`) VALUES " +
                    "(?,?,?,?)";
            PreparedStatement pst=connexion.prepareStatement(query);
            pst.setInt(1,panier.getIdClient());
            pst.setString(2,panier.getNom_produit());
            pst.setString(3,panier.getQuantite());
            pst.setString(4,panier.getPrix());

            pst.executeUpdate();
        }catch (SQLException e){
            System.out.println("erreur:"+e.getMessage());
        }


    }


    @Override
    public void delete(Panier panier) {

    }
    public void supprimer(int id){
        String sql = "DELETE FROM panier WHERE id_panier = ?";
        try {
            PreparedStatement statement = this.connexion.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Panier panier) {
        try {
            String query="UPDATE `panier` SET " +
                    "`id_user`=?,`nom_produit`=?,`quantite`=?," +
                    "`prix`=? WHERE id_panier=?";
            PreparedStatement preparedStatement=connexion.prepareStatement(query);
            preparedStatement.setInt(1,panier.getId());
            preparedStatement.setInt(2,panier.getIdClient());
            preparedStatement.setString(3,panier.getNom_produit());
            preparedStatement.setString(4,panier.getQuantite());
            preparedStatement.setString(5,panier.getPrix());

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println("erreur:"+e.getMessage());
        }

    }



    @Override
    public List<Panier> readAll() {
        List<Panier> lc=new ArrayList<>();
        try{
            String query="SELECT * FROM `panier`";
            Statement st= connexion.createStatement();
            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                Panier c=new Panier();
                c.setId(rs.getInt("id_panier"));
                c.setIdClient(rs.getInt("id_user"));
                c.setNom_produit(rs.getString("nom_produit"));
                c.setQuantite(rs.getString("quantite"));
                c.setPrix(rs.getString("prix"));
                lc.add(c);
            }
        }catch (SQLException e){
            System.out.println("erreur:"+e.getMessage());
        }

        return lc;
    }


    @Override
    public Panier  readById(int id) {
        return null;
    }

    @Override
    public List<Panier> searchByPlace(String place) {
        return null;
    }

    @Override
    public List<Panier> getRestaurantsByCategory(int categoryId) {
        return null;
    }
}
