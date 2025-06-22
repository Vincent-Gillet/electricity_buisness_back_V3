package com.electricitybuisness.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entité représentant une borne de réparation.
 * Une borne de réparation est associée à un réparateur et à une borne.
 */

@Data
@Entity
@Table(name = "bornes_reparateurs")
@NoArgsConstructor
@AllArgsConstructor
public class BorneReparateur {

    @EmbeddedId
    private BorneReparateurId id = new BorneReparateurId();

    @ManyToOne
    @JoinColumn(name = "reparateurId", referencedColumnName = "id", insertable= false, updatable = false)
    private Reparateur reparateur;

    @ManyToOne
    @JoinColumn(name = "id_borne", referencedColumnName = "id", insertable= false, updatable = false)
    private Borne borne;

    @Column(name = "reference", length = 100, nullable = false)
    @NotBlank(message = "La référence est obligatoire")
    private String reference;

    @Column(name = "date_reparation")
    @NotBlank(message = "La date de réparation est obligatoire")
    private LocalDate date_reparation;

    @Column(name = "description")
    @NotBlank(message = "La description est obligatoire")
    private String description;


}

