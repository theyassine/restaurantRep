package controller;

import entite.Recette;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import services.RecetteService;

import java.io.File;

public class test {

    @FXML
    private Button addStudents_addBtn;

    @FXML
    private DatePicker addStudents_birth;

    @FXML
    private Button addStudents_btn;

    @FXML
    private Button addStudents_clearBtn;

    @FXML
    private TableColumn<?, ?> addStudents_col_birth;

    @FXML
    private TableColumn<?, ?> addStudents_col_course;

    @FXML
    private TableColumn<?, ?> addStudents_col_firstName;

    @FXML
    private TableColumn<?, ?> addStudents_col_gender;

    @FXML
    private TableColumn<?, ?> addStudents_col_lastName;

    @FXML
    private TableColumn<?, ?> addStudents_col_status;

    @FXML
    private TableColumn<?, ?> addStudents_col_studentNum;

    @FXML
    private TableColumn<?, ?> addStudents_col_year;

    @FXML
    private ComboBox<?> addStudents_course;

    @FXML
    private Button addStudents_deleteBtn;

    @FXML
    private TextField addStudents_firstName;

    @FXML
    private AnchorPane addStudents_form;

    @FXML
    private ComboBox<?> addStudents_gender;

    @FXML
    private ImageView addStudents_imageView;
    @FXML
    private ImageView img1;


    @FXML
    private Button addStudents_insertBtn;

    @FXML
    private TextField addStudents_lastName;

    @FXML
    private TextField addStudents_search;
    @FXML
    private TextField imagetxf;
    @FXML
    private ComboBox<?> addStudents_status;

    @FXML
    private TextField addStudents_studentNum;

    @FXML
    private TableView<?> addStudents_tableView;

    @FXML
    private Button addStudents_updateBtn;

    @FXML
    private ComboBox<?> addStudents_year;

    @FXML
    private Button availableCourse_addBtn;

    @FXML
    private Button availableCourse_btn;

    @FXML
    private Button availableCourse_clearBtn;

    @FXML
    private TableColumn<?, ?> availableCourse_col_course;

    @FXML
    private TableColumn<?, ?> availableCourse_col_degree;

    @FXML
    private TableColumn<?, ?> availableCourse_col_description;

    @FXML
    private TextField availableCourse_course;

    @FXML
    private TextField availableCourse_degree;

    @FXML
    private Button availableCourse_deleteBtn;

    @FXML
    private TextField availableCourse_description;

    @FXML
    private AnchorPane availableCourse_form;

    @FXML
    private TableView<?> availableCourse_tableView;

    @FXML
    private Button availableCourse_updateBtn;

    @FXML
    private TableColumn<?, ?> col_des;

    @FXML
    private TableColumn<?, ?> col_titre;


    @FXML
    private TextField descriptionField;

    @FXML
    private TextField etapeField;

    @FXML
    private Button home_btn;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Button id_delete;

    @FXML
    private TextField ingredientsField;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button studentGrade_btn;

    @FXML
    private Button studentGrade_clearBtn;

    @FXML
    private TableColumn<?, ?> studentGrade_col_course;

    @FXML
    private TableColumn<?, ?> studentGrade_col_final;

    @FXML
    private TableColumn<?, ?> studentGrade_col_firstSem;

    @FXML
    private TableColumn<?, ?> studentGrade_col_secondSem;

    @FXML
    private TableColumn<?, ?> studentGrade_col_studentNum;

    @FXML
    private TableColumn<?, ?> studentGrade_col_year;

    @FXML
    private Label studentGrade_course;

    @FXML
    private TextField studentGrade_firstSem;

    @FXML
    private AnchorPane studentGrade_form;

    @FXML
    private TextField studentGrade_search;

    @FXML
    private TextField studentGrade_secondSem;

    @FXML
    private TextField studentGrade_studentNum;

    @FXML
    private TableView<?> studentGrade_tableView;

    @FXML
    private Button studentGrade_updateBtn;

    @FXML
    private Label studentGrade_year;

    @FXML
    private TextField titreField;
    @FXML
    private TextField videotxf1;
    @FXML
    private MediaView vid1;
    @FXML
    private TableView<Recette> tv_recette;
    @FXML
    private Label selectedImagePathLabel;

    @FXML
    private Label selectedVideoPathLabel;

