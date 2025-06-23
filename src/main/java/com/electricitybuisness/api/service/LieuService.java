package com.electricitybuisness.api.service;

import com.electricitybuisness.api.model.Lieu;
import com.electricitybuisness.api.repository.LieuRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service pour gérer les opérations liées aux lieux.
 * Fournit des méthodes pour récupérer, créer, mettre à jour et supprimer des lieux.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class LieuService {
    private final LieuRepository lieuRepository;

    /**
     * Récupère tous les vehicules.
     * @return Une liste de tous les lieux
     */
    public List<Lieu> getAllLieux() {
        return lieuRepository.findAll();
    }

    /**
     * Récupère un lieu par son ID.
     * @param id L'identifiant du lieu à récupérer
     * @return Un Optional contenant le lieu si trouvé, sinon vide
     */
    public Optional<Lieu> getLieuById(Long id) {
        return lieuRepository.findById(id);
    }

    /**
     * Crée un nouveau lieu.
     * @param lieu Le lieu à enregistrer
     * @return Le lieu enregistré
     */
    @Transactional
    public Lieu saveLieu(Lieu lieu) {
        return lieuRepository.save(lieu);
    }

    /**
     * Met à jour un lieu existant.
     * @param id L'identifiant du lieu à mettre à jour
     * @param lieu Le lieu avec les nouvelles informations
     * @return Le lieu mis à jour
     */
    public Lieu updateLieu(Long id, Lieu lieu) {
        lieu.setIdLieu(id);
        return lieuRepository.save(lieu);
    }

    /**
     * Supprime un utilisateur.
     * @param id L'identifiant du lieu à supprimer
     */
    public void deleteLieuById(Long id) {
        lieuRepository.deleteById(id);
    }


    /**
     * Vérifie si un vehicule existe.
     * @param id L'identifiant du vehicule à vérifier
     * @return true si le lieu existe, sinon false
     */
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return lieuRepository.existsById(id);
    }

}
