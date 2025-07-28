package com.electricitybuisness.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorneSearchDTO {
    private BigDecimal longitude;
    private BigDecimal latitude;
    private double rayon;
    private boolean occupee;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
}
