package com.example.projet_individuel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.projet_individuel.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndRole(String email, String role);
}