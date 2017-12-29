package com.tbsoaresvalkms.transfer.rate;

import com.tbsoaresvalkms.transfer.rate.rules.RateRule;
import com.tbsoaresvalkms.transfer.resources.TransferDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CalculateTransferRate {

    private RateRule rateRule;

    public CalculateTransferRate(@Qualifier("sameDay") RateRule rateRule) {
        this.rateRule = rateRule;
    }

    public BigDecimal process(TransferDTO transferDTO) {
        return rateRule.calculate(convert(transferDTO));
    }

    private RateQuery convert(TransferDTO transferDTO) {
        return RateQuery.builder()
                .scheduling(transferDTO.getScheduling())
                .transfer(transferDTO.getTransfer())
                .value(transferDTO.getValue())
                .build();
    }

}
