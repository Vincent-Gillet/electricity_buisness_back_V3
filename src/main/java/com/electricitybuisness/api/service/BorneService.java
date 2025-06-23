package com.electricitybuisness.api.service;

import com.electricitybuisness.api.model.Borne;
import com.electricitybuisness.api.model.Lieu;
import com.electricitybuisness.api.repository.BorneRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service pour gérer les opérations liées aux bornes.
 * Fournit des méthodes pour récupérer, créer, mettre à jour et supprimer des bornes.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BorneService {
    private final BorneRepository borneRepository;

    /**
     * Récupère tous les bornes.
     * @return Une liste de toutes les bornes
     */
    @Transactional(readOnly = true)
    public List<Borne> getAllBornes() {
        return borneRepository.findAll();
    }

    /**
     * Récupère un vehicule par son ID.
     * @param id L'identifiant du vehicule à récupérer
     * @return Un Optional contenant le vehicule si trouvé, sinon vide
     */
    public Optional<Borne> getBorneById(Long id) {
        return borneRepository.findById(id);
    }

    /**
     * Crée un nouveau vehicule.
     * @param borne La borne à enregistrer
     * @return La borne enregistrée
     */
    public Borne saveBorne(Borne borne) {
        return borneRepository.save(borne);
    }

    /**
     * Met à jour un vehicule existant.
     * @param id L'identifiant du vehicule à mettre à jour
     * @param borne La borne avec les nouvelles informations
     * @return La borne mise à jour
     */
    public Borne updateBorne(Long id, Borne borne) {
        borne.setIdBorne(id);
        return borneRepository.save(borne);
    }

    /**
     * Supprime un utilisateur.
     * @param id L'identifiant de la borne à supprimer
     */
    public void deleteBorneById(Long id) {
        borneRepository.deleteById(id);
    }

    /**
     * Vérifie si une borne existe.
     * @param id L'identifiant de la borne à vérifier
     * @return true si la borne existe, sinon false
     */
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return borneRepository.existsById(id);
    }

    /**
     * Récupère une borne par son lieu.
     * @param lieu Le lieu de la borne à récupérer
     * @return Une liste de bornes associées à ce lieu
     */
    @Transactional(readOnly = true)
    List<Borne> findByLieu(Lieu lieu) {
        return borneRepository.findByLieu(lieu);
    }

    /**
     * Récupère une borne par son état.
     * @param etatBorne L'état de la borne à récupérer
     * @return Une liste de bornes associées à cet état
     */
    @Transactional(readOnly = true)
    List<Borne> findByEtat(Borne etatBorne) {
        return borneRepository.findByEtat(etatBorne);
    }

    /**
     * Récupère une borne par son occupation.
     * @param occupee La borne occupée à récupérer
     * @return Une liste de bornes associées à cette occupation
     */
    @Transactional(readOnly = true)
    List<Borne> findByOccupee(Borne occupee) {
        return borneRepository.findByOccupee(occupee);
    }

    /**
     * Récupère une borne par son lieu et son état.
     * @param lieu Le lieu de la borne à récupérer
     * @param etatBorne L'état de la borne à récupérer
     * @return Une liste de bornes associées à ce lieu et cet état
     */
    @Transactional(readOnly = true)
    List<Borne> findByLieuAndEtat(Lieu lieu, Borne etatBorne) {
        return borneRepository.findByLieuAndEtat(lieu, etatBorne);
    }



}
