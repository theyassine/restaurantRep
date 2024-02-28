    package Controllers;

    import Entities.Evennement;
    import Service.EvennementService;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Node;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.Alert;
    import javafx.scene.control.ChoiceBox;
    import javafx.scene.control.ComboBox;
    import javafx.scene.control.DatePicker;
    import javafx.scene.control.TextArea;
    import javafx.scene.control.TextField;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.stage.FileChooser;
    import javafx.stage.Stage;

    import java.io.File;
    import java.io.IOException;
    import java.time.LocalDateTime;

    public class AddEventController {
        EvennementService evennementService = new EvennementService();

        @FXML
        private TextArea descriptionArea;

        @FXML
        private DatePicker endDatePicker;

        @FXML
        private ComboBox<String> endHourComboBox;

        @FXML
        private ComboBox<String> endMinuteComboBox;

        @FXML
        private TextField eventNameField;

        @FXML
        private TextField locationField;

        @FXML
        private TextField participantsField;

        @FXML
        private ChoiceBox<String> restaurantChoiceBox;

        @FXML
        private DatePicker startDatePicker;

        @FXML
        private ComboBox<String> startHourComboBox;

        @FXML
        private ComboBox<String> startMinuteComboBox;

        @FXML
        private ImageView imageView;
        private String imageURL; // Updated variable name

        @FXML
        void AjouterReservation(ActionEvent event) {
        }

        @FXML
        void importerImage(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir une image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Fichiers image", "*.png", "*.jpg", "*.jpeg")
            );

            File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

            if (selectedFile != null) {
                // Set the image URL in the member variable
                imageURL = selectedFile.getAbsolutePath();

                Image image = new Image("file:" + selectedFile.getAbsolutePath());
                imageView.setImage(image);
            }
        }

        @FXML
        void ListesEvennement(ActionEvent event) {
            try {
                FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/ListEvents.fxml"));
                Parent root1 = loader1.load();
                ListEventsControllers AO = loader1.getController();
                Scene scene = new Scene(root1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        @FXML
        void handleAddEvent(ActionEvent event) {
            String nomEvenement = eventNameField.getText();
            String description = descriptionArea.getText();
            String lieu = locationField.getText();
            String participants = participantsField.getText();
            String restaurant = restaurantChoiceBox.getValue();
            String heureDebut = startHourComboBox.getValue();
            String minuteDebut = startMinuteComboBox.getValue();
            String heureFin = endHourComboBox.getValue();
            String minuteFin = endMinuteComboBox.getValue();

            if (nomEvenement.isEmpty() || description.isEmpty() || lieu.isEmpty() || participants.isEmpty() ||
                    restaurant == null || heureDebut == null || minuteDebut == null || heureFin == null || minuteFin == null ||
                    startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
                showAlert("Veuillez remplir tous les champs");
                return;
            }

            if (!participants.matches("\\d+")) {
                showAlert("Le champ Participants doit être une valeur numérique");
                return;
            }

            LocalDateTime dateDebut = getDateTimeFromInputs(startDatePicker, heureDebut, minuteDebut);
            LocalDateTime dateFin = getDateTimeFromInputs(endDatePicker, heureFin, minuteFin);

            if (dateFin.isBefore(dateDebut)) {
                showAlert("La date et l'heure de fin doivent être après la date et l'heure de début");
                return;
            }

            Evennement evennement = new Evennement();
            evennement.setGerant_id(1);
            evennement.setNom_event(nomEvenement);
            evennement.setDesc_event(description);
            evennement.setDate_debut(dateDebut.toString());
            evennement.setDate_fin(dateFin.toString());
            evennement.setLieu_evenement(lieu);

            // Set the image URL from the member variable
            evennement.setImage_path(imageURL);

            try {
                evennement.setNbr_participants(Integer.parseInt(participants));
            } catch (NumberFormatException e) {
                showAlert("Le champ Participants doit être une valeur numérique");
                return;
            }

            evennement.setTime_debut(heureDebut + ":" + minuteDebut);
            evennement.setTime_fin(heureFin + ":" + minuteFin);
            evennement.setNameResto(restaurant);

            // Set the image URL in the ImageView to clear it
            imageView.setImage(null);

            evennementService.add(evennement);

            showAlert("Événement ajouté avec succès !");
            clearForm();
        }

        private void clearForm() {
            eventNameField.clear();
            descriptionArea.clear();
            locationField.clear();
            participantsField.clear();
            restaurantChoiceBox.setValue(null);
            startHourComboBox.setValue(null);
            startMinuteComboBox.setValue(null);
            endHourComboBox.setValue(null);
            endMinuteComboBox.setValue(null);
            startDatePicker.setValue(null);
            endDatePicker.setValue(null);
        }

        private void showAlert(String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Système de gestion des événements");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }

        private LocalDateTime getDateTimeFromInputs(DatePicker datePicker, String heure, String minute) {
            int annee = datePicker.getValue().getYear();
            int mois = datePicker.getValue().getMonthValue();
            int jour = datePicker.getValue().getDayOfMonth();
            int heureInt = Integer.parseInt(heure);
            int minuteInt = Integer.parseInt(minute);

            return LocalDateTime.of(annee, mois, jour, heureInt, minuteInt);
        }
    }
