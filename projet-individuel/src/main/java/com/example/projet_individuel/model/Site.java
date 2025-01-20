package com.example.projet_individuel.model;

import jakarta.persistence.*;
import javafx.beans.property.*;

@Entity
@Table(name = "site")
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 255, nullable = false)
    private String ville;

    // --- Propriétés JavaFX (pour TableView, UI) ---
    public LongProperty idProperty() {
        return new SimpleLongProperty(id);
    }

    @Override
    public String toString() {
        return this.ville; // Retourne le nom de la ville pour l'affichage
    }

    public StringProperty villeProperty() {
        return new SimpleStringProperty(ville);
    }

    // --- Getters et Setters pour JPA ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}
