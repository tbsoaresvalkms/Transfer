package com.tbsoaresvalkms.transfer.rates;

import com.tbsoaresvalkms.transfer.exceptions.RateNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class RateNotFoundTest {

    @InjectMocks
    private RateNotFound rateNotFound;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = RateNotFoundException.class)
    public void whenExecuteIsCalledShouldThrowException() {
        rateNotFound.execute();
    }
}
