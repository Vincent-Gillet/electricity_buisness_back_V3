package com.electricitybuisness.api.service;


import com.electricitybuisness.api.model.Borne;
import com.electricitybuisness.api.model.Reservation;
import com.electricitybuisness.api.model.StatutReservation;
import com.electricitybuisness.api.model.Utilisateur;
import com.electricitybuisness.api.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;

    /**
     * Récupère toutes les réservations.
     * @return Une liste de toutes les réservations
     */
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    /**
     * Récupère une réservation par son ID.
     * @param id L'identifiant de la réservation à récupérer
     * @return Un Optional contenant la réservation si trouvée, sinon vide
     */
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }


    /**
     * Crée une nouvelle réservation.
     * @param reservation La réservation à enregistrer
     * @return La réservation enregistrée
     */
    @Transactional
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }


    /**
     * Met à jour une réservation existante.
     * @param id L'identifiant de la réservation à mettre à jour
     * @param reservation La réservation avec les nouvelles informations
     * @return La réservation mise à jour
     */
    public Reservation updateReservation(Long id, Reservation reservation) {
        reservation.setIdReservation(id);
        return reservationRepository.save(reservation);
    }

    /**
     * Supprime une réservation.
     * @param id L'identifiant de la réservation à supprimer
     */
    public void deleteReservationById(Long id) {
        reservationRepository.deleteById(id);
    }

    /**
     * Vérifie si une réservation existe par son ID.
     * @param id L'identifiant de la réservation à vérifier
     * @return true si la réservation existe, false sinon
     */
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return reservationRepository.existsById(id);
    }

    /**
     * Récupère les réservations par utilisateur.
     * @param utilisateur L'utilisateur associé aux réservations
     * @return Une liste de réservations correspondant à l'utilisateur
     */
    @Transactional(readOnly = true)
    public List<Reservation> findByUtilisateur(Utilisateur utilisateur) {
        return reservationRepository.findByUtilisateur(utilisateur);
    }

    /**
     * Récupère les réservations par borne.
     * @param borne La borne associée aux réservations
     * @return Une liste de réservations correspondant à la borne
     */
    @Transactional(readOnly = true)
    public List<Reservation> findByBorne(Borne borne) {
        return reservationRepository.findByBorne(borne);
    }

    /**
     * Récupère les réservations par statut.
     * @param statut Le statut des réservations à récupérer
     * @return Une liste de réservations correspondant au statut
     */
    @Transactional(readOnly = true)
    public List<Reservation> findByStatut(StatutReservation statut) {
        return reservationRepository.findByStatut(statut);
    }

    /**
     * Récupère les réservations par utilisateur et statut.
     * @param utilisateur L'utilisateur associé aux réservations
     * @param statut Le statut des réservations à récupérer
     * @return Une liste de réservations correspondant à l'utilisateur et au statut
     */
    @Transactional(readOnly = true)
    public List<Reservation> findByUtilisateurAndEtat(Utilisateur utilisateur, StatutReservation statut) {
        return reservationRepository.findByUtilisateurAndStatut(utilisateur, statut);
    }

    /**
     * Récupère les réservations par borne et statut.
     * @param borne La borne associée aux réservations
     * @param statut Le statut des réservations à récupérer
     * @return Une liste de réservations correspondant à la borne et au statut
     */
    @Transactional(readOnly = true)
    public List<Reservation> findByBorneAndEtat(Borne borne, StatutReservation statut) {
        return reservationRepository.findByBorneAndStatut(borne, statut);
    }

    /**
     * Récupère les réservations actives par borne.
     * @param borne La borne associée aux réservations
     * @param actif L'état actif des réservations à récupérer
     * @return Une liste de réservations actives correspondant à la borne
     */
/*    @Transactional(readOnly = true)
    public List<Reservation> findByBorneAndActif(Borne borne, Boolean actif) {
        return reservationRepository.findByBorneAndActif(borne, actif);
    }*/

}
