package com.electricitybuisness.api.mapper;


import com.electricitybuisness.api.dto.*;
import com.electricitybuisness.api.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper pour convertir entre entités JPA et DTOs
 * Évite les références circulaires en contrôlant la sérialisation
 */
@Component
public class EntityMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

    String p1 = bc.encode("password");

    // === UTILISATEUR ===
    public UtilisateurDTO toDTO(Utilisateur utilisateur) {
        if (utilisateur == null) return null;
        List<BorneDTO> borneDTOs = null;
        if (utilisateur.getBornes() != null) {
            borneDTOs = utilisateur.getBornes().stream()
                    .map(this::toEntity)
                    .toList();
        }
        List<VehiculeDTO> vehiculeDTOs = null;
        if (utilisateur.getVehicule() != null) {
            vehiculeDTOs = utilisateur.getVehicule().stream()
                    .map(this::toEntity)
                    .toList();
        }
        return new UtilisateurDTO(
                utilisateur.getNomUtilisateur(),
                utilisateur.getPrenom(),
                utilisateur.getPseudo(),
                utilisateur.getEmailUtilisateur(),
                utilisateur.getRole(),
                utilisateur.getDateDeNaissance(),
                utilisateur.getIban(),
                borneDTOs,
                vehiculeDTOs
        );
    }

    public Utilisateur toEntity(UtilisateurDTO dto) {
        if (dto == null) return null;
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNomUtilisateur(dto.getNomUtilisateur());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setPseudo(dto.getPseudo());
        utilisateur.setRole(dto.getRole());
        utilisateur.setEmailUtilisateur(dto.getEmailUtilisateur());
        utilisateur.setDateDeNaissance(dto.getDateDeNaissance());
        utilisateur.setIban(dto.getIban());
        if (dto.getBornes() != null) {
            List<Borne> bornes = dto.getBornes().stream()
                    .map(this::toEntity)
                    .toList();
            utilisateur.setBornes(bornes);
        }
        if (dto.getVehicule() != null) {
            List<Vehicule> vehicules = dto.getVehicule().stream()
                    .map(this::toEntity)
                    .toList();
            utilisateur.setVehicule(vehicules);
        return utilisateur;
    }

    // === Créée UTILISATEUR ===

    public Utilisateur toEntity(UtilisateurCreateDTO dto) {
        if (dto == null) return null;
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNomUtilisateur(dto.getNomUtilisateur());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setPseudo(dto.getPseudo());
        utilisateur.setRole(RoleUtilisateur.UTILISATEUR);
        utilisateur.setMotDePasseUtilisateur(passwordEncoder.encode(dto.getMotDePasseUtilisateur()));
        utilisateur.setEmailUtilisateur(dto.getEmailUtilisateur());
        utilisateur.setDateDeNaissance(dto.getDateDeNaissance());
        utilisateur.setIban(null);
        utilisateur.setBanni(false);
        return utilisateur;
    }

    // === Mettre a jour UTILISATEUR ===

    public Utilisateur toEntity(UtilisateurUpdateDTO dto, Utilisateur existing) {
        existing.setNomUtilisateur(dto.getNomUtilisateur());
        existing.setPrenom(dto.getPrenom());
        existing.setPseudo(dto.getPseudo());
        existing.setEmailUtilisateur(dto.getEmailUtilisateur());
        existing.setDateDeNaissance(dto.getDateDeNaissance());
        existing.setIban(dto.getIban());
        return existing;
    }

    public UtilisateurUpdateDTO toUpdateDTO(Utilisateur utilisateur) {
        if (utilisateur == null) return null;
        return new UtilisateurUpdateDTO(
                utilisateur.getNomUtilisateur(),
                utilisateur.getPrenom(),
                utilisateur.getPseudo(),
                utilisateur.getEmailUtilisateur(),
                utilisateur.getDateDeNaissance(),
                utilisateur.getIban()
        );
    }

    // === Mettre a jour le mot de passe UTILISATEUR ===

    public Utilisateur toEntityPassword(UtilisateurUpdatePasswordDTO dto, Utilisateur existing) {
        existing.setMotDePasseUtilisateur(passwordEncoder.encode(dto.getMotDePasseUtilisateur()));
        return existing;
    }

    // === Mettre a jour le statut BANNI UTILISATEUR ===

    public Utilisateur toEntityBanni(UtilisateurUpdateBanniDTO dto, Utilisateur existing) {
        existing.setBanni(dto.getBanni());
        return existing;
    }

    // === Mettre a jour le role UTILISATEUR ===

    public Utilisateur toEntityRole(UtilisateurUpdateRoleDTO dto, Utilisateur existing) {
        existing.setRole(dto.getRole());
        return existing;
    }

    // === BORNE ===
    public BorneDTO toDTO(Borne borne) {
        if (borne == null) return null;
        return new BorneDTO(
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
        List<BorneDTO> borneDTOs = null;
        if (lieu.getBornes() != null) {
            borneDTOs = lieu.getBornes().stream()
                    .map(this::toDTO)
                    .toList();
        }
        return new LieuDTO(
                lieu.getInstructions(),
                borneDTOs
        );
    }

    public Lieu toEntity(LieuDTO dto) {
        if (dto == null) return null;
        Lieu lieu = new Lieu();
        lieu.setInstructions(dto.getInstructions());
        if (dto.getBornes() != null) {
            List<Borne> bornes = dto.getBornes().stream()
                    .map(this::toEntity)
                    .toList();
            lieu.setBornes(bornes);
        }
        return lieu;
    }

    // === SERVICE SUPPLEMENTAIRE ===

    public OptionDTO toDTO(Option serviceSup) {
        if (serviceSup == null) return null;
        return new OptionDTO(
                serviceSup.getNomOption(),
                serviceSup.getTarifOption(),
                serviceSup.getDescriptionOption()
        );
    }

    public Option toEntity(OptionDTO dto) {
        if (dto == null) return null;
        Option serviceSup = new Option();
        serviceSup.setNomOption(dto.getNomOption());
        serviceSup.setTarifOption(dto.getTarifOption());
        serviceSup.setDescriptionOption(dto.getDescriptionOption());
        return serviceSup;
    }

    // === MEDIA ===
    public MediaDTO toDTO(Media media) {
        if (media == null) return null;
        return new MediaDTO(
                media.getNomMedia(),
                media.getType(),
                media.getUrl(),
                media.getDescriptionMedia(),
                media.getTaille(),
                media.getDateCreation()
        );
    }

    public Media toEntity(MediaDTO dto) {
        if (dto == null) return null;
        Media media = new Media();
        media.setNomMedia(dto.getNomMedia());
        media.setType(dto.getType());
        media.setUrl(dto.getUrl());
        media.setDescriptionMedia(dto.getDescriptionMedia());
        media.setTaille(dto.getTaille());
        media.setDateCreation(dto.getDateCreation());

        return media;
    }

    // === REPARATEUR ===

    public ReparateurDTO toDTO(Reparateur reparateur) {
        if (reparateur == null) return null;
        return new ReparateurDTO(
                reparateur.getNomReparateur(),
                reparateur.getEmailReparateur()
        );
    }

    public Reparateur toEntity(ReparateurDTO dto) {
        if (dto == null) return null;
        Reparateur reparateur = new Reparateur();
        reparateur.setNomReparateur(dto.getNomReparateur());
        reparateur.setEmailReparateur(dto.getEmailReparateur());
        return reparateur;
    }

    // === RESERVATION ===
    public ReservationDTO toDTO(Reservation reservation) {
        if (reservation == null) return null;
        return new ReservationDTO(
                reservation.getDateDebut(),
                reservation.getDateFin(),
                reservation.getStatut(),
                reservation.getMontantPaye(),
                reservation.getDatePaiement(),
                reservation.getUtilisateur() != null ? reservation.getUtilisateur().getIdUtilisateur() : null,
                reservation.getBorne() != null ? reservation.getBorne().getIdBorne() : null,
                reservation.getVehicule() != null ? reservation.getVehicule().getIdVehicule() : null,
                reservation.getOption() != null ? reservation.getOption().getIdOption() : null
        );
    }

    public Reservation toEntity(ReservationDTO dto) {
        if (dto == null) return null;
        Reservation reservation = new Reservation();
        reservation.setDateDebut(dto.getDateDebutReservation());
        reservation.setDateFin(dto.getDateFinReservation());
        reservation.setStatut(dto.getStatut());
        reservation.setMontantPaye(dto.getMontantPaye());
        return reservation;
    }



}