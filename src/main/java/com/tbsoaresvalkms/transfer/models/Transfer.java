package com.tbsoaresvalkms.transfer.models;

import com.tbsoaresvalkms.transfer.resources.TransferDTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    private Long sender;
    private Long receiver;
    private BigDecimal value;
    private BigDecimal rate;
    private LocalDate transfer;
    private LocalDate scheduling;
}
