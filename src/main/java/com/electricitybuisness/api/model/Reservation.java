package com.electricitybuisness.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entité représentant une réservation dans le système.
 * Une réservation est associée à un utilisateur, un véhicule, une borne et éventuellement une option.
 */

@Data
@Entity
@Table(name = "reservations")
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    private Long idReservation;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", referencedColumnName = "id", insertable= false, updatable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "id_vehicule", referencedColumnName = "id", insertable= false, updatable = false)
    private Vehicule vehicule;

    @ManyToOne
    @JoinColumn(name = "id_borne", referencedColumnName = "id", insertable= false, updatable = false)
    private Borne borne;

    @ManyToOne
    @JoinColumn(name = "id_servivce_sup", referencedColumnName = "id", insertable= false, updatable = false)
    private Option option;

    private String numReservation;

    @Enumerated
    private StatutReservation statut;

    private BigDecimal montantPaye;

    private LocalDateTime datePaiement;

    @NotNull(message = "La date de début est obligatoire")
    @Future(message = "La date de début doit être dans le futur")
    private LocalDateTime dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDateTime dateFin;

}
