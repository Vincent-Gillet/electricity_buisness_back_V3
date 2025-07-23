package com.electricitybuisness.api.service;

import com.electricitybuisness.api.dto.UtilisateurUpdatePasswordDTO;
import com.electricitybuisness.api.mapper.EntityMapper;
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

    private final EntityMapper entityMapper;

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
     * Récupère un utilisateur par son pseudo.
     * @param pseudo Le pseudo de l'utilisateur à récupérer
     * @return Un Optional contenant l'utilisateur si trouvé, sinon vide
     */
    @Transactional(readOnly = true)
    public Optional<Utilisateur> findByPseudo(String pseudo) {
        return utilisateurRepository.findByPseudo(pseudo);
    }

    /**
     * Récupère un utilisateur par son adresse email.
     * @param adresseMail L'adresse email de l'utilisateur à récupérer
     * @return Un Optional contenant l'utilisateur si trouvé, sinon vide
     */
    @Transactional(readOnly = true)
    public Optional<Utilisateur> findByUtilisateurEmail(String adresseMail) {
        return utilisateurRepository.findByEmailUtilisateur(adresseMail);
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

    /**
     * Vérifie si un pseudo est déjà utilisé par un utilisateur.
     * @param pseudo Le pseudo à vérifier
     * @return true si le pseudo existe, sinon false
     */
    @Transactional(readOnly = true)
    public boolean existsByPseudo(String pseudo) {
        return utilisateurRepository.existsByPseudo(pseudo);
    }

    /**
     * Vérifie si une adresse email est déjà utilisée par un utilisateur.
     * @param adresseMail L'adresse email à vérifier
     * @return true si l'adresse email existe, sinon false
     */
    @Transactional(readOnly = true)
    public boolean existsByAdresseMail(String adresseMail) {
        return utilisateurRepository.existsByEmailUtilisateur(adresseMail);
    }

/*    public Utilisateur updatePassword(Long id, UtilisateurUpdatePasswordDTO dto) {
        Utilisateur existing = utilisateurRepository.findById(id).orElseThrow();
        entityMapper.toEntity(dto, existing);
        return utilisateurRepository.save(existing);
    }*/

}
