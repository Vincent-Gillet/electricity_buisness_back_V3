package com.electricitybuisness.api.controller;

import com.electricitybuisness.api.dto.UtilisateurCreateDTO;
import com.electricitybuisness.api.dto.UtilisateurDTO;
import com.electricitybuisness.api.mapper.EntityMapper;
import com.electricitybuisness.api.model.Utilisateur;
import com.electricitybuisness.api.service.UtilisateurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    @PostMapping
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
     * @param utilisateurDTO Les nouvelles informations de l'utilisateur
     * @return L'utilisateur mis à jour, ou un statut HTTP 404 Not Found si l'ID n'existe pas
     */
    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> updateUtilisateur(@PathVariable Long id, @Valid @RequestBody UtilisateurDTO utilisateurDTO) {
        if (!utilisateurService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Utilisateur utilisateur = mapper.toEntity(utilisateurDTO);

        System.out.println("utilisateur : " + utilisateur);

        Utilisateur updatedUtilisateur = utilisateurService.updateUtilisateur(id, utilisateur);

        System.out.println("updatedUtilisateur : " + updatedUtilisateur);

        UtilisateurDTO updatedDTO = mapper.toDTO(updatedUtilisateur);

        System.out.println("updatedDTO : " + updatedDTO);
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
