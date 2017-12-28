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

public class FortyDaysAndHundredThousandTest {

    @Mock
    private RateRule nextRateRule;

    @InjectMocks
    private FortyDaysAndHundredThousand fortyDaysAndOneHundredThousand;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenDateNotGreaterThanFortyDaysShouldCallNextRule() {
        Integer daysAfterToday = 40;
        Double value = 150_000.0;
        RateQuery rateQuery = buildRateQueryWithTransferDateAndValue(daysAfterToday, value);

        fortyDaysAndOneHundredThousand.calculate(rateQuery);

        Mockito.verify(nextRateRule).calculate(rateQuery);
        Mockito.verifyNoMoreInteractions(nextRateRule);
    }

    @Test
    public void whenValueNotGreaterThanHundredThousandShouldCallNextRule() {
        Integer daysAfterToday = 45;
        Double value = 100_000.0;

        RateQuery rateQuery = buildRateQueryWithTransferDateAndValue(daysAfterToday, value);

        fortyDaysAndOneHundredThousand.calculate(rateQuery);

        Mockito.verify(nextRateRule).calculate(rateQuery);
        Mockito.verifyNoMoreInteractions(nextRateRule);
    }

    @Test
    public void whenDateGreateThanFortyDaysAndValueGreatThanHundredThousandShouldCalculateRate() {
        Integer daysAfterToday = 45;
        Double value = 100_000.01;
        RateQuery rateQuery = buildRateQueryWithTransferDateAndValue(daysAfterToday, value);

        Double rate = 0.02;
        BigDecimal expectedValue = rateQuery.getValue().multiply(BigDecimal.valueOf(rate));

        BigDecimal calculatedValue = fortyDaysAndOneHundredThousand.calculate(rateQuery);

        Assert.assertEquals(expectedValue, calculatedValue);
    }

    @Test
    public void whenCallNextRuleShouldReturnNextRuleValue() {
        Integer daysAfterToday = 40;
        Double value = 100_000.00;
        RateQuery rateQuery = buildRateQueryWithTransferDateAndValue(daysAfterToday, value);

        BigDecimal expectedValue = BigDecimal.valueOf(3_000);
        Mockito.when(nextRateRule.calculate(rateQuery)).thenReturn(expectedValue);

        BigDecimal calculatedValue = fortyDaysAndOneHundredThousand.calculate(rateQuery);

        Mockito.verify(nextRateRule).calculate(rateQuery);
        Mockito.verifyNoMoreInteractions(nextRateRule);
        Assert.assertEquals(expectedValue, calculatedValue);
    }

    private RateQuery buildRateQueryWithTransferDateAndValue(Integer days, Double value) {
        return RateQuery.builder()
                .scheduling(LocalDate.now())
                .transfer(LocalDate.now().plusDays(days))
                .value(BigDecimal.valueOf(value))
                .build();
    }
}
