package com.electricitybuisness.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entité représentant un utilisateur du système.
 * Un utilisateur peut effectuer des réservations et appartient à un lieu.
 */
@Entity
@Table(name = "utilisateur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur")
    private Long idUtilisateur;

    @JsonProperty("nom_utilisateur")
    @NotBlank
    @Length(min = 2, max = 50)
    private String nomUtilisateur;

    @NotNull
    @Length(min = 2, max = 50)
    private String prenom;

    @Column(unique = true)
    @NotNull
    private String pseudo;

    @Column(unique = true)
    @Email
    @NotBlank
    private String emailUtilisateur;

    @Column(name = "mot_de_passe")
    @NotBlank
    private String motDePasseUtilisateur;

    @Enumerated
    private RoleUtilisateur role = RoleUtilisateur.UTILISATEUR;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return motDePasseUtilisateur;
    }

    @Override
    public String getUsername() {
        return emailUtilisateur;
    }

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDeNaissance;

    @Length(min = 27, max = 27)
    private String iban;

    @NotNull
    private Boolean banni = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_media")
    private Media media;

/*    @ManyToMany
    private Set<Borne> bornes = new HashSet<>();*/

    @OneToMany
    private Set<Borne> bornes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Vehicule> vehicule = new HashSet<>();
}
