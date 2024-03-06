package tn.esprit.yummyfoodfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
       FXMLLoader fxmlLoader = new FXMLLoader(FXMain.class.getResource("afficher-commande-livreur.fxml"));//LIVREUR
        //FXMLLoader fxmlLoader = new FXMLLoader(FXMain.class.getResource("ajouter-commande-view.fxml"));//USER
        //FXMLLoader fxmlLoader = new FXMLLoader(FXMain.class.getResource("afficher-commande-view.fxml"));//ADMIN
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}