package org.example.Service;

import org.example.entities.Avis;
import org.example.entities.Restaurant;
import org.example.entities.User;
import org.example.Utils.Data;
import org.example.entities.role;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AvisService implements services.IService<Avis> {
    private Connection connexion;
    private PreparedStatement pst;
    private Statement ste;

    public AvisService() {
        connexion = Data.getInstance().getCnx();
    }
    @Override
    public void add(Avis avis) {
        String requete = "INSERT INTO Avis (Date, Note, Commentaire, id_user, id_recette) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            Date Datenow = Date.valueOf(LocalDate.now());
            pst = connexion.prepareStatement(requete);
            pst.setDate(1, Datenow);
            pst.setInt(2, avis.getNote());
            pst.setString(3, avis.getCommentaire());
            pst.setInt(4, avis.getIdUser());
            pst.setInt(5, avis.getIdRecette());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(Avis avis) {
        String requete = "DELETE FROM Avis WHERE id_avis = ?";

        try {
            pst = connexion.prepareStatement(requete);
            pst.setInt(1, avis.getIdAvis());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteAvis(int avisId) {
        String requete = "DELETE FROM Avis WHERE id_avis = ?";

        try {
            pst = connexion.prepareStatement(requete);
            pst.setInt(1, avisId);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteAvis(int avisId, int userId) {
        String requete = "DELETE FROM Avis WHERE id_avis = ? AND id_user = ?";

        try {
            pst = connexion.prepareStatement(requete);
            pst.setInt(1, avisId);
            pst.setInt(2, userId);
            int rowCount = pst.executeUpdate();

            if (rowCount == 0) {
                throw new RuntimeException("Unable to delete the review. Either the review doesn't exist or the user does not have permission.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(Avis avis) {

    }

    @Override
    public List<Avis> readAll() {
        String requete = "SELECT * FROM Avis";
        List<Avis> list = new ArrayList<>();

        try {
            ste = connexion.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                list.add(new Avis(
                        rs.getInt("id_avis"),
                        rs.getDate("Date"),
                        rs.getInt("Note"),
                        rs.getString("Commentaire"),
                        rs.getInt("id_user"),
                        rs.getInt("id_recette")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    @Override
    public Avis readById(int id) {
        String requete = "SELECT * FROM Avis WHERE id_avis = ?";
        Avis avis = null;

        try (PreparedStatement pst = connexion.prepareStatement(requete)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                avis = new Avis(
                        rs.getInt("id_avis"),
                        rs.getDate("Date"),
                        rs.getInt("Note"),
                        rs.getString("Commentaire"),
                        rs.getInt("id_user"),
                        rs.getInt("id_recette")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return avis;
    }
    public User getUserById(int userId) {
        String query = "SELECT * FROM User WHERE id = ?";
        User user = null;

        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                user = new User();
                user.setId(rs.getInt("id"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setEmail( rs.getString("email"));
                user.setRole(role.valueOf(rs.getString("role")));

            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception properly in your application
        }

        return user;
    }
    public List<Avis> readAvisByRecetteId(int recetteId) {
        String requete = "SELECT * FROM Avis WHERE id_recette = ?";
        List<Avis> list = new ArrayList<>();

        try (PreparedStatement pst = connexion.prepareStatement(requete)) {
            pst.setInt(1, recetteId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                list.add(new Avis(
                        rs.getInt("id_avis"),
                        rs.getDate("Date"),
                        rs.getInt("Note"),
                        rs.getString("Commentaire"),
                        rs.getInt("id_user"),
                        rs.getInt("id_recette")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public int getTotalRatingForRecipe(int recetteId) {
        String query = "SELECT SUM(Note) AS totalRating FROM Avis WHERE id_recette = ?";
        int totalRating = 0;

        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            pst.setInt(1, recetteId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                totalRating = rs.getInt("totalRating");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return totalRating;
    }
    public Avis readAvisByIdAndRecetteId(int idAvis, int recetteId) {
        String requete = "SELECT * FROM Avis WHERE id_avis = ? AND id_recette = ?";
        Avis avis = null;

        try (PreparedStatement pst = connexion.prepareStatement(requete)) {
            pst.setInt(1, idAvis);
            pst.setInt(2, recetteId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                avis = new Avis(
                        rs.getInt("id_avis"),
                        rs.getDate("Date"),
                        rs.getInt("Note"),
                        rs.getString("Commentaire"),
                        rs.getInt("id_user"),
                        rs.getInt("id_recette")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return avis;
    }
    public void deleteAvisForUser(int idRecette, int idUser) {
        String requete = "DELETE FROM Avis WHERE id_recette = ? AND id_user = ?";

        try (PreparedStatement pst = connexion.prepareStatement(requete)) {
            pst.setInt(1, idRecette);
            pst.setInt(2, idUser);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
