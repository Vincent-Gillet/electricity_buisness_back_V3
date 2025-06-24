package com.electricitybuisness.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.Year;

/**
 * DTO pour l'entité Vehicule
 * Actuellement vide, mais peut être étendu pour inclure des propriétés spécifiques
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculeDTO {

    @JsonProperty("plaqueImmatriculation")
    @NotBlank(message = "L'adresse email est obligatoire")
    @Length(min = 7, max = 7)
    private String plaqueImmatriculation;

    @NotBlank(message = "La marque est obligatoire")
    private String marque;

    @NotBlank(message = "Le modèle est obligatoire")
    private String modele;

    @Past(message = "L'année de fabrication doit être dans le passé")
    private Year annee;

    @DecimalMin(value = "15", message = "La capacité de la batterie doit être comprise entre 15 et 100")
    @DecimalMax(value = "100", message = "La capacité de la batterie doit être comprise entre 15 et 100")
    @NotNull(message = "La capacité de la batterie est obligatoire")
    private int capaciteBatterie; // en kWh


}
