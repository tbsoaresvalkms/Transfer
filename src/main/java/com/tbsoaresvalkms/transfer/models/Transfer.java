package com.tbsoaresvalkms.transfer.models;

import com.tbsoaresvalkms.transfer.resources.TransferDTO;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Transfer {
    @Id
    @GeneratedValue
    private Long id;
    private Long sender;
    private Long receiver;
    private BigDecimal value;
    private BigDecimal rate;
    private LocalDate transfer;
    private LocalDate scheduling;
}
