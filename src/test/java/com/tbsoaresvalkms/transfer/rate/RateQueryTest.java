package com.tbsoaresvalkms.transfer.rate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RateQueryTest {

    @Test
    public void shouldCalculateDifferenceBetweenDates() {
        Long daysAfter = 4L;

        RateQuery rateQuery = RateQuery.builder()
                .scheduling(LocalDate.now())
                .transfer(LocalDate.now().plusDays(daysAfter))
                .build();

        Assert.assertEquals(daysAfter, rateQuery.daysSchedulingForTransfer());
    }

}
