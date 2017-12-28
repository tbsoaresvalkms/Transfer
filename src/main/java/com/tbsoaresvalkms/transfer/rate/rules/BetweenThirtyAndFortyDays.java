package com.tbsoaresvalkms.transfer.rate.rules;

import com.tbsoaresvalkms.transfer.rate.RateQuery;
import com.tbsoaresvalkms.transfer.rate.RateRule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class BetweenThirtyAndFortyDays extends RateRule {

    private RateQuery rateQuery;

    public BetweenThirtyAndFortyDays(@Qualifier("fortyDaysAndHundredThousand") RateRule rateRule) {
        this.nextRateRule = rateRule;
    }

    @Override
    public BigDecimal calculate(RateQuery rateQuery) {
        this.rateQuery = rateQuery;
        return shouldCalculateRate() ? calculateRate() : nextRateRule.calculate(rateQuery);
    }

    private Boolean shouldCalculateRate() {
        long daysBetween = rateQuery.daysSchedulingForTransfer();

        Integer startDay = 30;
        Integer endDay = 40;
        return daysBetween > startDay && daysBetween <= endDay;
    }

    private BigDecimal calculateRate() {
        BigDecimal value = rateQuery.getValue();

        Double rate = 0.04;
        return value.multiply(BigDecimal.valueOf(rate));
    }
}
