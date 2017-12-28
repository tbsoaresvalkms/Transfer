package com.tbsoaresvalkms.transfer.rates;

import com.tbsoaresvalkms.transfer.exceptions.RateNotFoundException;

import java.math.BigDecimal;

public class RateNotFound extends RateRule {
    @Override
    public BigDecimal calculate(RateQuery rateQuery) {
        throw new RateNotFoundException("There is no applicable fee");
    }
}
