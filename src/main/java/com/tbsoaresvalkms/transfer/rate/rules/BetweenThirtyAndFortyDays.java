package com.tbsoaresvalkms.transfer.rate.rules;

import com.tbsoaresvalkms.transfer.rate.RateQuery;
import com.tbsoaresvalkms.transfer.rate.RateRule;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class BetweenThirtyAndFortyDays extends RateRule {

    private RateQuery rateQuery;

    public BetweenThirtyAndFortyDays(RateRule rateRule) {
        this.nextRateRule = rateRule;
    }

    @Override
    public BigDecimal calculate(RateQuery rateQuery) {
        this.rateQuery = rateQuery;
        return shouldCalculateRate() ? calculateRate() : nextRateRule.calculate(rateQuery);
    }

    private Boolean shouldCalculateRate() {
        LocalDate scheduling = rateQuery.getScheduling();
        LocalDate transfer = rateQuery.getTransfer();

        long daysBetween = DAYS.between(scheduling, transfer);
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
