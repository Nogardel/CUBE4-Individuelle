package com.example.projet_individuel.service;

import org.springframework.stereotype.Service;
import com.example.projet_individuel.repository.UserRepository;
import com.example.projet_individuel.model.User;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean validateAdminCredentials(String email, String password) {
        User user = userRepository.findByEmailAndRole(email, "ADMIN");
        return user != null && user.getMotDePasse().equals(password);
    }
}