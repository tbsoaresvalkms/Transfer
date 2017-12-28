package com.tbsoaresvalkms.transfer.rate.rules;

import com.tbsoaresvalkms.transfer.rate.RateQuery;
import com.tbsoaresvalkms.transfer.rate.RateRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BetweenThirtyAndFortyDaysTest {

    @Mock
    private RateRule nextRateRule;

    @InjectMocks
    private BetweenThirtyAndFortyDays betweenThirtyAndFortyDays;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenDateNotGreaterThanThirtyDaysShouldCallNextRule() {
        Integer daysAfterToday = 30;
        RateQuery rateQuery = buildRateQueryWithTransferDate(daysAfterToday);

        betweenThirtyAndFortyDays.calculate(rateQuery);

        Mockito.verify(nextRateRule).calculate(rateQuery);
        Mockito.verifyNoMoreInteractions(nextRateRule);
    }

    @Test
    public void whenDateGreaterThanFortyDaysShouldCallNextRule() {
        Integer daysAfterToday = 41;
        RateQuery rateQuery = buildRateQueryWithTransferDate(daysAfterToday);

        betweenThirtyAndFortyDays.calculate(rateQuery);

        Mockito.verify(nextRateRule).calculate(rateQuery);
        Mockito.verifyNoMoreInteractions(nextRateRule);
    }

    @Test
    public void whenDateGreateThanThirtyDaysAndNotGreateThanFortyDaysShouldCalculateRate() {
        Double rate = 0.04;
        Integer daysAfterToday = 40;
        RateQuery rateQuery = buildRateQueryWithTransferDateAndValue(daysAfterToday, 100_000.0);

        BigDecimal expectedValue = rateQuery.getValue().multiply(BigDecimal.valueOf(rate));

        BigDecimal calculatedValue = betweenThirtyAndFortyDays.calculate(rateQuery);

        Assert.assertEquals(expectedValue, calculatedValue);
    }

    @Test
    public void whenCallNextRuleShouldReturnNextRuleValue() {
        Integer daysAfterToday = 41;
        RateQuery rateQuery = buildRateQueryWithTransferDate(daysAfterToday);

        BigDecimal expectedValue = BigDecimal.valueOf(3_000);
        Mockito.when(nextRateRule.calculate(rateQuery)).thenReturn(expectedValue);

        BigDecimal calculatedValue = betweenThirtyAndFortyDays.calculate(rateQuery);

        Mockito.verify(nextRateRule).calculate(rateQuery);
        Mockito.verifyNoMoreInteractions(nextRateRule);
        Assert.assertEquals(expectedValue, calculatedValue);
    }

    private RateQuery buildRateQueryWithTransferDate(Integer days) {
        return RateQuery.builder()
                .scheduling(LocalDate.now())
                .transfer(LocalDate.now().plusDays(days))
                .value(BigDecimal.ZERO)
                .build();
    }

    private RateQuery buildRateQueryWithTransferDateAndValue(Integer days, Double value) {
        return RateQuery.builder()
                .scheduling(LocalDate.now())
                .transfer(LocalDate.now().plusDays(days))
                .value(BigDecimal.valueOf(value))
                .build();
    }
}
