package com.electricitybuisness.api.repository;

import com.electricitybuisness.api.model.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de gestion des opérations CRUD pour les véhicules.
 * Hérite de JpaRepository pour les opérations de base de données.
 */
@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
}
