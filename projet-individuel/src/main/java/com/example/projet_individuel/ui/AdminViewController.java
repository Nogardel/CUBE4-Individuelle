package com.example.projet_individuel.ui;

import com.example.projet_individuel.model.Employe;
import com.example.projet_individuel.model.ServiceEntity;
import com.example.projet_individuel.model.Site;
import com.example.projet_individuel.service.EmployeApiService;
import com.example.projet_individuel.service.ServiceApiService;
import com.example.projet_individuel.service.SiteApiService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminViewController {

    @Autowired
    private EmployeApiService employeApiService;

    @Autowired
    private SiteApiService siteApiService;

    @Autowired
    private ServiceApiService serviceApiService;

    @FXML
    private TableView<Employe> employeTableView;
    @FXML
    private TableView<Site> siteTableView;
    @FXML
    private TableView<ServiceEntity> serviceTableView;

    @FXML
    private TableColumn<Employe, String> nomColumn;
    @FXML
    private TableColumn<Employe, String> prenomColumn;
    @FXML
    private TableColumn<Employe, String> telephoneFixeColumn;
    @FXML
    private TableColumn<Employe, String> telephonePortableColumn;
    @FXML
    private TableColumn<Employe, String> emailColumn;
    @FXML
    private TableColumn<Employe, String> siteColumn;
    @FXML
    private TableColumn<Employe, String> serviceColumn;

    @FXML
    private TableColumn<Site, String> villeColumn;
    @FXML
    private TableColumn<ServiceEntity, String> serviceNomColumn;

    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField telephoneFixeField;
    @FXML
    private TextField telephonePortableField;
    @FXML
    private TextField emailField;
    @FXML
    private ComboBox<Site> siteComboBox;
    @FXML
    private ComboBox<ServiceEntity> serviceComboBox;

    @FXML
    private TextField serviceNameField;

    @FXML
    private TextField siteNameField;

    @FXML
    public void initialize() {
        // Initialiser les colonnes des employés
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        telephoneFixeColumn.setCellValueFactory(new PropertyValueFactory<>("telephoneFixe"));
        telephonePortableColumn.setCellValueFactory(new PropertyValueFactory<>("telephonePortable"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        siteColumn.setCellValueFactory(new PropertyValueFactory<>("site"));
        serviceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));

        // Initialiser les colonnes des sites et services
        villeColumn.setCellValueFactory(new PropertyValueFactory<>("ville"));
        serviceNomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

        // Charger les données initiales
        loadEmployes();
        loadSites();
        loadServices();

        // Remplir les ComboBox
        fillSiteComboBox();
        fillServiceComboBox();

        // Ajouter un écouteur pour la sélection d'un employé
        employeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillEmployeForm(newValue);
            }
        });

        // Ajouter un écouteur pour la sélection d'un service
        serviceTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillServiceForm(newValue);
            }
        });

        // Ajouter un écouteur pour la sélection d'un site
        siteTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillSiteForm(newValue);
            }
        });
    }

    private void loadEmployes() {
        List<Employe> employes = employeApiService.getAllEmployes();
        employeTableView.getItems().setAll(employes);
    }

    private void loadSites() {
        List<Site> sites = siteApiService.getAllSites();
        siteTableView.getItems().setAll(sites);
    }

    private void loadServices() {
        List<ServiceEntity> services = serviceApiService.getAllServices();
        serviceTableView.getItems().setAll(services);
    }

    private void fillSiteComboBox() {
        List<Site> sites = siteApiService.getAllSites();
        siteComboBox.getItems().setAll(sites);
    }

    private void fillServiceComboBox() {
        List<ServiceEntity> services = serviceApiService.getAllServices();
        serviceComboBox.getItems().setAll(services);
    }

    private void fillEmployeForm(Employe employe) {
        nomField.setText(employe.getNom());
        prenomField.setText(employe.getPrenom());
        telephoneFixeField.setText(employe.getTelephoneFixe());
        telephonePortableField.setText(employe.getTelephonePortable());
        emailField.setText(employe.getEmail());
        siteComboBox.setValue(employe.getSite());
        serviceComboBox.setValue(employe.getService());
    }

    private void fillServiceForm(ServiceEntity service) {
        serviceNameField.setText(service.getNom());
    }

    private void fillSiteForm(Site site) {
        siteNameField.setText(site.getVille());
    }

    // Méthodes pour gérer les employés
    @FXML
    private void addEmploye() {
        Employe newEmploye = new Employe();
        newEmploye.setNom(nomField.getText());
        newEmploye.setPrenom(prenomField.getText());
        newEmploye.setTelephoneFixe(telephoneFixeField.getText());
        newEmploye.setTelephonePortable(telephonePortableField.getText());
        newEmploye.setEmail(emailField.getText());
        newEmploye.setSite(siteComboBox.getValue());
        newEmploye.setService(serviceComboBox.getValue());

        employeApiService.createEmploye(newEmploye);
        loadEmployes(); // Recharger les données après ajout
    }

    @FXML
    private void editEmploye() {
        Employe selectedEmploye = employeTableView.getSelectionModel().getSelectedItem();
        if (selectedEmploye != null) {
            selectedEmploye.setNom(nomField.getText());
            selectedEmploye.setPrenom(prenomField.getText());
            selectedEmploye.setTelephoneFixe(telephoneFixeField.getText());
            selectedEmploye.setTelephonePortable(telephonePortableField.getText());
            selectedEmploye.setEmail(emailField.getText());
            selectedEmploye.setSite(siteComboBox.getValue());
            selectedEmploye.setService(serviceComboBox.getValue());

            employeApiService.updateEmploye(selectedEmploye.getId(), selectedEmploye);
            loadEmployes(); // Recharger les données après modification
        }
    }

    @FXML
    private void deleteEmploye() {
        Employe selectedEmploye = employeTableView.getSelectionModel().getSelectedItem();
        if (selectedEmploye != null) {
            employeApiService.deleteEmploye(selectedEmploye.getId());
            loadEmployes(); // Recharger les données après suppression
        }
    }

    // Méthodes pour gérer les sites
    @FXML
    private void addSite() {
        Site newSite = new Site();
        newSite.setVille(siteNameField.getText());

        siteApiService.createSite(newSite);
        loadSites(); // Recharger les données après ajout
    }

    @FXML
    private void editSite() {
        Site selectedSite = siteTableView.getSelectionModel().getSelectedItem();
        if (selectedSite != null) {
            selectedSite.setVille(siteNameField.getText());

            siteApiService.updateSite(selectedSite.getId(), selectedSite);
            loadSites(); // Recharger les données après modification
        }
    }

    @FXML
    private void deleteSite() {
        Site selectedSite = siteTableView.getSelectionModel().getSelectedItem();
        if (selectedSite != null) {
            siteApiService.deleteSite(selectedSite.getId());
            loadSites(); // Recharger les données après suppression
        }
    }

    // Méthodes pour gérer les services
    @FXML
    private void addService() {
        ServiceEntity newService = new ServiceEntity();
        newService.setNom(serviceNameField.getText());

        serviceApiService.createService(newService);
        loadServices(); // Recharger les données après ajout
    }

    @FXML
    private void editService() {
        ServiceEntity selectedService = serviceTableView.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            selectedService.setNom(serviceNameField.getText());

            serviceApiService.updateService(selectedService.getId(), selectedService);
            loadServices(); // Recharger les données après modification
        }
    }

    @FXML
    private void deleteService() {
        ServiceEntity selectedService = serviceTableView.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            serviceApiService.deleteService(selectedService.getId());
            loadServices(); // Recharger les données après suppression
        }
    }

    @FXML
    private void refreshData() {
        fillSiteComboBox();
        fillServiceComboBox();
        System.out.println("Données rafraîchies");
    }
}
