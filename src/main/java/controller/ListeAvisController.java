package controller;

import entite.Avis;
import entite.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import services.AvisService;
public class ListeAvisController {

    @FXML
    private Label id_commentaire;

    @FXML
    private Label id_date;

    @FXML
    private Label id_nbreavis;

    @FXML
    private Label id_nom;


    private AvisService avisService = new AvisService();


    public void initialize() {
        displayAvisData(2);
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

        int totalAvisCount = avis.getNote();
        String totalAvisStars = convertCountToStars(totalAvisCount);
        if (avis != null) {

            // Assuming 'id_date', 'id_nom', 'id_commentaire', 'id_nbreavis' are your other UI components
            id_date.setText(avis.getDate().toString());
             // Adjust based on your 'Avis' class
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
