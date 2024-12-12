package com.example.projet_individuel.controller;

import com.example.projet_individuel.model.Employe;
import com.example.projet_individuel.model.Service;
import com.example.projet_individuel.model.Site;
import com.example.projet_individuel.repository.EmployeRepository;
import com.example.projet_individuel.repository.ServiceRepository;
import com.example.projet_individuel.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
public class EmployeController {

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    // Récupérer tous les employés
    @GetMapping
    public List<Employe> getAllEmployes() {
        return employeRepository.findAll();
    }

    // Récupérer un employé par ID
    @GetMapping("/{id}")
    public Employe getEmployeById(@PathVariable Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employe not found"));
    }

    // Ajouter un nouvel employé
    @PostMapping
    public Employe createEmploye(@RequestBody Employe employe) {
        validateAndSetRelationships(employe);
        return employeRepository.save(employe);
    }

    // Mettre à jour un employé
    // @PutMapping("/{id}")
    // public Employe updateEmploye(@PathVariable Long id, @RequestBody Employe
    // employeDetails) {
    // if (employeDetails.getNom() == null || employeDetails.getPrenom() == null) {
    // throw new RuntimeException("Les champs 'nom' et 'prenom' sont obligatoires");
    // }

    // Employe employe = employeRepository.findById(id)
    // .orElseThrow(() -> new RuntimeException("Employe not found"));

    // employe.setNom(employeDetails.getNom());
    // employe.setPrenom(employeDetails.getPrenom());
    // employe.setTelephoneFixe(employeDetails.getTelephoneFixe());
    // employe.setTelephonePortable(employeDetails.getTelephonePortable());
    // employe.setEmail(employeDetails.getEmail());

    // validateAndSetRelationships(employe);

    // return employeRepository.save(employe);
    // }
    @PutMapping("/{id}")
    public Employe updateEmploye(@PathVariable Long id, @RequestBody Employe employeDetails) {
        System.out.println("Reçu employeDetails: " + employeDetails);

        if (employeDetails.getNom() == null || employeDetails.getPrenom() == null) {
            throw new RuntimeException("Les champs 'nom' et 'prenom' sont obligatoires");
        }

        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employe not found"));

        employe.setNom(employeDetails.getNom());
        employe.setPrenom(employeDetails.getPrenom());
        employe.setTelephoneFixe(employeDetails.getTelephoneFixe());
        employe.setTelephonePortable(employeDetails.getTelephonePortable());
        employe.setEmail(employeDetails.getEmail());

        validateAndSetRelationships(employeDetails);

        return employeRepository.save(employe);
    }

    // Supprimer un employé
    @DeleteMapping("/{id}")
    public void deleteEmploye(@PathVariable Long id) {
        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employe not found"));
        employeRepository.delete(employe);
    }

    // Valider et définir les relations pour site et service
    private void validateAndSetRelationships(Employe employe) {
        if (employe.getSite() != null && employe.getSite().getId() != null) {
            Site site = siteRepository.findById(employe.getSite().getId())
                    .orElseThrow(() -> new RuntimeException("Site not found"));
            employe.setSite(site);
        }

        if (employe.getService() != null && employe.getService().getId() != null) {
            Service service = serviceRepository.findById(employe.getService().getId())
                    .orElseThrow(() -> new RuntimeException("Service not found"));
            employe.setService(service);
        }
    }
}
