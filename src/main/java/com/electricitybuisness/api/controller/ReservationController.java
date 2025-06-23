package com.electricitybuisness.api.controller;

import com.electricitybuisness.api.dto.ReservationDTO;
import com.electricitybuisness.api.mapper.EntityMapper;
import com.electricitybuisness.api.model.Reservation;
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

}

