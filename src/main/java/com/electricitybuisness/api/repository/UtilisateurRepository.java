package com.electricitybuisness.api.repository;

import com.electricitybuisness.api.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface de gestion des opérations CRUD pour les utilisateurs.
 * Hérite de JpaRepository pour les opérations de base de données.
 */
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByPseudo(String pseudo);

    Optional<Utilisateur> findByUtilisateurEmail(String adresseMail);

    boolean existsByPseudo(String pseudo);

    boolean existsByAdresseMail(String adresseMail);
}
