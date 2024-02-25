package Controllers;


import javafx.scene.image.Image;
import org.example.entities.Restaurant;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.example.Service.RestaurantService;

import java.io.File;


public class CardRecetteController {

    @FXML
    private Label TitreLabel;

    @FXML
    private Label descriptionLable;

    @FXML
    private Label SpecLabel;

    @FXML
    private Label PlaceLabel;

    @FXML
    private ImageView img;

    @FXML
    void click(MouseEvent event) {

    }
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
}


