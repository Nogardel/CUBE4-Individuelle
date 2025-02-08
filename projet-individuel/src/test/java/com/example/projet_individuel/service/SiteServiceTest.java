package com.example.projet_individuel.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.projet_individuel.model.Site;
import com.example.projet_individuel.repository.SiteRepository;
import com.example.projet_individuel.exception.DeletionNotAllowedException;
import com.example.projet_individuel.model.Employe; // Ajout de l'import

@ExtendWith(MockitoExtension.class)
class SiteServiceTest {

    @Mock
    private SiteRepository siteRepository;

    @InjectMocks
    private SiteApiService siteApiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllSites_ShouldReturnListOfSites() {
        // Arrange
        Site site1 = Site.builder()
                .id(1L)
                .ville("Paris")
                .build();
        Site site2 = Site.builder()
                .id(2L)
                .ville("Lyon")
                .build();
        when(siteRepository.findAll()).thenReturn(Arrays.asList(site1, site2));

        // Act
        List<Site> result = siteApiService.getAllSites();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(siteRepository).findAll();
    }

    @Test
    void getSiteById_WhenSiteExists_ShouldReturnSite() {
        // Arrange
        Site site = Site.builder()
                .id(1L)
                .ville("Paris")
                .build();
        when(siteRepository.findById(1L)).thenReturn(Optional.of(site));

        // Act
        Site result = siteApiService.getSiteById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(site.getVille(), result.getVille());
    }

    @Test
    void getSiteById_WhenSiteDoesNotExist_ShouldThrowException() {
        // Arrange
        when(siteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> siteApiService.getSiteById(1L));
    }

    @Test
    void createSite_ShouldReturnCreatedSite() {
        // Arrange
        Site newSite = Site.builder()
                .ville("Paris")
                .build();
        Site savedSite = Site.builder()
                .id(1L)
                .ville("Paris")
                .build();
        when(siteRepository.save(newSite)).thenReturn(savedSite);

        // Act
        Site result = siteApiService.createSite(newSite);

        // Assert
        assertNotNull(result);
        assertEquals(savedSite.getId(), result.getId());
        verify(siteRepository).save(newSite);
    }

    @Test
    void updateSite_WhenSiteExists_ShouldReturnUpdatedSite() {
        // Arrange
        Site existingSite = Site.builder()
                .id(1L)
                .ville("Paris")
                .build();
        Site updatedSite = Site.builder()
                .id(1L)
                .ville("Paris")
                .build();
        when(siteRepository.findById(1L)).thenReturn(Optional.of(existingSite));
        when(siteRepository.save(any(Site.class))).thenReturn(updatedSite);

        // Act
        Site result = siteApiService.updateSite(1L, updatedSite);

        // Assert
        assertNotNull(result);
        assertEquals(updatedSite.getVille(), result.getVille());
    }

    @Test
    void deleteSite_WhenSiteExists_ShouldDeleteSuccessfully() {
        // Arrange
        Site site = Site.builder()
                .id(1L)
                .ville("Paris")
                .build();
        when(siteRepository.findById(1L)).thenReturn(Optional.of(site));

        // Act
        siteApiService.deleteSite(1L);

        // Assert
        verify(siteRepository).delete(site);
    }

    @Test
    void deleteSite_WhenSiteHasEmployees_ShouldThrowException() {
        // Arrange
        List<Employe> employes = new ArrayList<>();
        employes.add(new Employe(1L, "John", "Doe"));

        Site site = Site.builder()
                .id(1L)
                .ville("Paris")
                .employes(employes)
                .build();
        when(siteRepository.findById(1L)).thenReturn(Optional.of(site));

        // Act & Assert
        assertThrows(DeletionNotAllowedException.class, () -> siteApiService.deleteSite(1L));
        verify(siteRepository, never()).delete(any());
    }
}