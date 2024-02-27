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
        // Ajoutez le premier champ d'étape au chargement de la vue
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
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            selectedImagePathLabel.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void browseVideo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Video File");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
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
            String etapesText = etapeField.getText();
            String imagePath = selectedImagePathLabel.getText();
            String videoPath = selectedVideoPathLabel.getText();

            // Concaténer les valeurs des champs d'ingrédients supplémentaires
            StringBuilder ingredientsBuilder = new StringBuilder();
            ingredientsBuilder.append(ingredients);
            for (TextField field : additionalIngredientFields) {
                String additionalIngredient = field.getText();
                if (!additionalIngredient.isEmpty()) {
                    ingredientsBuilder.append(", ").append(additionalIngredient);
                }
            }
            String combinedIngredients = ingredientsBuilder.toString();

            if (titre.isEmpty() || description.isEmpty() || combinedIngredients.isEmpty() || etapesText.isEmpty()) {
                System.out.println("Veuillez remplir tous les champs obligatoires.");
                return;
            }
            StringBuilder etapesBuilder = new StringBuilder();
            etapesBuilder.append(premierEtape).append("\n");
            for (TextArea etapeField : etapeFields) {
                String etapeText = etapeField.getText();
                if (!etapesText.isEmpty()) {
                    etapesBuilder.append(etapeText).append("\n"); // Utilisation du caractère de nouvelle ligne comme séparateur
                }
            }
            String etapes = etapesBuilder.toString();

            // Le reste du code pour ajouter la recette dans la base de données
            if (titre.isEmpty() || description.isEmpty() || ingredients.isEmpty() || etapes.isEmpty()) {
                System.out.println("Veuillez remplir tous les champs obligatoires.");
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
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez compléter tous les champs, s'il vous plaît");
            alert.showAndWait();
        }
    }
}