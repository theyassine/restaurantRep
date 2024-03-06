package controller;

import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import entite.Avis;
import entite.Recette;

import entite.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaView;
import com.google.zxing.BarcodeFormat;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import services.AvisService;
import services.PdfExporter;
import services.RecetteService;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;


import java.nio.charset.StandardCharsets;
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
    @FXML
    private GridPane grid;

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

    @FXML
    void MesRecette_btn(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/test.fxml"));
            titleLabel.getScene().setRoot(root); // Assuming grid is a control from the current scene
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addRecette_btn(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterRecette.fxml"));
            titleLabel.getScene().setRoot(root); // Assuming grid is a control from the current scene
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void Home_btn(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListeDesRecette.fxml"));
            titleLabel.getScene().setRoot(root); // Assuming grid is a control from the current scene
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateQRCode(int recipeId) {
        try {

            Recette recette = recetteService.readById(recipeId);

            if (recette != null) {

                StringBuilder formattedData = new StringBuilder();
                formattedData.append("|Title: ").append(recette.getTitre())
                        .append("|Ingredients: ").append(formatList(recette.getIngredients()))
                        .append("\n|Les Etapes: ").append(formatSteps(recette.getEtape()));

                byte[] utf8Bytes = formattedData.toString().getBytes(StandardCharsets.UTF_8);
                String encodedData = new String(utf8Bytes, StandardCharsets.UTF_8);

                BitMatrix matrix = new MultiFormatWriter().encode(encodedData, BarcodeFormat.QR_CODE, 226, 174);
                Image image = createImageFromBitMatrix(matrix);
                qrCodeImageView.setImage(image);
            } else {
                showAlert("Recipe Not Found", "Recipe not found for ID: " + recipeId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while generating the QR code.");
        }

    }

    private String formatList(String list) {
        // Split the list by commas and join with newline characters
        return String.join("\n", list.split(", "));
    }

    private String formatSteps(String steps) {
        // Split the steps by newline characters and prepend "Etape" to each
        String[] stepArray = steps.split("\\n");
        StringBuilder formattedSteps = new StringBuilder();
        for (int i = 0; i < stepArray.length; i++) {
            formattedSteps.append("Etape").append(i + 1).append(": ").append(stepArray[i]);
            if (i < stepArray.length - 1) {
                formattedSteps.append("\n");
            }
        }
        return formattedSteps.toString();
    }



    private Image createImageFromBitMatrix(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Use Color.BLACK and Color.WHITE directly
                Paint color = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
                pixelWriter.setColor(x, y, (Color) color);
            }
        }

        return writableImage;
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


        try {
            avisService.add(avis);
            showAlert("Avis ajouté", "Votre avis a été ajouté avec succès.");
            initialize(null, null);
        } catch (RuntimeException e) {
            showAlert("Erreur", "Une erreur est survenue lors de l'ajout de l'avis. Veuillez réessayer.");
            e.printStackTrace();
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
    private int targetRecipeId;
    @FXML
    private Label totalRatingLabel;
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
        videoView.setFitWidth(400);
        videoView.setFitHeight(250);






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
    private int targetAvisId;
    @FXML
    private Button deleteAvisButton;
    public void displayRecetteData(int recetteId) {
        Recette recette = recetteService.readById(recetteId);
        this.targetRecipeId = recetteId;
        this.targetAvisId = recette.getId_user();

        if (recette != null) {

            titleLabel.setText("" + recette.getTitre());
            descriptionLabel.setText("" + recette.getDescription());
            ingredientsLabel.setText(String.join("\n", recette.getIngredients().split(", ")));
            String[] steps = recette.getEtape().split("\\n");
            StringBuilder labeledSteps = new StringBuilder();
            for (int i = 0; i < steps.length; i++) {
                labeledSteps.append("Etape ").append(i + 1).append(": ").append(steps[i]);
                if (i < steps.length - 1) {
                    labeledSteps.append("\n");
                }
                stepsLabel.setText(labeledSteps.toString());

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
            int totalRating = avisService.getTotalRatingForRecipe(targetRecipeId);
            totalRatingLabel.setText("Note totale de cette recette: (" + totalRating+")");
    }

        AvisService avisService1 = new AvisService();
        List<Avis> allRecettes = avisService1.readAvisByRecetteId(targetRecipeId);



        // Load and display each recipe in a CardRecette
        int columnIndex = 0;
        int rowIndex = 1;

        for (Avis avis : allRecettes) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeAvis.fxml"));
                Node cardRecetteNode = loader.load();

                ListeAvisController cardController = loader.getController();
                cardController.displayAvisData(avis.getIdAvis(),targetRecipeId);

                // Set columnIndex to 0 for each recipe to place it in a new row
                columnIndex = 0;

                // Increment rowIndex for each new row
                rowIndex++;

                grid.add(cardRecetteNode, columnIndex, rowIndex);
                this.grid.setMinWidth(-1.0);
                this.grid.setPrefWidth(-1.0);
                this.grid.setMaxWidth(Double.NEGATIVE_INFINITY);
                this.grid.setMinHeight(-1.0);
                this.grid.setPrefHeight(-1.0);
                this.grid.setMaxHeight(Double.NEGATIVE_INFINITY);
                GridPane.setMargin(cardRecetteNode, new Insets(10.0));
            } catch (IOException e) {
                e.printStackTrace(); // Handle exception appropriately
            }
        }

    }
}