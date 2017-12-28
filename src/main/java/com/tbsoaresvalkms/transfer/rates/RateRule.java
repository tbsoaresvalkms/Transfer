package com.tbsoaresvalkms.transfer.rates;

import java.math.BigDecimal;

public abstract class RateRule {
    protected RateRule nextRateRule;

    public abstract BigDecimal calculate(RateQuery rateQuery);
}
