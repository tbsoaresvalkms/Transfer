package com.tbsoaresvalkms.transfer.rate.rules;

import com.tbsoaresvalkms.transfer.rate.RateQuery;
import com.tbsoaresvalkms.transfer.rate.RateRule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class FortyDaysAndHundredThousand extends RateRule {

    private RateQuery rateQuery;

    public FortyDaysAndHundredThousand(@Qualifier("rateNotFound") RateRule rateRule) {
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
        long daysBetween = rateQuery.daysSchedulingForTransfer();

        Integer startDay = 40;
        return daysBetween > startDay;
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
