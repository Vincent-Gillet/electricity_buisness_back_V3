package com.electricitybuisness.api.repository;

import com.electricitybuisness.api.model.Borne;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface BorneRepositoryCustom {
    List<Borne> searchBornesWithCriteria(
            BigDecimal longitude,
            BigDecimal latitude,
            Double rayon,
            Boolean occupee,
            LocalDateTime dateDebut,
            LocalDateTime dateFin
    );
}

