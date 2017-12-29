package com.tbsoaresvalkms.transfer.repositories;

import com.tbsoaresvalkms.transfer.models.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class TransferRepositoryTest {
    private TransferRepository transferRepository;

    @Before
    public void init() {
        transferRepository = new TransferRepository();
        transferRepository.clean();
    }

    @Test
    public void shouldReturnedSameTransferWasSaved() {
        Transfer transfer = Transfer.builder()
                .sender(123L)
                .value(BigDecimal.valueOf(100))
                .build();

        Transfer returnedTransfer = transferRepository.save(transfer);

        Assert.assertEquals(transfer, returnedTransfer);
    }

    @Test
    public void whenSameAccountTransferShouldSaveSameList() {
        Transfer transferOne = Transfer.builder()
                .sender(123L)
                .value(BigDecimal.valueOf(100))
                .build();

        Transfer transferTwo = Transfer.builder()
                .sender(123L)
                .value(BigDecimal.valueOf(200))
                .build();

        transferRepository.save(transferOne);
        transferRepository.save(transferTwo);

        List<Transfer> allAccounts = transferRepository.findAll(123L);

        Assert.assertEquals(2, allAccounts.size());
        Assert.assertEquals(transferOne.getValue(), allAccounts.get(0).getValue());
        Assert.assertEquals(transferTwo.getValue(), allAccounts.get(1).getValue());
    }

    @Test
    public void whenDontHaveTransferShouldBeEmptyList() {
        List<Transfer> allAccounts = transferRepository.findAll(123L);

        Assert.assertEquals(0, allAccounts.size());
    }
}
