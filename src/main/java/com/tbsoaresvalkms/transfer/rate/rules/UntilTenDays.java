package com.tbsoaresvalkms.transfer.rate.rules;

import com.tbsoaresvalkms.transfer.rate.RateQuery;
import com.tbsoaresvalkms.transfer.rate.RateRule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UntilTenDays extends RateRule {

    private RateQuery rateQuery;

    public UntilTenDays(@Qualifier("betweenTenAndTwentyDays") RateRule rateRule) {
        this.nextRateRule = rateRule;
    }

    @Override
    public BigDecimal calculate(RateQuery rateQuery) {
        this.rateQuery = rateQuery;
        return shouldCalculateRate() ? calculateRate() : nextRateRule.calculate(rateQuery);
    }

    private Boolean shouldCalculateRate() {
        long daysBetween = rateQuery.daysSchedulingForTransfer();

        Integer endDay = 10;
        return daysBetween <= endDay;
    }

    private BigDecimal calculateRate() {
        long daysBetween = rateQuery.daysSchedulingForTransfer();
        Double valuePerDay = 12.0;
        return BigDecimal.valueOf(daysBetween * valuePerDay);
    }
}
