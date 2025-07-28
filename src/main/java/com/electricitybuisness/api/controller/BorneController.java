package com.electricitybuisness.api.controller;

import com.electricitybuisness.api.dto.BorneDTO;
import com.electricitybuisness.api.dto.BorneSearchDTO;
import com.electricitybuisness.api.mapper.EntityMapper;
import com.electricitybuisness.api.model.Borne;
import com.electricitybuisness.api.service.BorneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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




    @GetMapping("/bornes-libres")
    public ResponseEntity<List<BorneDTO>> getAllBornesNonOccupees() {
        List<Borne> bornes = borneService.findAvailableBornes();
        List<BorneDTO> borneDTO = bornes.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(borneDTO);
    }

    @GetMapping("/recherche-bornes-disponibilites-rayon")
    public ResponseEntity<List<BorneDTO>> findBornesDisponiblesInRadius(
            @RequestParam BigDecimal longitude,
            @RequestParam BigDecimal latitude,
            @RequestParam double rayon,
            @RequestParam(defaultValue = "false") boolean occupee
    ) {
        List<Borne> bornes = borneService.findBornesAvailableInRadius(longitude, latitude, rayon, occupee);
        List<BorneDTO> borneDTO = bornes.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(borneDTO);
    }

    @GetMapping("/recherche-bornes-dates-disponibilites")
    public ResponseEntity<List<BorneDTO>> findBornesDisponiblesInRadiusAndPeriod(
            @RequestParam(defaultValue = "false") boolean occupee,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin
    ) {
        List<Borne> bornes = borneService.findBornesAvailableInPeriod(
                occupee, dateDebut, dateFin);
        List<BorneDTO> borneDTO = bornes.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(borneDTO);
    }

    @GetMapping("/recherche-bornes-dates-disponibilites-rayon")
    public ResponseEntity<List<BorneDTO>> findBornesDisponiblesInRadiusAndPeriod(
            @RequestParam BigDecimal longitude,
            @RequestParam BigDecimal latitude,
            @RequestParam double rayon,
            @RequestParam(defaultValue = "false") boolean occupee,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin
    ) {
        List<Borne> bornes = borneService.findBornesAvailableInRadiusAndPeriod(
                longitude, latitude, rayon, occupee, dateDebut, dateFin);
        List<BorneDTO> borneDTO = bornes.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(borneDTO);
    }


/*    @PostMapping("/recherche")
    public ResponseEntity<List<BorneDTO>> searchBornes(
            @RequestParam(required = false) BigDecimal longitude,
            @RequestParam(required = false) BigDecimal latitude,
            @RequestParam(required = false) double rayon,
            @RequestParam(defaultValue = "false", required = false) boolean occupee,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin
    ) {
        List<Borne> bornes = borneService.searchBornes(
                longitude, latitude, rayon, occupee, dateDebut, dateFin);
        List<BorneDTO> borneDTO = bornes.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(borneDTO);
    }*/




    @GetMapping("/search-bornes")
    public ResponseEntity<List<BorneDTO>> searchBornesWithCriteria(
            @RequestParam(required = false) BigDecimal longitude,
            @RequestParam(required = false) BigDecimal latitude,
            @RequestParam(required = false) Double rayon,
            @RequestParam(required = false) Boolean occupee,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin
    ) {
        List<Borne> bornes = borneService.searchBornesWithCriteria(
                longitude, latitude, rayon, occupee, dateDebut, dateFin);
        List<BorneDTO> borneDTO = bornes.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(borneDTO);
    }


}
