package Controllers;

import Entities.Evennement;
import Service.EvennementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class ListEventsControllers {
    EvennementService evennementService = new EvennementService();

    @FXML
    private Button Addeventbutton;

    @FXML
    private TableColumn<Evennement, String> dateDebutColumn;

    @FXML
    private TableColumn<Evennement, String> dateFinColumn;

    @FXML
    private TableColumn<Evennement, String> descEventColumn;

    @FXML
    private TableView<Evennement> eventTableView;

    @FXML
    private TableColumn<Evennement, String> lieuEvenementColumn;

    @FXML
    private TableColumn<Evennement, Integer> nbrParticipantsColumn;

    @FXML
    private TableColumn<Evennement, String> nomEventColumn;

    @FXML
    private TableColumn<Evennement, String> nomRestoColumn;

    @FXML
    private TableColumn<Evennement, String> timeDebutColumn;

    @FXML
    private TableColumn<Evennement, String> timeFinColumn;

    @FXML
    private TableColumn<Evennement, String> imageColumn;

    @FXML
    void AddEvent(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/AddEvent.fxml"));
            Parent root1 = loader1.load();

            AddEventController addEventController = loader1.getController();

            Scene scene = new Scene(root1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void initialize() {
        try {
            ObservableList<Evennement> observableList = FXCollections.observableList(evennementService.readAll());
            eventTableView.setItems(observableList);
            dateDebutColumn.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
            dateFinColumn.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
            descEventColumn.setCellValueFactory(new PropertyValueFactory<>("desc_event"));
            lieuEvenementColumn.setCellValueFactory(new PropertyValueFactory<>("lieu_evenement"));
            nbrParticipantsColumn.setCellValueFactory(new PropertyValueFactory<>("Nbr_participants"));
            nomEventColumn.setCellValueFactory(new PropertyValueFactory<>("nom_event"));
            nomRestoColumn.setCellValueFactory(new PropertyValueFactory<>("NameResto"));
            timeDebutColumn.setCellValueFactory(new PropertyValueFactory<>("Time_debut"));
            timeFinColumn.setCellValueFactory(new PropertyValueFactory<>("Time_fin"));
            imageColumn.setCellValueFactory(new PropertyValueFactory<>("image_path"));
            imageColumn.setCellFactory(new Callback<TableColumn<Evennement, String>, javafx.scene.control.TableCell<Evennement, String>>() {
                @Override
                public javafx.scene.control.TableCell<Evennement, String> call(TableColumn<Evennement, String> param) {
                    return new javafx.scene.control.TableCell<Evennement, String>() {
                        private final ImageView imageView = new ImageView();

                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);

                            if (item == null || empty) {
                                setGraphic(null);
                            } else {
                                // Load the image and set it in the ImageView
                                Image image = new Image("file:" + item);
                                imageView.setImage(image);

                                // Set larger dimensions for better visibility
                                imageView.setFitWidth(100); // Set the desired width
                                imageView.setFitHeight(100); // Set the desired height

                                setGraphic(imageView);
                            }
                        }
                    };
                }
            });

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void Modifier(ActionEvent event) {

    }

    @FXML
    void Supprimer(ActionEvent event) {

    }
}
