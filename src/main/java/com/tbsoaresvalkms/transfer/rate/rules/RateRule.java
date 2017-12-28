package com.tbsoaresvalkms.transfer.rate.rules;

import com.tbsoaresvalkms.transfer.rate.RateQuery;

import java.math.BigDecimal;

public abstract class RateRule {
    protected RateRule nextRateRule;

    public abstract BigDecimal calculate(RateQuery rateQuery);
}
