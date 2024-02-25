package Controllers;

import javafx.collections.FXCollections;
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
    private Restaurant restaurant= new Restaurant();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        return 10; // Remplacez ceci par l'ID réel
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
    }
}
