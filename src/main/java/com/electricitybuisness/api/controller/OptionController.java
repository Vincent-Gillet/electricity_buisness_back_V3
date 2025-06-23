package com.electricitybuisness.api.controller;

import com.electricitybuisness.api.dto.OptionDTO;
import com.electricitybuisness.api.mapper.EntityMapper;
import com.electricitybuisness.api.model.Option;
import com.electricitybuisness.api.service.OptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/options")
@RequiredArgsConstructor
public class OptionController {

    private final OptionService serviceOption;
    private final EntityMapper mapper;

    /**
     * Récupère toutes les options.
     * GET /api/options
     * @return Une liste de toutes les options
     */
    @GetMapping
    public ResponseEntity<List<OptionDTO>> getAllServiceSups() {
        List<Option> serviceSups = serviceOption.getAllOptions();
        List<OptionDTO> serviceSupsDTO = serviceSups.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(serviceSupsDTO);
    }

    /**
     * Récupère une option par son ID.
     * GET /api/options/{id}
     * @param id L'identifiant de l'option à récupérer
     * @return L'option correspondant à l'ID, ou un statut HTTP 404 Not Found si non trouvé
     */
    @GetMapping("/{id}")
    public ResponseEntity<OptionDTO> getServiceSupById(@PathVariable Long id) {
        return serviceOption.getOptionById(id)
                .map(serviceOption -> ResponseEntity.ok(mapper.toDTO(serviceOption)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crée une nouvelle option.
     * POST /api/options
     * @param optionDTO L'option à créer
     * @return L'option créé avec un statut HTTP 201 Created
     */
    @PostMapping
    public ResponseEntity<OptionDTO> saveServiceSup(@Valid @RequestBody OptionDTO optionDTO) {
        Option optionSup = mapper.toEntity(optionDTO);
        Option savedOption = serviceOption.saveOption(optionSup);
        OptionDTO savedDTO = mapper.toDTO(savedOption);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
    }

    /**
     * Met à jour une option existant.
     * PUT /api/options/{id}
     * @param id L'identifiant de l'option à mettre à jour
     * @param optionDTO L'option avec les nouvelles informations
     * @return L'option mis à jour, ou un statut HTTP 404 Not Found si non trouvé
     */
    @PutMapping("/{id}")
    public ResponseEntity<OptionDTO> updateOption(@PathVariable Long id, @Valid @RequestBody OptionDTO optionDTO) {
        if (!serviceOption.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Option option = mapper.toEntity(optionDTO);
        Option updatedOption = serviceOption.updateOption(id, option);
        OptionDTO updatedDTO = mapper.toDTO(updatedOption);
        return ResponseEntity.ok(updatedDTO);
    }

    /**
     * Supprime une option par son ID.
     * DELETE /api/options/{id}
     * @param id L'identifiant de l'option à supprimer
     * @return Une réponse vide avec le HTTP 204 No Content si la suppression est réussie, ou 404 Not Found si l'ID n'existe pas
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOption(@PathVariable Long id) {
        if (!serviceOption.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        serviceOption.deleteOptionById(id);
        return ResponseEntity.noContent().build();
    }

}
