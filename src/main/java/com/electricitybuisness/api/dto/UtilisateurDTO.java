package com.electricitybuisness.api.dto;

import com.electricitybuisness.api.model.RoleUtilisateur;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

/**
 * DTO pour l'entité Utilisateur
 * Exclut le mot de passe et évite les relations circulaires
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurDTO {

    @NotBlank(message = "Le nom de l'utilisateur est obligatoire")
    private String nomUtilisateur;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "Le pseudo est obligatoire")
    private String pseudo;

    @NotBlank(message = "L'adresse email est obligatoire")
    @Email(message = "L'adresse email doit être valide")
    private String emailUtilisateur;

    @NotNull(message = "Le rôle est obligatoire")
    private RoleUtilisateur role;

    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate dateDeNaissance;

    private String iban;

    @NotNull(message = "Le statut de bannissement est obligatoire")
    private Boolean banni;
}