    @FXML
    void browseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            selectedImagePathLabel.setText(selectedFile.getAbsolutePath());
        }
    }


    @FXML
    void browseVideo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Video File");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            selectedVideoPathLabel.setText(selectedFile.getAbsolutePath());
        }
    }
    @FXML
    void ModiferRecette(ActionEvent event) {

        // Get data from UI fields
        String titre = titreField.getText();
        String description = descriptionField.getText();
        String ingredients = ingredientsField.getText();
        String etape = etapeField.getText();
        String imagePath = selectedImagePathLabel.getText();
        String videoPath = selectedVideoPathLabel.getText();

        // Validate required fields
        if (titre.isEmpty() || description.isEmpty() || ingredients.isEmpty() || etape.isEmpty()) {
            // Display an alert or handle validation error as needed
            System.out.println("Please fill in all required fields.");
            return;
        }

        // Assuming you have a selected recipe to modify
        Recette selectedRecette = tv_recette.getSelectionModel().getSelectedItem();

        if (selectedRecette == null) {
            // Display an alert or handle the case where no recipe is selected
            System.out.println("Aucune recette sélectionnée pour la modification.");
            return;
        }

        // Check which fields have been modified
        if (!titre.equals(selectedRecette.getTitre())) {
            selectedRecette.setTitre(titre);
        }

        if (!description.equals(selectedRecette.getDescription())) {
            selectedRecette.setDescription(description);
        }

        if (!ingredients.equals(selectedRecette.getIngredients())) {
            selectedRecette.setIngredients(ingredients);
        }

        if (!etape.equals(selectedRecette.getEtape())) {
            selectedRecette.setEtape(etape);
        }

        if (!imagePath.equals(selectedRecette.getImage())) {
            selectedRecette.setImage(imagePath);
        }

        if (!videoPath.equals(selectedRecette.getVideo())) {
            selectedRecette.setVideo(videoPath);
        }

        // Assuming you have an instance of RecetteService
        RecetteService recetteService = new RecetteService();
        recetteService.update(selectedRecette);

        tv_recette.refresh();
        // Display a success message or reset the form
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("Recette est modifiée avec succès");
        alert.showAndWait();

        // Clear the form or reset fields
    }



    @FXML
    void supprimer_recette(ActionEvent event) {
        Recette selectedRecette = tv_recette.getSelectionModel().getSelectedItem();

        if (selectedRecette != null) {
            recetteService.delete(selectedRecette);
            tv_recette.getItems().remove(selectedRecette);
            tv_recette.refresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Recette est supprimer");
            alert.showAndWait();
        } else {
            System.out.println("Aucune recette sélectionnée");
        }
    }


    RecetteService recetteService = new RecetteService();


    @FXML
    void initialize() {
        selectedImagePathLabel = new Label();
        selectedVideoPathLabel = new Label();
        ObservableList<Recette> observableList = FXCollections.observableList(recetteService.readAll());
        tv_recette.setItems(observableList);
        col_titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        col_des.setCellValueFactory(new PropertyValueFactory<>("description"));

        tv_recette.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Recette>() {
            @Override
            public void changed(ObservableValue<? extends Recette> observable, Recette oldValue, Recette newValue) {
                // Check if a row is selected
                if (newValue != null) {
                    // Set the values from the selected Recette object to the text fields
                    titreField.setText(newValue.getTitre());
                    descriptionField.setText(newValue.getDescription());
                    etapeField.setText(newValue.getEtape());
                    ingredientsField.setText(newValue.getIngredients());
                    imagetxf.setText(newValue.getImage());
                    videotxf1.setText(newValue.getVideo());


                    // Display the image in the ImageView
                    displayImage(newValue.getImage());
                    displayVideo(newValue.getVideo());

                }
            }
        });
    }

    // Method to display the image in the ImageView
    private void displayImage(String imagePath) {
        try {
            File file = new File(imagePath);
            if (!file.exists()) {
                // Handle the case where the file doesn't exist
                System.out.println("Image file not found: " + imagePath);
                return;
            }

            Image image = new Image(file.toURI().toString());
            img1.setImage(image);
        } catch (Exception e) {
            // Handle other exceptions that might occur during image loading
            System.out.println("Error loading image: " + e.getMessage());
        }
    }
    private void displayVideo(String videoPath) {
        try {
            File file = new File(videoPath);
            if (!file.exists()) {
                // Handle the case where the file doesn't exist
                System.out.println("Video file not found: " + videoPath);
                return;
            }

            Media media = new Media(file.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);

            // Bind the MediaPlayer to the MediaView
            vid1.setMediaPlayer(mediaPlayer);

            // Play the video
            mediaPlayer.play();
        } catch (Exception e) {
            // Handle other exceptions that might occur during video loading
            System.out.println("Error playing video: " + e.getMessage());
        }
    }}