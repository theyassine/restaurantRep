package services;

import entities.Supplement;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplementService implements IService<Supplement> {
    private final Connection connexion;

    public SupplementService() {
        connexion = DataSource.getInstance().getCnx();
    }

    @Override
    public  void add(Supplement supplement) {
        String query = "INSERT INTO supplement (nom, prix) VALUES (?, ?)";

        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            pst.setString(1, supplement.getNom());
            pst.setDouble(2, supplement.getPrix());
            pst.executeUpdate();
            System.out.println("Supplément ajouté avec succès !");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'ajout du supplément: " + ex.getMessage());
        }
    }

    @Override
    public void delete(Supplement supplement) {
        String query = "DELETE FROM supplement WHERE id_supp = ?";

        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            pst.setInt(1, supplement.getId_supp());
            int rowsDeleted = pst.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Supplement supprimé avec succès !");
            } else {
                System.out.println("Aucun supplement trouvé avec cet ID !");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression du supplement: " + ex.getMessage());
        }

    }

    @Override
    public void update(Supplement supplement) {
        String query = "UPDATE supplement SET nom = ?, prix = ? WHERE id_supp = ?";

        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            pst.setString(1, supplement.getNom());
            pst.setDouble(2, supplement.getPrix());
            pst.setInt(3, supplement.getId_supp());

            int updatedRows = pst.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Supplement mis à jour avec succès !");
            } else {
                System.out.println("Aucun supplement trouvé avec l'ID spécifié.");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour du supplement: " + ex.getMessage());
        }
    }

    @Override
    public List<Supplement> readAll() {
        String query = "SELECT * FROM supplement";
        List<Supplement> supplementList = new ArrayList<>();

        try (Statement statement = connexion.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Supplement supplement = new Supplement();
                supplement.setId_supp(resultSet.getInt("id_supp"));
                supplement.setNom(resultSet.getString("nom"));
                supplement.setPrix(resultSet.getDouble("prix"));
                // Supplement might not have an 'image' field, consider your entity
                // supplement.setImage(resultSet.getString("image"));
                supplementList.add(supplement);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la lecture de tous les supplements: " + e.getMessage());
        }
        return supplementList;
    }

    @Override
    public Supplement readById(int id) {
        String query = "SELECT * FROM supplement WHERE id_supp = ?";
        Supplement supplement = null;

        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet resultSet = pst.executeQuery()) {
                if (resultSet.next()) {
                    supplement = new Supplement();
                    supplement.setId_supp(resultSet.getInt("id_supp"));
                    supplement.setNom(resultSet.getString("nom"));
                    supplement.setPrix(resultSet.getDouble("prix"));
                    // supplement.setImage(resultSet.getString("image"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la lecture du supplement par ID: " + e.getMessage());
        }
        return supplement;
    }
}
