package com.electricitybuisness.api.controller;

import com.electricitybuisness.api.dto.VehiculeDTO;
import com.electricitybuisness.api.mapper.EntityMapper;
import com.electricitybuisness.api.model.Vehicule;
import com.electricitybuisness.api.service.VehiculeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des véhicules.
 * Expose les endpoints pour les opérations CRUD sur les véhicules.
 */
@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final VehiculeService vehiculeService;
    private final EntityMapper mapper;

    /**
     * Récupère tous les vehicules.
     * GET /api/vehicules
     * @return Une liste de tous les véhicules
     */
    @GetMapping
    public ResponseEntity<List<VehiculeDTO>> getAllVehicules() {
        List<Vehicule> vehicules = vehiculeService.getAllVehicules();
        List<VehiculeDTO> vehiculesDTO = vehicules.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(vehiculesDTO);
    }

    /**
     * Récupère un vehicule par son ID.
     * GET /api/vehicules/{id}
     * @param id L'identifiant du véhicule à récupérer
     * @return Le véhicule correspondant à l'ID, ou un statut HTTP 404 Not Found si non trouvé
     */
    @GetMapping("/{id}")
    public ResponseEntity<VehiculeDTO> getVehiculeById(@PathVariable Long id) {
        return vehiculeService.getVehiculeById(id)
                .map(vehicule -> ResponseEntity.ok(mapper.toDTO(vehicule)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crée un nouveau vehicule.
     * POST /api/vehicules
     * @param vehiculeDTO Le véhicule à créer
     * @return Le véhicule créé avec un statut HTTP 201 Created
     */
    @PostMapping
    public ResponseEntity<VehiculeDTO> saveVehicule(@Valid @RequestBody VehiculeDTO vehiculeDTO) {
        Vehicule vehicule = mapper.toEntity(vehiculeDTO);
        Vehicule savedVehicule = vehiculeService.saveVehicule(vehicule);
        VehiculeDTO savedDTO = mapper.toDTO(savedVehicule);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
    }

    /**
     * Met à jour un vehicule existant.
     * PUT /api/vehicules/{id}
     * @param id L'identifiant du véhicule à mettre à jour
     * @param vehiculeDTO Le véhicule avec les nouvelles informations
     * @return Le véhicule mis à jour, ou un statut HTTP 404 Not Found si l'ID n'existe pas
     */
    @PutMapping("/{id}")
    public ResponseEntity<VehiculeDTO> updateVehicule(@PathVariable Long id, @Valid @RequestBody VehiculeDTO vehiculeDTO) {
        if (!vehiculeService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Vehicule vehicule = mapper.toEntity(vehiculeDTO);
        Vehicule updatedVehicule = vehiculeService.updateVehicule(id, vehicule);
        VehiculeDTO updatedDTO = mapper.toDTO(updatedVehicule);
        return ResponseEntity.ok(updatedDTO);    }


    /**
     * Supprime un vehicule.
     * DELETE /api/vehicules/{id}
     * @param id L'identifiant du véhicule à supprimer
     * @return Une réponse vide avec le statut 204 No Content si le véhicule a été supprimé, ou 404 Not Found si le véhicule n'existe pas
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicule(@PathVariable Long id) {
        if (!vehiculeService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        vehiculeService.deleteVehiculeById(id);
        return ResponseEntity.noContent().build();
    }

}