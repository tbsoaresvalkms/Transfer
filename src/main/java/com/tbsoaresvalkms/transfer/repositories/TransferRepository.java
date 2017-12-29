package com.tbsoaresvalkms.transfer.repositories;

import com.tbsoaresvalkms.transfer.models.Transfer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TransferRepository {
    private static Map<Long, List<Transfer>> transfers = new HashMap<>();

    public Transfer save(Transfer transfer) {
        Long sender = transfer.getSender();

        transfers.computeIfAbsent(sender, account -> transfers.put(account, new ArrayList<>()));
        transfers.get(sender).add(transfer);

        return transfer;
    }

    public List<Transfer> findAll(Long account) {
        return transfers.getOrDefault(account, new ArrayList<>());
    }

    public void clean() {
        transfers = new HashMap<>();
    }
}
