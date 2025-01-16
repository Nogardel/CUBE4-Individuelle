package com.example.projet_individuel;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Création d'une vue basique pour des tests rapides
        Parent root = new StackPane();
        Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("Application de Test JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
