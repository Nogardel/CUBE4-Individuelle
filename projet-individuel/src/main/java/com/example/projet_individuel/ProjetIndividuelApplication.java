package com.example.projet_individuel;

import com.example.projet_individuel.util.SpringFXMLLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProjetIndividuelApplication extends Application {

	private ConfigurableApplicationContext springContext;
	private static final String ADMIN_PASSWORD = "admin123";

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

			// Configurer l'écouteur pour la combinaison de touches
			scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
				if (event.isControlDown() && event.getCode() == KeyCode.A) { // Exemple de combinaison Ctrl + A
					showPasswordDialog(primaryStage);
				}
			});

			primaryStage.setTitle("Application JavaFX");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace(); // Remplacez cela par un logger plus robuste en production.
		}
	}

	private void showPasswordDialog(Stage primaryStage) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Accès Administrateur");
		dialog.setHeaderText("Entrez le mot de passe administrateur");
		dialog.setContentText("Mot de passe:");

		dialog.showAndWait().ifPresent(password -> {
			if (ADMIN_PASSWORD.equals(password)) {
				// Afficher la page d'administration
				showAdminPage(primaryStage);
			} else {
				// Afficher un message d'erreur
				System.out.println("Mot de passe incorrect");
			}
		});
	}

	private void showAdminPage(Stage primaryStage) {
		try {
			SpringFXMLLoader fxmlLoader = new SpringFXMLLoader(springContext);
			Parent adminRoot = fxmlLoader.load(getClass().getResource("/fxml/admin_view.fxml"));
			Scene adminScene = new Scene(adminRoot);
			primaryStage.setScene(adminScene);
		} catch (Exception e) {
			e.printStackTrace();
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
