package com.example.projet_individuel.service;

import com.example.projet_individuel.model.Employe;
import com.example.projet_individuel.model.ServiceEntity;
import com.example.projet_individuel.model.Site;
import com.example.projet_individuel.repository.EmployeRepository;
import com.example.projet_individuel.repository.ServiceRepository;
import com.example.projet_individuel.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Service
public class EmployeApiService {
    private static final String BASE_URL = "http://localhost:8080/api/employes";
    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    /**
     * Récupérer tous les employés
     */
    public List<Employe> getAllEmployes() {
        return employeRepository.findAll();
    }

    /**
     * Récupérer un employé par ID
     * @throws RuntimeException si l'employé n'existe pas
     */
    public Employe getEmployeById(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé introuvable pour l'ID " + id));
    }

    /**
     * Créer un nouvel employé
     * @throws RuntimeException si nom/prenom manquants
     */
    public Employe createEmploye(Employe employe) {
        // Vérification minimale
        if (employe.getNom() == null || employe.getNom().isBlank()
                || employe.getPrenom() == null || employe.getPrenom().isBlank()) {
            throw new RuntimeException("Nom et Prenom obligatoires pour créer un employé.");
        }
        validateAndSetRelationships(employe);

        return employeRepository.save(employe);
    }

    /**
     * Mettre à jour un employé
     */
    public Employe updateEmploye(Long id, Employe employeDetails) {
        // Vérification minimale
        if (employeDetails.getNom() == null || employeDetails.getNom().isBlank()
                || employeDetails.getPrenom() == null || employeDetails.getPrenom().isBlank()) {
            throw new RuntimeException("Nom et Prenom obligatoires pour mettre à jour un employé.");
        }

        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé introuvable pour l'ID " + id));

        employe.setNom(employeDetails.getNom());
        employe.setPrenom(employeDetails.getPrenom());
        employe.setTelephoneFixe(employeDetails.getTelephoneFixe());
        employe.setTelephonePortable(employeDetails.getTelephonePortable());
        employe.setEmail(employeDetails.getEmail());

        // Gérer site & service
        if (employeDetails.getSite() != null && employeDetails.getSite().getId() != null) {
            Site site = siteRepository.findById(employeDetails.getSite().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Site introuvable pour l'ID " + employeDetails.getSite().getId()));
            employe.setSite(site);
        } else {
            employe.setSite(null);
        }

        if (employeDetails.getService() != null && employeDetails.getService().getId() != null) {
            ServiceEntity service = serviceRepository.findById(employeDetails.getService().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Service introuvable pour l'ID " + employeDetails.getService().getId()));
            employe.setService(service);
        } else {
            employe.setService(null);
        }

        return employeRepository.save(employe);
    }

    /**
     * Supprimer un employé
     */
    public void deleteEmploye(Long id) {
        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé introuvable pour l'ID " + id));
        employeRepository.delete(employe);
    }

    /**
     * Valider et définir les relations pour site et service
     * (uniquement pour la création, la mise à jour c'est un autre flux)
     */
    private void validateAndSetRelationships(Employe employe) {
        if (employe.getSite() != null && employe.getSite().getId() != null) {
            Site site = siteRepository.findById(employe.getSite().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Site introuvable pour l'ID " + employe.getSite().getId()));
            employe.setSite(site);
        }

        if (employe.getService() != null && employe.getService().getId() != null) {
            ServiceEntity service = serviceRepository.findById(employe.getService().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Service introuvable pour l'ID " + employe.getService().getId()));
            employe.setService(service);
        }
    }
    public List<Employe> searchEmployes(String searchTerm, Long siteId, Long serviceId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("nom", searchTerm != null ? searchTerm : "")
                .queryParam("siteId", siteId != null ? siteId : "")
                .queryParam("serviceId", serviceId != null ? serviceId : "");

        RestTemplate restTemplate = new RestTemplate();
        Employe[] response = restTemplate.getForObject(builder.toUriString(), Employe[].class);

        return Arrays.asList(response);
    }


}
