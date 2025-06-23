package com.electricitybuisness.api.repository;

import com.electricitybuisness.api.model.Lieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de gestion des opérations CRUD pour les lieux.
 * Hérite de JpaRepository pour les opérations de base de données.
 */
@Repository
public interface LieuRepository extends JpaRepository<Lieu, Long> {
}
