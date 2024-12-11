package com.example.projet_individuel.controller;

import com.example.projet_individuel.model.Site;
import com.example.projet_individuel.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sites")
public class SiteController {

    @Autowired
    private SiteRepository siteRepository;

    // Récupérer tous les sites
    @GetMapping
    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }

    // Récupérer un site par ID
    @GetMapping("/{id}")
    public Site getSiteById(@PathVariable Long id) {
        return siteRepository.findById(id).orElseThrow(() -> new RuntimeException("Site not found"));
    }

    // Ajouter un nouveau site
    @PostMapping
    public Site createSite(@RequestBody Site site) {
        System.out.println("Reçu : " + site.getVille());
        return siteRepository.save(site);
    }

    // Mettre à jour un site
    @PutMapping("/{id}")
    public Site updateSite(@PathVariable Long id, @RequestBody Site siteDetails) {
        Site site = siteRepository.findById(id).orElseThrow(() -> new RuntimeException("Site not found"));
        site.setVille(siteDetails.getVille());
        return siteRepository.save(site);
    }

    // Supprimer un site
    @DeleteMapping("/{id}")
    public void deleteSite(@PathVariable Long id) {
        Site site = siteRepository.findById(id).orElseThrow(() -> new RuntimeException("Site not found"));
        siteRepository.delete(site);
    }
}
