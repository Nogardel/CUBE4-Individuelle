CREATE TABLE IF NOT EXISTS site (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ville VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS service (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS employe (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    telephone_fixe VARCHAR(15),
    telephone_portable VARCHAR(15),
    email VARCHAR(100),
    site_id BIGINT,
    service_id BIGINT,
    FOREIGN KEY (site_id) REFERENCES site(id),
    FOREIGN KEY (service_id) REFERENCES service(id)
);