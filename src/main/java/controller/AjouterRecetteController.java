package controller;

import entite.Recette;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import services.RecetteService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AjouterRecetteController {

    public HBox etapeBox1;
    @FXML
    private TextField titreField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField ingredientsField;


    @FXML
    private TextArea etapeField;

    @FXML
    private Label selectedImagePathLabel;

    @FXML
    private Label selectedVideoPathLabel;
    @FXML
    private VBox ingredientsVBox;

    private List<TextField> additionalIngredientFields = new ArrayList<>();
    @FXML
    private VBox etapesVBox;

    private List<HBox> etapeBoxes = new ArrayList<>();
    private List<TextArea> etapeFields = new ArrayList<>();
    @FXML
    public void initialize() {
        addEtapeField(null);
    }
    @FXML
    void Anuuler(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListeDesRecette.fxml"));
            titreField.getScene().setRoot(root); // Assuming grid is a control from the current scene
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void addEtapeField(ActionEvent event) {
        int etapeNumber = etapeBoxes.size() + 1;

        HBox newEtapeBox = new HBox();
        Label etapeLabel = new Label("Étape " + etapeNumber + ":");
        TextArea newEtapeField = new TextArea();
        newEtapeField.setPromptText("Étape");
        newEtapeField.setPrefHeight(60.0);
        newEtapeField.setPrefWidth(250.0);
        newEtapeBox.getChildren().addAll(etapeLabel, newEtapeField);
        etapesVBox.getChildren().add(newEtapeBox);

        etapeBoxes.add(newEtapeBox);
        etapeFields.add(newEtapeField);
    }

    @FXML
    private void removeEtapeField(ActionEvent event) {
        if (!etapeBoxes.isEmpty()) {
            HBox removedBox = etapeBoxes.remove(etapeBoxes.size() - 1);
            etapesVBox.getChildren().remove(removedBox);
            etapeFields.remove(etapeFields.size() - 1);
        }
    }
    @FXML
    private void addIngredientField(ActionEvent event) {
        TextField newIngredientField = new TextField();
        newIngredientField.setPromptText("Ingrédients");
        ingredientsVBox.getChildren().add(newIngredientField);
        additionalIngredientFields.add(newIngredientField);
    }

    @FXML
    private void removeIngredientField(ActionEvent event) {
        if (!additionalIngredientFields.isEmpty()) {
            TextField removedField = additionalIngredientFields.remove(additionalIngredientFields.size() - 1);
            ingredientsVBox.getChildren().remove(removedField);
        }
    }

    @FXML
    private void browseImage(ActionEvent event) {
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
        return false;
    }

    @FXML
    private void browseVideo(ActionEvent event) {
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

    @FXML
    private void ajouterRecette(ActionEvent event) {
        try {
            String titre = titreField.getText();
            String description = descriptionField.getText();
            String ingredients = ingredientsField.getText();
            String premierEtape = etapeField.getText();

            String imagePath = selectedImagePathLabel.getText();
            String videoPath = selectedVideoPathLabel.getText();

            StringBuilder ingredientsBuilder = new StringBuilder();
            ingredientsBuilder.append(ingredients);
            for (TextField field : additionalIngredientFields) {
                String additionalIngredient = field.getText();
                if (!additionalIngredient.isEmpty()) {
                    ingredientsBuilder.append(", ").append(additionalIngredient);
                }
            }
            String combinedIngredients = ingredientsBuilder.toString();

            if (titre.isEmpty() || description.isEmpty() || combinedIngredients.isEmpty() || premierEtape.isEmpty()|| imagePath.isEmpty()||videoPath.isEmpty()) {
                showAlert("Veuillez remplir tous les champs obligatoires.");
                return;
            }

            StringBuilder etapesBuilder = new StringBuilder();
            etapesBuilder.append(premierEtape).append("\n");
            for (TextArea etapeTextArea : etapeFields) {
                String etapeText = etapeTextArea.getText();
                if (!etapeText.isEmpty()) {
                    etapesBuilder.append(etapeText).append("\n");
                }
            }
            String etapes = etapesBuilder.toString();

            if (titre.isEmpty() || description.isEmpty() || combinedIngredients.isEmpty() || etapes.isEmpty()|| imagePath.isEmpty()||videoPath.isEmpty()) {
                showAlert("Veuillez remplir tous les champs obligatoires.");
                return;
            }

            Recette recette = new Recette();
            recette.setTitre(titre);
            recette.setDescription(description);
            recette.setIngredients(combinedIngredients);
            recette.setEtape(etapes);
            recette.setImage(imagePath);
            recette.setVideo(videoPath);
            recette.setId_user(1);

            RecetteService recetteService = new RecetteService();
            recetteService.add(recette);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setContentText("Recette ajoutée avec succès");
            alert.showAndWait();
            clearFields();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez compléter tous les champs, s'il vous plaît");
            alert.showAndWait();
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void clearFields() {
        // Clear or update fields, text areas, or other components
        titreField.clear();
        descriptionField.clear();
        ingredientsField.clear();
        etapeField.clear();
        selectedImagePathLabel.setText("");
        selectedVideoPathLabel.setText("");

        // Clear additional ingredient fields
        additionalIngredientFields.forEach(TextField::clear);

        // Clear etape fields
        etapeFields.forEach(TextArea::clear);
}
}