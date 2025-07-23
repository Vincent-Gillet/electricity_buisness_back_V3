package com.electricitybuisness.api.controller;

import com.electricitybuisness.api.dto.*;
import com.electricitybuisness.api.mapper.EntityMapper;
import com.electricitybuisness.api.model.Utilisateur;
import com.electricitybuisness.api.service.UtilisateurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des utilisateurs.
 * Expose les endpoints pour les opérations CRUD sur les utilisateurs.
 */
@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    private static final Logger log = LoggerFactory.getLogger(UtilisateurController.class);


    private final UtilisateurService utilisateurService;
    private final EntityMapper mapper;

    /**
     * Récupère tous les utilisateurs.
     * GET /api/utilisateurs
     * @return Une liste de tous les utilisateurs
     */
    @GetMapping
    public ResponseEntity<List<UtilisateurDTO>> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
        List<UtilisateurDTO> utilisateursDTO = utilisateurs.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(utilisateursDTO);
    }

    /**
     * Récupère un utilisateur par son ID.
     * GET /api/utilisateurs/{id}
     * @param id L'identifiant de l'utilisateur à récupérer
     * @return L'utilisateur correspondant à l'ID, ou un statut HTTP 404 Not Found si non trouvé
     */
    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> getUtilisateurById(@PathVariable Long id) {
        return utilisateurService.getUtilisateurById(id)
                .map(utilisateur -> ResponseEntity.ok(mapper.toDTO(utilisateur)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crée un nouvel utilisateur.
     * POST /api/utilisateurs
     * @param utilisateurDTO L'utilisateur à créer
     * @return L'utilisateur créé avec un statut HTTP 201 Created
     */
    @PostMapping("/register")
    public ResponseEntity<UtilisateurDTO> saveUtilisateur(@Valid @RequestBody UtilisateurCreateDTO utilisateurDTO) {
        Utilisateur utilisateur = mapper.toEntity(utilisateurDTO);
        Utilisateur savedUtilisateur = utilisateurService.saveUtilisateur(utilisateur);
        UtilisateurDTO savedDTO = mapper.toDTO(savedUtilisateur);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
    }

    /**
     * Met à jour un utilisateur existant.
     * PUT /api/utilisateurs/{id}
     * @param id L'identifiant de l'utilisateur à mettre à jour
     * @param utilisateurUpdateDTO Les nouvelles informations de l'utilisateur
     * @return L'utilisateur mis à jour, ou un statut HTTP 404 Not Found si l'ID n'existe pas
     */
    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> updateUtilisateur(@PathVariable Long id, @Valid @RequestBody UtilisateurUpdateDTO utilisateurUpdateDTO) {
        if (!utilisateurService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Utilisateur existing = utilisateurService.getUtilisateurById(id).orElseThrow();
        Utilisateur utilisateur = mapper.toEntity(utilisateurUpdateDTO, existing);
        Utilisateur updatedUtilisateur = utilisateurService.updateUtilisateur(id, utilisateur);
        UtilisateurDTO updatedDTO = mapper.toDTO(updatedUtilisateur);
        return ResponseEntity.ok(updatedDTO);
    }

    /**
     * Met à jour le mot de passe d'un utilisateur.
     * PUT /api/utilisateurs/password/{id}
     * @param id L'identifiant de l'utilisateur dont le mot de passe doit être mis à jour
     * @param utilisateurUpdatePasswordDTO Les nouvelles informations de mot de passe
     * @return L'utilisateur mis à jour avec le nouveau mot de passe, ou un statut HTTP 404 Not Found si l'ID n'existe pas
     */
/*    @PutMapping("/password/{id}")
    public ResponseEntity<UtilisateurDTO> updateUtilisateur(@PathVariable Long id, @Valid @RequestBody UtilisateurUpdatePasswordDTO utilisateurUpdatePasswordDTO) {
       if (!utilisateurService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
       *//*
        Utilisateur existing = utilisateurService.getUtilisateurById(id).orElseThrow();
        Utilisateur utilisateur = mapper.toEntity(utilisateurUpdatePasswordDTO, existing);
        Utilisateur updatedUtilisateur = utilisateurService.updateUtilisateur(id, utilisateur);
        UtilisateurDTO updatedDTO = mapper.toDTO(updatedUtilisateur);
        return ResponseEntity.ok(updatedDTO);*//*
        Utilisateur updated = utilisateurService.updatePassword(id, utilisateurUpdatePasswordDTO);
        System.out.println("Updated user: " + updated);
        UtilisateurDTO updatedDTO = mapper.toDTO(updated);
        return ResponseEntity.ok(updatedDTO);
    }*/


    @PutMapping("/password/{id}")
    public ResponseEntity<UtilisateurDTO> updateUtilisateurPassword(@PathVariable Long id, @Valid @RequestBody UtilisateurUpdatePasswordDTO utilisateurUpdatePasswordDTO) {
        if (!utilisateurService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Utilisateur existing = utilisateurService.getUtilisateurById(id).orElseThrow();
        Utilisateur utilisateur = mapper.toEntityPassword(utilisateurUpdatePasswordDTO, existing);
        Utilisateur updatedUtilisateur = utilisateurService.updateUtilisateur(id, utilisateur);
        UtilisateurDTO updatedDTO = mapper.toDTO(updatedUtilisateur);
        return ResponseEntity.ok(updatedDTO);

    }


    @PutMapping("/banni/{id}")
    public ResponseEntity<UtilisateurDTO> updateUtilisateurBanni(@PathVariable Long id, @Valid @RequestBody UtilisateurUpdateBanniDTO utilisateurUpdateBanniDTO) {
        if (!utilisateurService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Utilisateur existing = utilisateurService.getUtilisateurById(id).orElseThrow();
        Utilisateur utilisateur = mapper.toEntityBanni(utilisateurUpdateBanniDTO, existing);
        Utilisateur updatedUtilisateur = utilisateurService.updateUtilisateur(id, utilisateur);
        UtilisateurDTO updatedDTO = mapper.toDTO(updatedUtilisateur);
        return ResponseEntity.ok(updatedDTO);
    }

    @PutMapping("/role/{id}")
    public ResponseEntity<UtilisateurDTO> updateUtilisateurRole(@PathVariable Long id, @Valid @RequestBody UtilisateurUpdateRoleDTO utilisateurUpdateRoleDTO) {
        if (!utilisateurService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Utilisateur existing = utilisateurService.getUtilisateurById(id).orElseThrow();
        Utilisateur utilisateur = mapper.toEntityRole(utilisateurUpdateRoleDTO, existing);
        Utilisateur updatedUtilisateur = utilisateurService.updateUtilisateur(id, utilisateur);
        UtilisateurDTO updatedDTO = mapper.toDTO(updatedUtilisateur);
        return ResponseEntity.ok(updatedDTO);
    }


    /**
     * Supprime un utilisateur par son ID.
     * DELETE /api/utilisateurs/{id}
     * @param id L'identifiant de l'utilisateur à supprimer
     * @return Une réponse vide avec le statut 204 No Content si l'utilisateur a été supprimé, ou 404 Not Found si l'utilisateur n'existe pas
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        if (!utilisateurService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        utilisateurService.deleteUtilisateurById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Récupère un utilisateur par son pseudo.
     * GET /api/utilisateurs/pseudo/{pseudo}
     * @param pseudo Le pseudo de l'utilisateur à récupérer
     * @return L'utilisateur correspondant au pseudo, ou un statut HTTP 404 Not Found si non trouvé
     */
    @GetMapping("/pseudo/{pseudo}")
    public ResponseEntity<UtilisateurDTO> getUtilisateurByPseudo(@PathVariable String pseudo) {
        return utilisateurService.findByPseudo(pseudo)
                .map(utilisateur -> ResponseEntity.ok(mapper.toDTO(utilisateur)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Récupère un utilisateur par son adresse email.
     * GET /api/utilisateurs/email/{email}
     * @param email L'adresse email de l'utilisateur à récupérer
     * @return L'utilisateur correspondant à l'email, ou un statut HTTP 404 Not Found si non trouvé
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UtilisateurDTO> getUtilisateurByEmail(@PathVariable String email) {
        return utilisateurService.findByUtilisateurEmail(email)
                .map(utilisateur -> ResponseEntity.ok(mapper.toDTO(utilisateur)))
                .orElse(ResponseEntity.notFound().build());
    }



}
