package Service;

import Entities.Evennement;
import Entities.IService;
import utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class EvennementService implements IService<Evennement> {
    private Connection cnx;
    private PreparedStatement pst;

    public EvennementService() {
        cnx = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(Evennement evennement) {
        String query = "INSERT INTO `evennement` (`gerant_id`, `nom_event`, `desc_event`, `date_debut`, `date_fin`, `lieu_evenement`, `Nbr_participants`, `Time_debut`, `Time_fin`, `NameResto`,`image_path`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        try {
            if (isIdExists("gerant", "id", evennement.getGerant_id())) {
                pst = cnx.prepareStatement(query);
                pst.setInt(1, evennement.getGerant_id());
                pst.setString(2, evennement.getNom_event());
                pst.setString(3, evennement.getDesc_event());
                pst.setString(4, evennement.getDate_debut());
                pst.setString(5, evennement.getDate_fin());
                pst.setString(6, evennement.getLieu_evenement());
                pst.setInt(7, evennement.getNbr_participants());
                pst.setString(8, evennement.getTime_debut());
                pst.setString(9, evennement.getTime_fin());
                pst.setString(10, evennement.getNameResto());
                pst.setString(11, evennement.getImage_path());
                pst.executeUpdate();
            } else {
                System.out.println("Invalid gerant_id");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(Evennement evennement) {
        String query = "DELETE FROM `evennement` WHERE `id` = ?";
        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, evennement.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(Evennement evennement) {
        String query = "UPDATE `evennement` SET `gerant_id`=?, `nom_event`=?, `desc_event`=?, `date_debut`=?, `date_fin`=?, `lieu_evenement`=?, `Nbr_participants`=?, `Time_debut`=?, `Time_fin`=?, `NameResto`=?, `image_path`=? WHERE `id`=?";
        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, evennement.getGerant_id());
            pst.setString(2, evennement.getNom_event());
            pst.setString(3, evennement.getDesc_event());
            pst.setString(4, evennement.getDate_debut());
            pst.setString(5, evennement.getDate_fin());
            pst.setString(6, evennement.getLieu_evenement());
            pst.setInt(7, evennement.getNbr_participants());
            pst.setString(8, evennement.getTime_debut());
            pst.setString(9, evennement.getTime_fin());
            pst.setString(10, evennement.getNameResto());
            pst.setString(11, evennement.getImage_path());
            pst.setInt(12, evennement.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Evennement> readAll() {
        String query = "SELECT * FROM `evennement`";
        List<Evennement> list = new ArrayList<>();
        try {
            pst = cnx.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Evennement evennement = new Evennement(
                        rs.getInt("id"),
                        rs.getInt("gerant_id"),
                        rs.getString("nom_event"),
                        rs.getString("desc_event"),
                        rs.getString("date_debut"),
                        rs.getString("date_fin"),
                        rs.getString("lieu_evenement"),
                        rs.getInt("Nbr_participants"),
                        rs.getString("Time_debut"),
                        rs.getString("Time_fin"),
                        rs.getString("NameResto"),
                        rs.getString("image_path")
                );
                list.add(evennement);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Evennement readById(int id) {
        String query = "SELECT * FROM `evennement` WHERE `id`=?";
        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Evennement evennement = new Evennement(
                        rs.getInt("id"),
                        rs.getInt("gerant_id"),
                        rs.getString("nom_event"),
                        rs.getString("desc_event"),
                        rs.getString("date_debut"),
                        rs.getString("date_fin"),
                        rs.getString("lieu_evenement"),
                        rs.getInt("Nbr_participants"),
                        rs.getString("Time_debut"),
                        rs.getString("Time_fin"),
                        rs.getString("NameResto"),
                        rs.getString("image_path")
                );

                return evennement;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // Helper method to check if the given id exists in a table
    private boolean isIdExists(String tableName, String idColumnName, int id) {
        String query = "SELECT COUNT(*) FROM `" + tableName + "` WHERE `" + idColumnName + "` = ?";
        try {
            PreparedStatement checkStmt = cnx.prepareStatement(query);
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
