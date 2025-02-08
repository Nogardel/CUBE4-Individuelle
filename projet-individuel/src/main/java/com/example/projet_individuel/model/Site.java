package com.example.projet_individuel.model;

import jakarta.persistence.*;
import javafx.beans.property.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "site")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 255, nullable = false)
    private String ville;

    @OneToMany(mappedBy = "site", fetch = FetchType.EAGER) // Changement ici
    @Builder.Default
    private List<Employe> employes = new ArrayList<>();

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

    // Ajout du getter explicite pour employes
    public List<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }
}
