package com.example.projet_individuel;

import com.example.projet_individuel.util.SpringFXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProjetIndividuelApplication extends Application {

	private ConfigurableApplicationContext springContext;

	@Override
	public void init() {
		springContext = new SpringApplicationBuilder(ProjetIndividuelApplication.class).run();
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			SpringFXMLLoader fxmlLoader = new SpringFXMLLoader(springContext);
			Parent root = fxmlLoader.load(getClass().getResource("/fxml/main_view.fxml"));
			Scene scene = new Scene(root);

			primaryStage.setTitle("Application JavaFX");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace(); // Remplacez cela par un logger plus robuste en production.
		}
	}

	@Override
	public void stop() {
		if (springContext != null) {
			springContext.close();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
