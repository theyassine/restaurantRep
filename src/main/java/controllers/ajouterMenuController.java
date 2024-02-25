package controllers;

import entities.Menu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.MenuService;

public class ajouterMenuController {
    private MenuService menuService = new MenuService();

    @FXML
    private TextField tf_calorie;

    @FXML
    private TextField tf_description;

    @FXML
    private TextField tf_nom;

    @FXML
    private TextField tf_prix;

    @FXML
    void ajouterMenu(ActionEvent event) {
        try {
            // Retrieve data from text fields
            String nom = tf_nom.getText();
            String description = tf_description.getText();
            int calories = 0;
            double prix = 0.0;

            // Validate and parse calorie value
            if (!tf_calorie.getText().isEmpty()) {
                calories = Integer.parseInt(tf_calorie.getText());
            } else {
                showAlert("Calorie field is empty or invalid.");
                return; // Exit method if calorie field is empty
            }

            // Validate and parse price value
            if (!tf_prix.getText().isEmpty()) {
                prix = Double.parseDouble(tf_prix.getText());
            } else {
                showAlert("Price field is empty or invalid.");
                return; // Exit method if price field is empty
            }

            // Create a new Menu object
            Menu menu = new Menu();
            menu.setNom(nom);
            menu.setDescription(description);
            menu.setCalories(calories);
            menu.setPrix(prix);

            // Add the menu using the MenuService
            menuService.add(menu);

            // Show a success message
            showAlert("Menu ajouté");
        } catch (NumberFormatException e) {
            showAlert("Invalid input");
        }
    }

    @FXML
    void afficherMenu(ActionEvent event) {
        // Implement logic to display menus
    }

    @FXML
    void modifierMenu(ActionEvent event) {
        // Implement logic to modify menus
    }

    @FXML
    void supprimerMenu(ActionEvent event) {
        // Implement logic to delete menus
    }

    @FXML
    void importerImage(ActionEvent event) {
        // Implement image importing logic
    }

    private void showAlert(String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        String message = null;
        alert.setContentText(message);
        alert.showAndWait();
    }
}
