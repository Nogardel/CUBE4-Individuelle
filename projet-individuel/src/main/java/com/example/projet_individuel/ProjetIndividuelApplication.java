package com.example.projet_individuel;

import com.example.projet_individuel.util.SpringFXMLLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.projet_individuel.service.UserService;

@SpringBootApplication
public class ProjetIndividuelApplication extends Application {

	private ConfigurableApplicationContext springContext;
	private static final String ADMIN_PASSWORD = "admin123";

	@Autowired
	private UserService userService;

	@Override
	public void init() {
		springContext = new SpringApplicationBuilder(ProjetIndividuelApplication.class).run();
		userService = springContext.getBean(UserService.class); // Obtenez le bean UserService ici
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
		TextInputDialog emailDialog = new TextInputDialog();
		emailDialog.setTitle("Accès Administrateur");
		emailDialog.setHeaderText("Entrez votre email d'administrateur");
		emailDialog.setContentText("Email:");

		emailDialog.showAndWait().ifPresent(email -> {
			TextInputDialog passwordDialog = new TextInputDialog();
			passwordDialog.setTitle("Accès Administrateur");
			passwordDialog.setHeaderText("Entrez votre mot de passe");
			passwordDialog.setContentText("Mot de passe:");

			passwordDialog.showAndWait().ifPresent(password -> {
				if (userService.validateAdminCredentials(email, password)) {
					showAdminPage(primaryStage);
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Erreur d'authentification");
					alert.setHeaderText(null);
					alert.setContentText("Email ou mot de passe incorrect.");
					alert.showAndWait();
				}
			});
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

	private void retournerInterfaceUtilisateur() {
		try {
			// Charger l'interface utilisateur classique
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main_view.fxml"));
			Parent root = loader.load();

			// Obtenir le Stage actuel à partir d'un Node
			Stage stage = (Stage) root.getScene().getWindow();

			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
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
