package com.example.projet_individuel.ui;

import com.example.projet_individuel.model.Employe;
import com.example.projet_individuel.model.ServiceEntity;
import com.example.projet_individuel.model.Site;
import com.example.projet_individuel.service.EmployeApiService;
import com.example.projet_individuel.service.ServiceApiService;
import com.example.projet_individuel.service.SiteApiService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MainViewController {

    @Autowired
    private EmployeApiService employeApiService;

    @Autowired
    private SiteApiService siteApiService;

    @Autowired
    private ServiceApiService serviceApiService;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<Site> siteComboBox;

    @FXML
    private ComboBox<ServiceEntity> serviceComboBox;

    @FXML
    private TableView<Employe> employeTableView;

    @FXML
    private TableColumn<Employe, String> nomColumn;

    @FXML
    private TableColumn<Employe, String> prenomColumn;

    @FXML
    private TableColumn<Employe, String> emailColumn;

    @FXML
    private TableColumn<Employe, String> siteColumn;

    @FXML
    private TableColumn<Employe, String> serviceColumn;

    @FXML
    private TextArea detailsArea;

    @FXML
    public void initialize() {
        // Initialiser les colonnes
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        siteColumn.setCellValueFactory(cellData -> cellData.getValue().siteProperty());
        serviceColumn.setCellValueFactory(cellData -> cellData.getValue().serviceProperty());

        // Charger les données initiales
        loadSites();
        loadServices();
        loadEmployes();

        // Écouter les clics sur la table
        employeTableView.setOnMouseClicked(this::handleTableClick);
    }

    private void loadSites() {
        List<Site> sites = siteApiService.getAllSites();
        siteComboBox.getItems().addAll(sites);
    }

    private void loadServices() {
        List<ServiceEntity> services = serviceApiService.getAllServices();
        serviceComboBox.getItems().addAll(services);
    }

    private void loadEmployes() {
        List<Employe> employes = employeApiService.getAllEmployes();
        employeTableView.getItems().setAll(employes);
    }

    @FXML
    private void handleSearch() {
        try {
            // Récupérer les valeurs des champs de recherche
            String searchTerm = searchField.getText();
            Site selectedSite = siteComboBox.getValue();
            ServiceEntity selectedService = serviceComboBox.getValue();

            // Afficher les paramètres pour vérifier qu'ils sont corrects
            System.out.println("Search Term: " + searchTerm);
            System.out.println("Selected Site: " + (selectedSite != null ? selectedSite.getVille() : "None"));
            System.out.println("Selected Service: " + (selectedService != null ? selectedService.getNom() : "None"));

            // Filtrer les employés
            List<Employe> filteredEmployes = employeApiService.searchEmployes(
                    searchTerm,
                    selectedSite != null ? selectedSite.getId() : null,
                    selectedService != null ? selectedService.getId() : null
            );

            // Mettre à jour le tableau avec les données filtrées
            employeTableView.getItems().setAll(filteredEmployes);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Selected Service: ");
        }
    }


    @FXML
    private void handleTableClick(MouseEvent event) {
        Employe selectedEmploye = employeTableView.getSelectionModel().getSelectedItem();
        if (selectedEmploye != null) {
            // Construire les détails ligne par ligne
            StringBuilder details = new StringBuilder();
            details.append("Nom : ").append(selectedEmploye.getNom()).append("\n");
            details.append("Prénom : ").append(selectedEmploye.getPrenom()).append("\n");
            details.append("Email : ").append(selectedEmploye.getEmail()).append("\n");
            details.append("Téléphone fixe : ").append(selectedEmploye.getTelephoneFixe() != null ? selectedEmploye.getTelephoneFixe() : "N/A").append("\n");
            details.append("Téléphone portable : ").append(selectedEmploye.getTelephonePortable() != null ? selectedEmploye.getTelephonePortable() : "N/A").append("\n");
            details.append("Site : ").append(selectedEmploye.getSite() != null ? selectedEmploye.getSite().getVille() : "N/A").append("\n");
            details.append("Service : ").append(selectedEmploye.getService() != null ? selectedEmploye.getService().getNom() : "N/A").append("\n");

            // Afficher les détails dans le TextArea
            detailsArea.setText(details.toString());
        }
    }
}
