package com.example.projet_individuel.service;

import com.example.projet_individuel.model.Employe; // Ajout de l'import
import com.example.projet_individuel.exception.DeletionNotAllowedException;
import com.example.projet_individuel.model.ServiceEntity;
import com.example.projet_individuel.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager; // Ajouter cette ligne
import java.util.List;

@Service // Attention à l'import : org.springframework.stereotype.Service
public class ServiceApiService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private EntityManager entityManager; // Ajouter cette ligne

    /**
     * Récupérer tous les services
     */
    public List<ServiceEntity> getAllServices() {
        return serviceRepository.findAll();
    }

    /**
     * Récupérer un service par ID
     * 
     * @throws RuntimeException si le service n'existe pas
     */
    public ServiceEntity getServiceById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found (ID: " + id + ")"));
    }

    /**
     * Créer un nouveau service
     * 
     * @throws RuntimeException si le nom est vide ou invalide, par exemple
     */
    public ServiceEntity createService(ServiceEntity serviceEntity) {
        // Ici, si besoin, on pourrait valider que le champ nom n’est pas vide
        return serviceRepository.save(serviceEntity);
    }

    /**
     * Mettre à jour un service
     */
    public ServiceEntity updateService(Long id, ServiceEntity serviceDetails) {
        ServiceEntity serviceEntity = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found (ID: " + id + ")"));

        serviceEntity.setNom(serviceDetails.getNom());
        // Ajoute d’autres champs si tu en as besoin

        return serviceRepository.save(serviceEntity);
    }

    /**
     * Supprimer un service
     * 
     * @throws DeletionNotAllowedException si le service contient des employés
     * @throws RuntimeException            si le service n'existe pas
     */
    public void deleteService(Long id) {
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found (ID: " + id + ")"));

        // Charger explicitement la collection d'employés
        int employeCount = entityManager.createQuery(
                "SELECT COUNT(e) FROM Employe e WHERE e.service.id = :serviceId", Long.class)
                .setParameter("serviceId", id)
                .getSingleResult()
                .intValue();

        if (employeCount > 0) {
            throw new DeletionNotAllowedException(
                    "Impossible de supprimer le service car il contient encore des employés");
        }

        serviceRepository.delete(service);
    }
}
