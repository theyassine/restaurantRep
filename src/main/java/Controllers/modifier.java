package Controllers;

import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import org.example.entities.Restaurant;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.example.Service.RestaurantService;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class modifier implements Initializable {
    @FXML
    private TextField nomTextField;

    @FXML
    private ChoiceBox<String> mychoicebox;

    @FXML
    private TextField telephoneTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField placeTextField;

    @FXML
    private TextField rateTextField;
    @FXML
    private TextField Cte;
    @FXML
    private ListView<Restaurant> list;


    private RestaurantService restaurantService;
    private Restaurant restaurant = new Restaurant();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        mychoicebox.getItems().addAll("Tunisian", "Fast food", "italien");
        // Initialisation de restaurantService
        this.restaurantService = new RestaurantService();
        // Assurez-vous que restaurantService est initialisé avec succès
        if (restaurantService != null) {
            list.setItems(FXCollections.observableArrayList(restaurantService.readAll()));
            // Définir un écouteur d'événements pour la sélection de la ListView
            list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    // Remplir les champs de texte avec les valeurs de l'élément sélectionné
                    nomTextField.setText(newValue.getNom());
                    mychoicebox.setValue(newValue.getSpeciality());
                    telephoneTextField.setText(newValue.getTelephone());
                    descriptionTextField.setText(newValue.getDescription());
                    placeTextField.setText(newValue.getPlace());
                    rateTextField.setText(newValue.getRate());
                    Cte.setText(String.valueOf(newValue.getid_categorie()));
                }
            });
        } else {
            System.out.println("restaurantService n'a pas pu être initialisé correctement.");
        }
        addTextFieldsListeners();
    }
    private void addTextFieldsListeners() {
        // Ajout de contrôles de saisie pour les champs nécessaires
        telephoneTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                telephoneTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        rateTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                rateTextField.setText(oldValue);
            }
        });

        // Ajoutez d'autres contrôles de saisie selon vos besoins pour les autres champs
    }


    @FXML
    public void modifier() {
        Restaurant selectedRestaurant = list.getSelectionModel().getSelectedItem();
        if (selectedRestaurant != null) {
            if (validateFields()) {
                // Mettre à jour les champs modifiés du restaurant avec les valeurs des champs de texte
                selectedRestaurant.setNom(nomTextField.getText());
                selectedRestaurant.setSpeciality(mychoicebox.getValue());
                selectedRestaurant.setTelephone(telephoneTextField.getText());
                selectedRestaurant.setDescription(descriptionTextField.getText());
                selectedRestaurant.setPlace(placeTextField.getText());
                selectedRestaurant.setRate(rateTextField.getText());
                selectedRestaurant.setid_categorie(Integer.parseInt(Cte.getText()));

                // Appeler la méthode update() de restaurantService pour mettre à jour le restaurant dans la base de données
                restaurantService.update(selectedRestaurant);

                // Actualiser la ListView après la modification
                refreshListView();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs correctement.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Avertissement", " la modification assure avec succes.");
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean validateFields() {
        // Implémentez la validation des champs ici
        // Par exemple, vérifiez si tous les champs sont remplis correctement
        return true; // Pour l'exemple, retourne toujours true
    }

    private void refreshListView() {
        // Actualiser la ListView avec les données mises à jour depuis la base de données
        list.getItems().clear();
        list.getItems().addAll(restaurantService.readAll());
    }

    public void fillforum(javafx.scene.input.MouseEvent mouseEvent) {
        Restaurant m = list.getSelectionModel().getSelectedItem();
        if (m != null) {
            nomTextField.setText(String.valueOf(m.getNom()));
            mychoicebox.setValue(String.valueOf(m.getSpeciality()));
            telephoneTextField.setText(String.valueOf(m.getTelephone()));
            descriptionTextField.setText(m.getDescription());
            placeTextField.setText(m.getPlace());
            rateTextField.setText(m.getRate());

        }

    }
}
        /*
        mychoicebox.getItems().addAll("Tunisian", "Fast food", "italien");
        // Initialisation de restaurantService
        this.restaurantService = new RestaurantService();
        // Assurez-vous que restaurantService est initialisé avec succès
        if (restaurantService != null) {
            list.setItems(FXCollections.observableArrayList(restaurantService.readAll()));
        } else {
            System.out.println("restaurantService n'a pas pu être initialisé correctement.");
        }
    }


    @FXML
    public void modifier() {
        // Obtenir l'ID de l'élément que vous souhaitez modifier
        int id = getIdFromSomewhere(); // Remplacez getIdFromSomewhere() par la logique appropriée pour obtenir l'ID

        // Lire le restaurant à partir de la base de données en utilisant l'ID
        Restaurant restaurant = restaurantService.readById(id);
        if (restaurant == null) {
            System.out.println("Restaurant avec l'ID " + id + " n'existe pas.");
            return;
        }

        // Mettre à jour les champs modifiés du restaurant avec les valeurs des champs de texte
        restaurant.setNom(nomTextField.getText());
        restaurant.setSpeciality(mychoicebox.getValue());
        restaurant.setTelephone(telephoneTextField.getText());
        restaurant.setDescription(descriptionTextField.getText());
        restaurant.setPlace(placeTextField.getText());
        restaurant.setRate(rateTextField.getText());
        restaurant.setid_categorie(Integer.parseInt(Cte.getText()));

        // Appeler la méthode update() de restaurantService pour mettre à jour le restaurant dans la base de données
        restaurantService.update(restaurant);
    }

    // Méthode factice pour obtenir l'ID de l'élément que vous souhaitez modifier
    private int getIdFromSomewhere() {
        // Implémentez votre logique pour obtenir l'ID à partir de quelque part
        return 15 ; // Remplacez ceci par l'ID réel
    }


    public void fillforum(javafx.scene.input.MouseEvent mouseEvent) {
        Restaurant m=list.getSelectionModel().getSelectedItem();
        if(m!=null){
            nomTextField.setText(String.valueOf(m.getNom()));
            mychoicebox.setValue(String.valueOf(m.getSpeciality()));
            telephoneTextField.setText(String.valueOf(m.getTelephone()));
            descriptionTextField.setText(m.getDescription());
            placeTextField.setText(m.getPlace());
            rateTextField.setText(m.getRate());

        }
    }*/

