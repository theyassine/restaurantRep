package controllers;
import entities.User;
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
        String email = inputEmail.getText();
        String password = inputPassword.getText();

        if (email.isEmpty()) {
            alert.errorMessage("Veuillez remplir le champ email !");
        } else if (password.isEmpty()) {
            alert.errorMessage("Veuillez remplir le champ password !");
        } else {
            if (email.equals("ghaith.taieb01@gmail.com") && password.equals("nowomennocry01")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashbordAdmin.fxml"));
                Parent root = loader.load();
                inputEmail.getScene().setRoot(root);
            } else {
                String result = userService.login(email, password);
                if (!result.equals("success")) {
                    alert.errorMessage("Erreur d'authentification !");
                } else {
                    User currentUser = UserService.getCurrentUser();
                    if (currentUser != null) {
                        FXMLLoader loader = new FXMLLoader();
                        Parent root;
                        profileGerantController controllerG;
                        profileClientController controllerC;
                        profileLivreurController controllerL;
                        switch (currentUser.getRole()) {
                            case LIVREUR:
                                loader.setLocation(getClass().getResource("/profileLivreur.fxml"));
                                root = loader.load();
                                controllerL = loader.getController();
                                controllerL.initializeUserName(currentUser.getPrenom());
                                inputEmail.getScene().setRoot(root);
                                break;
                            case CLIENT:
                                loader.setLocation(getClass().getResource("/profileClient.fxml"));
                                root = loader.load();
                                controllerC = loader.getController();
                                controllerC.initializeUserName(currentUser.getPrenom());
                                inputEmail.getScene().setRoot(root);
                                break;
                            case GERANT:
                                loader.setLocation(getClass().getResource("/profileGerant.fxml"));
                                root = loader.load();
                                controllerG = loader.getController();
                                controllerG.initializeUserName(currentUser.getPrenom());
                                inputEmail.getScene().setRoot(root);
                                break;

                            default:
                                alert.errorMessage("Erreur: Rôle d'utilisateur non reconnu.");
                                break;
                        }
                    } else {
                        // Gérer le cas où l'utilisateur actuel n'est pas défini
                        alert.errorMessage("Erreur: Impossible de récupérer les informations de l'utilisateur.");
                    }
                }
            }
        }

    }

    @FXML
    void redirectToForgetPassword(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForgetPassword.fxml"));
        Parent root = loader.load();
        inputEmail.getScene().setRoot(root);

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
