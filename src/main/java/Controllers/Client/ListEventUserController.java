package Controllers;

import Entities.Evennement;
import Service.EvennementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;

public class ListEventUserController {

    @FXML
    private GridPane grid;

    @FXML
    private TextField search;

    @FXML
    void searchEvents() {
        updateEvents(search.getText());
    }

    @FXML
    void listevnts(ActionEvent event) {
        updateEvents(search.getText());
    }

    public void initialize() {
        updateEvents("");
    }

    private void updateEvents(String filter) {
        EvennementService eventService = new EvennementService();
        List<Evennement> filteredEvents = eventService.searchByNom(filter);

        grid.getChildren().clear();

        int columnIndex = 0;
        int rowIndex = 1;

        for (Evennement event : filteredEvents) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/cardEvent.fxml"));
                Node cardEventNode = loader.load();

                CardEventsController cardController = loader.getController();
                cardController.displayEventData(event);

                grid.add(cardEventNode, columnIndex, rowIndex);

                columnIndex++;
                if (columnIndex == 2) {
                    columnIndex = 0;
                    ++rowIndex;
                }
                GridPane.setMargin(cardEventNode, new Insets(10.0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
