package com.example.projet_individuel.controller;

import com.example.projet_individuel.model.Site;
import com.example.projet_individuel.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SiteController {
    @Autowired
    private SiteRepository siteRepository;

    @GetMapping("/api/sites")
    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }
}
