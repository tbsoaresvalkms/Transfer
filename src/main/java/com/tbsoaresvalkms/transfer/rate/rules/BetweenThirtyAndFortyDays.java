package com.tbsoaresvalkms.transfer.rate.rules;

import com.tbsoaresvalkms.transfer.rate.RateQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BetweenThirtyAndFortyDays extends RateRule {

    public BetweenThirtyAndFortyDays(@Qualifier("fortyDaysAndHundredThousand") RateRule rateRule) {
        this.nextRateRule = rateRule;
    }

    protected Boolean conditional() {
        long daysBetween = rateQuery.daysSchedulingForTransfer();

        Integer differenceStart = 30;
        Integer differenceEnd = 40;
        return daysBetween > differenceStart && daysBetween <= differenceEnd;
    }

    protected BigDecimal calculate() {
        BigDecimal value = rateQuery.getValue();

        Double percentage = 0.04;
        return value.multiply(BigDecimal.valueOf(percentage));
    }
}
