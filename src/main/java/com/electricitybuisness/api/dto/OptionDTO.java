package com.electricitybuisness.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO pour l'entité ServiceSupDTO
 * Actuellement vide, mais peut être étendu pour inclure des propriétés spécifiques
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionDTO {

    private Long id;

    @NotNull(message = "Le nom de l'option est obligatoire")
    private String nomOption;

    @NotNull(message = "Le tarif de l'option est obligatoire")
    private BigDecimal tarifOption;

    @NotNull(message = "La description de l'option est obligatoire")
    private String descriptionOption;

}
