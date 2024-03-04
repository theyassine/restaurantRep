package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResetPasswordController implements Initializable {

    @FXML
    private PasswordField confirmInput;

    @FXML
    private TextField emailInput;

    @FXML
    private PasswordField passwordInput;
    UserService serviceUser = new UserService();
    public void setServiceUser(UserService serviceUser) {
        this.serviceUser = serviceUser;
    }


    @FXML
    void saveAction(ActionEvent event) throws IOException {
        alerteMessage alerte = new alerteMessage();
        if (passwordInput.getText().isEmpty()) {
            alerte.errorMessage("le mot de passe est obligatoire");
        } else if (passwordInput.getText().length() < 8) {
            alerte.errorMessage("le mot de passe doit comporter au moins 8 caractères");
        } else if (confirmInput.getText().isEmpty()) {
            alerte.errorMessage("confirmez votre mot de passe");
        } else if (!passwordInput.getText().equals(confirmInput.getText())) {
            alerte.errorMessage("les deux cases ne sont pas compatibles");
        } else {

            if (serviceUser != null) {
                serviceUser.changePassword(emailInput.getText(), passwordInput.getText());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
                Parent root = loader.load();
                emailInput.getScene().setRoot(root);
            } else {
                System.err.println("L'objet serviceUser n'a pas été initialisé.");
            }
    }}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CodeValidationController codeValidationController = new CodeValidationController();
        emailInput.setText(codeValidationController.getEmail());

    }
}