-- Désactiver les contraintes de clé étrangère
SET FOREIGN_KEY_CHECKS = 0;

-- Vider les tables
TRUNCATE TABLE employe;
TRUNCATE TABLE site;
TRUNCATE TABLE service;

-- Réactiver les contraintes de clé étrangère
SET FOREIGN_KEY_CHECKS = 1;
-- Ajout des sites

INSERT INTO site (ville) VALUES
                             ('Paris'),
                             ('Nantes'),
                             ('Toulouse'),
                             ('Nice'),
                             ('Lille');

-- Ajout des services
INSERT INTO service (nom) VALUES
                              ('Informatique'),
                              ('Comptabilité'),
                              ('Production'),
                              ('Accueil'),
                              ('Commercial');

-- Ajout des employés
INSERT INTO employe (nom, prenom, telephone_fixe, telephone_portable, email, site_id, service_id)
VALUES
    ('Dupont', 'Jean', '0102030405', '0605040302', 'jean.dupont@example.com', 1, 1),
    ('Doe', 'Jane', NULL, '0612345678', 'jane.doe@example.com', 2, 1),
    ('Martin', 'Paul', '0147253625', '0652451287', 'paul.martin@example.com', 3, 3),
    ('Durand', 'Sophie', NULL, '0611223344', 'sophie.durand@example.com', 3, 5),
    ('Leroy', 'Nathalie', '0144001122', '0677889900', 'nathalie.leroy@example.com', 5, 2);