package com.electricitybuisness.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité représentant une adresse dans le système.
 * Une adresse peut être associée à un utilisateur et à un lieu.
 */

@Data
@Entity
@Table(name = "adresses")
@NoArgsConstructor
@AllArgsConstructor
public class Adresse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_adresse")
    private Long idAdresse;

    @Column(name = "nom_adresse")
    @NotBlank(message = "Le nom est obligatoire")
    private String nomAdresse;

    @Column(name = "adresse", length = 200, nullable = false)
    @NotBlank(message = "L'adresse est obligatoire")
    private String adresse;

    @Column(name = "code_postal", length = 10, nullable = false)
    @NotBlank(message = "Le code postal est obligatoire")
    private String codePostal;

    @Column(name = "ville", length = 100, nullable = false)
    @NotBlank(message = "La ville est obligatoire")
    private String ville;

    @Column(name = "pays", length = 100, nullable = false)
    @NotBlank(message = "Le pays est obligatoire")
    private String pays;

    @Column(name = "region", length = 100)
    private String region;

    @Column(name = "complement", length = 200)
    private String complement;

    @Column(name = "etage", length = 10)
    private String etage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_lieu")
    private Lieu lieu;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;
}
