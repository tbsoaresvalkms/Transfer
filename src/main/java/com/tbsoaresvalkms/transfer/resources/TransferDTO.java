package com.tbsoaresvalkms.transfer.resources;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferDTO {
    private Long sender;
    private Long receiver;
    private BigDecimal value;
    private LocalDate transfer;
    private LocalDate scheduling;
    private BigDecimal rate;
}
