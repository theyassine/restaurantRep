package Controllers;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.entities.Restaurant;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.example.Service.RestaurantService;

import java.io.File;
import java.io.IOException;


public class CardRestoController {

    @FXML
    private Label TitreLabel;

    @FXML
    private Label descriptionLable;

    @FXML
    private Label SpecLabel;

    @FXML
    private Label PlaceLabel;

    @FXML
    private Label rate;

    @FXML
    private ImageView img;


    private RestaurantService RestaurantService = new RestaurantService();

    public void initialize() {

    }


    public void displayRecetteData(int id, String imagePath) {
        Restaurant recette = RestaurantService.readById(id);
        if (recette != null) {
            TitreLabel.setText(recette.getNom());
            descriptionLable.setText(recette.getDescription());
            PlaceLabel.setText(recette.getPlace());
            SpecLabel.setText(recette.getSpeciality());
            rate.setText(recette.getRate());

            if (imagePath != null && !imagePath.isEmpty()) {
                File file = new File(imagePath);
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    img.setImage(image);
                } else {
                    System.out.println("L'image n'existe pas : " + imagePath);
                }
            } else {
                System.out.println("Le chemin de l'image est vide.");
            }
        }
    }

    @FXML
    void click(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/affresto.fxml"));
        try {
            Parent root = loader.load();
            affrestocontroller controller = loader.getController();
            // Récupérer les informations du restaurant depuis la carte
            String nom = TitreLabel.getText();
            String description = descriptionLable.getText();
            String speciality = SpecLabel.getText();
            String place = PlaceLabel.getText();
            String restaurantRate = rate.getText();
            String imagePath = ""; // Remplacez ceci par le chemin de l'image si nécessaire
            controller.displayRestaurantData(nom, description, speciality, place, restaurantRate, imagePath);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


