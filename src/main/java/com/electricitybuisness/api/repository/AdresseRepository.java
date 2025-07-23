package com.electricitybuisness.api.repository;

import com.electricitybuisness.api.model.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface de gestion des opérations CRUD pour les adresses.
 * Hérite de JpaRepository pour les opérations de base de données.
 */
@Repository
public interface AdresseRepository extends JpaRepository<Adresse, Long> {
}
