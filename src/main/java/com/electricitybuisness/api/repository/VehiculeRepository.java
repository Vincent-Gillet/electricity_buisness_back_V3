package com.electricitybuisness.api.repository;

import com.electricitybuisness.api.model.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
}
