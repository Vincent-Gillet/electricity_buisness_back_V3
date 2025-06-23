package com.electricitybuisness.api.repository;

import com.electricitybuisness.api.model.Reparateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReparateurRepository extends JpaRepository<Reparateur, Long> {
    Optional<Reparateur> findByEmailReparateur(String email);
}
