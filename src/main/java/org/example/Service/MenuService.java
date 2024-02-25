package org.example.Service;

import org.example.Utils.Data;
import org.example.entities.MSERVICE;
import org.example.entities.Menu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuService implements MSERVICE<Menu> {
    private final Connection connexion;

    public MenuService() {
        connexion = Data.getInstance().getCnx();
    }

    @Override
    public void add(Menu menu) {
        String query = "INSERT INTO menu (nom, prix, description, categories, calories, image, id_user) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            pst.setString(1, menu.getNom());
            pst.setDouble(2, menu.getPrix());
            pst.setString(3, menu.getDescription());
            pst.setString(4, menu.getCategories());
            pst.setInt(5, menu.getCalories());
            pst.setString(6, menu.getImage());
            pst.setInt(7, menu.getId_user());
            pst.executeUpdate();
            System.out.println("Menu ajouté avec succès !");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'ajout du menu: " + ex.getMessage());
        }
    }

    @Override
    public void delete(Menu menu) {
        String query = "DELETE FROM menu WHERE id_menu = ?";

        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            pst.setInt(1, menu.getId());
            int rowsDeleted = pst.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Menu supprimé avec succès !");
            } else {
                System.out.println("Aucun menu trouvé avec cet ID !");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression du menu: " + ex.getMessage());
        }
    }

    @Override
    public void update(Menu menu) {
        String query = "UPDATE menu SET nom = ?, prix = ?, description = ?, categories = ?, calories = ?, image = ? WHERE id_menu = ?";

        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            pst.setString(1, menu.getNom());
            pst.setDouble(2, menu.getPrix());
            pst.setString(3, menu.getDescription());
            pst.setString(4, menu.getCategories());
            pst.setInt(5, menu.getCalories());
            pst.setString(6, menu.getImage());
            pst.setInt(7, menu.getId());

            int updatedRows = pst.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Menu mis à jour avec succès !");
            } else {
                System.out.println("Aucun menu trouvé avec l'ID spécifié.");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour du menu: " + ex.getMessage());
        }
    }

    @Override
    public List<Menu> readAll() {
        String query = "SELECT * FROM menu";
        List<Menu> menuList = new ArrayList<>();

        try (Statement statement = connexion.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Menu menu = new Menu();
                menu.setId(resultSet.getInt("id_menu"));
                menu.setNom(resultSet.getString("nom"));
                menu.setPrix(resultSet.getDouble("prix"));
                menu.setDescription(resultSet.getString("description"));
                menu.setCategories(resultSet.getString("categories"));
                menu.setCalories(resultSet.getInt("calories"));
                menu.setImage(resultSet.getString("image"));
                menuList.add(menu);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la lecture de tous les menus: " + e.getMessage());
        }
        return menuList;
    }

    @Override
    public Menu readById(int id) {
        String query = "SELECT * FROM menu WHERE id_menu = ?";
        Menu menu = null;

        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet resultSet = pst.executeQuery()) {
                if (resultSet.next()) {
                    menu = new Menu();
                    menu.setId(resultSet.getInt("id_menu"));
                    menu.setNom(resultSet.getString("nom"));
                    menu.setPrix(resultSet.getDouble("prix"));
                    menu.setDescription(resultSet.getString("description"));
                    menu.setCategories(resultSet.getString("categories"));
                    menu.setCalories(resultSet.getInt("calories"));
                    menu.setImage(resultSet.getString("image"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la lecture du menu par ID: " + e.getMessage());
        }
        return menu;
    }
}
