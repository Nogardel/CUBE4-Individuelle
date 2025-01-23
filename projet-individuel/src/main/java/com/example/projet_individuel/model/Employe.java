package com.example.projet_individuel.model;

import jakarta.persistence.*;
import javafx.beans.property.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employe")
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nom;

    @Column(length = 100, nullable = false)
    private String prenom;

    @Column(name = "telephone_fixe", length = 15)
    private String telephoneFixe;

    @Column(name = "telephone_portable", length = 15)
    private String telephonePortable;

    @Column(length = 100, unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private Site site;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    // --- Propriétés JavaFX ---
    public LongProperty idProperty() {
        return new SimpleLongProperty(id);
    }

    public StringProperty nomProperty() {
        return new SimpleStringProperty(nom);
    }

    public StringProperty prenomProperty() {
        return new SimpleStringProperty(prenom);
    }

    public StringProperty emailProperty() {
        return new SimpleStringProperty(email);
    }

    public StringProperty telephoneFixeProperty() {
        return new SimpleStringProperty(telephoneFixe);
    }

    public StringProperty telephonePortableProperty() {
        return new SimpleStringProperty(telephonePortable);
    }

    public StringProperty siteProperty() {
        return new SimpleStringProperty(site != null ? site.getVille() : "Aucun site");
    }

    public StringProperty serviceProperty() {
        return new SimpleStringProperty(service != null ? service.getNom() : "Aucun service");
    }

    // --- Getters et Setters ---
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephoneFixe() {
        return telephoneFixe;
    }

    public void setTelephoneFixe(String telephoneFixe) {
        this.telephoneFixe = telephoneFixe;
    }

    public String getTelephonePortable() {
        return telephonePortable;
    }

    public void setTelephonePortable(String telephonePortable) {
        this.telephonePortable = telephonePortable;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public ServiceEntity getService() {
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "Nom: " + nom + ", Prénom: " + prenom + ", Email: " + email +
                ", Site: " + (site != null ? site.getVille() : "Aucun") +
                ", Service: " + (service != null ? service.getNom() : "Aucun");
    }

    public Employe(Long id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }
}
