package Controllers;


import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.entities.Restaurant;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import org.example.Service.RestaurantService;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListeDesRstoController {

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;
    @FXML
    private Button searchbutton;
    @FXML
    private TextField search;
    private String destinationDirectory = "C:\\xampp\\htdocs\\img";


    public void initialize() {
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            updateRestaurants(newValue);
        });

        scroll.vvalueProperty().addListener((observable, oldValue, newValue) -> {
            grid.setLayoutY(newValue.doubleValue() * (grid.getHeight() - scroll.getViewportBounds().getHeight()));
        });
        RestaurantService RestaurantService = new RestaurantService();
        List<Restaurant> allRecettes = RestaurantService.readAll();

        // Retrieve all image paths from the destination directory
        List<String> imagePaths = new ArrayList<>();
        File folder = new File(destinationDirectory);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    // Ajouter le chemin du fichier à la liste
                    imagePaths.add(file.getAbsolutePath());
                }
            }
        }

        int columnIndex = 0;
        int rowIndex = 0;

        for (int i = 0; i < allRecettes.size(); i++) {
            Restaurant restaurant = allRecettes.get(i);
            String imagePath = i < imagePaths.size() ? imagePaths.get(i) : null; // Chemin d'image correspondant

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CARD.fxml"));
                Node cardRecetteNode = loader.load();
                CardRestoController cardController = loader.getController();
                cardController.displayRecetteData(restaurant.getId(), imagePath); // Passer le chemin de l'image

                if (i % 4 == 0) {

                    rowIndex++; // Nouvelle ligne pour chaque groupe de 4 cartes
                }
                grid.add(cardRecetteNode, i % 4, rowIndex);

                this.grid.setMinWidth(-1.0);
                this.grid.setPrefWidth(-1.0);
                this.grid.setMaxWidth(Double.NEGATIVE_INFINITY);
                this.grid.setMinHeight(-1.0);
                this.grid.setPrefHeight(-1.0);
                this.grid.setMaxHeight(Double.NEGATIVE_INFINITY);

                GridPane.setMargin(cardRecetteNode, new Insets(10.0));
            } catch (IOException e) {
                e.printStackTrace(); // Gérer l'exception de manière appropriée
            }
        }
    }

    private void updateRestaurants(String filter) {
        RestaurantService restaurantService = new RestaurantService();
        List<Restaurant> filteredRestaurants = restaurantService.searchByNom(filter); // Vous devez implémenter cette méthode dans RestaurantService

        grid.getChildren().clear(); // Efface les éléments précédemment affichés

        int columnIndex = 0;
        int rowIndex = 1;

        for (Restaurant restaurant : filteredRestaurants) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardrecette.fxml"));
                Node cardRecetteNode = loader.load();

                CardRestoController cardController = loader.getController();
                cardController.displayRecetteData(restaurant.getId(), null); // Vous pouvez charger l'image ultérieurement si nécessaire

                columnIndex++;
                if (columnIndex == 3) {
                    columnIndex = 0;
                    ++rowIndex;
                }
                grid.add(cardRecetteNode, columnIndex, rowIndex);
                GridPane.setMargin(cardRecetteNode, new Insets(10.0));
            } catch (IOException e) {
                e.printStackTrace(); // Gérer l'exception de manière appropriée
            }
        }
    }
    @FXML
    void naviguezVersModifier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/stat.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Récupérer le contrôleur de la scène chargée
            statcontrol controller = loader.getController();

            // Passer des données au contrôleur si nécessaire
            // controller.setXXX(); // Définir les données à afficher

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
}








