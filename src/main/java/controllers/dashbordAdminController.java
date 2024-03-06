package controllers;

import entities.User;
import entities.role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mindrot.jbcrypt.BCrypt;
import services.UserService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class dashbordAdminController implements Initializable {
    UserService US =new UserService();

    @FXML
    private TableColumn<User, String> colEmail;

    @FXML
    private TableColumn<User,Integer> colId;

    @FXML
    private TableColumn<User, String> colNom;

    @FXML
    private TableColumn<User, String> colPrenom;

    @FXML
    private TableColumn<User, String> colPwd;

    @FXML
    private TableColumn<User, String> colRole;

    @FXML
    private TextField inputEmail;

    @FXML
    private TextField inputNom;

    @FXML
    private TextField inputPrenom;

    @FXML
    private PasswordField inputPwd;

    @FXML
    private ComboBox<entities.role> inputRole;
    @FXML
    private TableView<User> userTable;



    @FXML
    void addAction(ActionEvent event) {alerteMessage alert = new alerteMessage();
        if (inputNom.getText().isEmpty() || inputPrenom.getText().isEmpty() || inputEmail.getText().isEmpty() || inputPwd.getText().isEmpty() || inputRole.getSelectionModel().getSelectedItem() == null) {
            alert.errorMessage("Tous les champs doivent être remplis !");
        } else {
            Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
            if (!pattern.matcher(inputEmail.getText()).matches()) {
                alert.errorMessage("L'adresse email n'est pas valide !");
            } else if (inputPwd.getText().length() < 8) {
                alert.errorMessage("Le mot de passe doit comporter au moins 8 caractères !");
            } else {
                String hashedPassword = BCrypt.hashpw(inputPwd.getText(), BCrypt.gensalt());
                User newUser = new User(inputNom.getText(), inputPrenom.getText(), inputEmail.getText(), hashedPassword, inputRole.getValue());
                US.add(newUser);
                alert.successMessage("Votre compte a été enregistré avec succès !");
                populateTable();
            }
        }}



    @FXML
    void deleteAction(ActionEvent event) {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            alerteMessage alert = new alerteMessage();
            alert.errorMessage("Veuillez sélectionner un utilisateur à supprimer !");
        } else {
            alerteMessage alert = new alerteMessage();
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText("Voulez-vous vraiment supprimer cet utilisateur ?");
            confirmationAlert.setContentText("Nom : " + selectedUser.getNom() + "\n"
                    + "Prénom : " + selectedUser.getPrenom() + "\n"
                    + "Email : " + selectedUser.getEmail());

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                UserService userService = new UserService();
                userService.delete(selectedUser);
                alert.successMessage("L'utilisateur a été supprimé avec succès !");

                populateTable();
            }
        }

    }
    @FXML
    void OnExport(ActionEvent event) {// Get data from ListView
        // Create a new Excel workbook
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("User");

            // Create headers
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Nom");
            headerRow.createCell(2).setCellValue("Prénom");
            headerRow.createCell(3).setCellValue("Email");
            headerRow.createCell(4).setCellValue("Pwd");
            headerRow.createCell(5).setCellValue("Role");

            // Fill data rows
            ObservableList<User> userList = userTable.getItems();
            int rowNum = 1;
            for (User utilisateur : userList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(utilisateur.getId());
                row.createCell(1).setCellValue(utilisateur.getNom());
                row.createCell(2).setCellValue(utilisateur.getPrenom());
                row.createCell(3).setCellValue(utilisateur.getEmail());
                row.createCell(4).setCellValue(utilisateur.getPwd());
                row.createCell(5).setCellValue(utilisateur.getRole().toString());
            }

            try (FileOutputStream fileOut = new FileOutputStream("utilisateurs.xlsx")) {
                workbook.write(fileOut);
                System.out.println("Exported successfully to utilisateurs.xlsx");
            }
        } catch (IOException e) {
            System.out.println("Error exporting to Excel: " + e.getMessage());
        }
    }

    @FXML
    void redirectToEvent(ActionEvent event) {

    }

    @FXML
    void redirectToPub(ActionEvent event) {

    }

    @FXML
    void redirectToRestaurant(ActionEvent event) {

    }

    @FXML
    void rediretToMenu(ActionEvent event) {

    }




    @FXML
    void updateAction(ActionEvent event) {// Vérifier si une ligne est sélectionnée dans le TableView
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            // Aucune ligne sélectionnée, afficher un message d'erreur
            alerteMessage alert = new alerteMessage();
            alert.errorMessage("Veuillez sélectionner un utilisateur à mettre à jour !");
            return;
        }

        String newNom = inputNom.getText();
        String newPrenom = inputPrenom.getText();
        String newEmail = inputEmail.getText();
        entities.role newRole = inputRole.getValue();

        if (newNom.isEmpty() || newPrenom.isEmpty() || newEmail.isEmpty() || newRole == null) {
            alerteMessage alert = new alerteMessage();
            alert.errorMessage("Tous les champs doivent être remplis !");
            return;
        }


        selectedUser.setNom(newNom);
        selectedUser.setPrenom(newPrenom);
        selectedUser.setEmail(newEmail);
        selectedUser.setRole(newRole);

        US.update(selectedUser);

        populateTable();

        alerteMessage alert = new alerteMessage();
        alert.successMessage("Utilisateur mis à jour avec succès !");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPwd.setCellValueFactory(new PropertyValueFactory<>("pwd"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        populateTable();
        ObservableList<entities.role> roleChoices = FXCollections.observableArrayList(
                role.GERANT, role.LIVREUR
        );


        inputRole.setItems(roleChoices);
        US = new UserService();
        userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Remplir les champs de texte avec les données de l'utilisateur sélectionné
                inputNom.setText(newSelection.getNom());
                inputPrenom.setText(newSelection.getPrenom());
                inputEmail.setText(newSelection.getEmail());
                inputRole.setValue(newSelection.getRole());
            } else {
                // Effacer les champs de texte si aucune ligne n'est sélectionnée
                inputNom.clear();
                inputPrenom.clear();
                inputEmail.clear();
                inputRole.getSelectionModel().clearSelection();
            }
        });


    }
    private void populateTable() {
        UserService userService = new UserService();
        List<User> userList = userService.readAll();
        ObservableList<User> observableUserList = FXCollections.observableArrayList(userList);
        userTable.setItems(observableUserList);

    }
}
