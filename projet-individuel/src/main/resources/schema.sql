-- Création de la table site
CREATE TABLE IF NOT EXISTS site (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    ville VARCHAR(255) NOT NULL,
    CONSTRAINT UK_site_ville UNIQUE (ville)
    );

-- Création de la table service
CREATE TABLE IF NOT EXISTS service (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       nom VARCHAR(100) NOT NULL,
    CONSTRAINT UK_service_nom UNIQUE (nom)
    );

-- Création de la table employe
CREATE TABLE IF NOT EXISTS employe (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    telephone_fixe VARCHAR(15),
    telephone_portable VARCHAR(15),
    email VARCHAR(100) UNIQUE,
    site_id BIGINT,
    service_id BIGINT,
    CONSTRAINT FK_employe_site
    FOREIGN KEY (site_id) REFERENCES site(id)
    ON DELETE RESTRICT,
    CONSTRAINT FK_employe_service
    FOREIGN KEY (service_id) REFERENCES service(id)
    ON DELETE RESTRICT
    );
