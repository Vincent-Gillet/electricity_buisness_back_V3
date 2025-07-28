package com.electricitybuisness.api.repository;

import com.electricitybuisness.api.model.Borne;
import com.electricitybuisness.api.model.EtatBorne;
import com.electricitybuisness.api.model.Lieu;
import com.electricitybuisness.api.model.Reservation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface de gestion des opérations CRUD pour les bornes.
 * Hérite de JpaRepository pour les opérations de base de données.
 */
@Repository
public interface BorneRepository extends JpaRepository<Borne, Long>, BorneRepositoryCustom {
    List<Borne> findByLieu(Lieu lieu);

    List<Borne> findByEtatBorne(Borne etatBorne);

    List<Borne> findByOccupee(Borne occupee);

    List<Borne> findByLieuAndEtatBorne(Lieu lieu, Borne etatBorne);

    // Récupérer toutes les bornes disponibles (occupees = 0)
    @Query(value = "SELECT DISTINCT b.* FROM bornes b " +
            "WHERE b.occupee = 0 ",
            nativeQuery = true)
    List<Borne> findAvailableBornes();

    // Récupérer toutes les bornes dans le rayon
    @Query(value = "SELECT * FROM bornes b " +
            "WHERE b.occupee = :occupee " +
            "AND (6371 * 2 * ASIN(SQRT(POWER(SIN((RADIANS(b.latitude) - RADIANS(:latitude)) / 2), 2) " +
            "+ COS(RADIANS(:latitude)) * COS(RADIANS(b.latitude)) * POWER(SIN((RADIANS(b.longitude) - RADIANS(:longitude)) / 2), 2)))) <= :rayon",
            nativeQuery = true)
    List<Borne> findBornesInRadius(
            @Param("longitude") BigDecimal longitude,
            @Param("latitude") BigDecimal latitude,
            @Param("rayon") double rayon,
            @Param("occupee") boolean occupee
    );

    // Rechercher fonctionne non modulaire
    @Query(value = "SELECT DISTINCT b.* FROM bornes b " +
            "WHERE b.occupee = :occupee " +
            "AND NOT EXISTS (" +
            "    SELECT 1 FROM reservations r " +
            "    WHERE r.id_borne = b.id_borne " +
            "    AND r.date_fin > :dateDebut " +
            "    AND r.date_debut < :dateFin" +
            ")",
            nativeQuery = true)
    List<Borne> findBornesAvailableInPeriod(
            @Param("occupee") boolean occupee,
            @Param("dateDebut") LocalDateTime dateDebut,
            @Param("dateFin") LocalDateTime dateFin);

    // Rechercher fonctionne non modulaire
    @Query(value = "SELECT DISTINCT b.* FROM bornes b " +
            "WHERE b.occupee = :occupee " +
            "AND (6371 * 2 * ASIN(SQRT(POWER(SIN((RADIANS(b.latitude) - RADIANS(:latitude)) / 2), 2) " +
            "+ COS(RADIANS(:latitude)) * COS(RADIANS(b.latitude)) * POWER(SIN((RADIANS(b.longitude) - RADIANS(:longitude)) / 2), 2)))) <= :rayon " +
            "AND NOT EXISTS (" +
            "    SELECT 1 FROM reservations r " +
            "    WHERE r.id_borne = b.id_borne " +
            "    AND r.date_fin > :dateDebut " +
            "    AND r.date_debut < :dateFin" +
            ")",
            nativeQuery = true)
    List<Borne> findBornesAvailableInRadiusAndPeriod(
            @Param("longitude") BigDecimal longitude,
            @Param("latitude") BigDecimal latitude,
            @Param("rayon") double rayon,
            @Param("occupee") boolean occupee,
            @Param("dateDebut") LocalDateTime dateDebut,
            @Param("dateFin") LocalDateTime dateFin);







    @Query("SELECT b FROM Borne b " +
            "WHERE (:occupee IS NULL OR b.occupee = :occupee) " +
            "AND (:latitude IS NULL OR :longitude IS NULL OR :rayon IS NULL OR " +
            "(6371 * 2 * ASIN(SQRT(POWER(SIN((RADIANS(b.latitude) - RADIANS(:latitude)) / 2), 2) " +
            "+ COS(RADIANS(:latitude)) * COS(RADIANS(b.latitude)) * POWER(SIN((RADIANS(b.longitude) - RADIANS(:longitude)) / 2), 2)))) <= :rayon) " +
            "AND (:dateDebut IS NULL OR :dateFin IS NULL OR NOT EXISTS (" +
            "    SELECT 1 FROM Reservation r " +
            "    WHERE r.borne = b " +
            "    AND r.dateFin > :dateDebut " +
            "    AND r.dateDebut < :dateFin))")
    List<Borne> searchBornes(
            @Param("longitude") BigDecimal longitude,
            @Param("latitude") BigDecimal latitude,
            @Param("rayon") Double rayon,
            @Param("occupee") Boolean occupee,
            @Param("dateDebut") LocalDateTime dateDebut,
            @Param("dateFin") LocalDateTime dateFin
    );
}


/*
public class BorneRepositoryImpl implements BorneRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override

    public List<Borne> searchBornesWithCriteria(
            BigDecimal longitude,
            BigDecimal latitude,
            Double rayon,
            Boolean occupee,
            LocalDateTime dateDebut,
            LocalDateTime dateFin) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Borne> cq = cb.createQuery(Borne.class);
        Root<Borne> root = cq.from(Borne.class);
        List<Predicate> predicates = new ArrayList<>();

        // Add occupee filter
        if (occupee != null) {
            predicates.add(cb.equal(root.get("occupee"), occupee));
        }

        // Add location radius filter
        if (longitude != null && latitude != null && rayon != null) {
            String distanceFormula = "(6371 * 2 * ASIN(SQRT(POWER(SIN((RADIANS(?2) - RADIANS(?1)) / 2), 2) + " +
                    "COS(RADIANS(?1)) * COS(RADIANS(?2)) * POWER(SIN((RADIANS(?4) - RADIANS(?3)) / 2), 2))))";

            Expression<Double> distance = cb.function(
                    "distance_formula",
                    Double.class,
                    root.get("latitude"),
                    cb.literal(latitude),
                    root.get("longitude"),
                    cb.literal(longitude)
            );

            predicates.add(cb.le(distance, rayon));
        }

        // Add date availability filter
        if (dateDebut != null && dateFin != null) {
            Subquery<Long> reservationSubquery = cq.subquery(Long.class);
            Root<Reservation> reservationRoot = reservationSubquery.from(Reservation.class);

            reservationSubquery.select(cb.count(reservationRoot))
                    .where(
                            cb.and(
                                    cb.equal(reservationRoot.get("borne"), root),
                                    cb.greaterThan(reservationRoot.get("dateFin"), dateDebut),
                                    cb.lessThan(reservationRoot.get("dateDebut"), dateFin)
                            )
                    );

            predicates.add(cb.equal(reservationSubquery, 0L));
        }

        // Apply predicates if any exist
        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        return entityManager.createQuery(cq).getResultList();
    }
}*/
