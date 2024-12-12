package com.example.projet_individuel.controller;

import com.example.projet_individuel.model.Service;
import com.example.projet_individuel.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    // Récupérer tous les services
    @GetMapping
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    // Récupérer un service par ID
    @GetMapping("/{id}")
    public Service getServiceById(@PathVariable Long id) {
        return serviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Service not found"));
    }

    // Ajouter un nouveau service
    @PostMapping
    public Service createService(@RequestBody Service service) {
        return serviceRepository.save(service);
    }

    // Mettre à jour un service
    @PutMapping("/{id}")
    public Service updateService(@PathVariable Long id, @RequestBody Service serviceDetails) {
        Service service = serviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Service not found"));
        service.setNom(serviceDetails.getNom());
        return serviceRepository.save(service);
    }

    // Supprimer un service
    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable Long id) {
        Service service = serviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Service not found"));
        serviceRepository.delete(service);
    }
}
