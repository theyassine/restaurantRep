package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;

public class cardProductController {

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
    private Button addButton;

    public cardProductController(Label titreLabel, Label descriptionLable, Label specLabel, Label placeLabel, ImageView img, Button commanderButton) {
        TitreLabel = titreLabel;
        this.descriptionLable = descriptionLable;
        SpecLabel = specLabel;
        PlaceLabel = placeLabel;
        this.img = img;
        this.addButton = addButton;
    }

    @FXML
    void handleClick(MouseEvent event) {
        // Gérer l'action de clic sur le bouton "Commander"
        System.out.println("Commande lancée !");
    }

    @FXML
    public void initialize() {
        // Code d'initialisation
    }

    public void displayRecetteData(String titre, String description, String specialite, String place, String imagePath) {
        TitreLabel.setText(titre);
        descriptionLable.setText(description);
        SpecLabel.setText(specialite);
        PlaceLabel.setText(place);

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
