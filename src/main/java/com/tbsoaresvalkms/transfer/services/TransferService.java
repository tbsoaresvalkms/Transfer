package com.tbsoaresvalkms.transfer.services;

import com.tbsoaresvalkms.transfer.models.Transfer;
import com.tbsoaresvalkms.transfer.rate.CalculateTransferRate;
import com.tbsoaresvalkms.transfer.repositories.TransferRepository;
import com.tbsoaresvalkms.transfer.resources.TransferDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

@Component
public class TransferService {
    private CalculateTransferRate calculateTransferRate;
    private TransferRepository transferRepository;

    public TransferService(CalculateTransferRate calculateTransferRate, TransferRepository transferRepository) {
        this.calculateTransferRate = calculateTransferRate;
        this.transferRepository = transferRepository;
    }

    public Transfer create(TransferDTO transferDTO) {
        return Stream.of(transferDTO)
                .map(Transfer::convertToTransfer)
                .map(this::setTodayDate)
                .map(this::setRate)
                .map(this::saveInRepository)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private Transfer setRate(Transfer transfer) {
        BigDecimal rate = calculateTransferRate.process(transfer);
        transfer.setRate(rate);
        return transfer;
    }

    private Transfer setTodayDate(Transfer transfer) {
        transfer.setScheduling(LocalDate.now());
        return transfer;
    }

    private Transfer saveInRepository(Transfer transfer) {
        transferRepository.save(transfer);
        return transfer;
    }
}
