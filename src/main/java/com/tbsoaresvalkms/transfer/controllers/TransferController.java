package com.tbsoaresvalkms.transfer.controllers;

import com.tbsoaresvalkms.transfer.models.Transfer;
import com.tbsoaresvalkms.transfer.resources.TransferDTO;
import com.tbsoaresvalkms.transfer.services.TransferService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/transfer")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public Transfer create(TransferDTO transferDTO) {
        return transferService.create(transferDTO);
    }
}
