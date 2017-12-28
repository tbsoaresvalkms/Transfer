package com.tbsoaresvalkms.transfer.models;

import com.tbsoaresvalkms.transfer.resources.TransferDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class Transfer {
    private Account sender;
    private Account receiver;
    private BigDecimal value;
    private BigDecimal rate;
    private LocalDate transfer;
    private LocalDate scheduling;

    public static Transfer convertToTransfer(TransferDTO transfer) {
        return null;
    }
}
