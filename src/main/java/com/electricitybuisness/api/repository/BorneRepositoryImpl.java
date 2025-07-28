package com.electricitybuisness.api.repository;

import com.electricitybuisness.api.model.Borne;
import com.electricitybuisness.api.model.Reservation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        CriteriaQuery<Borne> query = cb.createQuery(Borne.class);
        Root<Borne> root = query.from(Borne.class);

        List<Predicate> predicates = new ArrayList<>();


        // Add location radius filter
        if (longitude != null && latitude != null && rayon != null) {
            // Haversine formula components
            Expression<Double> latRadians = cb.function("radians", Double.class, root.get("latitude"));
            Expression<Double> lonRadians = cb.function("radians", Double.class, root.get("longitude"));
            Expression<Double> latParamRadians = cb.function("radians", Double.class, cb.literal(latitude));
            Expression<Double> lonParamRadians = cb.function("radians", Double.class, cb.literal(longitude));

            Expression<Double> deltaLat = cb.diff(latRadians, latParamRadians);
            Expression<Double> deltaLon = cb.diff(lonRadians, lonParamRadians);

            // a = sin²(Δlat/2) + cos(lat1) * cos(lat2) * sin²(Δlon/2)
            Expression<Double> a = cb.sum(
                    cb.prod(cb.function("sin", Double.class, cb.prod(deltaLat, cb.literal(0.5))),
                            cb.function("sin", Double.class, cb.prod(deltaLat, cb.literal(0.5)))),
                    cb.prod(
                            cb.prod(
                                    cb.function("cos", Double.class, latRadians),
                                    cb.function("cos", Double.class, latParamRadians)
                            ),
                            cb.prod(
                                    cb.function("sin", Double.class, cb.prod(deltaLon, cb.literal(0.5))),
                                    cb.function("sin", Double.class, cb.prod(deltaLon, cb.literal(0.5)))
                            )
                    )
            );

            // Distance = 2 * R * asin(√a) where R = 6371 km (Earth radius)
            Expression<Double> distance = cb.prod(
                    cb.literal(12742.0), // 2 * 6371
                    cb.function("asin", Double.class, cb.function("sqrt", Double.class, a))
            );

            predicates.add(cb.le(distance, rayon));
        }


        // Add occupee filter
        if (occupee != null) {
            predicates.add(cb.equal(root.get("occupee"), occupee));
        }

        // Add date availability filter
        if (dateDebut != null && dateFin != null) {
            Subquery<Long> reservationSubquery = query.subquery(Long.class);
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

        query.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }
}


