package com.tbsoaresvalkms.transfer.commands;

import com.tbsoaresvalkms.transfer.models.Transfer;
import com.tbsoaresvalkms.transfer.rate.CalculateTransferRate;
import com.tbsoaresvalkms.transfer.repositories.TransferRepository;
import com.tbsoaresvalkms.transfer.resources.TransferDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static com.tbsoaresvalkms.transfer.util.Optional.optional;

@Component
public class TransferCreate {
    private final CalculateTransferRate calculateTransferRate;
    private final TransferRepository transferRepository;
    private final ModelMapper modelMapper;

    public TransferCreate(CalculateTransferRate calculateTransferRate, TransferRepository transferRepository, ModelMapper modelMapper) {
        this.calculateTransferRate = calculateTransferRate;
        this.transferRepository = transferRepository;
        this.modelMapper = modelMapper;
    }

    public TransferDTO execute(TransferDTO transferDTO) {
        return optional(transferDTO)
                .map(this::schedulingToday)
                .map(this::transferRate)
                .map(this::toEntity)
                .map(transferRepository::save)
                .map(this::toResource)
                .orElseThrow(RuntimeException::new);
    }

    private TransferDTO schedulingToday(TransferDTO transferDTO) {
        transferDTO.setScheduling(LocalDate.now());
        return transferDTO;
    }

    private TransferDTO transferRate(TransferDTO transferDTO) {
        transferDTO.setRate(calculateTransferRate.process(transferDTO));
        return transferDTO;
    }

    private Transfer toEntity(TransferDTO transferDTO) {
        return modelMapper.map(transferDTO, Transfer.class);
    }

    private TransferDTO toResource(Transfer transfer) {
        return modelMapper.map(transfer, TransferDTO.class);
    }

}
