package controllers;
import entities.Supplement;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.SupplementService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ajouterSupplementController implements Initializable {

    @FXML
    private TableView<Supplement> tv_supp;

    @FXML
    private TableColumn<Supplement, String> col_nomsupp;

    @FXML
    private TableColumn<Supplement, Double> col_prixsupp;

    @FXML
    private TextField tf_nomsupp;

    @FXML
    private TextField tf_prixsupp;

    @FXML
    private ImageView iv_supp;

    @FXML
    private TextField searchTextField;

    private ObservableList<Supplement> supplementData = FXCollections.observableArrayList();

    private SupplementService supplementService = new SupplementService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize table columns
        col_nomsupp.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prixsupp.setCellValueFactory(new PropertyValueFactory<>("prix"));

        populateSupplementTableView();
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            handleLiveSearch(newValue);
        });
    }

    private void populateSupplementTableView() {
        List<Supplement> supplementList = supplementService.readAll();
        ObservableList<Supplement> observableSupplementList = FXCollections.observableArrayList(supplementList);
        tv_supp.setItems(observableSupplementList);
    }

    @FXML
    public void btn_ajoutersupp(ActionEvent actionEvent) {
        try {
            String nom = tf_nomsupp.getText();
            double prix = 0.0;

            // Validate if all fields are filled
            if (nom.isEmpty() || tf_prixsupp.getText().isEmpty()) {
                showAlert("Tous les champs doivent être remplis.", "Le supplément a été ajouté avec succès.");
                return;
            }

            // Validate price field
            if (!tf_prixsupp.getText().matches("\\d+(\\.\\d+)?")) {
                showAlert("Le champ du prix doit contenir des chiffres (et un point décimal optionnel pour les décimales).", "Le supplément a été modifié avec succès.");
                return;
            } else {
                prix = Double.parseDouble(tf_prixsupp.getText());
            }

            // Create a new Supplement object and set its properties
            Supplement supplement = new Supplement();
            supplement.setNom(nom);
            supplement.setPrix(prix);

            // Add the new supplement to the database or service
            supplementService.add(supplement);

            // Show a success message
            showAlert("Supplément ajouté", "Le supplément a été ajouté avec succès.");

            // Add the new supplement to the TableView
            tv_supp.getItems().add(supplement);
            populateSupplementTableView();
            clearTextFields();

        } catch (NumberFormatException e) {
            showAlert("Entrée invalide pour les nombres.", "Le supplément a été ajouté avec succès.");
        }
    }





    @FXML
    public void btn_importerimagesupp(javafx.event.ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            showAlert("Image sélectionnée : " + selectedFile.getName(), "Le supplement a été modifié avec succès.");
            // Call the method to display the selected image
            afficherImage(selectedFile.getAbsolutePath());
        } else {
            showAlert("Aucune image sélectionnée.", "Le supplement a été modifié avec succès.");
        }
    }

    void afficherImage(String chemin) {
        // Create an Image object from the specified path
        javafx.scene.image.Image image = new javafx.scene.image.Image(new File(chemin).toURI().toString());

        // Set the image in the ImageView object
        iv_supp.setImage(image);
    }

    private void showAlert(String message, String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void btn_supprimersupp(ActionEvent actionEvent) {
        Supplement selectedItem = tv_supp.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Delete the selected menu item
            supplementService.delete(selectedItem);
            // Remove the item from the TableView
            tv_supp.getItems().remove(selectedItem);
            showAlert("supplement supprimé avec succès", "Le supplement a été modifié avec succès.");
            populateSupplementTableView();

        } else {
            showAlert("Aucun supplement sélectionné.", "Le supplement a été modifié avec succès.");
        }
    }


    @FXML
    private void btn_modifiersupp() {
        Supplement selectedItem = tv_supp.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Populate the text fields with the selected menu item data
            tf_nomsupp.setText(selectedItem.getNom());
            tf_prixsupp.setText(String.valueOf(selectedItem.getPrix()));

        } else {
            showAlert("Aucun supplement sélectionné.", "Veuillez sélectionner un supplement à modifier.");
        }
    }

    @FXML
    private void btn_save_modifications() {
        Supplement selectedItem = tv_supp.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                // Update the selected menu item with the modified data
                selectedItem.setNom(tf_nomsupp.getText());
                selectedItem.setPrix(Double.parseDouble(tf_prixsupp.getText()));


                // Update the menu item in the database or service
                supplementService.update(selectedItem);

                // Show a success message
                showAlert("supplement modifié avec succès", "Le supplement a été modifié avec succès.");

                // Refresh the TableView
                populateSupplementTableView();
                clearTextFields();
            } catch (NumberFormatException e) {
                showAlert("Erreur lors de la modification du supplement.", "Veuillez vérifier les valeurs entrées.");
            }
        } else {
            showAlert("Aucun supplement sélectionné.", "Veuillez sélectionner un supplement à modifier.");
        }
    }


    @FXML
    private void versMenu(ActionEvent event) {
        try {
            // Load the Add_idee.fxml file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ajoutMenu.fxml"));
            Parent root = fxmlLoader.load();

            // Get the controller
            AjouterMenuController ajoutermenuController = fxmlLoader.getController();

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the stage from the button and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearTextFields() {
        tf_prixsupp.clear();
        tf_nomsupp.clear(); // Clear choice box selection

    }

    @FXML
    void handleExitButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment quitter ?");

        ButtonType yesButton = new ButtonType("Oui");
        ButtonType noButton = new ButtonType("Non");

        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == yesButton) {
                // Exit the application
                Platform.exit();
            }
        });
    }

    private void handleLiveSearch(String searchText) {
        try {
            // Create a filtered list to store the filtered items
            ObservableList<Supplement> filteredList = FXCollections.observableArrayList();

            // If the search text is empty or null, display all items in the TableView
            if (searchText == null || searchText.trim().isEmpty()) {
                tv_supp.setItems(supplementData); // Use the original data
                return; // Exit the method
            }

            // Iterate through each item in the TableView and filter based on search text
            for (Supplement supplement : supplementData) {
                // Perform case-insensitive search on nom and prix attributes of the supplement
                if (supplement.getNom().toLowerCase().contains(searchText.toLowerCase()) ||
                        String.valueOf(supplement.getPrix()).toLowerCase().contains(searchText.toLowerCase())) {
                    filteredList.add(supplement);
                }
            }

            // Update the TableView with the filtered data
            tv_supp.setItems(filteredList);
        } catch (Exception e) {
            e.printStackTrace(); // Proper error handling might include showing an alert to the user
        }
    }




}