package com.tbsoaresvalkms.transfer.rate.rules;

import com.tbsoaresvalkms.transfer.rate.RateQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BetweenTwentyAndThirtyDays extends RateRule {

    private RateQuery rateQuery;

    public BetweenTwentyAndThirtyDays(@Qualifier("betweenThirtyAndFortyDays") RateRule rateRule) {
        this.nextRateRule = rateRule;
    }

    @Override
    public BigDecimal calculate(RateQuery rateQuery) {
        this.rateQuery = rateQuery;
        return shouldCalculateRate() ? calculateRate() : nextRateRule.calculate(rateQuery);
    }

    private Boolean shouldCalculateRate() {
        long daysBetween = rateQuery.daysSchedulingForTransfer();

        Integer startDay = 20;
        Integer endDay = 30;
        return daysBetween > startDay && daysBetween <= endDay;
    }

    private BigDecimal calculateRate() {
        BigDecimal value = rateQuery.getValue();

        Double rate = 0.06;
        return value.multiply(BigDecimal.valueOf(rate));
    }
}
