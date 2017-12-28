package com.tbsoaresvalkms.transfer.rate.rules;

import com.tbsoaresvalkms.transfer.rate.RateQuery;
import com.tbsoaresvalkms.transfer.rate.RateRule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SameDay extends RateRule {

    private RateQuery rateQuery;

    public SameDay(@Qualifier("untilTenDays") RateRule rateRule) {
        this.nextRateRule = rateRule;
    }

    @Override
    public BigDecimal calculate(RateQuery rateQuery) {
        this.rateQuery = rateQuery;
        return shouldCalculateRate() ? calculateRate() : nextRateRule.calculate(rateQuery);
    }

    private Boolean shouldCalculateRate() {
        long daysBetween = rateQuery.daysSchedulingForTransfer();

        Integer difference = 0;
        return daysBetween == difference;
    }

    private BigDecimal calculateRate() {
        BigDecimal value = rateQuery.getValue();

        Double rate = 0.03;
        return value
                .multiply(BigDecimal.valueOf(rate))
                .add(BigDecimal.valueOf(3));
    }
}
