package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.example.Service.MenuService;
import org.example.entities.Menu;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;


public class paniercontroller {
    private MenuService menuService = new MenuService(); // Instancier le service de menu

    @FXML
    private TableView<Menu> menu_tableView;

    @FXML
    private Label menu_total;

    private double totalP;
    /*public void menuSelectOrder(MouseEvent mouseEvent) {
        productData prod = menu_tableView.getSelectionModel().getSelectedItem();
        int num = menu_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
        // TO GET THE ID PER ORDER
        getid = prod.getId();

    }
    private double totalP;

    public void menuGetTotal() {
        customerID();
        String total = "SELECT SUM(price) FROM customer WHERE customer_id = " + cID;

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(total);
            result = prepare.executeQuery();

            if (result.next()) {
                totalP = result.getDouble("SUM(price)");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void menuDisplayTotal() {
        menuGetTotal();
        menu_total.setText("$" + totalP);
    }

    private double amount;
    private double change;

    public void menuAmount() {
        menuGetTotal();
        if (menu_amount.getText().isEmpty() || totalP == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid :3");
            alert.showAndWait();
        } else {
            amount = Double.parseDouble(menu_amount.getText());
            if (amount < totalP) {
                menu_amount.setText("");
            } else {
                change = (amount - totalP);
                menu_change.setText("$" + change);
            }
        }
    }*/

    public void menuPayBtn() {
        if (totalP == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please choose your order first!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("pay");
            alert.setHeaderText(null);
            alert.setContentText("Please pay");
            alert.showAndWait();
        }
            // Logique pour effectuer le paiement ici
            // Vous pouvez appeler des méthodes de service ou effectuer des opérations directement
            // Par exemple, vous pouvez enregistrer les éléments du panier dans la base de données comme une transaction payée
            // Assurez-vous d'ajuster cette log

       /* if (totalP == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please choose your order first!");
            alert.showAndWait();
        } else {
            menuGetTotal();
            String insertPay = "INSERT INTO receipt (customer_id, total, date, em_username) "
                    + "VALUES(?,?,?,?)";

            connect = database.connectDB();

            try {

                if (amount == 0) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Messaged");
                    alert.setHeaderText(null);
                    alert.setContentText("Something wrong :3");
                    alert.showAndWait();
                } else {
                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure?");
                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.get().equals(ButtonType.OK)) {
                        customerID();
                        menuGetTotal();
                        prepare = connect.prepareStatement(insertPay);
                        prepare.setString(1, String.valueOf(cID));
                        prepare.setString(2, String.valueOf(totalP));

                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                        prepare.setString(3, String.valueOf(sqlDate));
                        prepare.setString(4, data.username);

                        prepare.executeUpdate();

                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Infomation Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successful.");
                        alert.showAndWait();

                        menuShowOrderData();

                    } else {
                        alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Infomation Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Cancelled.");
                        alert.showAndWait();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/

    }

    public void menuRemoveBtn() {}
/*
        if (getid == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the order you want to remove");
            alert.showAndWait();
        } else {
            String deleteData = "DELETE FROM customer WHERE id = " + getid;
            connect = database.connectDB();
            try {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this order?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();
                }

                menuShowOrderData();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }*/


    public void menuReceiptBtn() {
            if (totalP == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please order first");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("you orderdered");
                alert.setHeaderText(null);
                alert.setContentText("you order");
                alert.showAndWait();
            }
        }
                // Logique pour imprimer le reçu
                // Vous pouvez utiliser une bibliothèque de génération de rapports comme JasperReports pour générer le reçu à partir des données du panier
                // Assurez-vous d'ajuster cette logique en fonction de votre architecture et de vos besoins spécifiques}


       /* if (totalP == 0 || menu_amount.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Please order first");
            alert.showAndWait();
        } else {
            HashMap map = new HashMap();
            map.put("getReceipt", (cID - 1));

            try {

                JasperDesign jDesign = JRXmlLoader.load("C:\\Users\\WINDOWS 10\\Documents\\NetBeansProjects\\cafeShopManagementSystem\\src\\cafeshopmanagementsystem\\report.jrxml");
                JasperReport jReport = JasperCompileManager.compileReport(jDesign);
                JasperPrint jPrint = JasperFillManager.fillReport(jReport, map, connect);

                JasperViewer.viewReport(jPrint, false);

                menuRestart();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }*/



    public void menuRestart() {
            totalP = 0;
            // Réinitialiser d'autres variables ou états nécessaires
            menu_total.setText("$0.0");
    }


}
