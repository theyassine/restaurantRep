package Controllers;

import org.example.entities.Restaurant;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.example.Service.RestaurantService;

import java.net.URL;
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


    public void supprimer(ActionEvent actionEvent) {
        Restaurant selectedRestaurant = labelfromdb.getSelectionModel().getSelectedItem();
        if (selectedRestaurant != null) {
            int id = selectedRestaurant.getId(); // Supposons que votre classe Restaurant a une méthode getId() pour récupérer l'ID
            SU.supprimer(id);
            // Maintenant vous pouvez rafraîchir la liste après suppression si nécessaire
            labelfromdb.getItems().remove(selectedRestaurant);
        }
    }

}
