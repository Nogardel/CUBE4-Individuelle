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
        String searchTerm = searchField.getText();
        Site selectedSite = siteComboBox.getValue();
        ServiceEntity selectedService = serviceComboBox.getValue();

        // Passez les objets directement
        List<Employe> filteredEmployes = employeApiService.searchEmployes(searchTerm, selectedSite, selectedService);
        employeTableView.getItems().setAll(filteredEmployes);
    }


    private void handleTableClick(MouseEvent event) {
        Employe selectedEmploye = employeTableView.getSelectionModel().getSelectedItem();
        if (selectedEmploye != null) {
            detailsArea.setText(selectedEmploye.toString());
        }
    }
}
