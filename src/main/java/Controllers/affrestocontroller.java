package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.example.Service.RestaurantService;
import org.example.entities.Restaurant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class affrestocontroller {
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


    private String destinationDirectory = "C:\\xampp\\htdocs\\img";


    private org.example.Service.RestaurantService RestaurantService = new RestaurantService();

    public void initialize() {
        String firstImagePath = getFirstImagePath();
        displayImage(firstImagePath);

    }
    private String getFirstImagePath() {
        File folder = new File(destinationDirectory);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null && listOfFiles.length > 0) {
            // Return the path of the first image file found in the directory
            return listOfFiles[0].getAbsolutePath();
        } else {
            System.out.println("Aucune image trouvée dans le répertoire : " + destinationDirectory);
            return null;
        }
    }

    public void displayImage(String imagePath) {
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

    public void displayRestaurantData(String nom, String description, String speciality, String place, String restaurantRate, String imagePath) {
        TitreLabel.setText(nom);
        descriptionLable.setText(description);
        SpecLabel.setText(speciality);
        PlaceLabel.setText(place);
        rate.setText(restaurantRate);

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
    @FXML
    public void diversionary(ActionEvent event) {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/liste.fxml"));
            Parent root1 = loader1.load();

            // Passer des données à AfficherOffreController si nécessaire
            modifier AO = loader1.getController();
            // controller.setXXX(); // Définir les données à afficher

            Scene scene = new Scene(root1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
