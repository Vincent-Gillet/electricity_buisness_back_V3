package com.electricitybuisness.api.service;


import com.electricitybuisness.api.model.Vehicule;
import com.electricitybuisness.api.repository.VehiculeRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VehiculeService {

    private final VehiculeRepository vehiculeRepository;

    /**
     * Récupère tous les vehicules.
     * @return Une liste de tous les vehicules
     */
    @Transactional(readOnly = true)
    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }

    /**
     * Récupère un vehicule par son ID.
     * @param id L'identifiant du vehicule à récupérer
     * @return Un Optional contenant le vehicule si trouvé, sinon vide
     */
    public Optional<Vehicule> getVehiculeById(Long id) {
        return vehiculeRepository.findById(id);
    }


    /**
     * Crée un nouveau vehicule.
     * @param vehicule Le vehicule à enregistrer
     * @return Le vehicule enregistré
     */
    public Vehicule saveVehicule(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }


    /**
     * Met à jour un vehicule existant.
     * @param id L'identifiant du vehicule à mettre à jour
     * @param vehicule Le vehicule avec les nouvelles informations
     * @return Le vehicule mis à jour
     */
    public Vehicule updateVehicule(Long id, Vehicule vehicule) {
        vehicule.setIdVehicule(id);
        return vehiculeRepository.save(vehicule);
    }

    /**
     * Supprime un vehicule.
     * @param id L'identifiant du vehicule à supprimer
     */
    public void deleteVehiculeById(Long id) {
        vehiculeRepository.deleteById(id);
    }

    /**
     * Vérifie si un vehicule existe.
     * @param id L'identifiant du vehicule à vérifier
     * @return true si le vehicule existe, sinon false
     */
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return vehiculeRepository.existsById(id);
    }


}
