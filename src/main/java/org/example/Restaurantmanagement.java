package org.example;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;

import javafx.stage.Stage;
import javafx.scene.Scene;

public class Restaurantmanagement extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("/Ajouterresto.fxml"));

        // Set the stage title
        primaryStage.setTitle("Recipe Application");

        // Set the scene with the loaded FXML file
        primaryStage.setScene(new Scene(root));

        // Show the stage
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }




}


