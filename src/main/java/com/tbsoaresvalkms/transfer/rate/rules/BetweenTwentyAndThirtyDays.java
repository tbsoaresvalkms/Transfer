package com.tbsoaresvalkms.transfer.rate.rules;

import com.tbsoaresvalkms.transfer.rate.RateQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BetweenTwentyAndThirtyDays extends RateRule {

    public BetweenTwentyAndThirtyDays(@Qualifier("betweenThirtyAndFortyDays") RateRule rateRule) {
        this.nextRateRule = rateRule;
    }

    protected Boolean conditional() {
        long daysBetween = rateQuery.daysSchedulingForTransfer();

        Integer differenceStart = 20;
        Integer differenceEnd = 30;
        return daysBetween > differenceStart && daysBetween <= differenceEnd;
    }

    protected BigDecimal calculate() {
        BigDecimal value = rateQuery.getValue();

        Double percentage = 0.06;
        return value.multiply(BigDecimal.valueOf(percentage));
    }
}
