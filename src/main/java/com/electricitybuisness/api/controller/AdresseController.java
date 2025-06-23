package com.electricitybuisness.api.controller;

import com.electricitybuisness.api.dto.AdresseDTO;
import com.electricitybuisness.api.mapper.EntityMapper;
import com.electricitybuisness.api.model.Adresse;
import com.electricitybuisness.api.service.AdresseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des adresses.
 * Expose les endpoints pour les opérations CRUD sur les adresses.
 */
@RestController
@RequestMapping("/api/adresses")
@RequiredArgsConstructor
public class AdresseController {

    private final AdresseService adresseService;
    private final EntityMapper mapper;

    /**
     * Récupère toutes les adresses.
     * GET /api/adresses
     * @return Une liste de toutes les adresses
     */
    @GetMapping
    public ResponseEntity<List<AdresseDTO>> getAllAdresses() {
        List<Adresse> adresses = adresseService.getAllAdresses();
        List<AdresseDTO> adressesDTO = adresses.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(adressesDTO);
    }

    /**
     * Récupère une adresse par son ID.
     * GET /api/adresses/{id}
     * @param id L'identifiant de l'adresse à récupérer
     * @return L'adresse correspondante à l'ID, ou un statut HTTP 404 Not Found si non trouvée
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdresseDTO> getAdresseById(@PathVariable Long id) {
        return adresseService.getAdresseById(id)
                .map(adresse -> ResponseEntity.ok(mapper.toDTO(adresse)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crée une nouvelle adresse.
     * POST /api/adresses
     * @param adresseDTO L'adresse à créer
     * @return L'adresse créée avec un statut HTTP 201 Created
     */
    @PostMapping
    public ResponseEntity<AdresseDTO> saveAdresse (@Valid @RequestBody AdresseDTO adresseDTO) {
        Adresse adresse = mapper.toEntity(adresseDTO);
        Adresse savedAdresse = adresseService.saveAdresse(adresse);
        AdresseDTO savedDTO = mapper.toDTO(savedAdresse);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
    }

    /**
     * Met à jour une adresse existante.
     * PUT /api/adresses/{id}
     * @param id L'identifiant de l'adresse à mettre à jour
     * @param adresseDTO L'adresse avec les nouvelles informations
     * @return L'adresse mise à jour, ou un statut HTTP 404 Not Found si l'adresse n'existe pas
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdresseDTO> updateAdresse(@PathVariable Long id, @Valid @RequestBody AdresseDTO adresseDTO) {
        if (!adresseService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Adresse adresse = mapper.toEntity(adresseDTO);
        Adresse updatedAdresse = adresseService.updateAdresse(id, adresse);
        AdresseDTO updatedDTO = mapper.toDTO(updatedAdresse);
        return ResponseEntity.ok(updatedDTO);
    }

    /**
     * Supprime une adresse par son ID.
     * DELETE /api/adresses/{id}
     * @param id L'identifiant de l'adresse à supprimer
     * @return Un statut HTTP 204 No Content si la suppression est réussie, ou 404 Not Found si l'adresse n'existe pas
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdresseById(@PathVariable Long id) {
        if (!adresseService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        adresseService.deleteAdresseById(id);
        return ResponseEntity.noContent().build();
    }


}
