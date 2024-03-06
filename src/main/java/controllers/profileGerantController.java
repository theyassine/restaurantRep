package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class profileGerantController implements Initializable {

    @FXML
    private Label prenom;

    @FXML
    void redirectToEvent(ActionEvent event) {

    }

    @FXML
    void redirectToPub(ActionEvent event) {

    }

    @FXML
    void redirectToRestaurant(ActionEvent event) {

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