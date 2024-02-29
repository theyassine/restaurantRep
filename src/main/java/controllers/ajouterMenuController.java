package controllers;

import entities.Menu;
import entities.Supplement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import services.MenuService;
import services.SupplementService;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ajouterMenuController implements Initializable {

    public ImageView id_image;
    private MenuService menuService = new MenuService();
    private SupplementService supplementService = new SupplementService();


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
    @FXML
    private ImageView id_imzge;
    @FXML
    private TextField tf_suppl;
    @FXML
    private TextField tf_prixx;
    @FXML
    private TextField tf_calo;
    @FXML
    private TextField tf_recherche;



@Override
    public void initialize(URL location, ResourceBundle resources) {
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        prixColumn.setCellValueFactory(cellData -> cellData.getValue().prixProperty().asObject());
        calorieColumn.setCellValueFactory(cellData -> cellData.getValue().caloriesProperty().asObject());
        imageColumn.setCellValueFactory(cellData -> cellData.getValue().imageProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        populateMenuTableView();

        FilteredList<Menu> filteredData = new FilteredList<>(tableView.getItems(), p -> true);



        SortedList<Menu> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);
    }

    private void populateMenuTableView() {
        List<Menu> menuList = menuService.readAll();
        ObservableList<Menu> observableMenuList = FXCollections.observableArrayList(menuList);
        tableView.setItems(observableMenuList);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }









    @FXML
    public void btn_ajouter(javafx.event.ActionEvent actionEvent) {
        try {
            String nom = tf_nom.getText();
            String description = tf_description.getText();
            int calories = 0;
            double prix = 0.0;

            // Validate if all fields are filled
            if (nom.isEmpty() || description.isEmpty() || tf_calorie.getText().isEmpty() || tf_prix.getText().isEmpty()) {
                showAlert("Tous les champs doivent être remplis.");
                return;
            }

            // Validate calorie field
            if (!tf_calorie.getText().matches("\\d+")) {
                showAlert("Le champ des calories doit contenir des chiffres uniquement.");
                return;
            } else {
                calories = Integer.parseInt(tf_calorie.getText());
            }

            // Validate price field
            if (!tf_prix.getText().matches("\\d+(\\.\\d+)?")) {
                showAlert("Le champ du prix doit contenir des chiffres (et un point décimal optionnel pour les décimales).");
                return;
            } else {
                prix = Double.parseDouble(tf_prix.getText());
            }

            Menu menu = new Menu();
            menu.setNom(nom);
            menu.setDescription(description);
            menu.setCalories(calories);
            menu.setPrix(prix);
            menu.setId_user(1);

            menuService.add(menu);

            showAlert("Menu ajouté");
            tableView.refresh();


            tableView.getItems().add(menu);
        } catch (NumberFormatException e) {
            showAlert("Entrée invalide pour les nombres.");
        }
    }


    public void btn_supprimermenu(javafx.event.ActionEvent actionEvent) {
        Menu menuSelectionne = tableView.getSelectionModel().getSelectedItem();

        if (menuSelectionne != null) {
            menuService.delete(menuSelectionne);
            tableView.getItems().remove(menuSelectionne);
            tableView.refresh();
            showAlert("Menu supprimé avec succès.");
        } else {
            showAlert("Veuillez sélectionner un menu à supprimer.");
        }


    }
    void afficherImage(String chemin) {
        // Créer un objet Image à partir du chemin spécifié
        Image image = new Image(new File(chemin).toURI().toString());

        // Définir l'image dans l'objet ImageView
        id_imzge.setImage(image);
    }

    public void btn_imprt(javafx.event.ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            showAlert("Image sélectionnée : " + selectedFile.getName());
            // Appeler la méthode pour afficher l'image sélectionnée
            afficherImage(selectedFile.getAbsolutePath());
        } else {
            showAlert("Aucune image sélectionnée.");
        }
    }

    public void btn_import(javafx.event.ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            showAlert("Image sélectionnée : " + selectedFile.getName());
            // Appeler la méthode pour afficher l'image sélectionnée
            afficherImage(selectedFile.getAbsolutePath());
        } else {
            showAlert("Aucune image sélectionnée.");
        }
    }




    @FXML
    public void enregistrerModif(ActionEvent actionEvent) {
    }

    public void btn_addSupplements(javafx.event.ActionEvent actionEvent) {
        try {
            String nom = tf_suppl.getText();
            double prix = 0.0;

            // Validate if all fields are filled
            if (nom.isEmpty() || tf_prixx.getText().isEmpty()) {
                showAlert("Tous les champs doivent être remplis.");
                return;
            }

            // Validate price field
            if (!tf_prixx.getText().matches("\\d+(\\.\\d+)?")) {
                showAlert("Le champ du prix doit contenir des chiffres (et un point décimal optionnel pour les décimales).");
                return;
            } else {
                prix = Double.parseDouble(tf_prixx.getText());
            }

            Supplement supplement = new Supplement();
            supplement.setNom(nom);
            supplement.setPrix(prix);
            supplement.setId_user(1);

            // Add the supplement to your data source (supplementService)
            supplementService.add(supplement);

            // Add the supplement to the TableView
            tableView.getItems().add(supplement);

            showAlert("Supplément ajouté avec succès.");
        } catch (NumberFormatException e) {
            showAlert("Entrée invalide pour les nombres.");
        } catch (Exception ex) {
            showAlert("Une erreur s'est produite lors de l'ajout du supplément.");
        }
    }


    public void btn_modi(javafx.event.ActionEvent actionEvent) {
    }

    public void btn_modif(javafx.event.ActionEvent actionEvent) {
    }
}


