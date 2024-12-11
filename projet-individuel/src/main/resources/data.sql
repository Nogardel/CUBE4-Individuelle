-- Données pour la table site
INSERT INTO site (ville) VALUES ('Paris');
INSERT INTO site (ville) VALUES ('Lyon');
INSERT INTO site (ville) VALUES ('Marseille');

-- Données pour la table service
INSERT INTO service (nom) VALUES ('Informatique');
INSERT INTO service (nom) VALUES ('Marketing');
INSERT INTO service (nom) VALUES ('Ressources Humaines');

-- Données pour la table employe
INSERT INTO employe (nom, prenom, telephoneFixe, telephonePortable, email, site_id, service_id)
VALUES 
('Durand', 'Jean', '0123456789', '0612345678', 'jean.durand@example.com', 1, 1),
('Dupont', 'Marie', '0987654321', '0698765432', 'marie.dupont@example.com', 2, 2),
('Martin', 'Paul', '0111222333', '0611223344', 'paul.martin@example.com', 3, 3);