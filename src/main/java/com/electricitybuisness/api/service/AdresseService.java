package com.electricitybuisness.api.service;

import com.electricitybuisness.api.model.Adresse;
import com.electricitybuisness.api.repository.AdresseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdresseService {
    private final AdresseRepository adresseRepository;

    /**
     * Récupère tous les adresse.
     * @return Une liste de toutes les adresses
     */
    @Transactional(readOnly = true)
    public List<Adresse> getAllAdresses() {
        return adresseRepository.findAll();
    }

    /**
     * Récupère une adress par son ID.
     * @param id L'identifiant de l'adresse à récupérer
     * @return Une adresse si elle existe, sinon un Optional vide
     */
    public Optional<Adresse> getAdresseById(Long id) {
        return adresseRepository.findById(id);
    }


    /**
     * Crée une nouvelle adresse.
     * @param adresse L'adresse à enregistrer
     * @return L'adresse enregistrée
     */
    public Adresse saveAdresse(Adresse adresse) {
        return adresseRepository.save(adresse);
    }

    /**
     * Met à jour une adresse existant.
     * @param id L'identifiant de l'adresse à mettre à jour
     * @param adresse L'adresse avec les nouvelles informations
     */
    public Adresse updateAdresse(Long id, Adresse adresse) {
        adresse.setIdAdresse(id);
        return adresseRepository.save(adresse);
    }

    /**
     * Supprime une adresse.
     * @param id L'identifiant de l'adresse à supprimer
     */
    public void deleteAdresseById(Long id) {
        adresseRepository.deleteById(id);
    }


    /**
     * Vérifie si une vehicule existe.
     * @param id L'identifiant de l'adresse à vérifier
     * @return true si l'adresse existe, sinon false
     */
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return adresseRepository.existsById(id);
    }


}
