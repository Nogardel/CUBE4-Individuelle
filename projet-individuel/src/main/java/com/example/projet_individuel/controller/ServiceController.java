package com.example.projet_individuel.controller;

import com.example.projet_individuel.model.ServiceEntity;
import com.example.projet_individuel.service.ServiceApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceApiService serviceApiService;

    /**
     * Récupérer tous les services
     */
    @GetMapping
    public ResponseEntity<List<ServiceEntity>> getAllServices() {
        List<ServiceEntity> services = serviceApiService.getAllServices();
        return ResponseEntity.ok(services);  // 200 OK
    }

    /**
     * Récupérer un service par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceEntity> getServiceById(@PathVariable Long id) {
        try {
            ServiceEntity service = serviceApiService.getServiceById(id);
            return ResponseEntity.ok(service); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    /**
     * Ajouter un nouveau service
     */
    @PostMapping
    public ResponseEntity<ServiceEntity> createService(@RequestBody ServiceEntity serviceEntity) {
        try {
            ServiceEntity newService = serviceApiService.createService(serviceEntity);
            return new ResponseEntity<>(newService, HttpStatus.CREATED); // 201 Created
        } catch (RuntimeException e) {
            // Si tu veux gérer un cas d'erreur (ex: champ 'nom' vide)
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Mettre à jour un service
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceEntity> updateService(@PathVariable Long id, @RequestBody ServiceEntity serviceDetails) {
        try {
            ServiceEntity updatedService = serviceApiService.updateService(id, serviceDetails);
            return ResponseEntity.ok(updatedService); // 200 OK
        } catch (RuntimeException e) {
            // Soit on considère 404 si non trouvé, soit 400 si champs invalides
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Supprimer un service
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        try {
            serviceApiService.deleteService(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            // ex: si l'ID n'existe pas
            return ResponseEntity.notFound().build();
        }
    }
}
