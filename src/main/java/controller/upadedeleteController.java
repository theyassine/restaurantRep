package controller;

import entite.Recette;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import services.RecetteService;


import java.io.File;
import java.io.IOException;
import java.util.List;

public class upadedeleteController {
    @FXML
    private TextField descriptionField;

    @FXML
    private TextField etapeField;

    @FXML
    private GridPane grid1;

    @FXML
    private TextField imagetxf;

    @FXML
    private ImageView img1;

    @FXML
    private TextField ingredientsField;
    @FXML
    private ScrollPane scroll;
    @FXML
    private Label titleLabel;

    @FXML
    private TextField titreField;

    @FXML
    private MediaView vid1;

    @FXML
    private TextField videotxf1;
    @FXML
    private Label selectedImagePathLabel;

    @FXML
    private Label selectedVideoPathLabel;

    @FXML
    void Home_btn(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListeDesRecette.fxml"));
            grid1.getScene().setRoot(root); // Assuming grid is a control from the current scene
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void MesRecette_btn(ActionEvent event) {

    }
    @FXML
    void addRecette_btn(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterRecette.fxml"));
            grid1.getScene().setRoot(root); // Assuming grid is a control from the current scene
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void browseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String[] allowedExtensions = {".png", ".jpg", ".jpeg"};
            if (!isValidFileExtension(selectedFile, allowedExtensions)) {
                showAlert("Le type de fichier image sélectionné n'est pas pris en charge. Veuillez sélectionner un fichier .png, .jpg ou .jpeg.");
                return;
            }

            selectedImagePathLabel.setText(selectedFile.getAbsolutePath());
        }
    }

    private boolean isValidFileExtension(File file, String[] allowedExtensions) {
        for (String extension : allowedExtensions) {
            if (file.getName().toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        showAlert("Le type de fichier image sélectionné n'est pas pris en charge. Veuillez sélectionner un fichier .png, .jpg ou .jpeg.");
        return false;
    }


    @FXML
    void browseVideo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Video File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Video Files (*.mp4)", "*.mp4");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            if (!selectedFile.getName().toLowerCase().endsWith(".mp4")) {
                showAlert("Le type de fichier vidéo sélectionné n'est pas pris en charge. Veuillez sélectionner un fichier .mp4.");
                return;
            }

            selectedVideoPathLabel.setText(selectedFile.getAbsolutePath());
        }
    }

    public void initialize() {
        // Retrieve all recipes from the data source
        RecetteService recetteService = new RecetteService();
        List<Recette> allRecettes = recetteService.readAll();

        // Load and display each recipe in a CardRecette
        int columnIndex = 0;
        int rowIndex = 0; // Start from 0 to have the cards in the same row

        for (Recette recette : allRecettes) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardRecette.fxml"));
                Node cardRecetteNode = loader.load();

                CardRecetteController cardController = loader.getController();
                cardController.displayRecetteData(recette.getId());

                // Set the same row for all cards
                grid1.add(cardRecetteNode, columnIndex, rowIndex);
                this.grid1.setMinWidth(-1.0);
                this.grid1.setPrefWidth(-1.0);
                this.grid1.setMaxWidth(Double.NEGATIVE_INFINITY);
                this.grid1.setMinHeight(-1.0);
                this.grid1.setPrefHeight(-1.0);
                this.grid1.setMaxHeight(Double.NEGATIVE_INFINITY);
                GridPane.setMargin(cardRecetteNode, new Insets(10.0));

                // Add event handler to each card to display recipe info on click


                columnIndex++; // Increment the columnIndex for the next card

            } catch (IOException e) {
                e.printStackTrace(); // Handle exception appropriately
            }
        }

        // Enable horizontal scrolling in the ScrollPane
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    }


    @FXML
    void ModiferRecette(ActionEvent event) {

    }
    @FXML
    void supprimer_recette(ActionEvent event) {

    }
}