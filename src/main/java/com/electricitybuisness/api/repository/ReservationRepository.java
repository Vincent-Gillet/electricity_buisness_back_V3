package com.electricitybuisness.api.repository;

import com.electricitybuisness.api.model.Borne;
import com.electricitybuisness.api.model.Reservation;
import com.electricitybuisness.api.model.StatutReservation;
import com.electricitybuisness.api.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUtilisateur(Utilisateur utilisateur);

    List<Reservation> findByBorne(Borne borne);

    List<Reservation> findByStatut(StatutReservation statut);

    List<Reservation> findByUtilisateurAndStatut(Utilisateur utilisateur, StatutReservation statut);

    List<Reservation> findByBorneAndStatut(Borne borne, StatutReservation statut);

/*
    List<Reservation> findByBorneAndActif(Borne borne, Boolean actif);
*/
}
