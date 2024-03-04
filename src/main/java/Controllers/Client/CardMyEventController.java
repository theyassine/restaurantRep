package Controllers;

import Entities.Evennement;
import Entities.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class CardMyEventController  {

    @FXML
    private Label Email;

    @FXML
    private Label dateColumn;

    @FXML
    private Label desc;

    @FXML
    private Label lieuEvenementColumn;
    @FXML
    private Label name;

    @FXML
    private Label nbrParticipantsColumn;

    @FXML
    private Label nomRestoColumn;
    @FXML
    private ImageView imageColumn;
    @FXML
    void AnnulerRse(ActionEvent event) {}
    @FXML
    void editReservation(ActionEvent event) {}
    public void initialize() {
    }

    public void displayEventData(Evennement evennement) {
        name.setText(evennement.getNom_event());
        desc.setText(evennement.getDesc_event());
        lieuEvenementColumn.setText(evennement.getLieu_evenement());
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
    public void displayReservationData(Reservation reservation) {
        Email.setText(reservation.getEmail_contact());
        dateColumn.setText(reservation.getDate_reservation());
        nbrParticipantsColumn.setText( reservation.getNombre_participants());
    }

}
