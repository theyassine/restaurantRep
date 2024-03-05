package org.example.Service;

import org.example.Utils.Data;
import org.example.entities.ISevice;
import org.example.entities.Restaurant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService implements ISevice<Restaurant> {
    private Connection connexion;
    private PreparedStatement pst;
    private Statement st;




    public RestaurantService() {
        connexion= Data.getInstance().getCnx();

    }
    @Override
    public void add(Restaurant restaurant) {
        try{
            String query="INSERT INTO `restaurant`" +
                    "(`id_categorie`,`nom`,`Speciality`," +
                    " `telephone`, `Description`, `Place`," +
                    " `Rate`,`image`) VALUES " +
                    "(?,?,?,?,?,?,?,?)";
            PreparedStatement pst=connexion.prepareStatement(query);
            pst.setInt(1,restaurant.getid_categorie());
            pst.setString(2,restaurant.getNom());
            pst.setString(3,restaurant.getSpeciality());
            pst.setString(4,restaurant.getTelephone());
            pst.setString(5,restaurant.getDescription());
            pst.setString(6,restaurant.getPlace());
            pst.setString(7,restaurant.getRate());
            pst.setString(8,restaurant.getImage());

            pst.executeUpdate();
        }catch (SQLException e){
            System.out.println("erreur:"+e.getMessage());
        }


    }

    @Override
    public void delete(Restaurant restaurant) {

    }

    public void supprimer(int id){
        String sql = "DELETE FROM restaurant WHERE id = ?";
        try {
            PreparedStatement statement = this.connexion.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Restaurant restaurant) {
        try {
                String query="UPDATE `restaurant` SET " +
                "`id_categorie`=?,`nom`=?,`Speciality`=?," +
                "`telephone`=?,`Description`=?," +
                "`Place`=?,`Rate`=? WHERE id=?";

                PreparedStatement preparedStatement=connexion.prepareStatement(query);

            preparedStatement.setInt(1,restaurant.getid_categorie());
            preparedStatement.setString(2, restaurant.getNom());
            preparedStatement.setString(3, restaurant.getSpeciality());
            preparedStatement.setString(4, restaurant.getTelephone());
            preparedStatement.setString(5, restaurant.getDescription());
            preparedStatement.setString(6, restaurant.getPlace());
            preparedStatement.setString(7, restaurant.getRate());
            preparedStatement.setInt(8,restaurant.getId());


            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println("erreur:"+e.getMessage());
        }

    }

    @Override
    public void update1(Restaurant restaurant) {

    }

    @Override
    public List<Restaurant> readAll() {
        List<Restaurant> lc=new ArrayList<>();
        try{
            String query="SELECT * FROM `restaurant`";
            Statement st= connexion.createStatement();
            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                Restaurant c=new Restaurant();
                c.setId(rs.getInt("id"));
                c.setid_categorie(rs.getInt("id_categorie"));
                c.setNom(rs.getString("nom"));
                c.setDescription(rs.getString("Description"));
                c.setSpeciality(rs.getString("Speciality"));
                c.setPlace(rs.getString("Place"));
                c.setTelephone(rs.getString("telephone"));
                c.setRate(rs.getString("Rate"));
                c.setImage(rs.getString("image"));
                lc.add(c);
            }
        }catch (SQLException e){
            System.out.println("erreur:"+e.getMessage());
        }

        return lc;
    }


    @Override

        public Restaurant readById(int id) {
            String requete = "SELECT * FROM `restaurant` WHERE id = ?";
            Restaurant recette = null;

            try (PreparedStatement pst = connexion.prepareStatement(requete)) {
                pst.setInt(1, id);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    recette = new Restaurant(); // Create a new instance of Restaurant
                    recette.setId(rs.getInt("id"));
                    recette.setid_categorie(rs.getInt("id_categorie"));
                    recette.setNom(rs.getString("nom"));
                    recette.setDescription(rs.getString("Description"));
                    recette.setSpeciality(rs.getString("Speciality"));
                    recette.setPlace(rs.getString("Place"));
                    recette.setTelephone(rs.getString("telephone"));
                    recette.setRate(rs.getString("Rate"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return recette;
        }


    public List<Restaurant> searchByPlace(String place) {
        List<Restaurant> resultList = new ArrayList<>();
        try {
            // Préparation de la requête SQL
            String query = "SELECT * FROM restaurant WHERE Place LIKE ?";
            PreparedStatement statement = connexion.prepareStatement(query);
            statement.setString(1, "%" + place + "%");

            // Exécution de la requête
            ResultSet resultSet = statement.executeQuery();

            // Traitement des résultats
            while (resultSet.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(resultSet.getInt("id"));
                restaurant.setNom(resultSet.getString("nom"));
                restaurant.setSpeciality(resultSet.getString("Speciality"));
                restaurant.setTelephone(resultSet.getString("telephone"));
                restaurant.setDescription(resultSet.getString("Description"));
                restaurant.setPlace(resultSet.getString("Place"));
                restaurant.setRate(resultSet.getString("Rate"));
                resultList.add(restaurant);
            }
        } catch (SQLException e) {
            System.out.println("erreur:"+e.getMessage());
        }
        return resultList;
    }


    public List<Restaurant> getRestaurantsByCategory(int id_categorie) {
        List<Restaurant> restaurants = new ArrayList<>();
        String query = "SELECT * FROM restaurant WHERE id_categorie  LIKE ?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(query)) {
            preparedStatement.setInt(1, id_categorie);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Restaurant restaurant = new Restaurant();
                    restaurant.setId(resultSet.getInt("id"));
                    restaurant.setNom(resultSet.getString("nom"));

                    restaurants.add(restaurant);
                }
            }
        } catch (SQLException e) {
            System.out.println("erreur:"+e.getMessage());
        }
        return restaurants;
    }
    public List<Restaurant> display(String filterOption) {
        List<Restaurant> events = new ArrayList<>();
        String sql = "SELECT * FROM restaurant ";

        try {
            PreparedStatement statement = this.connexion.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Restaurant event = new Restaurant();

                Restaurant restaurant = new Restaurant();
                restaurant.setId(resultSet.getInt("id"));
                restaurant.setNom(resultSet.getString("nom"));
                restaurant.setSpeciality(resultSet.getString("Speciality"));
                restaurant.setTelephone(resultSet.getString("telephone"));
                restaurant.setDescription(resultSet.getString("Description"));
                restaurant.setPlace(resultSet.getString("Place"));
                restaurant.setRate(resultSet.getString("Rate"));
                restaurant.setImage(resultSet.getString("image"));
                events.add(event);
            }
        } catch (SQLException var6) {
            System.out.println(var6.getMessage());
        }
        return events;
    }
    public List<Restaurant> searchByNom(String filter) {
        List<Restaurant> filteredRestaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurant WHERE nom LIKE ?";

        try (PreparedStatement statement = connexion.prepareStatement(sql)) {
            statement.setString(1, filter + "%"); // Utilisation du filtre avec le joker % pour rechercher les noms commençant par le filtre
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(resultSet.getInt("id"));
                restaurant.setNom(resultSet.getString("nom"));
                restaurant.setSpeciality(resultSet.getString("Speciality"));
                restaurant.setTelephone(resultSet.getString("telephone"));
                restaurant.setDescription(resultSet.getString("Description"));
                restaurant.setPlace(resultSet.getString("Place"));
                restaurant.setRate(resultSet.getString("Rate"));
                restaurant.setImage(resultSet.getString("image"));
                filteredRestaurants.add(restaurant);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche par nom : " + e.getMessage());
        }

        return filteredRestaurants;
    }
    public int NbrDisTotal() {
        String sql = "SELECT COUNT(*) AS total FROM restaurant";

        try (PreparedStatement statement = connexion.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du calcul du nombre total de restaurants : " + e.getMessage());
        }

        return 0; // Retourne 0 en cas d'erreur ou si aucun restaurant n'est trouvé
    }

    public List<Restaurant> read() {
        List<Restaurant> lc=new ArrayList<>();
        try{
            String query="SELECT * FROM `restaurant`";
            Statement st= connexion.createStatement();
            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                Restaurant c=new Restaurant();

                c.setNom(rs.getString("nom"));
                c.setDescription(rs.getString("Description"));
                c.setSpeciality(rs.getString("Speciality"));
                c.setPlace(rs.getString("Place"));
                c.setTelephone(rs.getString("telephone"));
                c.setRate(rs.getString("Rate"));
                c.setImage(rs.getString("image"));
                lc.add(c);
            }
        }catch (SQLException e){
            System.out.println("erreur:"+e.getMessage());
        }

        return lc;
    }
    public boolean restaurantExists(String nom, String place) {
        String query = "SELECT * FROM restaurant WHERE nom = ? AND Place = ?";
        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            pst.setString(1, nom);
            pst.setString(2, place);
            try (ResultSet resultSet = pst.executeQuery()) {
                return resultSet.next(); // Si un résultat est trouvé, cela signifie qu'un restaurant avec le même nom et lieu existe déjà
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la vérification de l'existence du restaurant : " + e.getMessage());
            return false; // En cas d'erreur, retourne false par défaut
        }
    }


}
