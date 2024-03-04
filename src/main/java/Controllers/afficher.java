package Controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.example.entities.Restaurant;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.example.Service.RestaurantService;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class afficher implements Initializable {

    @FXML
    private ListView<Restaurant> labelfromdb;

    RestaurantService SU = new RestaurantService();

    @FXML
    void AfficherDB(ActionEvent event) {
        labelfromdb.setItems(FXCollections.observableArrayList(SU.readAll()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


    @FXML
    public void supprimer(ActionEvent actionEvent) {
        Restaurant selectedRestaurant = labelfromdb.getSelectionModel().getSelectedItem();

        if (selectedRestaurant == null) {
            showAlert("Veuillez sélectionner un restaurant à supprimer.");
            return;
        }

        // Boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Suppression de restaurant");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer le restaurant sélectionné ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // L'utilisateur a confirmé la suppression, procéder à la suppression du restaurant

            // Récupérer l'ID du restaurant sélectionné
            int id = selectedRestaurant.getId();

            // Supprimer le restaurant de la base de données
            SU.supprimer(id);

            // Supprimer l'élément sélectionné de la liste
            labelfromdb.getItems().remove(selectedRestaurant);
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Avertissement");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


