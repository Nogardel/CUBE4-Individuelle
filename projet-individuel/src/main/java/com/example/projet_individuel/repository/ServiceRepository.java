package com.example.projet_individuel.repository;

import com.example.projet_individuel.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}