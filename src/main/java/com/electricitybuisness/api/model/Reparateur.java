package com.electricitybuisness.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entité représentant un réparateur dans le système.
 * Un réparateur peut être associé à plusieurs bornes.
 */

@Data
@Entity
@Table(name = "reparateurs")
@NoArgsConstructor
@AllArgsConstructor
public class Reparateur implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reparateur")
    private Long idReparateur;

    @NotBlank
    @Column(name = "nom_reparateur")
    private String nomReparateur;

    @NotBlank
    @Column(name = "email_reparateur")
    private String emailReparateur;

    @NotBlank
    @Column(name = "mot_de_passe_reparateur")
    private String motDePasseReparateur;

    @Enumerated
    @NotNull
    private RoleUtilisateur role = RoleUtilisateur.UTILISATEUR;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    };

    @Override
    public String getPassword() {
        return motDePasseReparateur;
    }

    @Override
    public String getUsername() {
        return emailReparateur;
    }

    @ManyToMany(mappedBy = "reparateurs")
    private Set<Borne> bornes = new HashSet<>();

}

