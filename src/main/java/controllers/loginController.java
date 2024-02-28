package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    @FXML
    private TextField inputEmail;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private Button loginButton;

    @FXML
    private BorderPane showLogin;
    UserService userService;

    @FXML
    void loginAction(ActionEvent event) throws IOException {
        alerteMessage alert = new alerteMessage();
        if (inputEmail.getText().isEmpty()) {
            alert.errorMessage("veuillez remplir le champ email !");
        }
        if (inputPassword.getText().isEmpty()) {
            alert.errorMessage("veuillez remplir le champ password!");
        }
        String result = userService.login(inputEmail.getText(), inputPassword.getText());
        if (result != "success") {
            alert.errorMessage("Error");
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Back/Template.fxml"));
            Parent root = loader.load();
            inputEmail.getScene().setRoot(root);
        }



    }

    @FXML
    void redirectToForgetPassword(ActionEvent event) {

    }

    @FXML
    void redirectToSignup(ActionEvent event) throws IOException
    { FXMLLoader loader = new FXMLLoader(getClass().getResource("/signup.fxml"));
        Parent root = loader.load();
        inputEmail.getScene().setRoot(root);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userService = new UserService();

    }
}
