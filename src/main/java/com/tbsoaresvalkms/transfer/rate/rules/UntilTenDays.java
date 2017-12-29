package com.tbsoaresvalkms.transfer.rate.rules;

import com.tbsoaresvalkms.transfer.rate.RateQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UntilTenDays extends RateRule {

    public UntilTenDays(@Qualifier("betweenTenAndTwentyDays") RateRule rateRule) {
        this.nextRateRule = rateRule;
    }

    protected Boolean conditional() {
        long daysBetween = rateQuery.daysSchedulingForTransfer();

        Integer differenceEnd = 10;
        return daysBetween <= differenceEnd;
    }

    protected BigDecimal calculate() {
        long daysBetween = rateQuery.daysSchedulingForTransfer();
        Double valuePerDay = 12.0;
        return BigDecimal.valueOf(daysBetween * valuePerDay);
    }
}
