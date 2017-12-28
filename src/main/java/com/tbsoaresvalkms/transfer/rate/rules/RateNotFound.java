package com.tbsoaresvalkms.transfer.rate.rules;

import com.tbsoaresvalkms.transfer.exceptions.RateNotFoundException;
import com.tbsoaresvalkms.transfer.rate.RateQuery;
import com.tbsoaresvalkms.transfer.rate.RateRule;

import java.math.BigDecimal;

public class RateNotFound extends RateRule {
    @Override
    public BigDecimal calculate(RateQuery rateQuery) {
        throw new RateNotFoundException("There is no applicable fee");
    }
}
