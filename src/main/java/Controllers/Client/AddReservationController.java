package Controllers;

import Entities.Evennement;
import Entities.Reservation;
import Service.ReservationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddReservationController {
    ReservationService rs = new ReservationService();
    private Evennement selectedEvent;

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

        // Valider les saisies
        if (emailValue.isEmpty() || nbrparrValue.isEmpty() || dateValue == null) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }

        // Valider le format de l'email
        if (!isValidEmail(emailValue)) {
            showAlert("Veuillez saisir une adresse e-mail valide.");
            return;
        }

        // Vérifier la compatibilité de la date de réservation avec la date de l'événement
        if (!isDateCompatible(dateValue, selectedEvent.getDate_debut(), selectedEvent.getDate_fin())) {
            showAlert("La date de réservation n'est pas compatible avec la date de l'événement.");
            return;
        }

        // Créer un objet Reservation
        Reservation reservation = new Reservation();
        reservation.setEmail_contact(emailValue);

        // Convertir le nombre de participants en entier
        try {
            int nombreParticipants = Integer.parseInt(nbrparrValue);
            if (nombreParticipants <= 0) {
                showAlert("Le nombre de participants doit être supérieur à zéro.");
                return;
            }
            reservation.setNombre_participants(String.valueOf(nombreParticipants));
        } catch (NumberFormatException e) {
            showAlert("Le nombre de participants doit être une valeur numérique.");
            return;
        }

        reservation.setDate_reservation(dateValue);

        // Ajouter la réservation au service
        rs.add(reservation);

        // Optionnel : afficher une alerte ou vider le formulaire
        showAlert("Réservation ajoutée avec succès !");
        clearForm();
    }

    private boolean isDateCompatible(String reservationDate, String eventStartDate, String eventEndDate) {
        return (reservationDate.compareTo(eventStartDate) >= 0) && (reservationDate.compareTo(eventEndDate) <= 0);
    }


    @FXML
    void ListesEvent(ActionEvent event) {try {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/ListEvents.fxml"));
        Parent root1 = loader1.load();
        ListEventsControllers AO = loader1.getController();
        Scene scene = new Scene(root1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        System.err.println(e.getMessage());
    }}

    @FXML
    void dateres(ActionEvent event) {
        // Gérer l'action de sélection de la date
    }

    @FXML
    void email(ActionEvent event) {
        // Gérer l'action du champ email
    }

    @FXML
    void nbrparr(ActionEvent event) {
        // Gérer l'action du champ nombre de participants
    }

    private void clearForm() {
        email.clear();
        nbrparr.clear();
        dateRES.setValue(null);
    }

    private void showAlert(String message) {
        // Afficher une boîte de dialogue d'alerte
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isValidEmail(String email) {
        // Utiliser une expression régulière pour valider le format de l'email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        if (email.matches(emailRegex)) {
            return true;
        } else {
            showAlert("Veuillez saisir une adresse e-mail valide.");
            return false;
        }
    }

    public void setEvent(Evennement selectedEvent) {
        // Set the selected event
        this.selectedEvent = selectedEvent;
    }
}
