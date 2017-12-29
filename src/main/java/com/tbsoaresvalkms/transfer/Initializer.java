package com.tbsoaresvalkms.transfer;

import com.tbsoaresvalkms.transfer.controllers.TransferController;
import com.tbsoaresvalkms.transfer.models.Transfer;
import com.tbsoaresvalkms.transfer.resources.TransferDTO;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.LongStream;

@Component
public class Initializer implements ApplicationRunner {
    private TransferController transferController;

    public Initializer(TransferController transferController) {
        this.transferController = transferController;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        LongStream.range(0, 100).forEach(this::execute);
    }

    private void execute(long count) {
        transferController.create(generate());
    }

    private TransferDTO generate() {
        return TransferDTO.builder()
                .sender(random(0, 20))
                .receiver(random(0, 20))
                .transfer(LocalDate.now().plusDays(random(0, 50)))
                .value(BigDecimal.valueOf(random(100_001, 200_000)))
                .build();
    }

    private Long random(int min, int max) {
        return (long) (Math.random() * max) + min;
    }
}
