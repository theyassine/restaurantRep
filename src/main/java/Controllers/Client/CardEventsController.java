package Controllers;

import Entities.Evennement;
import Entities.Reservation;
import Service.EvennementService;
import Service.ReservationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CardEventsController {

    @FXML
    private Label dateDebutColumn;

    @FXML
    private Label dateFinColumn;

    @FXML
    private Label desc;

    @FXML
    private ImageView imageColumn;

    @FXML
    private Label lieuEvenementColumn;

    @FXML
    private Label nbrParticipantsColumn;

    @FXML
    private Label nom;

    @FXML
    private Label nomRestoColumn;

    @FXML
    private Label timeDebutColumn;

    @FXML
    private Label timeFinColumn;

    @FXML
    void addres(ActionEvent event) {
        Evennement selectedEvent = getEventData();

        if (selectedEvent != null && !isEventReserved(selectedEvent)) {
            try {
                FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/Client/AddReservation.fxml"));
                Parent root1 = loader1.load();
                AddReservationController addReservationController = loader1.getController();
                addReservationController.setEvent(selectedEvent);

                Scene scene = new Scene(root1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Tu as déjà réservé à cet événement ou l'événement est introuvable.");
        }
    }
    private EvennementService evennementService = new EvennementService();
    public void initialize() {
    }
    public void displayEventData(Evennement evennement) {
        nom.setText(evennement.getNom_event());
        desc.setText(evennement.getDesc_event());
        lieuEvenementColumn.setText(evennement.getLieu_evenement());
        dateDebutColumn.setText(evennement.getDate_debut());
        dateFinColumn.setText(evennement.getDate_fin());
        nbrParticipantsColumn.setText(String.valueOf(evennement.getNbr_participants()));
        timeDebutColumn.setText(evennement.getTime_debut());
        timeFinColumn.setText(evennement.getTime_fin());
        nomRestoColumn.setText(evennement.getNameResto());

        String imagePath = evennement.getImage_path();

        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                imageColumn.setImage(image);
            } else {
                System.out.println("L'image n'existe pas : " + imagePath);
            }
        } else {
            System.out.println("Le chemin de l'image est vide.");
        }
    }

    private boolean isEventReserved(Evennement event) {
        if (event != null) {
            ReservationService reservationService = new ReservationService();
            List<Reservation> reservations = reservationService.getReservationsByEvent(event.getId());
            return reservations.isEmpty();
        }
        return false;
    }
    public Evennement getEventData() {
        return evennementService.getEventById(1);
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
