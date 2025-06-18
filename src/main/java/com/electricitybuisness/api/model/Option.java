package com.electricitybuisness.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité représentant une option dans le système.
 * Une option peut être associée à des médias.
 */

@Data
@Entity
@Table(name = "options")
@NoArgsConstructor
@AllArgsConstructor
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_option")
    private Long idOption;

    @Column(name = "nom_option", length = 100, nullable = false)
    @NotNull(message = "Le nom de l'option est obligatoire")
    private String nomOption;

    @Column(name = "tarif_option")
    @NotNull(message = "Le tarif de l'option est obligatoire")
    private BigDecimal tarifOption;

    @Column(name = "description_option")
    @NotNull(message = "La description de l'option est obligatoire")
    private String descriptionOption;

    @OneToMany(mappedBy = "options")
    private Set<Media> media = new HashSet<>();

}