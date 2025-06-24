package com.electricitybuisness.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
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
    @JoinColumn(name = "idUtilisateur", referencedColumnName = "id_utilisateur", insertable= false, updatable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "idVehicule", referencedColumnName = "id_vehicule", insertable= false, updatable = false)
    private Vehicule vehicule;

    @ManyToOne
    @JoinColumn(name = "idBorne", referencedColumnName = "id_borne", insertable= false, updatable = false)
    private Borne borne;

    @ManyToOne
    @JoinColumn(name = "idOption", referencedColumnName = "id_option", insertable= false, updatable = false)
    private Option option;

    @Column(name = "num_reservation", length = 20, nullable = false, unique = true)
    @NotBlank(message = "Le numéro de réservation est obligatoire")
    private String numReservation;

    @Column(name = "statut_reservation")
    @NotNull(message = "Le statut de la réservation est obligatoire")
    @Enumerated
    private StatutReservation statut;

    @Column(name = "montant_paye")
    @NotNull(message = "Le montant payé est obligatoire")
    private BigDecimal montantPaye;

    @Column(name = "date_paiement")
    @NotNull(message = "La date de paiement est obligatoire")
    private LocalDateTime datePaiement;

    @Column(name = "date_debut")
    @NotNull(message = "La date de début est obligatoire")
    @Future(message = "La date de début doit être dans le futur")
    private LocalDateTime dateDebut;

    @Column(name = "date_fin")
    @NotNull(message = "La date de fin est obligatoire")
    private LocalDateTime dateFin;

}
