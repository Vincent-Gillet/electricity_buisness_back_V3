package com.electricitybuisness.api.model;

import jakarta.persistence.Embeddable;

/**
 * Classe représentant l'identifiant d'une réservation.
 * Cette classe est utilisée pour créer une clé composite dans la base de données.
 */

@Embeddable
public class ReservationId {
    private Long utilisateurId;
    private Long borneId;
    private Long serviceSupId;
    private Long vehicleId;

}