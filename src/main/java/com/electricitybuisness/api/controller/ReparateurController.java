package com.electricitybuisness.api.controller;

import com.electricitybuisness.api.dto.ReparateurDTO;
import com.electricitybuisness.api.mapper.EntityMapper;
import com.electricitybuisness.api.model.Reparateur;
import com.electricitybuisness.api.service.ReparateurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des réparateurs.
 * Expose les endpoints pour les opérations CRUD sur les réparateurs.
 */
@RestController
@RequestMapping("/api/reparateurs")
@RequiredArgsConstructor
public class ReparateurController {

    private final ReparateurService reparateurService;
    private final EntityMapper mapper;

    /**
     * Récupère tous les réparateurs.
     * GET /api/reparateurs
     * @return Une liste de tous les réparateurs
     */
    @GetMapping
    public ResponseEntity<List<ReparateurDTO>> getAllReparateurs() {
        List<Reparateur> reparateurs = reparateurService.getAllReparateurs();
        List<ReparateurDTO> reparateursDTO = reparateurs.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reparateursDTO);
    }

    /**
     * Récupère un réparateur par son ID.
     * GET /api/reparateurs/{id}
     * @param id L'identifiant du réparateur à récupérer
     * @return Le réparateur correspondant à l'ID, ou un statut HTTP 404 Not Found si non trouvé
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReparateurDTO> getReparateurById(@PathVariable Long id) {
        return reparateurService.getReparateurById(id)
                .map(reparateur -> ResponseEntity.ok(mapper.toDTO(reparateur)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crée un nouveau réparateur.
     * POST /api/reparateurs
     * @param reparateurDTO Le réparateur à créer
     * @return Le réparateur créé avec un statut HTTP 201 Created
     */
    @PostMapping("/reparateurs")
    public ResponseEntity<ReparateurDTO> saveReparateur(@Valid @RequestBody ReparateurDTO reparateurDTO) {
        Reparateur reparateur = mapper.toEntity(reparateurDTO);
        Reparateur savedReparateur = reparateurService.saveReparateur(reparateur);
        ReparateurDTO savedDTO = mapper.toDTO(savedReparateur);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
    }

    /**
     * Met à jour un réparateur existant.
     * PUT /api/reparateurs/{id}
     * @param id L'identifiant du réparateur à mettre à jour
     * @param reparateurDTO Le réparateur avec les nouvelles informations
     * @return Le réparateur mis à jour, ou un statut HTTP 404 Not Found si l'ID n'existe pas
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReparateurDTO> updateReparateur(@PathVariable Long id, @Valid @RequestBody ReparateurDTO reparateurDTO) {
        if (!reparateurService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Reparateur reparateur = mapper.toEntity(reparateurDTO);
        Reparateur updatedReparateur = reparateurService.updateReparateur(id, reparateur);
        ReparateurDTO updatedDTO = mapper.toDTO(updatedReparateur);
        return ResponseEntity.ok(updatedDTO);
    }

    /**
     * Supprime un réparateur par son ID.
     * DELETE /api/reparateurs/{id}
     * @param id L'identifiant du réparateur à supprimer
     * @return Un statut HTTP 204 No Content si la suppression est réussie, ou 404 Not Found si l'ID n'existe pas
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReparateurById(@PathVariable Long id) {
        if (!reparateurService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        reparateurService.deleteReparateurById(id);
        return ResponseEntity.noContent().build();
    }




}
