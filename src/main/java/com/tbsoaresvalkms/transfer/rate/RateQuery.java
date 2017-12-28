package com.tbsoaresvalkms.transfer.rate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class RateQuery {
    private BigDecimal value;
    private LocalDate transfer;
    private LocalDate scheduling;
}
