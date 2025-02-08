package com.example.projet_individuel.service;

import com.example.projet_individuel.exception.DeletionNotAllowedException;
import com.example.projet_individuel.model.ServiceEntity;
import com.example.projet_individuel.repository.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import com.example.projet_individuel.model.Employe;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceApiServiceTest {

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private ServiceApiService serviceApiService;

    @Test
    void getAllServices_ShouldReturnServiceList() {
        // Arrange
        List<ServiceEntity> services = Arrays.asList(
                ServiceEntity.builder().id(1L).nom("RH").build(),
                ServiceEntity.builder().id(2L).nom("IT").build());
        when(serviceRepository.findAll()).thenReturn(services);

        // Act
        List<ServiceEntity> result = serviceApiService.getAllServices();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(serviceRepository).findAll();
    }

    @Test
    void getServiceById_WhenServiceExists_ShouldReturnService() {
        // Arrange
        ServiceEntity service = ServiceEntity.builder()
                .id(1L)
                .nom("RH")
                .build();
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));

        // Act
        ServiceEntity result = serviceApiService.getServiceById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("RH", result.getNom());
    }

    @Test
    void getServiceById_WhenServiceDoesNotExist_ShouldThrowException() {
        // Arrange
        when(serviceRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> serviceApiService.getServiceById(1L));
    }

    @Test
    void createService_ShouldReturnCreatedService() {
        // Arrange
        ServiceEntity newService = ServiceEntity.builder()
                .nom("RH")
                .build();
        ServiceEntity savedService = ServiceEntity.builder()
                .id(1L)
                .nom("RH")
                .build();
        when(serviceRepository.save(any(ServiceEntity.class))).thenReturn(savedService);

        // Act
        ServiceEntity result = serviceApiService.createService(newService);

        // Assert
        assertNotNull(result);
        assertEquals(savedService.getId(), result.getId());
        verify(serviceRepository).save(newService);
    }

    @Test
    void updateService_WhenServiceExists_ShouldReturnUpdatedService() {
        // Arrange
        ServiceEntity existingService = ServiceEntity.builder()
                .id(1L)
                .nom("RH")
                .build();
        ServiceEntity updatedService = ServiceEntity.builder()
                .id(1L)
                .nom("RH Updated")
                .build();
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(existingService));
        when(serviceRepository.save(any(ServiceEntity.class))).thenReturn(updatedService);

        // Act
        ServiceEntity result = serviceApiService.updateService(1L, updatedService);

        // Assert
        assertNotNull(result);
        assertEquals("RH Updated", result.getNom());
    }

    @Test
    void deleteService_WhenServiceExists_ShouldDeleteSuccessfully() {
        // Arrange
        ServiceEntity service = ServiceEntity.builder()
                .id(1L)
                .nom("RH")
                .build();
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));

        // Act
        serviceApiService.deleteService(1L);

        // Assert
        verify(serviceRepository).delete(service);
    }

    @Test
    void deleteService_WhenServiceHasEmployees_ShouldThrowException() {
        // Arrange
        List<Employe> employes = new ArrayList<>();
        Employe employe = Employe.builder()
                .id(1L)
                .nom("John")
                .prenom("Doe")
                .build();
        employes.add(employe);

        ServiceEntity service = ServiceEntity.builder()
                .id(1L)
                .nom("RH")
                .employes(employes)
                .build();
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));

        // Act & Assert
        assertThrows(DeletionNotAllowedException.class, () -> serviceApiService.deleteService(1L));
        verify(serviceRepository, never()).delete(any());
    }
}