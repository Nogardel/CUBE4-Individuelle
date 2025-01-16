package com.example.projet_individuel.controller;

import com.example.projet_individuel.model.Employe;
import com.example.projet_individuel.service.EmployeApiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@Validated  // Si on utilise Bean Validation sur les champs
public class EmployeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeController.class);

    @Autowired
    private EmployeApiService employeApiService;

    /**
     * Récupérer tous les employés
     */
    @GetMapping
    public ResponseEntity<List<Employe>> getAllEmployes() {
        List<Employe> employes = employeApiService.getAllEmployes();
        return ResponseEntity.ok(employes);  // 200 OK
    }

    /**
     * Récupérer un employé par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employe> getEmployeById(@PathVariable Long id) {
        Employe employe = employeApiService.getEmployeById(id);
        return ResponseEntity.ok(employe); // 200 OK
    }

    /**
     * Ajouter un nouvel employé
     */
    @PostMapping
    public ResponseEntity<Employe> createEmploye(@RequestBody Employe employe) {
        try {
            Employe newEmploye = employeApiService.createEmploye(employe);
            return new ResponseEntity<>(newEmploye, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Ici on renvoie 400 si la création est invalide (ex: nom/prénom manquants)
            logger.error("Erreur lors de la création d'un employé : {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Mettre à jour un employé
     */
    @PutMapping("/{id}")
    public ResponseEntity<Employe> updateEmploye(@PathVariable Long id, @RequestBody Employe employeDetails) {
        logger.info("Reçu employeDetails: {}", employeDetails);

        try {
            Employe updatedEmploye = employeApiService.updateEmploye(id, employeDetails);
            return ResponseEntity.ok(updatedEmploye); // 200 OK
        } catch (RuntimeException e) {
            // Selon le cas (employé introuvable vs. validation), on renvoie 404 ou 400
            logger.error("Erreur lors de la mise à jour de l'employé : {}", e.getMessage());
            // Pour simplifier, on renvoie 400. On pourrait différencier si le message = "introuvable" => 404
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Supprimer un employé
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmploye(@PathVariable Long id) {
        try {
            employeApiService.deleteEmploye(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("Erreur lors de la suppression de l'employé : {}", e.getMessage());
            // Si l'employé n'existe pas, on renvoie 404 ou 400, au choix
            return ResponseEntity.notFound().build();
        }
    }
}
