package com.tbsoaresvalkms.transfer.rates;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FortyDaysAndHundredThousand extends RateRule {
    private final Integer fortyDays = 40;
    private final Integer minimumValue = 100_000;
    private final Double rate = 0.02;

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

        LocalDate fortyDaysAfter = scheduling.plusDays(fortyDays);

        return transfer.isAfter(fortyDaysAfter);
    }

    private Boolean valueGreaterThanOneHundredThousand() {
        BigDecimal value = rateQuery.getValue();

        return value.compareTo(BigDecimal.valueOf(minimumValue)) > 0;
    }

    private BigDecimal calculateRate() {
        BigDecimal value = rateQuery.getValue();

        return value.multiply(BigDecimal.valueOf(rate));
    }
}
