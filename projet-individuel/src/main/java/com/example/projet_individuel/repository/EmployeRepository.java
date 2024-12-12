package com.example.projet_individuel.repository;

import com.example.projet_individuel.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeRepository extends JpaRepository<Employe, Long> {
}
