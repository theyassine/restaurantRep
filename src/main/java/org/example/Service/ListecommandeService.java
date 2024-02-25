package org.example.Service;

import org.example.Utils.Data;
import org.example.entities.Iservlist;
import org.example.entities.Listecommande;
import org.example.entities.ModePayment;

import java.sql.Connection;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ListecommandeService implements Iservlist<Listecommande> {

    private Connection cnx;

    public ListecommandeService() {
        cnx = Data.getInstance().getCnx();
    }

    @Override
    public void ajouter(Listecommande listecommande) {
        try{
            String query="INSERT INTO `listecommd`" +
                    "(`id_user`,`nom_item`,`nom_user`," +
                    " `email_user`, `adresse_user`, `Modepaiment`," +
                    " `nom_resto`) VALUES " +
                    "(?,?,?,?,?,?,?)";
            PreparedStatement pst=cnx.prepareStatement(query);
            pst.setInt(1,listecommande.getIdClient());
            pst.setString(2,listecommande.getNomitem());
            pst.setString(3,listecommande.getNomclient());
            pst.setString(4,listecommande.getEmailclient());
            pst.setString(5,listecommande.getAdresseclient());
            pst.setString(6,listecommande.getModePayment().toString());
            pst.setString(7,listecommande.getNomrestaurant());

            pst.executeUpdate();
        }catch (SQLException e){
            System.out.println("erreur:"+e.getMessage());
        }

    }

    @Override
    public void modifier(Listecommande listecommande) {
        try {
            String query = "UPDATE `listecommd` SET " +
                    "`id_user`=?,`nom_item`=?," +
                    "`nom_user`=?,`email_user`=?," +
                    "`adresse_user`=?,`Modepaiment`=?," +
                    "`nom_resto`=? WHERE id_listecom=?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, listecommande.getIdClient());
            pst.setString(2, listecommande.getNomitem());
            pst.setString(3, listecommande.getNomclient());
            pst.setString(4, listecommande.getEmailclient());
            pst.setString(5, listecommande.getAdresseclient());
            pst.setString(6, listecommande.getModePayment().toString());
            pst.setString(7, listecommande.getNomrestaurant());

            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("erreur:" + e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String query = "DELETE FROM `listecommd` WHERE id_listecom=?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("erreur:" + e.getMessage());
        }
    }

    @Override
    public List<Listecommande> afficher() {
        List<Listecommande> lc = new ArrayList<>();
        try {
            String query = "SELECT * FROM `listecommd`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Listecommande c = new Listecommande();

                c.setId(rs.getInt("id_listecom"));
                c.setIdClient(rs.getInt("id_user"));
                c.setNomitem(rs.getString("nom_item"));
                c.setNomclient(rs.getString("email_user"));
                c.setEmailclient(rs.getString("adresse_user"));
                c.setModePayment(ModePayment.valueOf(rs.getString("Modepaiment")));
                c.setNomrestaurant(rs.getString("nom_resto"));
                lc.add(c);
            }
        } catch (SQLException e) {
            System.out.println("erreur:" + e.getMessage());
        }

        return lc;
    }
}