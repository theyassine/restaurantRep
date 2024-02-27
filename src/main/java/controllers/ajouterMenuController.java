package controllers;

import entities.Menu;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.MenuService;

import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

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
    private TableView<Menu> tableView;


    @FXML
    private TableColumn<Menu, String> nomColumn;

    @FXML
    private TableColumn<Menu, Double> prixColumn;

    @FXML
    private TableColumn<Menu, Integer> calorieColumn;

    @FXML
    private TableColumn<Menu, String> imageColumn;

    @FXML
    private TableColumn<Menu, String> descriptionColumn;

    // Initialize method to set up the controller
    @FXML
    private void initialize() {
        // Set up cell value factories to map Menu properties to TableColumn cells
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        prixColumn.setCellValueFactory(cellData -> cellData.getValue().prixProperty().asObject());
        calorieColumn.setCellValueFactory(cellData -> cellData.getValue().caloriesProperty().asObject());
        imageColumn.setCellValueFactory(cellData -> cellData.getValue().imageProperty());
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

    private User currentUser;

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
            menu.setId_user(1);

            // Add the menu using the MenuService
            menuService.add(menu);

            // Show a success message
            showAlert("Menu ajouté");

            // Update the TableView to reflect the changes
            tableView.getItems().add(menu); // Add the new menu to the TableView
        } catch (NumberFormatException e) {
            showAlert("Invalid input");
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
    void importerImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );

        Stage stage = (Stage) tf_nom.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();
            // Set the image to your ImageView
            // For example, if you have an ImageView named 'imageView'
            // imageView.setImage(new Image(imagePath));
            showAlert("Image sélectionnée : " + selectedFile.getName());
        } else {
            showAlert("Aucune image sélectionnée.");
        }
    }
    @FXML
    void supprimermenu(ActionEvent event) {
        // Récupérer l'élément sélectionné dans la TableView
        Menu menuSelectionne = tableView.getSelectionModel().getSelectedItem();

        if (menuSelectionne != null) {
            // Supprimer le menu de la base de données
            menuService.delete(menuSelectionne); // Passer l'objet Menu directement

            // Supprimer le menu de la TableView
            tableView.getItems().remove(menuSelectionne);

            // Afficher un message de succès
            showAlert("Menu supprimé avec succès.");
        } else {
            showAlert("Veuillez sélectionner un menu à supprimer.");
        }
    }






    public void enregistrerModif(ActionEvent actionEvent) {
    }

    public void ajouterSupplement(ActionEvent actionEvent) {
    }

    // Other methods...
}
