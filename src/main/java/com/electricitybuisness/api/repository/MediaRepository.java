package com.electricitybuisness.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electricitybuisness.api.model.Media;

import java.util.List;

/**
 * Interface de gestion des opérations CRUD pour les médias.
 * Hérite de JpaRepository pour les opérations de base de données.
 */
@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findByType(String type);
}

