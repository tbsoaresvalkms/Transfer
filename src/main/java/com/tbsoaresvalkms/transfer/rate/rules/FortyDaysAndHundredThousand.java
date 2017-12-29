package com.tbsoaresvalkms.transfer.rate.rules;

import com.tbsoaresvalkms.transfer.rate.RateQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FortyDaysAndHundredThousand extends RateRule {

    public FortyDaysAndHundredThousand(@Qualifier("rateNotFound") RateRule rateRule) {
        this.nextRateRule = rateRule;
    }

    protected Boolean conditional() {
        return greaterThanFortyDays() && valueGreaterThanOneHundredThousand();
    }

    protected BigDecimal calculate() {
        BigDecimal value = rateQuery.getValue();

        Double percentage = 0.02;
        return value.multiply(BigDecimal.valueOf(percentage));
    }

    private Boolean greaterThanFortyDays() {
        long daysBetween = rateQuery.daysSchedulingForTransfer();

        Integer differenceStart = 40;
        return daysBetween > differenceStart;
    }

    private Boolean valueGreaterThanOneHundredThousand() {
        BigDecimal value = rateQuery.getValue();

        Integer minimumValue = 100_000;
        return value.compareTo(BigDecimal.valueOf(minimumValue)) > 0;
    }

}
