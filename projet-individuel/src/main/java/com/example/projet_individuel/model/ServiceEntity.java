package com.example.projet_individuel.model;

import jakarta.persistence.*;
import javafx.beans.property.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "service")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 100, nullable = false)
    private String nom;

    // --- Propriétés JavaFX (pour TableView, UI) ---
    public LongProperty idProperty() {
        return new SimpleLongProperty(id);
    }

    @Override
    public String toString() {
        return this.nom; // Retourne le nom du service pour l'affichage
    }

    public StringProperty nomProperty() {
        return new SimpleStringProperty(nom);
    }

    // --- Getters et Setters pour JPA ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
