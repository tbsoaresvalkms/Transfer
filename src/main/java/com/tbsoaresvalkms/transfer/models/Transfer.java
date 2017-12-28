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
    private Long sender;
    private Long receiver;
    private BigDecimal value;
    private BigDecimal rate;
    private LocalDate transfer;
    private LocalDate scheduling;

    public static Transfer convertToTransfer(TransferDTO transfer) {
        return Transfer.builder()
                .sender(transfer.getSender())
                .receiver(transfer.getReceiver())
                .value(transfer.getValue())
                .rate(transfer.getRate())
                .transfer(transfer.getTransfer())
                .scheduling(transfer.getScheduling())
                .build();
    }
}
