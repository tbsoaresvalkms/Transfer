package com.tbsoaresvalkms.transfer.rates;

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
        RateQuery rateQuery = builderDateNotGreaterThanFortyDays();

        fortyDaysAndOneHundredThousand.calculate(rateQuery);

        Mockito.verify(nextRateRule).calculate(rateQuery);
        Mockito.verifyNoMoreInteractions(nextRateRule);
    }

    @Test
    public void whenValueNotGreaterThanHundredThousandShouldCallNextRule() {
        RateQuery rateQuery = builderValueNotGreaterThanHundredThousand();

        fortyDaysAndOneHundredThousand.calculate(rateQuery);

        Mockito.verify(nextRateRule).calculate(rateQuery);
        Mockito.verifyNoMoreInteractions(nextRateRule);
    }

    @Test
    public void whenDateGreateThanFortyDaysAndValueGreatThanHundredThousandShouldCalculateRate() {
        Double rate = 0.02;

        RateQuery rateQuery = builderDateGreaterThanFortyDaysAndValueGreaterThanHundredThousand();
        BigDecimal expectedValue = rateQuery.getValue().multiply(BigDecimal.valueOf(rate));

        BigDecimal calculatedValue = fortyDaysAndOneHundredThousand.calculate(rateQuery);

        Assert.assertEquals(expectedValue, calculatedValue);
    }

    @Test
    public void whenCallNextRuleShouldReturnNextRuleValue() {
        RateQuery rateQuery = builderValueNotGreaterThanHundredThousand();
        BigDecimal expectedValue = BigDecimal.valueOf(3_000);
        Mockito.when(nextRateRule.calculate(rateQuery)).thenReturn(expectedValue);

        BigDecimal calculatedValue = fortyDaysAndOneHundredThousand.calculate(rateQuery);

        Mockito.verify(nextRateRule).calculate(rateQuery);
        Mockito.verifyNoMoreInteractions(nextRateRule);
        Assert.assertEquals(expectedValue, calculatedValue);
    }

    private RateQuery builderDateNotGreaterThanFortyDays() {
        return RateQuery.builder()
                .scheduling(LocalDate.of(2018, 01, 01))
                .transfer(LocalDate.of(2018, 02, 10))
                .value(BigDecimal.valueOf(150_000))
                .build();
    }

    private RateQuery builderValueNotGreaterThanHundredThousand() {
        return RateQuery.builder()
                .scheduling(LocalDate.of(2018, 01, 01))
                .transfer(LocalDate.of(2018, 05, 05))
                .value(BigDecimal.valueOf(100_000))
                .build();
    }

    private RateQuery builderDateGreaterThanFortyDaysAndValueGreaterThanHundredThousand() {
        return RateQuery.builder()
                .scheduling(LocalDate.of(2018, 01, 01))
                .transfer(LocalDate.of(2018, 02, 11))
                .value(BigDecimal.valueOf(100_001))
                .build();
    }
}
