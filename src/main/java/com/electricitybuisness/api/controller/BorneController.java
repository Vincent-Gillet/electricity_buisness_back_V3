package com.electricitybuisness.api.controller;

import com.electricitybuisness.api.dto.BorneDTO;
import com.electricitybuisness.api.mapper.EntityMapper;
import com.electricitybuisness.api.model.Borne;
import com.electricitybuisness.api.service.BorneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des bornes de recharge.
 * Expose les endpoints pour les opérations CRUD sur les bornes.
 */
@RestController
@RequestMapping("/api/bornes")
@RequiredArgsConstructor
public class BorneController {
    private final BorneService borneService;
    private final EntityMapper mapper;


    /**
     * Récupère toutes les bornes de recharge.
     * GET /api/bornes
     * @return Une liste de toutes les bornes
     */
    @GetMapping
    public ResponseEntity<List<BorneDTO>> getAllBornes() {
        List<Borne> bornes = borneService.getAllBornes();
        List<BorneDTO> borneDTO = bornes.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(borneDTO);
    }

    /**
     * Récupère une borne de recharge par son ID.
     * GET /api/bornes/{id}
     * @param id L'identifiant de la borne à récupérer
     * @return La borne correspondante à l'ID, ou un statut HTTP 404 Not Found si non trouvée
     */
    @GetMapping("/{id}")
    public ResponseEntity<BorneDTO> getBorneById (@PathVariable Long id) {
        return borneService.getBorneById(id)
                .map(borne -> ResponseEntity.ok(mapper.toDTO(borne)))
                .orElse(ResponseEntity.notFound().build());
    }


    /**
     * Crée une nouvelle borne de recharge.
     * POST /api/bornes
     * @param borneDTO La borne à créer
     * @return La borne créée avec un statut HTTP 201 Created
     */
    @PostMapping
    public ResponseEntity<BorneDTO> saveBorne (@Valid @RequestBody BorneDTO borneDTO) {
        Borne borne = mapper.toEntity(borneDTO);
        Borne savedBorne = borneService.saveBorne(borne);
        BorneDTO savedDTO = mapper.toDTO(savedBorne);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
    }

    /**
     * Met à jour une borne de recharge existante.
     * PUT /api/bornes/{id}
     * @param id L'identifiant de la borne à mettre à jour
     * @param borneDTO La borne avec les nouvelles informations
     * @return La borne mise à jour ou un statut HTTP 404 Not Found si non trouvée
     */
    @PutMapping("/{id}")
    public ResponseEntity<BorneDTO> updateBorne(@PathVariable Long id, @Valid @RequestBody BorneDTO borneDTO) {
        if (!borneService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Borne borne = mapper.toEntity(borneDTO);
        Borne updatedBorne = borneService.updateBorne(id, borne);
        BorneDTO updatedDTO = mapper.toDTO(updatedBorne);
        return ResponseEntity.ok(updatedDTO);
    }

    /**
     * Supprime une borne de recharge par son ID.
     * DELETE /api/bornes/{id}
     * @param id L'identifiant de la borne à supprimer
     * @return Un statut HTTP 204 No Content si la suppression est réussie, ou 404 Not Found si non trouvée
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorneById(@PathVariable Long id) {
        if (!borneService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        borneService.deleteBorneById(id);
        return ResponseEntity.noContent().build();
    }

}
