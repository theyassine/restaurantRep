    package Controllers;

    import Entities.Evennement;
    import Entities.Reservation;
    import Service.EvennementService;
    import Service.ReservationService;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.geometry.Insets;
    import javafx.scene.Node;
    import javafx.scene.layout.GridPane;
    import javafx.event.ActionEvent;


    import java.io.IOException;
    import java.util.List;

    public class MyReservationController {

        @FXML
        private GridPane grid;

        @FXML
        void listevnts(ActionEvent event) {
            updateEvents("");
        }

        public void initialize() {
            updateEvents("");
        }

        private void updateEvents(String filter) {
            EvennementService eventService = new EvennementService();
            ReservationService reservationService = new ReservationService();

            List<Evennement> filteredEvents = eventService.searchByNom(filter);
            List<Reservation> reservations = reservationService.readAll();

            int columnIndex = 0;
            int rowIndex = 1;

            for (Evennement event : filteredEvents) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/CardMyEvent.fxml"));
                    Node cardEventNode = loader.load();

                    CardMyEventController cardController = loader.getController();
                    cardController.displayEventData(event);

                    grid.add(cardEventNode, columnIndex, rowIndex);

                    columnIndex++;
                    if (columnIndex == 1) {
                        columnIndex = 0;
                        ++rowIndex;
                    }

                    // Set margin for spacing between cards
                    GridPane.setMargin(cardEventNode, new Insets(10.0));
                } catch (IOException e) {
                    e.printStackTrace(); // Handle exception appropriately
                }
            }

            // Display reservations in the same grid
            for (Reservation reservation : reservations) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/CardMyEvent.fxml"));
                    Node CardMyEventNode = loader.load();

                    CardMyEventController cardController = loader.getController();
                    cardController.displayReservationData(reservation);

                    grid.add(CardMyEventNode, columnIndex, rowIndex);

                    columnIndex++;
                    if (columnIndex == 1) {
                        columnIndex = 0;
                        ++rowIndex;
                    }

                    // Set margin for spacing between cards
                    GridPane.setMargin(CardMyEventNode, new Insets(10.0));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
