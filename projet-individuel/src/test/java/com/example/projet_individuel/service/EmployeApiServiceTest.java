package com.example.projet_individuel.service;

import com.example.projet_individuel.model.Employe;
import com.example.projet_individuel.repository.EmployeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeIntegrationTest {

    @Mock
    private EmployeRepository employeRepository;

    @InjectMocks
    private EmployeApiService employeApiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEmployes_ShouldReturnListOfEmployes() {
        // Arrange
        Employe employe1 = new Employe(1L, "John", "Doe");
        Employe employe2 = new Employe(2L, "Jane", "Smith");
        when(employeRepository.findAll()).thenReturn(Arrays.asList(employe1, employe2));

        // Act
        List<Employe> result = employeApiService.getAllEmployes();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(employeRepository).findAll();
    }

    @Test
    void getEmployeById_WhenEmployeExists_ShouldReturnEmploye() {
        // Arrange
        Employe employe = new Employe(1L, "John", "Doe");
        when(employeRepository.findById(1L)).thenReturn(Optional.of(employe));

        // Act
        Employe result = employeApiService.getEmployeById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(employe, result);
    }

    @Test
    void createEmploye_ShouldReturnCreatedEmploye() {
        // Arrange
        Employe employe = new Employe(null, "John", "Doe");
        Employe savedEmploye = new Employe(1L, "John", "Doe");
        when(employeRepository.save(employe)).thenReturn(savedEmploye);

        // Act
        Employe result = employeApiService.createEmploye(employe);

        // Assert
        assertNotNull(result);
        assertEquals(savedEmploye.getId(), result.getId());
        verify(employeRepository).save(employe);
    }

    @Test
    void updateEmploye_WhenEmployeExists_ShouldReturnUpdatedEmploye() {
        // Arrange
        Employe existingEmploye = new Employe(1L, "John", "Doe");
        Employe updatedEmploye = new Employe(1L, "John", "Smith");
        when(employeRepository.findById(1L)).thenReturn(Optional.of(existingEmploye));
        when(employeRepository.save(any(Employe.class))).thenReturn(updatedEmploye);

        // Act
        Employe result = employeApiService.updateEmploye(1L, updatedEmploye);

        // Assert
        assertNotNull(result);
        assertEquals(updatedEmploye.getNom(), result.getNom());
    }

    @Test
    void deleteEmploye_WhenEmployeExists_ShouldDeleteSuccessfully() {
        // Arrange
        when(employeRepository.findById(1L)).thenReturn(Optional.of(new Employe(1L, "John", "Doe")));

        // Act
        employeApiService.deleteEmploye(1L);

        // Assert
        verify(employeRepository).delete(any(Employe.class));
    }
}

@SpringBootTest
class ApplicationTests {
    @Test
    void contextLoads() {
        // Vérifie uniquement le chargement du contexte
    }
}