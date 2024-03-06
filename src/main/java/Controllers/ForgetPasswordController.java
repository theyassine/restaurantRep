package Controllers;

import Controllers.CodeValidationController;
import Controllers.alerteMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Service.UserService;
import org.example.Utils.EmailSender;
import org.example.entities.User;
import org.example.entities.role;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


public class ForgetPasswordController implements Initializable {




    @FXML
    private TextField emailInput;
    int verificationCode = 0;
    UserService serviceUser =new UserService();




    @FXML
    void sendEmail(ActionEvent event) throws IOException {
        alerteMessage alert = new alerteMessage();
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        if (emailInput.getText().isEmpty()) {
            alert.errorMessage("Entrer votre email");
        } else if (!pattern.matcher(emailInput.getText()).matches()) {
            alert.errorMessage("Email invalide");
        } else {
            boolean emailExists = serviceUser.emailExists(emailInput.getText());
            if (!emailExists) {
                alert.errorMessage("L'email n'existe pas dans la base de données");
            } else {
                Random rand = new Random();
                verificationCode = rand.nextInt(900000) + 100000;

                EmailSender emailSender = new EmailSender();
                emailSender.sendMail(emailInput.getText(), "Reset password code", "Voici le code de vérification pour changer votre mot de passe : " + verificationCode);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CodeValidation.fxml"));
                Parent root = loader.load();

                CodeValidationController codeValidationController = loader.getController();

                codeValidationController.setVerificationCode(verificationCode);
                codeValidationController.setEmail(emailInput.getText());

                Scene scene = new Scene(root);
                Stage stage = (Stage) emailInput.getScene().getWindow();
                stage.setScene(scene);
                stage.show();}}}
    public int getVerificationCode() {
        return verificationCode;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }



}
