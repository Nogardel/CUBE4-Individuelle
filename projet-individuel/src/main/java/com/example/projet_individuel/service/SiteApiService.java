package com.example.projet_individuel.service;

import com.example.projet_individuel.model.Site;
import com.example.projet_individuel.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.projet_individuel.exception.DeletionNotAllowedException;

import jakarta.persistence.EntityManager; // Ajouter cette ligne
import java.util.List;

@Service // On indique qu'il s'agit d'un "bean" de service Spring
public class SiteApiService {

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private EntityManager entityManager; // Ajouter cette ligne

    /**
     * Récupérer tous les sites
     */
    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }

    /**
     * Récupérer un site par ID
     * 
     * @throws RuntimeException si le site n'existe pas
     */
    public Site getSiteById(Long id) {
        return siteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Site not found (ID: " + id + ")"));
    }

    /**
     * Créer un nouveau site
     */
    public Site createSite(Site site) {
        // Ex : vérifier si site.getVille() n’est pas vide
        return siteRepository.save(site);
    }

    /**
     * Mettre à jour un site
     */
    public Site updateSite(Long id, Site siteDetails) {
        Site site = siteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Site not found (ID: " + id + ")"));

        site.setVille(siteDetails.getVille());
        // Si d’autres champs existent, on les met à jour ici
        return siteRepository.save(site);
    }

    /**
     * Supprimer un site
     */
    public void deleteSite(Long id) {
        Site site = siteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Site not found (ID: " + id + ")"));

        // Charger explicitement la collection d'employés
        int employeCount = entityManager.createQuery(
                "SELECT COUNT(e) FROM Employe e WHERE e.site.id = :siteId", Long.class)
                .setParameter("siteId", id)
                .getSingleResult()
                .intValue();

        if (employeCount > 0) {
            throw new DeletionNotAllowedException(
                    "Impossible de supprimer le site car il contient encore des employés");
        }

        siteRepository.delete(site);
    }
}
