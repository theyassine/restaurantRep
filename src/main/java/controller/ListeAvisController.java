package controller;

import entite.Avis;
import entite.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import services.AvisService;

import java.util.List;

public class ListeAvisController {

    @FXML
    private Label id_commentaire;

    @FXML
    private Label id_date;

    @FXML
    private Label id_nbreavis;

    @FXML
    private Label id_nom;
    private int selectedAvisId;
    private int targetRecipeId;

    private AvisService avisService = new AvisService();


    public void initialize() {
        displayAvisData(13);
    }


    private String convertCountToStars(int count) {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < count; i++) {
            stars.append("★");
        }
        return stars.toString();
    }

    public void displayAvisData(int recetteId) {
        Avis avis = avisService.readById(recetteId);

        if (avis != null) {
        int totalAvisCount = avis.getNote();
        String totalAvisStars = convertCountToStars(totalAvisCount);

            id_date.setText(avis.getDate().toString());
            id_commentaire.setText(avis.getCommentaire());
            id_nbreavis.setText(totalAvisStars);
            id_nom.setText(getUserNomById(avis.getIdUser()));

        }
    }
    private String getUserNomById(int userId) {
        User user = avisService.getUserById(userId);
        return user != null ? user.getNom() : "Unknown User";
    }

}
