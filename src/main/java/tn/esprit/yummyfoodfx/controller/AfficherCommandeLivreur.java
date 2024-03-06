package tn.esprit.yummyfoodfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import tn.esprit.yummyfoodfx.FXMain;
import tn.esprit.yummyfoodfx.entities.Commande;
import tn.esprit.yummyfoodfx.entities.EtatCommande;
import tn.esprit.yummyfoodfx.entities.EtatLivraison;
import tn.esprit.yummyfoodfx.entities.Livraison;
import tn.esprit.yummyfoodfx.services.OnChangeListener;
import tn.esprit.yummyfoodfx.services.ServiceCommande;
import tn.esprit.yummyfoodfx.services.ServiceLivraison;

import java.io.IOException;
import java.util.List;

public class AfficherCommandeLivreur implements OnChangeListener
{
    @FXML
    private AnchorPane anchore;

    @FXML
    private AnchorPane mainLayer;

    @FXML
    private GridPane grid;

    @FXML
    private Button btnPrete;

    @FXML
    private Button btnAnnuler;

    @FXML
    private Label idgetter;

    int idCom;
    @FXML
    private TextArea tfCommentaire;
    ServiceCommande serviceCommande =new ServiceCommande();
    ServiceLivraison serviceLivraison=new ServiceLivraison();
    @FXML
    public void initialize() {
        refresh();

    }
    public void refresh(){
        grid.getChildren().clear();
        List<Commande> lc=serviceLivraison.afficherLesCommandeDunLivreur(2);//idlogin
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
    void makeCommandeAnnuler(ActionEvent event) {
        Livraison l=serviceLivraison.getLivraisonParCommande(idCom);
        serviceLivraison.supprimer(l.getId());
        refresh();

    }

    @FXML
    void makeCommandePrete(ActionEvent event) {
        Livraison l=serviceLivraison.getLivraisonParCommande(idCom);
        l.setEtatLivraison(EtatLivraison.LIVREE);
        l.setCommentairesLivreur(tfCommentaire.getText());
        serviceLivraison.modifier(l);
        refresh();
    }

    @Override
    public void sendIdCommande(int id) {
        idCom=id;
        idgetter.setText(String.valueOf(id));
        Livraison l=serviceLivraison.getLivraisonParCommande(idCom);
        
        
        if(!l.getEtatLivraison().equals(EtatLivraison.EN_ROUTE)){
            btnPrete.setDisable(true);
            btnAnnuler.setDisable(true);
        }
        else{
            btnPrete.setDisable(false);
            btnAnnuler.setDisable(false);
        }
    }
    @FXML
    void tri(ActionEvent event) {

    }
}