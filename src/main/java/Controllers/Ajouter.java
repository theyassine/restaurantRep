package Controllers;

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

public class Ajouter {
    //@FXML
   // private AnchorPane main_form;


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




    @FXML
        private void initialize() {
            // Vous pouvez effectuer toutes les tâches d'initialisation ici
        }

        @FXML
        private void ajouterResto(ActionEvent event) throws IOException {
            // Obtenir les données des champs de l'interface utilisateur
            String titre = titreField.getText();
            String speciality = SpecialityField.getText();
            String telephone = telephoneField.getText();
            String description = descriptionField.getText();
            String place = placeField.getText();
            String rate = rateField.getText();
            String image = cv.getText();

            // Valider les champs requis
            if (titre.isEmpty() || speciality.isEmpty() || telephone.isEmpty() || description.isEmpty() || place.isEmpty() || rate.isEmpty()) {
                // Afficher une alerte ou gérer l'erreur de validation selon les besoins
                System.out.println("Veuillez remplir tous les champs requis.");
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


            // Ajouter le restaurant via le service
            RestaurantService restaurantService = new RestaurantService();
            restaurantService.add(recette);

            // Optionnellement, afficher un message de succès ou réinitialiser le formulaire
            System.out.println("Restaurant ajouté avec succès !");
            clearForm();
        }

        // Méthode pour effacer le formulaire

    //public void browseImage(ActionEvent actionEvent) {
      //  FileChooser fileChooser = new FileChooser();
        //fileChooser.setTitle("Select Image File");
       // File selectedFile = fileChooser.showOpenDialog(null);

        //if (selectedFile != null) {
          //  selectedImagePathLabel.setText("Selected Image: " + selectedFile.getAbsolutePath());
        //}
    //}


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
            System.out.println("No file selected.");
        }

    }
        /*
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*png", "*jpg"));

        File file = openFile.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {

            data1.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 120, 127, false, true);

            inventory_imageView.setImage(image);
        }
    }*/
}
