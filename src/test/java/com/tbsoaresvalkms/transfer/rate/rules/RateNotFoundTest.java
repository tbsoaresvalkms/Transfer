package com.tbsoaresvalkms.transfer.rate.rules;

import com.tbsoaresvalkms.transfer.exceptions.RateNotFoundException;
import com.tbsoaresvalkms.transfer.rate.RateQuery;
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
