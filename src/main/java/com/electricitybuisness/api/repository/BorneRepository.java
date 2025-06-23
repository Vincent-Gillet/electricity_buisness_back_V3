package com.electricitybuisness.api.repository;

import com.electricitybuisness.api.model.Borne;
import com.electricitybuisness.api.model.Lieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface de gestion des opérations CRUD pour les bornes.
 * Hérite de JpaRepository pour les opérations de base de données.
 */

@Repository
public interface BorneRepository extends JpaRepository<Borne, Long> {
    List<Borne> findByLieu(Lieu lieu);

    List<Borne> findByEtat(Borne etatBorne);

    List<Borne> findByOccupee(Borne occupee);

    List<Borne> findByLieuAndEtat(Lieu lieu, Borne etatBorne);
}
