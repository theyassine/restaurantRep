package Controllers;

import org.example.entities.User;
import org.example.entities.role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.mindrot.jbcrypt.BCrypt;
import org.example.Service.UserService;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class signupController implements Initializable {
    UserService US;

    @FXML
    private PasswordField confirmPwd;

    @FXML
    private Hyperlink hyperlinkLogin;

    @FXML
    private TextField inputEmail;

    @FXML
    private TextField inputNom;

    @FXML
    private TextField inputPrenom;

    @FXML
    private PasswordField inputPwd;

    @FXML
    private ComboBox<org.example.entities.role> inputRole;

    @FXML
    private Button registerButton;

    @FXML
    private BorderPane signUp;

    @FXML
    void redirectToLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root = loader.load();
        inputEmail.getScene().setRoot(root);

    }

    @FXML
    void selectRole(ActionEvent event) {

    }

    @FXML
    void signupAction(ActionEvent event) throws SQLException {
        alerteMessage alert = new alerteMessage();
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        if (inputNom.getText().isEmpty() || inputPrenom.getText().isEmpty() || inputEmail.getText().isEmpty() || inputPwd.getText().isEmpty() || confirmPwd.getText().isEmpty() || inputRole.getSelectionModel().getSelectedItem() == null) {
            alert.errorMessage("Tous les champs doivent être remplis !");
        }
        else if (!pattern.matcher(inputEmail.getText()).matches()) {
            alert.errorMessage("L'email n'est pas valide !");
        }
        else if (inputPwd.getText().length() < 8) {
            alert.errorMessage("Le mot de passe doit comporter au moins 8 caractères !");
        }

        else if (!inputPwd.getText().equals(confirmPwd.getText())) {
            alert.errorMessage("Le mot de passe n'est pas confirmé !");
        }

        else {


            User newUser = new User(inputNom.getText(), inputPrenom.getText(), inputEmail.getText(), inputPwd.getText(), inputRole.getValue());
            US.add(newUser);
            alert.successMessage("Votre compte a été enregistré avec succès !");

    }}



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<org.example.entities.role> roleChoices = FXCollections.observableArrayList(
                role.GERANT, role.LIVREUR , role.CLIENT
                );


        inputRole.setItems(roleChoices);
        US = new UserService();

    }


}
