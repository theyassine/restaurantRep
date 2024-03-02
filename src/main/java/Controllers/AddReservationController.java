package Controllers;

import Entities.Reservation;
import Service.ReservationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddReservationController {
    ReservationService rs = new ReservationService();

    @FXML
    private DatePicker dateRES;

    @FXML
    private TextField email;

    @FXML
    private TextField nbrparr;

    @FXML
    void AddRES(ActionEvent event) {
        String emailValue = email.getText();
        String nbrparrValue = nbrparr.getText();
        String dateValue = dateRES.getValue() != null ? dateRES.getValue().toString() : null;

        // Validate input
        if (emailValue.isEmpty() || nbrparrValue.isEmpty() || dateValue == null) {
            showAlert("Veuillez remplir tous les champs");
            return;
        }

        // Create a Reservation object
        Reservation reservation = new Reservation();
        reservation.setEmail_contact(emailValue);
        reservation.setNombre_participants(String.valueOf(Integer.parseInt(nbrparrValue)));
        reservation.setDate_reservation(dateValue);

        // Add the reservation to the service
        rs.add(reservation);

        // Optionally, you can show an alert or clear the form
        showAlert("Réservation ajoutée avec succès !");
        clearForm();
    }

    @FXML
    void ListesEvent(ActionEvent event) {
        // Handle navigating to the list of events
    }

    @FXML
    void dateres(ActionEvent event) {
        // Handle date selection action
    }

    @FXML
    void email(ActionEvent event) {
        // Handle email text field action
    }

    @FXML
    void nbrparr(ActionEvent event) {
        // Handle number of participants text field action
    }

    private void clearForm() {
        email.clear();
        nbrparr.clear();
        dateRES.setValue(null);
    }

    private void showAlert(String message) {
        // Implement your showAlert method similar to the one in AddEventController
    }
}
