package Controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CodeValidationController implements Initializable {
    private StringBuilder otpBuilder = new StringBuilder();

    @FXML
    private TextField digit1TextField;

    @FXML
    private TextField digit2TextField;

    @FXML
    private TextField digit3TextField;

    @FXML
    private TextField digit4TextField;

    @FXML
    private TextField digit5TextField;

    @FXML
    private TextField digit6TextField;

    @FXML
    private HBox otpContainer;
    private int verificationCode;
    private String email;
    public void setVerificationCode(int verificationCode) {
        this.verificationCode = verificationCode;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        digit1TextField.textProperty().addListener((obs, oldText, newText) -> {
            try {
                onDigitTextChanged(newText);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        digit2TextField.textProperty().addListener((obs, oldText, newText) -> {
            try {
                onDigitTextChanged(newText);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        digit3TextField.textProperty().addListener((obs, oldText, newText) -> {
            try {
                onDigitTextChanged(newText);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        digit4TextField.textProperty().addListener((obs, oldText, newText) -> {
            try {
                onDigitTextChanged(newText);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        digit5TextField.textProperty().addListener((obs, oldText, newText) -> {
            try {
                onDigitTextChanged(newText);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        digit6TextField.textProperty().addListener((obs, oldText, newText) -> {
            try {
                onDigitTextChanged(newText);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private void onDigitTextChanged(String newText) throws IOException {
        if (newText.length() >= 1) {
            String lastDigit = newText.substring(newText.length() - 1);
            otpBuilder.append(lastDigit);
            if (otpBuilder.length() == 6) {
                validateOTP(otpBuilder.toString());
            }
            focusNextTextField();
        } else if (newText.isEmpty()) {
            otpBuilder.setLength(otpBuilder.length() - 1);
            focusPreviousTextField();
        }
    }
    private void focusNextTextField() {
        int currentIndex = otpContainer.getChildren().indexOf(otpContainer.getScene().getFocusOwner());
        if (currentIndex < 5) {
            TextField nextField = (TextField) otpContainer.getChildren().get(currentIndex + 1);
            nextField.requestFocus();
        }
    }
    private void focusPreviousTextField() {
        int currentIndex = otpContainer.getChildren().indexOf(otpContainer.getScene().getFocusOwner());
        if (currentIndex > 0) {
            TextField previousField = (TextField) otpContainer.getChildren().get(currentIndex - 1);
            previousField.requestFocus();
        }
    }
    private void validateOTP(String otp) throws IOException {
        alerteMessage alerte = new alerteMessage();
        System.out.println(otp);
        if (Integer.toString(verificationCode).equals(otp)) {
            // Charger le fichier FXML de l'interface souhaitée
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ResetPassword.fxml"));
            Parent root = loader.load();

            // Obtenir la scène actuelle et la remplacer par la nouvelle scène
            Scene currentScene = digit1TextField.getScene();
            Scene newScene = new Scene(root);
            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(newScene);
            stage.show();
        } else {
            alerte.errorMessage("Le code n'est pas identique avec celui qui est envoyé ");
        }


}}