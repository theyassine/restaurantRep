package controllers;

import entities.Menu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import services.MenuService;

import java.util.List;

public class UpdateMenuController {

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
    private TableView<Menu> tableView;

    @FXML
    private TableColumn<Menu, String> nomColumn;

    @FXML
    private TableColumn<Menu, Double> prixColumn;

    @FXML
    private TableColumn<Menu, Integer> calorieColumn;

    @FXML
    private TableColumn<Menu, String> descriptionColumn;

    public UpdateMenuController(TextField tfCalorie, TextField tfDescription, TextField tfNom, TextField tfPrix, TableView<Menu> tableView, TableColumn<Menu, String> nomColumn, TableColumn<Menu, Double> prixColumn, TableColumn<Menu, Integer> calorieColumn, TableColumn<Menu, String> descriptionColumn) {
        tf_calorie = tfCalorie;
        tf_description = tfDescription;
        tf_nom = tfNom;
        tf_prix = tfPrix;
        this.tableView = tableView;
        this.nomColumn = nomColumn;
        this.prixColumn = prixColumn;
        this.calorieColumn = calorieColumn;
        this.descriptionColumn = descriptionColumn;
    }

    // Initialize method to set up the controller
    @FXML
    private void initialize() {
        // Set up cell value factories to map Menu properties to TableColumn cells
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        prixColumn.setCellValueFactory(cellData -> cellData.getValue().prixProperty().asObject());
        calorieColumn.setCellValueFactory(cellData -> cellData.getValue().caloriesProperty().asObject());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        // Populate the TableView with menus
        populateMenuTableView();
    }

    // Method to populate the TableView with menus
    private void populateMenuTableView() {
        List<Menu> menuList = menuService.readAll(); // Retrieve all menus from the database
        ObservableList<Menu> observableMenuList = FXCollections.observableArrayList(menuList);
        tableView.setItems(observableMenuList); // Set the menu list to the TableView
    }

    @FXML
    void updateMenu(ActionEvent event) {
        // Get the selected menu from the TableView
        Menu selectedMenu = tableView.getSelectionModel().getSelectedItem();
        if (selectedMenu != null) {
            try {
                // Retrieve data from text fields
                String nom = tf_nom.getText();
                String description = tf_description.getText();
                int calories = Integer.parseInt(tf_calorie.getText());
                double prix = Double.parseDouble(tf_prix.getText());

                // Update the selected menu
                selectedMenu.setNom(nom);
                selectedMenu.setDescription(description);
                selectedMenu.setCalories(calories);
                selectedMenu.setPrix(prix);

                // Update the menu using the MenuService
                menuService.update(selectedMenu);

                // Show a success message
                showAlert("Menu mis à jour avec succès");
            } catch (NumberFormatException e) {
                showAlert("Valeur invalide pour les calories ou le prix");
            }
        } else {
            showAlert("Aucun menu sélectionné pour la mise à jour");
        }
    }

    // Method to show an alert message
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
