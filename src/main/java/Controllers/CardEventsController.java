package Controllers;

import Entities.Evennement;
import Service.EvennementService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;

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
    void click(MouseEvent event) {

    }

    private EvennementService evennementService = new EvennementService();

    public void initialize() {

    }

    public void displayRecetteData(int id, String imagePath) {
        Evennement evennement = evennementService.readById(id);
        if (evennement != null) {
            nom.setText(evennement.getNom_event());
            desc.setText(evennement.getDesc_event());
            lieuEvenementColumn.setText(evennement.getLieu_evenement());
            dateDebutColumn.setText(evennement.getDate_debut());
            dateFinColumn.setText(evennement.getDate_fin());
            nbrParticipantsColumn.setText(String.valueOf(evennement.getNbr_participants()));
            timeDebutColumn.setText(evennement.getTime_debut());
            timeFinColumn.setText(evennement.getTime_fin());
            nomRestoColumn.setText(evennement.getNameResto());

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
    }
}
