package com.electricitybuisness.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "refresh_tokens")
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    private String idRefreshToken;

    @ManyToOne
    @JoinColumn(name = "utilisateur")
    private Utilisateur utilisateur;
}
