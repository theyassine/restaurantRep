package Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.entities.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.Service.RestaurantService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.nio.file.StandardCopyOption;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.scene.control.Alert;

public class Ajouter {

    @FXML
    private ChoiceBox<String> mychoicebox;

    @FXML
        private TextField titreField;

        @FXML
        private TextField SpecialityField;

        @FXML
        private TextField telephoneField;

        @FXML
        private TextField descriptionField;

        @FXML
        private TextField placeField;

        @FXML
        private TextField rateField;
    @FXML
    private TextField cv;
    @FXML
    private Button uploadImg;
    private String imgName ;
    FileChooser fileChooser = new FileChooser();
    private RestaurantService RestaurantService = new RestaurantService();

    private static final String ACCOUNT_SID = "ACc530ea679fa136df8d9d6295e473504d";
    private static final String AUTH_TOKEN = "fb360275c4c55b287929e890754b9125";



    @FXML
        private void initialize() {
        mychoicebox.getItems().addAll("Tunisian", "Fast food", "italien");
            // Vous pouvez effectuer toutes les tâches d'initialisation ici
        }
    @FXML
    private void ajouterResto(ActionEvent event) throws IOException {
        // Obtenir les données des champs de l'interface utilisateur
        String titre = titreField.getText();
        String speciality = mychoicebox.getValue();
        String telephone = telephoneField.getText();
        String description = descriptionField.getText();
        String place = placeField.getText();
        String rate = rateField.getText();
        String image = cv.getText();

        // Valider les champs requis
        if (titre.isEmpty() || speciality == null || telephone.isEmpty() || description.isEmpty() || place.isEmpty() || rate.isEmpty()) {
            // Afficher une alerte ou gérer l'erreur de validation selon les besoins
            showAlert("Veuillez remplir tous les champs requis.");
            return;
        }

        // Valider que le champ de téléphone ne contient que des chiffres
        if (!telephone.matches("\\d+")) {
            showAlert("Le numéro de téléphone ne peut contenir que des chiffres.");
            return;
        }

        // Valider que le champ de note (rate) contient uniquement des lettres


        // Vérifier si un restaurant avec le même nom existe déjà dans la base de données
        RestaurantService restaurantService = new RestaurantService();
        if (restaurantService.restaurantExists(titre, place)) {
            showAlert("Un restaurant avec ce nom existe déjà.");
            return;
        }

        // Créer un nouvel objet Restaurant
        Restaurant recette = new Restaurant();
        recette.setid_categorie(3);
        recette.setNom(titre);
        recette.setSpeciality(speciality);
        recette.setTelephone(telephone);
        recette.setDescription(description);
        recette.setPlace(place);
        recette.setRate(rate);
        recette.setImage(image);
        //generateRestaurantRating(recette.getId());

        restaurantService.add(recette);
        showAlert("VOTRE RESTAURANT AJOUTER AVEC SUCCES");
        sendSMS(titre, telephone);
        // Optionnellement, afficher un message de succès ou réinitialiser le formulaire
        showAlert("VOTRE RESTAURANT AJOUTER AVEC SUCCES");
        System.out.println("Restaurant ajouté avec succès !");
        clearForm();

    }
    private void sendSMS(String titre, String telephone) {
        String contenuSMS = "Votre restaurant " + titre + " a été ajouté avec succès. Contactez-nous pour plus d'informations.";
        String phoneNumber = "+21654512887";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber("+19124552157"),
                contenuSMS
        ).create();

        if (message.getStatus() == Message.Status.FAILED || message.getStatus() == Message.Status.UNDELIVERED) {
            showAlert("Erreur lors de l'envoi du SMS. Veuillez réessayer.");
        }
    }
   /* private void generateRestaurantRating(int restaurantId) {
        try {
            // Remplacez l'URL ci-dessous par l'URL de votre API pour récupérer les évaluations du restaurant
            URL url = new URL("https://api.example.com/restaurant/ratings?restaurantId=" + restaurantId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // Vérifiez si la réponse est réussie (code 200)
            if (conn.getResponseCode() == 200) {
                // Lire la réponse de l'API
                Scanner scanner = new Scanner(conn.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNextLine()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();

                // Traitez la réponse de l'API comme nécessaire
                System.out.println("Évaluations du restaurant " + restaurantId + ": " + response.toString());
            } else {
                // Gérez le cas où la réponse n'est pas réussie
                System.out.println("Échec de la récupération des évaluations pour le restaurant " + restaurantId);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Gérez les exceptions d'entrée/sortie
        }
    }*/

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }




    private void clearForm() {
        titreField.clear();
        SpecialityField.clear();
        telephoneField.clear();
        descriptionField.clear();
        placeField.clear();
        rateField.clear();
           // selectedImagePathLabel.setText("");
    }



    public void uploadImg(javafx.event.ActionEvent event) {
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            // Define the destination directory
            String destinationDirectory = "C:\\xampp\\htdocs\\img";
            // Get the name of the selected file
            String fileName = file.getName();
            // Create a Path for the destination file

            Path destinationPath = new File(destinationDirectory, fileName).toPath();
            try {
                // Copy the selected file to the destination directory
                Files.copy(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File uploaded successfully to: " + destinationPath);
                imgName = fileName;
                uploadImg.setText("ready");
            } catch (IOException e) {
                System.out.println("Error uploading file: " + e.getMessage());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("No file selected.");
            alert.showAndWait();
        }

    }

    public void navversmodif(ActionEvent event) {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/modifier.fxml"));
            Parent root1 = loader1.load();

            // Passer des données à AfficherOffreController si nécessaire
            modifier AO = loader1.getController();
            // controller.setXXX(); // Définir les données à afficher

            Scene scene = new Scene(root1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    }

