package com.electricitybuisness.api.dto;

import com.electricitybuisness.api.model.Borne;
import com.electricitybuisness.api.model.Lieu;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * DTO pour l'entité Lieu
 * Représentation simplifiée sans relations circulaires
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LieuDTO {

    @NotBlank(message = "Les instructions sont obligatoires")
    private String instructions;

    private List<BorneDTO> bornes;
}