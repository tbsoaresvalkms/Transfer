package com.tbsoaresvalkms.transfer.models;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class Transfer {
    private Account sender;
    private Account receiver;
    private BigDecimal value;
    private BigDecimal rate;
    private LocalDate transfer;
    private LocalDate scheduling;
}
