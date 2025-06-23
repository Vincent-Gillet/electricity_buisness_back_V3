package com.electricitybuisness.api.dto;

import com.electricitybuisness.api.model.StatutReservation;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO pour l'entité Reservation
 * Inclut des références simples aux entités liées sans relations circulaires
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    @NotNull(message = "La date de début est obligatoire")
    @Future(message = "La date de début doit être dans le futur")
    private LocalDateTime dateDebutReservation;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDateTime dateFinReservation;

    @NotNull(message = "L'état de la réservation est obligatoire")
    private StatutReservation statut;

    private BigDecimal montantPaye;
    private LocalDateTime datePaiement;

    @NotNull(message = "L'utilisateur est obligatoire")
    private Long utilisateur;

    @NotNull(message = "La borne est obligatoire")
    private Long borne;

    @NotNull(message = "Le véhicule est obligatoire")
    private Long vehicule;

    private Long serviceSup;
}