package com.electricitybuisness.api.service;

import com.electricitybuisness.api.model.Utilisateur;
import com.electricitybuisness.api.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service pour gérer les opérations liées aux utilisateurs.
 * Fournit des méthodes pour récupérer, créer, mettre à jour et supprimer des utilisateurs.
 */

@Service
@RequiredArgsConstructor
@Transactional
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;

    /**
     * Récupère tous les utilisateurs.
     * @return Une liste de tous les utilisateurs
     */
    @Transactional(readOnly = true)
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    /**
     * Récupère un utilisateur par son ID.
     * @param id L'identifiant de l'utilisateur à récupérer
     * @return Un Optional contenant l'utilisateur si trouvé, sinon vide
     */
    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id);
    }

    /**
     * Crée un nouveau utilisateur.
     * @param utilisateur L'utilisateur à enregistrer
     * @return L'utilisateur enregistré
     */
    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    /**
     * Met à jour un utilisateur existant.
     * @param id L'identifiant de l'utilisateur à mettre à jour
     * @param utilisateur L'utilisateur avec les nouvelles informations
     * @return L'utilisateur mis à jour
     */
    public Utilisateur updateUtilisateur(Long id, Utilisateur utilisateur) {
        utilisateur.setIdUtilisateur(id);
        return utilisateurRepository.save(utilisateur);
    }

    /**
     * Supprime un utilisateur.
     * @param id L'identifiant de l'utilisateur à supprimer
     */
    public void deleteUtilisateurById(Long id) {
        utilisateurRepository.deleteById(id);
    }

    /**
     * Vérifie si un utilisateur existe.
     * @param id L'identifiant de l'utilisateur à vérifier
     * @return true si l'utilisateur existe, sinon false
     */
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return utilisateurRepository.existsById(id);
    }

}
