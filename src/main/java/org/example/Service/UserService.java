package org.example.Service;

import org.example.Utils.Data;
import org.example.entities.User;
import org.example.entities.iservice;
import org.example.entities.role;
import org.mindrot.jbcrypt.BCrypt;
import org.example.Utils.Data;
import org.example.Utils.LocalStorage;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;



public class UserService implements iservice<User> {
    private Connection conn;
    private PreparedStatement pst;
    private Statement ste;

    public UserService(){
        conn= Data.getInstance().getCnx();
    }
    public static User currentUser = new User();
    public static User getCurrentUser() {
        return currentUser;
    }


    @Override
    public void add(User user) {
        String REQUETE ="INSERT INTO user (nom,prenom, email, pwd, role) VALUES (?,?, ?, ?, ?)";

        try {

            pst=conn.prepareStatement(REQUETE);
            pst.setString(1,user.getNom());
            pst.setString(2,user.getPrenom());
            pst.setString(3,user.getEmail());
            String hashedPassword = BCrypt.hashpw(user.getPwd(), BCrypt.gensalt());
            pst.setString(4, hashedPassword);
            pst.setString(5,user.getRole().toString());
            pst.executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e);
        }




    }
    public String login(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ?";
        String result = "failed";
        try {
            PreparedStatement ste = conn.prepareStatement(sql);
            ste.setString(1, email);
            ResultSet rs = ste.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("pwd");
                if (BCrypt.checkpw(password, hashedPassword)) {
                    result = "success";
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setNom(rs.getString("nom"));
                    user.setPrenom(rs.getString("prenom"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(role.valueOf(rs.getString("role")));
                    updateCurrentUser(user);
                    try {
                        LocalStorage localStorage = new LocalStorage();
                        localStorage.writeToStorage(user);
                    } catch (IOException ex) {
                        System.out.println("Failed to initialize storage :" + ex.getMessage());
                        result = "failed init storage :" + ex.getMessage();
                    }
                } else {
                    System.out.println("Mot de passe incorrect");
                    result = "mot de passe incorrect";
                }
            } else {
                System.out.println("Email introuvable");
                result = "email introuvable";
            }
        } catch (SQLException ex) {
            System.out.println("Exception SQL :" + ex.getMessage());
            result = "exception SQL :" + ex.getMessage();
        }
        return result;
    }
    public void updateCurrentUser(User user) {
        currentUser.setId(user.getId());
        currentUser.setNom(user.getNom());
        currentUser.setPrenom(user.getPrenom());
        currentUser.setEmail(user.getEmail());
        currentUser.setPwd(user.getPwd());
        currentUser.setRole(user.getRole());

    }

    @Override
    public void delete(User user) {
        deleteById(user.getId());

    }
    public void deleteById(int id) {
        try{
            String REQUETE="DELETE FROM `user` WHERE id=?";
            PreparedStatement pst=conn.prepareStatement(REQUETE);
            pst.setInt(1,id);
            pst.executeUpdate();
        }catch (SQLException e){
            System.out.println("erreur:"+e.getMessage());
        }

    }


    @Override
    public void update(User user) {
        try{
            String REQUETE="UPDATE `user` SET " +
                    "`id`=?,`nom`=?," +
                    "`prenom`=?,`email`=?," +
                    "`pwd`=?," +
                    "`role`=? WHERE id=?";
            PreparedStatement pst=conn.prepareStatement(REQUETE);
            pst.setInt(1,user.getId());
            pst.setString(2,user.getNom());
            pst.setString(3,user.getPrenom());
            pst.setString(4,user.getEmail());
            pst.setString(5,user.getPwd());
            pst.setString(6,user.getRole().toString());
            pst.setInt(7,user.getId());
            pst.executeUpdate();
            System.out.println("succes");
        }catch (SQLException e){
            System.out.println("erreur:"+e.getMessage());
        }
    }



    @Override
    public List<User> readAll() {
        List<User> list=new ArrayList<>();
        try{
            String REQUETE="SELECT * FROM `user`";
            Statement st= conn.createStatement();
            ResultSet rs=st.executeQuery(REQUETE);
            while(rs.next()){
                User u=new User();
                u.setId(rs.getInt("id"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setEmail(rs.getString("email"));
                u.setPwd(rs.getString("pwd"));
                u.setRole(role.valueOf(rs.getString("role")));

                list.add(u);
            }
        }catch (SQLException e){
            System.out.println("erreur:"+e.getMessage());
        }

        return list;
    }


    @Override
    public User readById(int id) {
        return null;
    }

    public void changePassword(String email, String pwd) {
        String sql = "UPDATE user SET pwd = ? WHERE email = ?";
        try {
            PreparedStatement ste = conn.prepareStatement(sql);
            String hashedPassword = BCrypt.hashpw(pwd, BCrypt.gensalt());
            ste.setString(1, hashedPassword);
            ste.setString(2, email);
            ste.executeUpdate();
            System.out.println("Mot de passe modifié avec succès");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la modification du mot de passe : " + ex.getMessage());
        }
    }
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
        try {
            PreparedStatement ste = conn.prepareStatement(sql);
            ste.setString(1, email);
            ResultSet rs = ste.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la recherche de l'e-mail dans la base de données : " + ex.getMessage());
        }
        return false;
    }
}



