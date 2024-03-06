package tn.esprit.yummyfoodfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tn.esprit.yummyfoodfx.FXMain;
import tn.esprit.yummyfoodfx.entities.Commande;
import tn.esprit.yummyfoodfx.entities.EtatCommande;
import tn.esprit.yummyfoodfx.entities.ModePayement;
import tn.esprit.yummyfoodfx.services.OnChangeListener;
import tn.esprit.yummyfoodfx.services.ServiceCommande;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class AjouterCommandeView implements OnChangeListener
{
    @FXML
    private TextField tfadresse;

    @FXML
    private TextField tflong;

    @FXML
    private TextField tflat;

    @FXML
    private TextArea taremarque;

    @FXML
    private ComboBox<ModePayement> cbmodepayement;
    @FXML
    private GridPane grid;
    ServiceCommande sc=new ServiceCommande();
    int idModifier=0;
    @FXML
    private TextField tfrecherche;

    @FXML
    private ComboBox<String> cbtri;

    @FXML
    public void initialize() {
        cbmodepayement.getItems().setAll(ModePayement.values());
        refresh();
    }

    @FXML
    void ajouterCommande(ActionEvent event) {
        Commande c=new Commande();
        c.setEtatCommande(EtatCommande.EN_PREPARATION);
        c.setDateCommande(new Date());
        c.setAdresse(tfadresse.getText());
        c.setLatitude(Double.parseDouble(tflat.getText()));
        c.setLongitude(Double.parseDouble(tflong.getText()));
        c.setRemarque(taremarque.getText());
        c.setIdMenu(1);
        c.setIdClient(1);
        c.setModePayement(cbmodepayement.getValue());
        sc.ajouter(c);
        refresh();
        Notifications.create().title("Ajout commande").text("Ajout commande avec succes").hideAfter(Duration.seconds(5)).showInformation();
    }
    public void refresh() {
        grid.getChildren().clear();
        List<Commande> lc=sc.getCommandeParIdUser(1);//idLoginuser
        int column=0;
        int row=1;
        for(Commande c:lc){
            FXMLLoader fxmlLoader = new FXMLLoader(FXMain.class.getResource("cart-commande-view.fxml"));
            AnchorPane anchorPane= null;
            try {
                anchorPane = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            CartCommandeView controller=fxmlLoader.getController();

            controller.remplireData(c);
            controller.setOnChangeListener(this);
            if(column==2){
                column=0;
                row++;
            }
            grid.add(anchorPane,column++,row);
            GridPane.setMargin(anchorPane,new Insets(10));

        }

    }
    @FXML
    void modifier(ActionEvent event) {
        if(idModifier!=0){
            Commande c=new Commande();
            c.setId(idModifier);
            c.setEtatCommande(EtatCommande.EN_PREPARATION);
            c.setDateCommande(new Date());
            c.setAdresse(tfadresse.getText());
            c.setLatitude(Double.parseDouble(tflat.getText()));
            c.setLongitude(Double.parseDouble(tflong.getText()));
            c.setRemarque(taremarque.getText());
            c.setIdMenu(1);
            c.setIdClient(1);
            c.setModePayement(cbmodepayement.getValue());
            sc.modifier(c);
            refresh();
        }
    }


    @Override
    public void sendIdCommande(int id) {
        idModifier=id;
        Commande c=sc.getCommandeById(id);
        tfadresse.setText(c.getAdresse());
    }
}