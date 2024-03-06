package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import org.example.Service.PanierService;
import org.example.Utils.Data;
import org.example.entities.Menu;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import org.example.entities.Panier;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;


public class paniercontroller {


    @FXML
    private TableView<Panier> menu_tableView;

    @FXML
    private TableColumn<Panier, String> menu_col_productName;

    @FXML
    private TableColumn<Panier, String> menu_col_quantity;

    @FXML
    private TableColumn<Panier, String> menu_col_price;

    @FXML
    private Label menu_total;

    @FXML
    private Button totl;
    @FXML
    private Spinner<Integer> spinner;

    private double totalP;
    private double change;
    private double amount;
    private Connection connexion;



    @FXML
    public void initialize() {
        // Appel de votre service pour obtenir les données du panier depuis la base de données
        PanierService panierService = new PanierService();
        List<Panier> panierItems = panierService.readAll();

        // Ajout des données à la TableView
        menu_tableView.getItems().addAll(panierItems);

        // Liaison des propriétés de l'objet Panier aux colonnes de la TableView
        menu_col_productName.setCellValueFactory(cellData -> cellData.getValue().nom_produitProperty());
        menu_col_quantity.setCellValueFactory(cellData -> cellData.getValue().quantiteProperty());
        menu_col_price.setCellValueFactory(cellData -> cellData.getValue().prixProperty());




        ObservableList<Integer> nombres = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(nombres);
        spinner.setValueFactory(valueFactory);

        // Convertisseur pour afficher les valeurs du spinner
        spinner.getValueFactory().setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                return value.toString();
            }

            @Override
            public Integer fromString(String string) {
                return Integer.valueOf(string);
            }
        });

        // Affichage des données dans la TableView
        refreshTableView();
    }



    public paniercontroller() {
        connexion= Data.getInstance().getCnx();

    }



    public void menuRestart() {
        totalP = 0;
        change = 0;
        amount = 0;
        menu_total.setText("$0.0");

    }
    public void menuGetTotal() {
        String total = "SELECT SUM(prix) FROM panier " ;

        try {

            PreparedStatement statement = connexion.prepareStatement(total);
            ResultSet result=statement.executeQuery();
            if (result.next()) {
                totalP = result.getDouble("SUM(prix)");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void menuDisplayTotal() {
        menuGetTotal();
        menu_total.setText("dt" + totalP);
    }



    public void menuRemoveBtn() {
        Panier selectedPanier = menu_tableView.getSelectionModel().getSelectedItem();
        if (selectedPanier == null) {
            // Aucune ligne sélectionnée, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner la commande que vous souhaitez supprimer");
            alert.showAndWait();
        } else {
            // Une ligne est sélectionnée, procéder à la suppression
            int panierId = selectedPanier.getId(); // Supposons que getId() retourne l'identifiant de la commande
            PanierService panierService = new PanierService();
            panierService.supprimer(panierId); // Appel à la méthode delete de PanierService
            // Actualiser l'affichage des données après la suppression
            menuShowOrderData(); // Vous devez implémenter cette méthode pour actualiser les données dans votre TableView
        }
    }
    void refreshTableView() {
        // Effacement des éléments actuels de la TableView
        menu_tableView.getItems().clear();

        // Rechargement des données depuis la base de données
        PanierService panierService = new PanierService();
        List<Panier> panierItems = panierService.readAll();
        ObservableList<Panier> observablePanierItems = FXCollections.observableArrayList(panierItems);
        menu_tableView.setItems(observablePanierItems);
    }
    public void menuShowOrderData() {
        // Effacer les éléments actuels de la TableView
        menu_tableView.getItems().clear();

        // Recharger les données depuis la base de données et les ajouter à la TableView
        PanierService panierService = new PanierService();
        List<Panier> panierItems = panierService.readAll();
        ObservableList<Panier> observablePanierItems = FXCollections.observableArrayList(panierItems);
        menu_tableView.setItems(observablePanierItems);
    }
    @FXML
    void naviguezVersmenu(ActionEvent event) {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/liste.fxml"));
            Parent root1 = loader1.load();

            // Passer des données à AfficherOffreController si nécessaire
            ListeDesRstoController AO = loader1.getController();
            // controller.setXXX(); // Définir les données à afficher

            Scene scene = new Scene(root1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void modifierQuantite(ActionEvent event) {
        Panier selectedPanier = menu_tableView.getSelectionModel().getSelectedItem();
        if (selectedPanier == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun produit sélectionné",
                    "Veuillez sélectionner un produit à modifier.");
            return;
        }

        // Récupération de la nouvelle quantité depuis le spinner
        int nouvelleQuantite = spinner.getValue();

        // Mise à jour de la quantité dans la base de données
        PanierService panierService = new PanierService();
        selectedPanier.setQuantite(Integer.toString(nouvelleQuantite));

        panierService.update1(selectedPanier);

        // Actualisation de l'affichage dans la TableView
        refreshTableView();
    }

    void showAlert(Alert.AlertType type, String title, String headerText, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }


}







