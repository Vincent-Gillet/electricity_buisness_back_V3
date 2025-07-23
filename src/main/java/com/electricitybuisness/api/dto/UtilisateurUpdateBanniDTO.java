package com.electricitybuisness.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurUpdateBanniDTO {
    @NotNull(message = "Le statut de bannissement est obligatoire")
    private Boolean banni;
}
