package com.tbsoaresvalkms.transfer.repositories;

import com.tbsoaresvalkms.transfer.models.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findAllBySender(Long sender);
}
