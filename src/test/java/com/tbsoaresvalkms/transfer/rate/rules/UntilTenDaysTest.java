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

public class UntilTenDaysTest {

    @Mock
    private RateRule nextRateRule;

    @InjectMocks
    private UntilTenDays untilTenDays;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenDateGreaterThanTenDaysShouldCallNextRule() {
        Integer daysAfterToday = 11;
        RateQuery rateQuery = buildRateQueryWithTransferDate(daysAfterToday);

        untilTenDays.calculate(rateQuery);

        Mockito.verify(nextRateRule).calculate(rateQuery);
        Mockito.verifyNoMoreInteractions(nextRateRule);
    }

    @Test
    public void whenDateIsUntilTenDaysShouldCalculateRate(){
        Double valuePerDay = 12.0;
        Integer daysAfterToday = 10;
        RateQuery rateQuery = buildRateQueryWithTransferDateAndValue(daysAfterToday, 100_000.0);

        BigDecimal expectedValue = BigDecimal.valueOf(valuePerDay * daysAfterToday);

        BigDecimal calculatedValue = untilTenDays.calculate(rateQuery);

        Assert.assertEquals(expectedValue, calculatedValue);
    }

    @Test
    public void whenCallNextRuleShouldReturnNextRuleValue() {
        Integer daysAfterToday = 11;
        RateQuery rateQuery = buildRateQueryWithTransferDate(daysAfterToday);

        BigDecimal expectedValue = BigDecimal.valueOf(3_000);
        Mockito.when(nextRateRule.calculate(rateQuery)).thenReturn(expectedValue);

        BigDecimal calculatedValue = untilTenDays.calculate(rateQuery);

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
