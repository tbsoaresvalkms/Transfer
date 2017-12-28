package com.tbsoaresvalkms.transfer.rate.rules;

import com.tbsoaresvalkms.transfer.rate.RateQuery;
import com.tbsoaresvalkms.transfer.rate.RateRule;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FortyDaysAndHundredThousand extends RateRule {

    private RateQuery rateQuery;

    public FortyDaysAndHundredThousand(RateRule rateRule) {
        this.nextRateRule = rateRule;
    }

    @Override
    public BigDecimal calculate(RateQuery rateQuery) {
        this.rateQuery = rateQuery;

        return shouldCalculateRate() ? calculateRate() : nextRateRule.calculate(rateQuery);
    }

    private Boolean shouldCalculateRate() {
        return greaterThanFortyDays() && valueGreaterThanOneHundredThousand();
    }

    private Boolean greaterThanFortyDays() {
        LocalDate scheduling = rateQuery.getScheduling();
        LocalDate transfer = rateQuery.getTransfer();

        Integer startDay = 40;
        LocalDate fortyDaysAfter = scheduling.plusDays(startDay);

        return transfer.isAfter(fortyDaysAfter);
    }

    private Boolean valueGreaterThanOneHundredThousand() {
        BigDecimal value = rateQuery.getValue();

        Integer minimumValue = 100_000;
        return value.compareTo(BigDecimal.valueOf(minimumValue)) > 0;
    }

    private BigDecimal calculateRate() {
        BigDecimal value = rateQuery.getValue();

        Double rate = 0.02;
        return value.multiply(BigDecimal.valueOf(rate));
    }
}
