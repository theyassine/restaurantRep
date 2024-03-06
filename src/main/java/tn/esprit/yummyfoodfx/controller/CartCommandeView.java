package tn.esprit.yummyfoodfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import org.jxmapviewer.viewer.GeoPosition;
import tn.esprit.yummyfoodfx.entities.Commande;
import tn.esprit.yummyfoodfx.entities.EtatCommande;
import tn.esprit.yummyfoodfx.entities.EtatLivraison;
import tn.esprit.yummyfoodfx.services.OnChangeListener;
import tn.esprit.yummyfoodfx.services.ServiceLivraison;
import tn.esprit.yummyfoodfx.utils.OpenStreetMapFX;

public class CartCommandeView {

    @FXML
    private ImageView img;

    @FXML
    private Label ladresse;

    @FXML
    private Label lmode;

    @FXML
    private Label lremarque;

    @FXML
    private Label ldate;

    @FXML
    private Label letatc;

    @FXML
    private Label letatl;


    Commande c;

    OpenStreetMapFX mapView;
    ServiceLivraison sl=new ServiceLivraison();

    @FXML
    private BorderPane pane;

    @FXML
    void goDown(ActionEvent event) {
        mapView.moveDown();
    }

    @FXML
    void goLeft(ActionEvent event) {
        mapView.moveLeft();
    }

    @FXML
    void goRight(ActionEvent event) {
        mapView.moveRight();
    }

    @FXML
    void goUp(ActionEvent event) {
        mapView.moveUp();
    }
    private OnChangeListener onChangeListener;

    public OnChangeListener getOnChangeListener() {
        return onChangeListener;
    }

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    public void remplireData(Commande c){
        this.c=c;
        mapView = new OpenStreetMapFX(new GeoPosition(c.getLatitude(),c.getLongitude()));
        pane.setCenter(mapView);
        ladresse.setText(c.getAdresse());
        ldate.setText(c.getDateCommande().toString());
        lmode.setText(c.getModePayement().toString());
        lremarque.setText(c.getRemarque());
        EtatCommande etatCommande=c.getEtatCommande();
        letatc.setText(etatCommande.toString());
        switch (etatCommande){
            case EN_PREPARATION :
                letatc.setStyle("-fx-text-fill: orange;");
                break;
            case PRETE :
                    letatc.setStyle("-fx-text-fill: green;");
                    break;
            case ANNULER :
                    letatc.setStyle("-fx-text-fill: red;");
                    break;
        }
        EtatLivraison etat=sl.getEtatLivraisonDuneCommande(c.getId());
        if(etat==null){
            letatl.setText("");
        }
        else{
            letatl.setText(etat.toString());
            switch (etat){
                case EN_ROUTE :
                    letatl.setStyle("-fx-text-fill: bleu;");
                    break;
                case LIVREE:
                    letatl.setStyle("-fx-text-fill: green;");
                    break;
                case ANNULER:
                    letatl.setStyle("-fx-text-fill: red;");
                    break;
            }
        }


    }
    @FXML
    void selectedCommande(MouseEvent event) {
        if(onChangeListener!=null){
            onChangeListener.sendIdCommande(c.getId());
        }

    }

}
