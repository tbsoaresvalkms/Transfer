package com.tbsoaresvalkms.transfer.rate.rules;

import com.tbsoaresvalkms.transfer.rate.RateQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BetweenTenAndTwentyDays extends RateRule {

    public BetweenTenAndTwentyDays(@Qualifier("betweenTwentyAndThirtyDays") RateRule rateRule) {
        this.nextRateRule = rateRule;
    }

    protected Boolean conditional() {
        long daysBetween = rateQuery.daysSchedulingForTransfer();

        Integer differenceStart = 10;
        Integer differenceEnd = 20;
        return daysBetween > differenceStart && daysBetween <= differenceEnd;
    }

    protected BigDecimal calculate() {
        BigDecimal value = rateQuery.getValue();

        Double percentage = 0.08;
        return value.multiply(BigDecimal.valueOf(percentage));
    }
}
