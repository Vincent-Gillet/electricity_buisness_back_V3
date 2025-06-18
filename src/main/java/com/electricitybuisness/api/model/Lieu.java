package com.electricitybuisness.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Entité représentant un lieu dans le système.
 * Un lieu peut contenir des instructions, être associé à un utilisateur,
 * et avoir plusieurs médias et bornes associés.
 */

@Data
@Entity
@Table(name = "lieux")
@NoArgsConstructor
@AllArgsConstructor
public class Lieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lieu")
    private Long idLieu;

    @Column(name = "instructions", columnDefinition = "TEXT")
    private String instructions;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    @ManyToMany
    @JoinTable(
            name = "lieu_media",
            joinColumns = @JoinColumn(name = "id_lieu"),
            inverseJoinColumns = @JoinColumn(name = "id_media")
    )
    private Set<Media> medias = new HashSet<>();


    @OneToMany(mappedBy = "lieu")
    private Set<Borne> bornes = new HashSet<>();



}
