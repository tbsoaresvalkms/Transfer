package com.tbsoaresvalkms.transfer.rate;

import com.tbsoaresvalkms.transfer.models.Transfer;
import com.tbsoaresvalkms.transfer.rate.rules.RateRule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CalculateTransferRate {

    private RateRule rateRule;

    public CalculateTransferRate(@Qualifier("sameDay") RateRule rateRule) {
        this.rateRule = rateRule;
    }

    public BigDecimal process(Transfer transfer) {
        return rateRule.calculate(convert(transfer));
    }

    private RateQuery convert(Transfer transfer) {
        return RateQuery.builder()
                .scheduling(transfer.getScheduling())
                .transfer(transfer.getTransfer())
                .value(transfer.getValue())
                .build();
    }

}
