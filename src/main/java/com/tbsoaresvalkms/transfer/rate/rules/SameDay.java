package com.tbsoaresvalkms.transfer.rate.rules;

import com.tbsoaresvalkms.transfer.rate.RateQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SameDay extends RateRule {

    public SameDay(@Qualifier("untilTenDays") RateRule rateRule) {
        this.nextRateRule = rateRule;
    }

    protected Boolean conditional() {
        long daysBetween = rateQuery.daysSchedulingForTransfer();

        Integer difference = 0;
        return daysBetween == difference;
    }

    protected BigDecimal calculate() {
        BigDecimal value = rateQuery.getValue();

        Double percentage = 0.03;
        return value
                .multiply(BigDecimal.valueOf(percentage))
                .add(BigDecimal.valueOf(3));
    }
}
