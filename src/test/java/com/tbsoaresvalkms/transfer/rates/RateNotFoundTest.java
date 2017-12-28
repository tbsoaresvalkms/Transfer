package com.tbsoaresvalkms.transfer.rates;

import com.tbsoaresvalkms.transfer.exceptions.RateNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class RateNotFoundTest {

    @InjectMocks
    private RateNotFound rateNotFound;
    private RateQuery rateQuery;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        rateQuery = builderRateQueryEmpty();
    }

    @Test(expected = RateNotFoundException.class)
    public void whenExecuteIsCalledShouldThrowException() {
        rateNotFound.calculate(rateQuery);
    }

    private RateQuery builderRateQueryEmpty() {
        return RateQuery.builder().build();
    }

}
