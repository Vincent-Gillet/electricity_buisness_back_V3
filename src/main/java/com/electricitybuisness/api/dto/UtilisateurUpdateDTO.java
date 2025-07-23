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
public class UtilisateurUpdateDTO {
    @NotBlank(message = "Le nom de l'utilisateur est obligatoire")
    private String nomUtilisateur;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "Le pseudo est obligatoire")
    private String pseudo;

    @NotBlank(message = "L'adresse email est obligatoire")
    @Email(message = "L'adresse email doit être valide mise à jour")
    private String emailUtilisateur;

    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate dateDeNaissance;

    private String iban;
}
