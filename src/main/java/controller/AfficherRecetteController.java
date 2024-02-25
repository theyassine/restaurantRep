package controller;

import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import entite.Avis;
import entite.Recette;

import entite.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaView;
import com.google.zxing.BarcodeFormat;

import services.AvisService;
import services.PdfExporter;
import services.RecetteService;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherRecetteController implements Initializable {

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label ingredientsLabel;

    @FXML
    private Label stepsLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private Button printButton;

    @FXML
    private ImageView star1;
    @FXML
    private MediaView videoView;
    @FXML
    private TextField id_avis;
    @FXML
    private ImageView star2;

    @FXML
    private ImageView star3;

    @FXML
    private ImageView star4;

    @FXML
    private ImageView star5;

    private ImageView[] starImageViews;

    private Image fullStarImage;
    private Image emptyStarImage;
    private Image hoverStarImage;
    private int currentRating = 0;

    private AvisService avisService;
    private Recette selectedRecipe;
    private User loggedInUser;
    @FXML
    private ImageView qrCodeImageView;

    // Existing code...

    private void generateQRCode(int recipeId) {
        try {
            Recette recette = recetteService.readById(recipeId);

            if (recette != null) {
                // Customize the data string to include more details
                String data = "RecipeID: " + recette.getId() +
                        "\nTitle: " + recette.getTitre() +
                        "\nDescription: " + recette.getDescription() +
                        "\nIngredients: " + recette.getIngredients() +
                        "\nSteps: " + recette.getEtape() +
                        "\nOtherDetails: ...";  // Add any other details you want

                BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 200, 200);
                WritableImage image = new WritableImage(200, 200);

                for (int y = 0; y < 200; y++) {
                    for (int x = 0; x < 200; x++) {
                        image.getPixelWriter().setArgb(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                    }
                }

                qrCodeImageView.setImage(image);
            } else {
                // Handle the case when the recipe with the given ID is not found
                showAlert("Recipe Not Found", "Recipe not found for ID: " + recipeId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while generating the QR code.");
        }
    }


    @FXML
    private void handleStarClick(MouseEvent event) {
        ImageView source = (ImageView) event.getSource();
        int clickedRating = Integer.parseInt(source.getId().substring(4));

        currentRating = clickedRating;
        updateStars(currentRating);
    }

    @FXML
    private void handleStarHover(MouseEvent event) {
        ImageView source = (ImageView) event.getSource();
        int hoveredRating = Integer.parseInt(source.getId().substring(4));

        for (int i = 0; i < starImageViews.length; i++) {
            if (i < hoveredRating) {
                starImageViews[i].setImage(hoverStarImage);
            } else {
                starImageViews[i].setImage(emptyStarImage);
            }
        }
    }

    @FXML
    private void handleStarExit(MouseEvent event) {
        updateStars(currentRating);
    }

    @FXML
    void AjouterAvis(ActionEvent event) {
         String comment = id_avis.getText();

        if (currentRating == 0) {
            showAlert("Erreur", "Veuillez sélectionner une note avant d'ajouter un avis.");
            return;
        }

        Avis avis = new Avis();
        avis.setNote(currentRating);
        avis.setCommentaire(comment);
        avis.setIdRecette(targetRecipeId);
        avis.setIdUser(targetAvisId);

        // Add the logic to set other attributes of the avis object if needed

        try {
            avisService.add(avis);
            showAlert("Avis ajouté", "Votre avis a été ajouté avec succès.");
        } catch (RuntimeException e) {
            showAlert("Erreur", "Une erreur est survenue lors de l'ajout de l'avis. Veuillez réessayer.");
            e.printStackTrace();  // You may want to log the exception for debugging purposes
        }
    }

    private void updateStars(int rating) {
        for (int i = 0; i < starImageViews.length; i++) {
            starImageViews[i].setImage(i < rating ? fullStarImage : emptyStarImage);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        starImageViews = new ImageView[]{star1, star2, star3, star4, star5};

        fullStarImage = new Image(getClass().getResource("/img/star.png").toExternalForm());
        emptyStarImage = new Image(getClass().getResource("/img/empty_star.png").toExternalForm());
        hoverStarImage = new Image(getClass().getResource("/img/star.png").toExternalForm());

        for (ImageView starImageView : starImageViews) {
            starImageView.setImage(emptyStarImage);
        }

        avisService = new AvisService();
    }


    @FXML
    void handlePrintButtonClick(ActionEvent event) {
        pdfExporter.exportToPdf(recettes, outputPath, targetRecipeId);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("Merci pour l'impression");
        alert.showAndWait();
    }

    private RecetteService recetteService = new RecetteService();
    private PdfExporter pdfExporter = new PdfExporter();
    List<Recette> recettes = recetteService.readAll();
    private String outputPath = "recette.pdf";
    private int targetRecipeId;

    private int targetAvisId;

    public void displayRecetteData(int recetteId) {
        Recette recette = recetteService.readById(recetteId);
        this.targetRecipeId = recetteId;
        this.targetAvisId = recette.getId_user();

        if (recette != null) {
            titleLabel.setText("" + recette.getTitre());
            descriptionLabel.setText("" + recette.getDescription());
            ingredientsLabel.setText("" + recette.getIngredients());
            stepsLabel.setText("" + recette.getEtape());

            if (recette.getImage() != null) {
                try {
                    String imagePath = recette.getImage();
                    File file = new File(imagePath);
                    String imageUrl = file.toURI().toURL().toString();
                    Image image = new Image(imageUrl);
                    imageView.setImage(image);
                } catch (Exception e) {
                    e.printStackTrace();
                    // Handle the exception and inform the user if necessary
                }

            }

            if (recette.getVideo() != null && !recette.getVideo().isEmpty()) {
                Media videoMedia = new Media(new File(recette.getVideo()).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(videoMedia);
                videoView.setMediaPlayer(mediaPlayer);
                mediaPlayer.play();
            }
            generateQRCode(targetRecipeId);

        }
    }


}