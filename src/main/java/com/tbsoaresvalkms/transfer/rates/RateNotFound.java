package com.tbsoaresvalkms.transfer.rates;

import com.tbsoaresvalkms.transfer.exceptions.RateNotFoundException;

public class RateNotFound {
    public void execute() {
        throw new RateNotFoundException("There is no applicable fee");
    }
}
