package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.Service.RestaurantService;
import org.example.entities.Restaurant;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import javafx.embed.swing.JFXPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javafx.embed.swing.SwingNode;

import javafx.scene.Scene;

import javax.swing.*;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class statcontrol {




    @FXML
    private Button btnhome;

    @FXML
    private Button btnstat;

    @FXML
    private Label lbltotale;
    @FXML
    private Button boutonStatistique;


    @FXML
    private TableView<Restaurant> tableViewRestaurants;


    @FXML
    private GridPane gridPaneRestaurantDetails;

    private RestaurantService restaurantService = new RestaurantService();

    private ObservableList<Restaurant> observableRestaurantList;

    public Label title;
    RestaurantService RestaurantService = new RestaurantService();
    Restaurant salle=new Restaurant();


private int SommeDiscipline;

    @FXML
    void initialize() {
        List<Restaurant> restaurantList = restaurantService.readAll();

        // Convertir les notes en nombres décimaux pour le tri
        Collections.sort(restaurantList, (r1, r2) -> {
            double rate1 = extractNumericRate(r1.getRate());
            double rate2 = extractNumericRate(r2.getRate());
            return Double.compare(rate1, rate2);
        });

        observableRestaurantList = FXCollections.observableArrayList(restaurantList);

        // Définir les colonnes du TableView et associer les propriétés de Restaurant
        TableColumn<Restaurant, String> nameColumn = new TableColumn<>("Nom");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom")); // Assurez-vous que la classe Restaurant a une méthode getName()

        TableColumn<Restaurant, String> addressColumn = new TableColumn<>("Rate");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("Rate"));
        //
        TableColumn<Restaurant, String> placeColumn = new TableColumn<>("Lieu");
        placeColumn.setCellValueFactory(new PropertyValueFactory<>("Place")); // Ass// Assurez-vous que la classe Restaurant a une méthode getAddress()

        // Ajouter les colonnes au TableView
        tableViewRestaurants.getColumns().addAll(nameColumn, addressColumn ,placeColumn);

        // Afficher la liste triée dans le TableView
        tableViewRestaurants.setItems(observableRestaurantList);

        // Gestionnaire d'événements pour le TableView
        tableViewRestaurants.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                afficherDetailsRestaurant(newValue);
            }
        });

        // Afficher le nombre total de restaurants
        lbltotale.setText(String.valueOf(observableRestaurantList.size()));

        // Afficher les statistiques du PieChart

    }

    // Méthode pour extraire la partie numérique de la chaîne de caractères de notation

        private double extractNumericRate(String rate) {
            try {
                // Vérifiez d'abord si la chaîne contient le caractère "/"
                if (rate.contains("/")) {
                    // Diviser la chaîne de notation pour extraire la partie numérique
                    String[] parts = rate.split("/");
                    // Convertir la partie numérique en un nombre décimal
                    double numericRate = Double.parseDouble(parts[0]) / Double.parseDouble(parts[1]);
                    return numericRate;
                } else {
                    // Si la chaîne ne contient pas le caractère "/", retourner 0 ou effectuer toute autre action appropriée
                    return 0.0; // Valeur par défaut si la chaîne ne contient pas le caractère "/"
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                // Gérer les cas où la conversion échoue
                e.printStackTrace();
                return 0.0; // Valeur par défaut si la conversion échoue
            }
        }


    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void home(ActionEvent actionEvent) {

        try {
            Parent root =FXMLLoader.load(getClass().getResource("/Afficher.fxml"));
            btnhome.getScene().setRoot(root);


        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
    @FXML
    private void afficherStatistiquesParLieu(ActionEvent actionEvent) {
        // Récupérer la liste des restaurants depuis le service
        List<Restaurant> restaurantList = restaurantService.readAll();

        // Maintenant, vous avez la liste des restaurants et vous pouvez appeler votre méthode afficherGraphique
        afficherStatistiquesParLieu(restaurantList);
    }
    public void afficherStatistiquesParLieu(List<Restaurant> restaurantList) {
        // Compter le nombre de restaurants par lieu
        Map<String, Long> lieuCounts = restaurantList.stream()
                .collect(Collectors.groupingBy(Restaurant::getPlace, Collectors.counting()));

        // Créer un ensemble de données pour le graphique en barres
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Nombre de restaurants par lieu");

        // Ajouter les données au graphique en barres
        for (Map.Entry<String, Long> entry : lieuCounts.entrySet()) {
            dataSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        // Créer les axes du graphique en barres
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        // Créer le graphique en barres
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Statistiques des restaurants par lieu");
        barChart.getData().add(dataSeries);

        // Créer une nouvelle fenêtre pour afficher le graphique
        Stage stage = new Stage();
        Scene scene = new Scene(barChart, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Statistiques des restaurants par lieu");
        stage.show();

        // Trouver le lieu avec le plus grand nombre de restaurants
        Map.Entry<String, Long> lieuMajoritaire = lieuCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        if (lieuMajoritaire != null) {
            System.out.println("Le lieu avec la majorité des restaurants est : " + lieuMajoritaire.getKey() +
                    " avec " + lieuMajoritaire.getValue() + " restaurants.");
        } else {
            System.out.println("Aucun restaurant trouvé.");
        }
    }


    public void stat(ActionEvent actionEvent) {
    }
    @FXML
    private void afficherGraphique1(ActionEvent actionEvent) {
        // Récupérer la liste des restaurants depuis le service
        List<Restaurant> restaurantList = restaurantService.readAll();

        // Maintenant, vous avez la liste des restaurants et vous pouvez appeler votre méthode afficherGraphique
        afficherGraphique(restaurantList);
    }
    public void afficherGraphique(List<Restaurant> restaurantList) {

        // Créez un ensemble de données par défaut pour le graphique
        DefaultPieDataset dataset = new DefaultPieDataset();

        // Remplissez l'ensemble de données avec les statistiques appropriées
        Map<String, Long> rateCounts = restaurantList.stream()
                .collect(Collectors.groupingBy(Restaurant::getRate, Collectors.counting()));

        rateCounts.forEach((rate, count) -> dataset.setValue(rate, count));

        // Créez le graphique JFreeChart
        JFreeChart chart = ChartFactory.createPieChart(
                "Statistiques des avis du client sur les restaurants",  // Titre du graphique
                dataset,  // Ensemble de données
                true,     // Afficher la légende
                true,     // Activer les outils
                false     // Activer l'URL
        );

        // Créez un ChartPanel pour afficher le graphique
        ChartPanel chartPanel = new ChartPanel(chart);

        // Créez un conteneur SwingNode pour intégrer le graphique JFreeChart dans JavaFX
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(chartPanel);

        // Créez un StackPane comme parent pour le SwingNode
        StackPane root = new StackPane();
        root.getChildren().add(swingNode);

        // Créez une scène JavaFX
        Scene scene = new Scene(root, 800, 600);

        // Créez une nouvelle fenêtre pour afficher le graphique
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Statistiques des avis du client sur les restaurants");
        stage.show();
    }





    private void afficherDetailsRestaurant(Restaurant restaurant) {
        // Effacer les enfants précédents du GridPane
        gridPaneRestaurantDetails.getChildren().clear();

        // Charger le FXML et le contrôleur de la carte du restaurant
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardrecette.fxml"));
        try {
            Parent cardRoot = loader.load();
            CardRestoController cardController = loader.getController();

            // Afficher les détails du restaurant dans la carte
            cardController.displayRecetteData(restaurant.getId(), restaurant.getImage());

            // Ajouter la carte du restaurant au GridPane
            gridPaneRestaurantDetails.add(cardRoot, 0, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void naviguezVersmenu(ActionEvent event) {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/liste.fxml"));
            Parent root1 = loader1.load();

            // Passer des données à AfficherOffreController si nécessaire
            ListeDesRstoController AO = loader1.getController();
            // controller.setXXX(); // Définir les données à afficher

            Scene scene = new Scene(root1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    public void naviguezVerpanier(ActionEvent event) {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/panier.fxml"));
            Parent root1 = loader1.load();

            // Passer des données à AfficherOffreController si nécessaire
            paniercontroller AO = loader1.getController();
            // controller.setXXX(); // Définir les données à afficher

            Scene scene = new Scene(root1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    // Autres méthodes de la classe...
}

