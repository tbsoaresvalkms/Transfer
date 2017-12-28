package com.tbsoaresvalkms.transfer.resources;

import com.tbsoaresvalkms.transfer.models.Account;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransferDTO {
    private Account sender;
    private Account receiver;
    private BigDecimal value;
    private LocalDate transfer;
}
