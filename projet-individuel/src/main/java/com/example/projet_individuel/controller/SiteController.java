package com.example.projet_individuel.controller;

import com.example.projet_individuel.model.Site;
import com.example.projet_individuel.service.SiteApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sites")
public class SiteController {

    @Autowired
    private SiteApiService siteApiService;

    /**
     * Récupérer tous les sites
     */
    @GetMapping
    public ResponseEntity<List<Site>> getAllSites() {
        List<Site> sites = siteApiService.getAllSites();
        return ResponseEntity.ok(sites); // 200 OK
    }

    /**
     * Récupérer un site par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Site> getSiteById(@PathVariable Long id) {
        try {
            Site site = siteApiService.getSiteById(id);
            return ResponseEntity.ok(site); // 200 OK
        } catch (RuntimeException e) {
            // ex: site introuvable
            return ResponseEntity.notFound().build(); // 404
        }
    }

    /**
     * Ajouter un nouveau site
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Site createSite(@RequestBody Site site) {
        return siteApiService.createSite(site);
    }

    /**
     * Mettre à jour un site
     */
    @PutMapping("/{id}")
    public ResponseEntity<Site> updateSite(@PathVariable Long id, @RequestBody Site siteDetails) {
        try {
            Site updatedSite = siteApiService.updateSite(id, siteDetails);
            return ResponseEntity.ok(updatedSite); // 200 OK
        } catch (RuntimeException e) {
            // Site introuvable, etc.
            return ResponseEntity.notFound().build(); // 404
        }
    }

    /**
     * Supprimer un site
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSite(@PathVariable Long id) {
        try {
            siteApiService.deleteSite(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
