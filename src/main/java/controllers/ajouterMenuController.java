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
        // Retrieve data from text fields
        String nom = tf_nom.getText();
        String description = tf_description.getText();
        int calories = Integer.parseInt(tf_calorie.getText());
        double prix = Double.parseDouble(tf_prix.getText());

        // Create a new Menu object
        Menu menu = new Menu();
        menu.setNom(nom);
        menu.setDescription(description);
        menu.setCalories(calories);
        menu.setPrix(prix);

        // Add the menu using the MenuService
        menuService.add(menu);

        // Show a success message
        showAlert();
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

    }

    private void showAlert() {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Menu ajouté");
            alert.setHeaderText(null);
            alert.setContentText("Le menu a été ajouté avec succès.");
            alert.showAndWait();
        } catch (Exception e) {
            System.err.println("An error occurred while showing the alert: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
