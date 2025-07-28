package com.electricitybuisness.api.service;

import com.electricitybuisness.api.dto.BorneDTO;
import com.electricitybuisness.api.model.Borne;
import com.electricitybuisness.api.model.Lieu;
import com.electricitybuisness.api.model.Reservation;
import com.electricitybuisness.api.repository.BorneRepository;
import com.electricitybuisness.api.repository.BorneRepositoryCustom;
import com.electricitybuisness.api.repository.ReservationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
import static org.aspectj.runtime.internal.Conversions.doubleValue;

/**
 * Service pour gérer les opérations liées aux bornes.
 * Fournit des méthodes pour récupérer, créer, mettre à jour et supprimer des bornes.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BorneService {

    private final BorneRepository borneRepository;
    private final ReservationRepository reservationRepository;

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
    public List<Borne> findByLieu(Lieu lieu) {
        return borneRepository.findByLieu(lieu);
    }

    /**
     * Récupère une borne par son état.
     * @param etatBorne L'état de la borne à récupérer
     * @return Une liste de bornes associées à cet état
     */
    @Transactional(readOnly = true)
    public List<Borne> findByEtat(Borne etatBorne) {
        return borneRepository.findByEtatBorne(etatBorne);
    }

    /**
     * Récupère une borne par son occupation.
     * @param occupee La borne occupée à récupérer
     * @return Une liste de bornes associées à cette occupation
     */
    @Transactional(readOnly = true)
    public List<Borne> findByOccupee(Borne occupee) {
        return borneRepository.findByOccupee(occupee);
    }

    /**
     * Récupère une borne par son lieu et son état.
     * @param lieu Le lieu de la borne à récupérer
     * @param etatBorne L'état de la borne à récupérer
     * @return Une liste de bornes associées à ce lieu et cet état
     */
    @Transactional(readOnly = true)
    public List<Borne> findByLieuAndEtat(Lieu lieu, Borne etatBorne) {
        return borneRepository.findByLieuAndEtatBorne(lieu, etatBorne);
    }


    //V1

    @Transactional(readOnly = true)
    public List<Borne> getNearbyBornes(BigDecimal longitude, BigDecimal latitude, double rayon) {
        List<Borne> liste_borne = borneRepository.findAll();
        List<Borne> liste_borne_resultat = new ArrayList<>();

        for (Borne borne : liste_borne) {
            double longitude_borne = borne.getLongitude().doubleValue();
            double latitude_borne = borne.getLatitude().doubleValue();

            double x = (longitude.doubleValue() - longitude_borne) * cos((latitude.doubleValue() + latitude_borne) / 2);
            double y = latitude_borne - latitude.doubleValue();
            double z = sqrt((x * x) + (y * y));
            double d = 1.852 * 60 * z;

            if (d <= rayon) {

                liste_borne_resultat.add(borne);

            }
        }

        return liste_borne_resultat;

    }

    //V2

    @Transactional(readOnly = true)
    public List<Borne> getNearbyAndAvaibleBornes(BigDecimal longitude, BigDecimal latitude, double rayon, boolean occupee) {
        List<Borne> liste_borne = borneRepository.findAll();

        if (occupee) {
            liste_borne.removeIf(borne -> !Boolean.TRUE.equals(borne.getOccupee()));
        }
        List<Borne> liste_borne_resultat = new ArrayList<>();

        for (Borne borne : liste_borne) {
            double longitude_borne = borne.getLongitude().doubleValue();
            double latitude_borne = borne.getLatitude().doubleValue();

            double x = (longitude.doubleValue() - longitude_borne) * cos((latitude.doubleValue() + latitude_borne) / 2);
            double y = latitude_borne - latitude.doubleValue();
            double z = sqrt((x * x) + (y * y));
            double d = 1.852 * 60 * z;

            if (d <= rayon) {

                liste_borne_resultat.add(borne);

            }
        }

        return liste_borne_resultat;

    }

    //V3

    @Transactional(readOnly = true)
    public List<Borne> getNearbyAndAvaibleInPeriodBornes(BigDecimal longitude, BigDecimal latitude, double rayon, boolean occupee, LocalDateTime dateDebut, LocalDateTime dateFin) {
        List<Borne> liste_borne = borneRepository.findAll();
        List<Borne> liste_borne_resultat = new ArrayList<>();

        for (Borne borne : liste_borne) {
            double longitude_borne = borne.getLongitude().doubleValue();
            double latitude_borne = borne.getLatitude().doubleValue();

            double x = (longitude.doubleValue() - longitude_borne) * Math.cos(Math.toRadians((latitude.doubleValue() + latitude_borne) / 2));
            double y = latitude_borne - latitude.doubleValue();
            double z = Math.sqrt((x * x) + (y * y));
            double d = 1.852 * 60 * z;  // distance en km

            if (d <= rayon) {
                boolean isOccupiedOnPeriod = false;

                List<Reservation> reservations = reservationRepository.findByBorne(borne);
                if (dateDebut != null && dateFin != null) {
                    for (Reservation reservation : reservations) {
                        LocalDateTime resStart = reservation.getDateDebut();
                        LocalDateTime resEnd = reservation.getDateFin();

                        // Vérifie si chevauchement avec la période demandée
                        if (!(dateFin.isBefore(resStart) || dateDebut.isAfter(resEnd))) {
                            isOccupiedOnPeriod = true;
                            break;
                        }
                    }
                } else {
                    // Pas de période donnée : considère la borne occupée si une réservation est en cours maintenant
                    LocalDateTime now = LocalDateTime.now();
                    for (Reservation reservation : reservations) {
                        if (!now.isBefore(reservation.getDateDebut()) && !now.isAfter(reservation.getDateFin())) {
                            isOccupiedOnPeriod = true;
                            break;
                        }
                    }
                }

                // Ajoute la borne si son occupation correspond à ce qu'on cherche (occupee ou libre)
                if (isOccupiedOnPeriod == occupee) {
                    liste_borne_resultat.add(borne);
                }
            }
        }
        return liste_borne_resultat;
    }




    public List<Borne> findAvailableBornes() {
        return borneRepository.findAvailableBornes();
    }


    public List<Borne> findBornesAvailableInRadius(BigDecimal longitude, BigDecimal latitude, double rayon, boolean occupee) {
        return borneRepository.findBornesInRadius(longitude, latitude, rayon, occupee);
    }

    // fonction non modulaire
    public List<Borne> findBornesAvailableInPeriod(
            boolean occupee,
            LocalDateTime dateDebut,
            LocalDateTime dateFin) {
        return borneRepository.findBornesAvailableInPeriod(
                occupee, dateDebut, dateFin);
    }


    // fonction non modulaire
    public List<Borne> findBornesAvailableInRadiusAndPeriod(
            BigDecimal longitude,
            BigDecimal latitude,
            double rayon,
            boolean occupee,
            LocalDateTime dateDebut,
            LocalDateTime dateFin) {
        return borneRepository.findBornesAvailableInRadiusAndPeriod(
                longitude, latitude, rayon, occupee, dateDebut, dateFin);
    }


    public List<Borne> searchBornes(
            BigDecimal longitude,
            BigDecimal latitude,
            double rayon,
            boolean occupee,
            LocalDateTime dateDebut,
            LocalDateTime dateFin) {
        return borneRepository.searchBornes(
                longitude, latitude, rayon, occupee, dateDebut, dateFin);
    }






    public List<Borne> searchBornesWithCriteria(
            BigDecimal longitude,
            BigDecimal latitude,
            Double rayon,
            Boolean occupee,
            LocalDateTime dateDebut,
            LocalDateTime dateFin) {

        return borneRepository.searchBornesWithCriteria(
                longitude, latitude, rayon, occupee, dateDebut, dateFin
        );
    }
}
