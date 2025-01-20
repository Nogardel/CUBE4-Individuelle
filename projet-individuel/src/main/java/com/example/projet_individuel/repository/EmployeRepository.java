package com.example.projet_individuel.repository;

import com.example.projet_individuel.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {

    @Query("SELECT e FROM Employe e WHERE " +
            "(:nom IS NULL OR e.nom LIKE %:nom%) AND " +
            "(:siteId IS NULL OR e.site.id = :siteId) AND " +
            "(:serviceId IS NULL OR e.service.id = :serviceId)")
    List<Employe> searchByFilters(
            @Param("nom") String nom,
            @Param("siteId") Long siteId,
            @Param("serviceId") Long serviceId);

}

