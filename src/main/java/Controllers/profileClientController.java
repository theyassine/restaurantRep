package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class profileClientController implements Initializable {

    @FXML
    private Label prenom;

    @FXML
    void redirectToEvent(ActionEvent event) {

    }

    @FXML
    void redirectToPub(ActionEvent event) {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/ListeDesRecette.fxml"));
            Parent root1 = loader1.load();

            // Passer des données à AfficherOffreController si nécessaire
            ListeDesRecetteController AO = loader1.getController();
            // controller.setXXX(); // Définir les données à afficher

            Scene scene = new Scene(root1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


    }

    @FXML
    void redirectToRestaurant(ActionEvent event) {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/liste.fxml"));
            Parent root1 = loader1.load();

            // Passer des données à AfficherOffreController si nécessaire
            ListeDesRstoController AO = loader1.getController();
            // controller.setXXX(); // Définir les données à afficher

            Scene scene = new Scene(root1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    @FXML
    void rediretToMenu(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initializeUserName(String userName) {
        prenom.setText(userName);
    }
}