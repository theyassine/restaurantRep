package org.example;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class Restaurantmanagement extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("/liste.fxml"));

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


