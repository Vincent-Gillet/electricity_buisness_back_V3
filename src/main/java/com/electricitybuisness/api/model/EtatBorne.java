package com.electricitybuisness.api.model;

/**
 * Classe représentant l'état d'une borne de recharge.
 */

public enum EtatBorne {
    LIBRE,
    OCCUPEE,
    EN_REPARATION,
    HORS_SERVICE,
    EN_ATTENTE_DE_CHARGEMENT,
    CHARGEMENT_EN_COURS,
    CHARGEMENT_TERMINE,
    EN_PANNE,
    EN_MAINTENANCE,
    RESERVEE
}
