package com.tbsoaresvalkms.transfer.rate.rules;

import com.tbsoaresvalkms.transfer.rate.RateQuery;

import java.math.BigDecimal;

public abstract class RateRule {
    protected RateQuery rateQuery;
    protected RateRule nextRateRule;

    public BigDecimal calculate(RateQuery rateQuery) {
        this.rateQuery = rateQuery;
        return conditional() ? calculate() : nextRateRule.calculate(rateQuery);
    }

    protected abstract Boolean conditional();

    protected abstract BigDecimal calculate();
}
