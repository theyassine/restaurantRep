package tn.esprit.yummyfoodfx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AfficherCommandeView implements OnChangeListener
{
    @FXML
    private AnchorPane anchore;

    @FXML
    private AnchorPane mainLayer;

    @FXML
    private GridPane grid;
    @FXML
    private Label idgetter;
    @FXML
    private Button btnPrete;
    @FXML
    private TextField tfrecherche;

    @FXML
    private ComboBox<String> cbtri;
    @FXML
    private Button btnAnnuler;
    ServiceCommande serviceCommande =new ServiceCommande();
    ServiceLivraison serviceLivraison=new ServiceLivraison();
    int idCom;
    @FXML
    public void initialize() {
        refresh(serviceCommande.afficher());
        cbtri.getItems().setAll("Adresse","Etat commande","Date commande");
        recherche_avance();

    }
    @FXML
    void tri(ActionEvent event) {
        refresh(serviceCommande.triCommandeParCritere(cbtri.getValue()));
    }
    public void refresh(List<Commande> lc){
        grid.getChildren().clear();

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
    void gotoGestionCommande(ActionEvent event) {

    }

    @FXML
    void gotoGestionLivraison(ActionEvent event) {

    }

    @FXML
    void makeCommandeAnnuler(ActionEvent event) {
        serviceCommande.supprimer(idCom);
        refresh(serviceCommande.afficher());
    }

    @FXML
    void makeCommandePrete(ActionEvent event) {
        serviceCommande.changeCommandeEtat(EtatCommande.PRETE,idCom);

        Livraison l=new Livraison();
        l.setIdCommande(idCom);
        l.setEtatLivraison(EtatLivraison.EN_ROUTE);
        Date d=new Date();
        l.setHeureDepart(d.getHours());
        l.setIdLivreur(2);
        l.setCommentairesLivreur("");
        System.out.println(l);
        serviceLivraison.ajouter(l);
        refresh(serviceCommande.afficher());
    }

    @Override
    public void sendIdCommande(int id) {
        idCom=id;
        idgetter.setText(String.valueOf(id));
        Commande c=serviceCommande.getCommandeById(id);
        if(!c.getEtatCommande().equals(EtatCommande.EN_PREPARATION)){
            btnPrete.setDisable(true);
            btnAnnuler.setDisable(true);
        }
        else{
            btnPrete.setDisable(false);
            btnAnnuler.setDisable(false);
        }
    }
    void recherche_avance(){
        ObservableList<Commande> data= FXCollections.observableArrayList(serviceCommande.afficher());
        FilteredList<Commande> filteredList=new FilteredList<>(data,c->true);
        tfrecherche.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(c->{
                if(newValue.isEmpty()||newValue==null){
                    return true;
                }
                if(c.getAdresse().toLowerCase().contains(newValue.toLowerCase())){
                    return true;
                } else if (c.getRemarque().toLowerCase().contains(newValue.toLowerCase())) {
                    return true;
                }
                else if (String.valueOf(c.getDateCommande()).contains(newValue.toLowerCase())) {
                    return true;
                }
                else if (String.valueOf(c.getModePayement()).toLowerCase().contains(newValue.toLowerCase())) {
                    return true;
                }
                else if (String.valueOf(c.getEtatCommande()).toLowerCase().contains(newValue.toLowerCase())) {
                    return true;
                }
                else {
                    return false;
                }

            });
            refresh(filteredList);
        }));
    }
}