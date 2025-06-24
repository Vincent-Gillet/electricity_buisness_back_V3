package com.electricitybuisness.api.controller;

import com.electricitybuisness.api.config.CustomUserDetailService;
import com.electricitybuisness.api.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailService customUserDetailService;

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.emailUtilisateur(), request.motDePasseUtilisateur())
        );

        final UserDetails userDetails = customUserDetailService.loadUserByUsername(request.emailUtilisateur());

        final String jwt = jwtService.generateAccessToken(userDetails.getUsername());

        return ResponseEntity.ok(jwt);
    }


    record AuthRequest (String emailUtilisateur, String motDePasseUtilisateur) {}

}
