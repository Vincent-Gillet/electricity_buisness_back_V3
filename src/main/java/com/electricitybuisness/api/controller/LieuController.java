package com.electricitybuisness.api.controller;

import com.electricitybuisness.api.dto.LieuDTO;
import com.electricitybuisness.api.mapper.EntityMapper;
import com.electricitybuisness.api.model.Lieu;
import com.electricitybuisness.api.service.LieuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des lieux.
 * Expose les endpoints pour les opérations CRUD sur les lieux.
 */
@RestController
@RequestMapping("/api/lieux")
@RequiredArgsConstructor
public class LieuController {

    private final LieuService lieuService;
    private final EntityMapper mapper;

    /**
     * Récupère tous les lieux.
     * GET /api/lieux
     * @return Une liste de tous les lieux
     */
    @GetMapping
    public ResponseEntity<List<LieuDTO>> getAllLieux() {
        List<Lieu> lieux = lieuService.getAllLieux();
        List<LieuDTO> lieuxDTO = lieux.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lieuxDTO);
    }

    /**
     * Récupère un lieu par son ID.
     * GET /api/lieux/{id}
     * @param id L'identifiant du lieu à récupérer
     * @return Le lieu correspondant à l'ID, ou un statut HTTP 404 Not Found si non trouvé
     */
    @GetMapping("/{id}")
    public ResponseEntity<LieuDTO> getLieuById(@PathVariable Long id) {
        return lieuService.getLieuById(id)
                .map(lieu -> ResponseEntity.ok(mapper.toDTO(lieu)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crée un nouveau lieu.
     * POST /api/lieux
     * @param lieuDTO Le lieu à créer
     * @return Le lieu créé avec un statut HTTP 201 Created
     */
    @PostMapping
    public ResponseEntity<LieuDTO> saveLieu(@Valid @RequestBody LieuDTO lieuDTO) {
        Lieu lieu = mapper.toEntity(lieuDTO);
        Lieu savedLieu = lieuService.saveLieu(lieu);
        LieuDTO savedDTO = mapper.toDTO(savedLieu);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
    }

    /**
     * Met à jour un lieu existant.
     * PUT /api/lieux/{id}
     * @param id L'identifiant du lieu à mettre à jour
     * @param lieuDTO Le lieu avec les nouvelles informations
     * @return Le lieu mis à jour, ou un statut HTTP 404 Not Found si non trouvé
     */
    @PutMapping("/{id}")
    public ResponseEntity<LieuDTO> updateLieu(@PathVariable Long id, @Valid @RequestBody LieuDTO lieuDTO) {
        if (!lieuService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Lieu lieu = mapper.toEntity(lieuDTO);
        Lieu updatedLieu = lieuService.updateLieu(id, lieu);
        LieuDTO updatedDTO = mapper.toDTO(updatedLieu);
        return ResponseEntity.ok(updatedDTO);
    }

    /**
     * Supprime un lieu par son ID.
     * DELETE /api/lieux/{id}
     * @param id L'identifiant du lieu à supprimer
     * @return Un statut HTTP 204 No Content si la suppression est réussie, ou 404 Not Found si le lieu n'existe pas
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLieuById(@PathVariable Long id) {
        if (!lieuService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        lieuService.deleteLieuById(id);
        return ResponseEntity.noContent().build();
    }


}