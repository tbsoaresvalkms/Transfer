package com.tbsoaresvalkms.transfer.integration;

import com.tbsoaresvalkms.transfer.models.Transfer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class TransferControllerTest {

    private final String URL = "http://localhost:8080/transfer";
    private TestRestTemplate testRestTemplate;

    @Before
    public void init() {
        testRestTemplate = new TestRestTemplate();
    }

    @Test
    public void itShouldReturnTransferCreated() {

        Transfer transfer = Transfer.builder()
                .sender(123L)
                .receiver(444L)
                .transfer(LocalDate.now())
                .value(BigDecimal.valueOf(100))
                .build();

        HttpEntity<Transfer> entity = new HttpEntity<>(transfer);
        ResponseEntity<Transfer> response = testRestTemplate.exchange(URL, HttpMethod.POST, entity, Transfer.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getSender()).isEqualTo(transfer.getSender());
        assertThat(response.getBody().getReceiver()).isEqualTo(transfer.getReceiver());
        assertThat(response.getBody().getTransfer()).isEqualTo(transfer.getTransfer());
        assertThat(response.getBody().getValue()).isEqualTo(transfer.getValue());
        assertThat(response.getBody().getRate()).isNotNull();
        assertThat(response.getBody().getScheduling()).isNotNull();
    }

    @Test
    public void itShouldReturnRateNotFoundWhenNoRateApply() {

        Transfer transfer = Transfer.builder()
                .sender(123L)
                .receiver(444L)
                .transfer(LocalDate.now().plusYears(1))
                .value(BigDecimal.valueOf(100))
                .build();

        HttpEntity<Transfer> entity = new HttpEntity<>(transfer);
        ResponseEntity<String> response = testRestTemplate.exchange(URL, HttpMethod.POST, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody().contains("Rate not found")).isTrue();
    }

    @Test
    public void itShouldBeRuleSameDay() {
        Double fixedValue = 3.0;
        Double percentage = 0.03;
        Double value = 100.0;

        Transfer transfer = Transfer.builder()
                .transfer(LocalDate.now())
                .value(BigDecimal.valueOf(value))
                .build();

        BigDecimal expectedRate = BigDecimal.valueOf(value)
                .multiply(BigDecimal.valueOf(percentage))
                .add(BigDecimal.valueOf(fixedValue));

        HttpEntity<Transfer> entity = new HttpEntity<>(transfer);
        ResponseEntity<Transfer> response = testRestTemplate.exchange(URL, HttpMethod.POST, entity, Transfer.class);

        assertThat(response.getBody().getRate()).isEqualTo(expectedRate);
    }

    @Test
    public void itShouldBeRuleUntilTenDays() {
        Double fixedValue = 12.0;
        Double value = 200.0;
        Integer daysAfterToday = 2;

        Transfer transfer = Transfer.builder()
                .transfer(LocalDate.now().plusDays(daysAfterToday))
                .value(BigDecimal.valueOf(value))
                .build();

        BigDecimal expectedRate = BigDecimal.valueOf(daysAfterToday * fixedValue);

        HttpEntity<Transfer> entity = new HttpEntity<>(transfer);
        ResponseEntity<Transfer> response = testRestTemplate.exchange(URL, HttpMethod.POST, entity, Transfer.class);

        assertThat(response.getBody().getRate()).isEqualTo(expectedRate);
    }

    @Test
    public void itShouldBeRuleBetweenTenAndTwentyDays() {
        Double percentage = 0.08;
        Integer daysAfterToday = 20;
        Double value = 200.0;

        Transfer transfer = Transfer.builder()
                .transfer(LocalDate.now().plusDays(daysAfterToday))
                .value(BigDecimal.valueOf(value))
                .build();

        BigDecimal expectedRate = BigDecimal.valueOf(value)
                .multiply(BigDecimal.valueOf(percentage));

        HttpEntity<Transfer> entity = new HttpEntity<>(transfer);
        ResponseEntity<Transfer> response = testRestTemplate.exchange(URL, HttpMethod.POST, entity, Transfer.class);

        assertThat(response.getBody().getRate()).isEqualTo(expectedRate);
    }

    @Test
    public void itShouldBeRuleBetweenTwentyAndThirtyDays() {
        Double percentage = 0.06;
        Integer daysAfterToday = 30;
        Double value = 400.0;

        Transfer transfer = Transfer.builder()
                .transfer(LocalDate.now().plusDays(daysAfterToday))
                .value(BigDecimal.valueOf(value))
                .build();

        BigDecimal expectedRate = BigDecimal.valueOf(value)
                .multiply(BigDecimal.valueOf(percentage));

        HttpEntity<Transfer> entity = new HttpEntity<>(transfer);
        ResponseEntity<Transfer> response = testRestTemplate.exchange(URL, HttpMethod.POST, entity, Transfer.class);

        assertThat(response.getBody().getRate()).isEqualTo(expectedRate);
    }

    @Test
    public void itShouldBeRuleBetweenThirtyAndFortyDays() {
        Double percentage = 0.04;
        Integer daysAfterToday = 40;
        Double value = 500.0;

        Transfer transfer = Transfer.builder()
                .transfer(LocalDate.now().plusDays(daysAfterToday))
                .value(BigDecimal.valueOf(value))
                .build();

        BigDecimal expectedRate = BigDecimal.valueOf(value)
                .multiply(BigDecimal.valueOf(percentage));

        HttpEntity<Transfer> entity = new HttpEntity<>(transfer);
        ResponseEntity<Transfer> response = testRestTemplate.exchange(URL, HttpMethod.POST, entity, Transfer.class);

        assertThat(response.getBody().getRate()).isEqualTo(expectedRate);
    }

    @Test
    public void itShouldBeRuleFortyDaysAndHundredThousand() {
        Double percentage = 0.02;
        Integer daysAfterToday = 41;
        Double value = 100_000.1;

        Transfer transfer = Transfer.builder()
                .transfer(LocalDate.now().plusDays(daysAfterToday))
                .value(BigDecimal.valueOf(value))
                .build();

        BigDecimal expectedRate = BigDecimal.valueOf(value)
                .multiply(BigDecimal.valueOf(percentage));

        HttpEntity<Transfer> entity = new HttpEntity<>(transfer);
        ResponseEntity<Transfer> response = testRestTemplate.exchange(URL, HttpMethod.POST, entity, Transfer.class);

        assertThat(response.getBody().getRate()).isEqualTo(expectedRate);
    }

    @Test
    public void itShouldBeRuleRateNotFound() {
        Integer daysAfterToday = 41;
        Double value = 100_000.0;

        Transfer transfer = Transfer.builder()
                .transfer(LocalDate.now().plusDays(daysAfterToday))
                .value(BigDecimal.valueOf(value))
                .build();

        HttpEntity<Transfer> entity = new HttpEntity<>(transfer);
        ResponseEntity<Transfer> response = testRestTemplate.exchange(URL, HttpMethod.POST, entity, Transfer.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
