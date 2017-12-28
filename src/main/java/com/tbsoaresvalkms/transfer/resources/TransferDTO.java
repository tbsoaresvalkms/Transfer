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
    @NotNull
    private Long sender;
    @NotNull
    private Long receiver;
    @NotNull
    private BigDecimal value;
    @NotNull
    private LocalDate transfer;
    private LocalDate scheduling;
    private BigDecimal rate;
}
