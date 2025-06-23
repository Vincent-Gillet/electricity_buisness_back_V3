package com.electricitybuisness.api.mapper;


import com.electricitybuisness.api.dto.*;
import com.electricitybuisness.api.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Mapper pour convertir entre entités JPA et DTOs
 * Évite les références circulaires en contrôlant la sérialisation
 */
@Component
public class EntityMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    // === UTILISATEUR ===
    public UtilisateurDTO toDTO(Utilisateur utilisateur) {
        if (utilisateur == null) return null;
        return new UtilisateurDTO(
                utilisateur.getIdUtilisateur(),
                utilisateur.getNomUtilisateur(),
                utilisateur.getPrenom(),
                utilisateur.getPseudo(),
                utilisateur.getEmailUtilisateur(),
                utilisateur.getRole(),
                utilisateur.getDateDeNaissance(),
                utilisateur.getIban(),
                utilisateur.getBanni()
        );
    }

    public Utilisateur toEntity(UtilisateurDTO dto) {
        if (dto == null) return null;
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setIdUtilisateur(dto.getId());
        utilisateur.setNomUtilisateur(dto.getNomUtilisateur());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setPseudo(dto.getPseudo());
        utilisateur.setRole(dto.getRole());
        utilisateur.setEmailUtilisateur(dto.getEmailUtilisateur());
        utilisateur.setDateDeNaissance(dto.getDateDeNaissance());
        utilisateur.setIban(dto.getIban());
        utilisateur.setBanni(dto.getBanni());
        return utilisateur;
    }

    // === UTILISATEUR CREATE ===

    public UtilisateurCreateDTO toCreateDTO(Utilisateur utilisateur) {
        if (utilisateur == null) return null;
        return new UtilisateurCreateDTO(
                utilisateur.getNomUtilisateur(),
                utilisateur.getPrenom(),
                utilisateur.getPseudo(),
                utilisateur.getMotDePasseUtilisateur(),
                utilisateur.getEmailUtilisateur(),
                utilisateur.getDateDeNaissance()
        );
    }

    public Utilisateur toEntity(UtilisateurCreateDTO dto) {
        if (dto == null) return null;
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNomUtilisateur(dto.getUtilisateurNom());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setPseudo(dto.getPseudo());
        utilisateur.setRole(RoleUtilisateur.UTILISATEUR);
        utilisateur.setMotDePasseUtilisateur(passwordEncoder.encode(dto.getUtilisateurMotDePasse()));
        utilisateur.setEmailUtilisateur(dto.getUtilisateurEmail());
        utilisateur.setDateDeNaissance(dto.getDateDeNaissance());
        utilisateur.setIban(null);
        utilisateur.setBanni(false);

        return utilisateur;
    }

    // === BORNE ===
    public BorneDTO toDTO(Borne borne) {
        if (borne == null) return null;
        return new BorneDTO(
                borne.getIdBorne(),
                borne.getNomBorne(),
                borne.getLatitude(),
                borne.getLongitude(),
                borne.getTarif(),
                borne.getPuissance(),
                borne.getInstruction(),
                borne.getSurPied(),
                borne.getEtatBorne(),
                borne.getOccupee(),
                borne.getDateCreation(),
                borne.getDerniereMaintenance()
        );
    }

    public Borne toEntity(BorneDTO dto) {
        if (dto == null) return null;
        Borne borne = new Borne();
        borne.setIdBorne(dto.getId());
        borne.setNomBorne(dto.getNomBorne());
        borne.setLatitude(dto.getLatitude());
        borne.setLongitude(dto.getLongitude());
        borne.setPuissance(dto.getPuissance());
        borne.setInstruction(dto.getInstruction());
        borne.setSurPied(dto.getSurPied());
        borne.setEtatBorne(dto.getEtatBorne());
        borne.setOccupee(dto.getOccupee());
        borne.setDateCreation(dto.getDateCreation());
        borne.setDerniereMaintenance(dto.getDerniereMaintenance());
        return borne;
    }

    // === VEHICULE ===

    public VehiculeDTO toDTO(Vehicule vehicule) {
        if (vehicule == null) return null;
        return new VehiculeDTO(
                vehicule.getIdVehicule(),
                vehicule.getPlaqueImmatriculation(),
                vehicule.getMarque(),
                vehicule.getModele(),
                vehicule.getAnnee(),
                vehicule.getCapaciteBatterie()
        );
    }

    public Vehicule toEntity(VehiculeDTO dto) {
        if (dto == null) return null;
        Vehicule vehicule = new Vehicule();
        vehicule.setIdVehicule(dto.getId());
        vehicule.setPlaqueImmatriculation(dto.getPlaqueImmatriculation());
        vehicule.setMarque(dto.getMarque());
        vehicule.setModele(dto.getModele());
        vehicule.setAnnee(dto.getAnnee());
        vehicule.setCapaciteBatterie(dto.getCapaciteBatterie());
        return vehicule;
    }


    // === ADRESSE ===
    public AdresseDTO toDTO(Adresse adresse) {
        if (adresse == null) return null;
        return new AdresseDTO(
                adresse.getIdAdresse(),
                adresse.getNomAdresse(),
                adresse.getAdresse(),
                adresse.getCodePostal(),
                adresse.getVille(),
                adresse.getPays(),
                adresse.getRegion(),
                adresse.getComplement(),
                adresse.getEtage(),
                toDTO(adresse.getLieu())
        );
    }

    public Adresse toEntity(AdresseDTO dto) {
        if (dto == null) return null;
        Adresse adresse = new Adresse();
        adresse.setIdAdresse(dto.getId());
        adresse.setNomAdresse(dto.getNomAdresse());
        adresse.setAdresse(dto.getAdresse());
        adresse.setCodePostal(dto.getCodePostal());
        adresse.setVille(dto.getVille());
        adresse.setPays(dto.getPays());
        adresse.setRegion(dto.getRegion());
        adresse.setComplement(dto.getComplement());
        adresse.setEtage(dto.getEtage());
        adresse.setLieu(toEntity(dto.getLieu()));
        return adresse;
    }

    // === LIEU ===
    public LieuDTO toDTO(Lieu lieu) {
        if (lieu == null) return null;
        return new LieuDTO(lieu.getIdLieu(), lieu.getInstructions());
    }

    public Lieu toEntity(LieuDTO dto) {
        if (dto == null) return null;
        Lieu lieu = new Lieu();
        lieu.setIdLieu(dto.getId());
        lieu.setInstructions(dto.getInstructions());
        return lieu;
    }


}