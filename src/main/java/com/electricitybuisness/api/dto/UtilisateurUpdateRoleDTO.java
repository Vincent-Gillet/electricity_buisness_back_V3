package com.electricitybuisness.api.dto;

import com.electricitybuisness.api.model.RoleUtilisateur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurUpdateRoleDTO {
    private RoleUtilisateur role;
}
