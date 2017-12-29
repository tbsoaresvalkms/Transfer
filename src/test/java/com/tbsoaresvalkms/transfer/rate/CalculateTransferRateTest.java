package com.tbsoaresvalkms.transfer.rate;

import com.tbsoaresvalkms.transfer.rate.rules.RateRule;
import com.tbsoaresvalkms.transfer.resources.TransferDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CalculateTransferRateTest {

    @Mock
    private RateRule nextRateRule;

    @InjectMocks
    private CalculateTransferRate calculateTransferRate;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCallNextRateRuleConvertingTransferToRateQuery() {
        TransferDTO transfer = TransferDTO.builder()
                .scheduling(LocalDate.now())
                .transfer(LocalDate.now().plusDays(1))
                .value(BigDecimal.valueOf(100_000))
                .build();

        RateQuery rateQuery = RateQuery.builder()
                .scheduling(LocalDate.now())
                .transfer(LocalDate.now().plusDays(1))
                .value(BigDecimal.valueOf(100_000))
                .build();

        calculateTransferRate.process(transfer);

        Mockito.verify(nextRateRule).calculate(rateQuery);
        Mockito.verifyNoMoreInteractions(nextRateRule);
    }

    @Test
    public void shouldReturnNextRateRuleReturn() {
        TransferDTO transfer = TransferDTO.builder().build();
        RateQuery rateQuery = RateQuery.builder().build();
        BigDecimal expectedValue = BigDecimal.valueOf(100_000);

        Mockito.when(nextRateRule.calculate(rateQuery)).thenReturn(BigDecimal.valueOf(100_000));

        BigDecimal returnedValue = calculateTransferRate.process(transfer);

        Assert.assertEquals(expectedValue, returnedValue);
    }
}
