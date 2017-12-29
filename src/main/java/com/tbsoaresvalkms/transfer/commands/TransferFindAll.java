package com.tbsoaresvalkms.transfer.commands;

import com.tbsoaresvalkms.transfer.models.Transfer;
import com.tbsoaresvalkms.transfer.repositories.TransferRepository;
import com.tbsoaresvalkms.transfer.repositories.TransferRepositoryCollection;
import com.tbsoaresvalkms.transfer.resources.TransferDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.tbsoaresvalkms.transfer.util.Optional.optional;

@Component
public class TransferFindAll {
    private final TransferRepository transferRepository;
    private final ModelMapper modelMapper;

    public TransferFindAll(TransferRepository transferRepository, ModelMapper modelMapper) {
        this.transferRepository = transferRepository;
        this.modelMapper = modelMapper;
    }

    public List<TransferDTO> execute(Long account) {
        return optional(account)
                .map(transferRepository::findAllBySender)
                .map(this::toResource)
                .orElseThrow(RuntimeException::new);
    }

    private List<TransferDTO> toResource(List<Transfer> transfers) {
        return transfers.stream()
                .map(transfer -> modelMapper.map(transfer, TransferDTO.class))
                .collect(Collectors.toList());
    }
}
