package com.electricitybuisness.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.Year;

/**
 * Entité représentant un véhicule dans le système.
 * Un véhicule est associé à un utilisateur.
 */

@Data
@Entity
@Table(name = "vehicules")
@NoArgsConstructor
@AllArgsConstructor
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehicule")
    private Long idVehicule;

    @NotBlank(message = "L'adresse email est obligatoire")
    @Length(min = 7, max = 7)
    private String plaqueImmatriculation;

    @NotBlank(message = "La marque est obligatoire")
    private String marque;

    @NotBlank(message = "Le modèle est obligatoire")
    private String modele;

    @Past(message = "L'année de fabrication doit être dans le passé")
    private Year annee;

    @Column(name = "capacite_batterie")
    @DecimalMin(value = "15", message = "La capacité de la batterie doit être comprise entre 15 et 100")
    @DecimalMax(value = "100", message = "La capacité de la batterie doit être comprise entre 15 et 100")
    @NotNull(message = "La capacité de la batterie est obligatoire")
    private int capaciteBatterie; // en kWh

    @ManyToOne
    @JoinColumn(name = "utilisateur")
    private Utilisateur utilisateur;

}
