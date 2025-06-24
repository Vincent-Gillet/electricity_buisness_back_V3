package com.electricitybuisness.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurCreateDTO {

    @NotBlank(message = "Le nom de l'utilisateur est obligatoire")
    private String utilisateurNom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "Le pseudo est obligatoire")
    private String pseudo;

/*
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String utilisateurMotDePasse;
*/

    @NotBlank(message = "L'adresse email est obligatoire")
    @Email(message = "L'adresse email doit être valide")
    private String utilisateurEmail;

    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate dateDeNaissance;

}
