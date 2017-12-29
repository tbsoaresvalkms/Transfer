package com.tbsoaresvalkms.transfer.commands;

import com.tbsoaresvalkms.transfer.models.Transfer;
import com.tbsoaresvalkms.transfer.repositories.TransferRepository;
import com.tbsoaresvalkms.transfer.resources.TransferDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

public class TransferFindAllTest {
    @Mock
    private TransferRepository transferRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private TransferFindAll transferFindAll;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = RuntimeException.class)
    public void whenAccountIsNullShouldThrowException() {
        transferFindAll.execute(null);
    }

    @Test
    public void itShouldCallRepositoryFindAll() {
        transferFindAll.execute(123L);
        Mockito.verify(transferRepository).findAllBySender(123L);
        Mockito.verifyNoMoreInteractions(transferRepository);
    }

    @Test
    public void itShouldConvertEachTransferEntityToTransferDTO() {
        Transfer transferOne = Transfer.builder().sender(1L).build();
        Transfer transferTwo = Transfer.builder().sender(2L).build();

        List<Transfer> transfers = Arrays.asList(transferOne, transferTwo);
        Mockito.when(transferRepository.findAllBySender(123L)).thenReturn(transfers);

        transferFindAll.execute(123L);

        Mockito.verify(modelMapper).map(transferOne, TransferDTO.class);
        Mockito.verify(modelMapper).map(transferTwo, TransferDTO.class);
        Mockito.verifyNoMoreInteractions(modelMapper);
    }

    @Test
    public void itShouldBeSameDatasOfRepository() {
        Transfer transferOne = Transfer.builder().sender(1L).build();
        Transfer transferTwo = Transfer.builder().sender(2L).build();

        TransferDTO transferDTOone = TransferDTO.builder().sender(1L).build();
        TransferDTO transferDTOtwo = TransferDTO.builder().sender(2L).build();

        List<Transfer> transfers = Arrays.asList(transferOne, transferTwo);
        Mockito.when(transferRepository.findAllBySender(123L)).thenReturn(transfers);
        Mockito.when(modelMapper.map(transferOne, TransferDTO.class)).thenReturn(transferDTOone);
        Mockito.when(modelMapper.map(transferTwo, TransferDTO.class)).thenReturn(transferDTOtwo);


        List<TransferDTO> transfersDTO = transferFindAll.execute(123L);

        Assert.assertEquals(transfers.size(), transfersDTO.size());
        Assert.assertEquals(transferDTOone.getSender(), transfersDTO.get(0).getSender());
        Assert.assertEquals(transferDTOtwo.getSender(), transfersDTO.get(1).getSender());
    }
}
