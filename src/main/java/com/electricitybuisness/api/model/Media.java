package com.electricitybuisness.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entité représentant un média.
 * Un média peut être associé à une option, une borne et plusieurs lieux.
 */

@Data
@Entity
@Table(name = "medias")
@NoArgsConstructor
@AllArgsConstructor
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_media")
    private Long idMedia;

    @Column(name = "nom_media", length = 100, nullable = false)
    @NotBlank(message = "Le nom est obligatoire")
    private String nomMedia;

    @Column(name = "url", length = 500, nullable = false)
    @NotBlank(message = "L'URL est obligatoire")
    private String url;

    @Column(name = "type", length = 50, nullable = false)
    @NotBlank(message = "Le type est obligatoire")
    private String type;

    @Column(name = "description", columnDefinition = "TEXT")
    private String descriptionMedia;

    @Column(name = "taille")
    private String taille;

    @Column(name = "date_creation")
    @NotNull(message = "La date de création est obligatoire")
    private LocalDateTime dateCreation = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "id_options")
    private Option options;

    @ManyToOne
    @JoinColumn(name = "id_borne")
    private Borne borne;

    @ManyToMany(mappedBy = "medias")
    private List<Lieu> lieux;
}
