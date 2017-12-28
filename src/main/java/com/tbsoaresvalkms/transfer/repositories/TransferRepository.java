package com.tbsoaresvalkms.transfer.repositories;

import com.tbsoaresvalkms.transfer.models.Account;
import com.tbsoaresvalkms.transfer.models.Transfer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TransferRepository {
    private static final Map<Account, List<Transfer>> transfers = new HashMap<>();

    public void save(Transfer transfer) {
        Account sender = transfer.getSender();

        transfers.computeIfAbsent(sender, account -> transfers.put(account, new ArrayList<>()));
        transfers.get(sender).add(transfer);
    }

    public List<Transfer> findAll(Account account){
        return transfers.get(account);
    }
}
