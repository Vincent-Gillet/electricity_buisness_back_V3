package com.electricitybuisness.api.controller;

import com.electricitybuisness.api.config.CustomUserDetailService;
import com.electricitybuisness.api.model.RefreshToken;
import com.electricitybuisness.api.model.Utilisateur;
import com.electricitybuisness.api.service.JwtService;
import com.electricitybuisness.api.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailService customUserDetailService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.emailUtilisateur(), request.motDePasseUtilisateur())
        );

        final UserDetails userDetails = customUserDetailService.loadUserByUsername(request.emailUtilisateur());

        final String jwt = jwtService.generateAccessToken(userDetails.getUsername());
        final RefreshToken refreshToken = refreshTokenService.generateRefreshTokenBdd((Utilisateur) userDetails);

        return ResponseEntity.ok(
                Map.of(
                    "accessToken", jwt,
                    "refreshToken", String.valueOf(refreshToken.getIdRefreshToken())
                )
        );
    }



    record AuthRequest (String emailUtilisateur, String motDePasseUtilisateur) {}


    @PostMapping("/refresh")
    public ResponseEntity<String> refreshAccessToken(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");

        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.badRequest().body("Le refresh token est manquant ou invalide.");
        }

        Optional<RefreshToken> refreshTokenSave = refreshTokenService.getRefreshTokenByToken(refreshToken);

        if (refreshTokenSave == null || refreshTokenSave.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Un refresh token est requis.");
        }

        try {
            Utilisateur utilisateur = refreshTokenSave.get().getUtilisateur();
            String username = utilisateur.getUsername();

            if (!refreshTokenService.isTokenValid(refreshToken, utilisateur)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Le refresh token est invalide.");
            }

            String nouveauAccessToken = jwtService.generateAccessToken(username);

            return ResponseEntity.ok(nouveauAccessToken);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Une erreur de traitement du refresh token.");
        }

    }

}
