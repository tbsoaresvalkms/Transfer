package com.tbsoaresvalkms.transfer.commands;

import com.tbsoaresvalkms.transfer.models.Transfer;
import com.tbsoaresvalkms.transfer.rate.CalculateTransferRate;
import com.tbsoaresvalkms.transfer.repositories.TransferRepository;
import com.tbsoaresvalkms.transfer.resources.TransferDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransferCreateTest {
    @Mock
    private CalculateTransferRate calculateTransferRate;
    @Mock
    private TransferRepository transferRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private TransferCreate transferCreate;

    private Transfer transfer;
    private TransferDTO transferDTO;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        transferDTO = TransferDTO.builder()
                .transfer(LocalDate.now())
                .scheduling(LocalDate.now())
                .value(BigDecimal.valueOf(100))
                .build();

        transfer = Transfer.builder()
                .transfer(LocalDate.now())
                .scheduling(LocalDate.now())
                .value(BigDecimal.valueOf(100))
                .build();

        Mockito.when(calculateTransferRate.process(transferDTO)).thenReturn(BigDecimal.valueOf(100));
        Mockito.when(transferRepository.save(transfer)).thenReturn(transfer);
        Mockito.when(modelMapper.map(transferDTO, Transfer.class)).thenReturn(transfer);
        Mockito.when(modelMapper.map(transfer, TransferDTO.class)).thenReturn(transferDTO);
    }

    @Test(expected = RuntimeException.class)
    public void whenTransferDTOisNullShouldThrowException() {
        transferCreate.execute(null);
    }

    @Test
    public void itShouldBeSameTransferDTOwithSchedulingAndRate() {
        TransferDTO returnedTransferDTO = transferCreate.execute(transferDTO);

        Assert.assertEquals(transferDTO.getReceiver(), returnedTransferDTO.getReceiver());
        Assert.assertEquals(transferDTO.getSender(), returnedTransferDTO.getSender());
        Assert.assertEquals(transferDTO.getScheduling(), returnedTransferDTO.getScheduling());
        Assert.assertEquals(transferDTO.getTransfer(), returnedTransferDTO.getTransfer());
        Assert.assertEquals(transferDTO.getValue(), returnedTransferDTO.getValue());
        Assert.assertEquals(transferDTO.getRate(), returnedTransferDTO.getRate());
    }

    @Test
    public void itShouldSetRateWithValueCalculateTransferRate() {
        Mockito.when(calculateTransferRate.process(transferDTO)).thenReturn(BigDecimal.valueOf(200));

        TransferDTO returnedTransferDTO = transferCreate.execute(transferDTO);

        Assert.assertEquals(transferDTO.getRate(), returnedTransferDTO.getRate());
    }

    @Test
    public void itShouldSaveTransferInRepository() {
        transferCreate.execute(transferDTO);

        Mockito.verify(transferRepository).save(transfer);
        Mockito.verifyNoMoreInteractions(transferRepository);
    }

    @Test
    public void itShouldConvertTransferDTOtoTransferEntity() {
        transferCreate.execute(transferDTO);

        Mockito.verify(modelMapper).map(transferDTO, Transfer.class);
    }

    @Test
    public void itShouldConvertTransferEntityToTransferDTO() {
        transferCreate.execute(transferDTO);

        Mockito.verify(modelMapper).map(transfer, TransferDTO.class);
    }
}
