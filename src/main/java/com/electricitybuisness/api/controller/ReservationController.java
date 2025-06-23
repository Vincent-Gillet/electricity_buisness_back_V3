package com.electricitybuisness.api.controller;

import com.electricitybuisness.api.dto.ReservationDTO;
import com.electricitybuisness.api.mapper.EntityMapper;
import com.electricitybuisness.api.model.Borne;
import com.electricitybuisness.api.model.Reservation;
import com.electricitybuisness.api.model.StatutReservation;
import com.electricitybuisness.api.model.Utilisateur;
import com.electricitybuisness.api.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des réservations.
 * Expose les endpoints pour les opérations CRUD sur les réservations.
 */
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final EntityMapper mapper;

    /**
     * Récupère toutes les réservations.
     * GET /api/reservations
     *
     * @return Une liste de toutes les réservations
     */
    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        List<ReservationDTO> reservationDTO = reservations.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservationDTO);
    }

    /**
     * Récupère une réservation par son ID.
     * GET /api/reservations/{id}
     *
     * @param id L'identifiant de la réservation à récupérer
     * @return La réservation correspondante à l'ID, ou un statut HTTP 404 Not Found si non trouvée
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id)
                .map(reservation -> ResponseEntity.ok(mapper.toDTO(reservation)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crée une nouvelle réservation.
     * POST /api/reservations
     *
     * @param reservationDTO La réservation à créer
     * @return La réservation créée avec un statut HTTP 201 Created
     */
    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = mapper.toEntity(reservationDTO);
        Reservation savedReservation = reservationService.saveReservation(reservation);
        ReservationDTO savedDTO = mapper.toDTO(savedReservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
    }

    /**
     * Met à jour une réservation existante.
     * PUT /api/reservations/{id}
     * @param id L'identifiant de la réservation à mettre à jour
     * @param reservationDTO La réservation avec les nouvelles informations
     * @return La réservation mise à jour
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable Long id, @Valid @RequestBody ReservationDTO reservationDTO) {
        if (!reservationService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Reservation reservation = mapper.toEntity(reservationDTO);
        Reservation updatedReservation = reservationService.updateReservation(id, reservation);
        ReservationDTO updatedDTO = mapper.toDTO(updatedReservation);
        return ResponseEntity.ok(updatedDTO);
    }

    /**
     * Supprime une réservation.
     * DELETE /api/reservations/{id}
     * @param id L'identifiant de la réservation à supprimer
     * @return Un statut HTTP 204 No Content si la suppression est réussie, ou 404 Not Found si l'ID n'existe pas
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        if (!reservationService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        reservationService.deleteReservationById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Récupère les réservations par utilisateur.
     * GET /api/reservations/utilisateur/{utilisateur}
     * @param utilisateur L'utilisateur associé aux réservations
     * @return Une liste de réservations correspondant à l'utilisateur
     */
    @GetMapping("/{utilisateur}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByUtilisateur(@PathVariable Utilisateur utilisateur) {
        List<Reservation> reservations = reservationService.findByUtilisateur(utilisateur);
        List<ReservationDTO> reservationDTO = reservations.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservationDTO);
    }

    /**
     * Récupère les réservations par borne.
     * GET /api/reservations/borne/{borne}
     * @param borne La borne associée aux réservations
     * @return Une liste de réservations correspondant à la borne
     */
    @GetMapping("/{borne}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByBorne(@PathVariable Borne borne) {
        List<Reservation> reservations = reservationService.findByBorne(borne);
        List<ReservationDTO> reservationDTO = reservations.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservationDTO);
    }

    /**
     * Récupère les réservations par statut.
     * GET /api/reservations/statut/{statut}
     * @param statut Le statut des réservations à récupérer
     * @return Une liste de réservations correspondant au statut
     */
    @GetMapping("/{statut}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByStatut(@PathVariable StatutReservation statut) {
        List<Reservation> reservations = reservationService.findByStatut(statut);
        List<ReservationDTO> reservationDTO = reservations.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservationDTO);
    }

    /**
     * Récupère les réservations par utilisateur et statut.
     * GET /api/reservations/{utilisateur}/{statut}
     * @param utilisateur L'utilisateur associé aux réservations
     * @param statut Le statut des réservations à récupérer
     * @return Une liste de réservations correspondant à l'utilisateur et au statut
     */
    @GetMapping("/{utilisateur}/{statut}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByUtilisateurAndStatut(@PathVariable Utilisateur utilisateur, StatutReservation statut) {
        List<Reservation> reservations = reservationService.findByUtilisateurAndEtat(utilisateur, statut);
        List<ReservationDTO> reservationDTO = reservations.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservationDTO);
    }

    /**
     * Récupère les réservations par borne et statut.
     * GET /api/reservations/{borne}/{statut}
     * @param borne La borne associée aux réservations
     * @param statut Le statut des réservations à récupérer
     * @return Une liste de réservations correspondant à la borne et au statut
     */
    @GetMapping("/{borne}/{statut}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByBorneAndStatut(@PathVariable Borne borne, StatutReservation statut) {
        List<Reservation> reservations = reservationService.findByBorneAndEtat(borne, statut);
        List<ReservationDTO> reservationDTO = reservations.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservationDTO);
    }

    /**
     * Récupère les réservations actives par borne.
     * GET /api/reservations/{borne}/{actif}
     * @param borne La borne associée aux réservations
     * @param actif L'état actif des réservations à récupérer
     * @return Une liste de réservations actives correspondant à la borne
     */
    @GetMapping("/{borne}/{actif}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByBorneAndActif(@PathVariable Borne borne, Boolean actif) {
        List<Reservation> reservations = reservationService.findByBorneAndActif(borne, actif);
        List<ReservationDTO> reservationDTO = reservations.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservationDTO);
    }




}

