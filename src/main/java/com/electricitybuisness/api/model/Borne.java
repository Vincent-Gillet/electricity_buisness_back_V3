package com.electricitybuisness.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité représentant une borne électrique dans le système.
 * Une borne appartient à un lieu et peut avoir des réservations et des tarifs.
 */

@Data
@Entity
@Table(name = "bornes")
@NoArgsConstructor
@AllArgsConstructor
public class Borne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_borne")
    private Long idBorne;

    @Column(name = "nom_borne", length = 100, nullable = false)
    @NotBlank(message = "Le nom de la borne est obligatoire")
    private String nomBorne;

    @Column(name = "latitude", precision = 10, scale = 8, nullable = false)
    @DecimalMin(value = "-90.0", message = "La latitude doit être entre -90 et 90")
    @DecimalMax(value = "90.0", message = "La latitude doit être entre -90 et 90")
    @NotNull(message = "La latitude est obligatoire")
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 11, scale = 8, nullable = false)
    @DecimalMin(value = "-180.0", message = "La longitude doit être entre -180 et 180")
    @DecimalMax(value = "180.0", message = "La longitude doit être entre -180 et 180")
    @NotNull(message = "La longitude est obligatoire")
    private BigDecimal longitude;

    @Column(name = "tarif", nullable = false)
    @DecimalMin(value = "0.0", message = "Le tarif doit être positive")
    @NotNull(message = "Le tarif est obligatoire")
    private BigDecimal tarif;

    @Column(name = "puissance", precision = 10, scale = 2, nullable = false)
    @DecimalMin(value = "0.0", message = "La puissance doit être positive")
    @NotNull(message = "La puissance est obligatoire")
    private BigDecimal puissance;

    @Column(name = "instruction", columnDefinition = "TEXT")
    private String instruction;

    @Column(name = "sur_pied", nullable = false)
    private Boolean surPied;

    @Column(name = "etat_borne", nullable = false)
    @Enumerated(EnumType.STRING)
    private EtatBorne etatBorne;

    @Column(name = "occupee", nullable = false)
    private Boolean occupee = false;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    @Column(name = "derniere_maintenance")
    private LocalDateTime derniereMaintenance;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "borne")
    private Set<Media> medias = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "id_lieu")
    private Lieu lieu;

    @ManyToMany
    private Set<Reparateur> reparateurs = new HashSet<>();

}
