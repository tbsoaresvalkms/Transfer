package com.tbsoaresvalkms.transfer.rate.rules;

import com.tbsoaresvalkms.transfer.exceptions.RateNotFoundException;
import com.tbsoaresvalkms.transfer.rate.RateQuery;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RateNotFound extends RateRule {
    @Override
    public BigDecimal calculate(RateQuery rateQuery) {
        throw new RateNotFoundException("There is no applicable fee");
    }
}
