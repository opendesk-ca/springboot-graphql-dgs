package com.accounts.dataloader;

import com.accounts.entity.Account;
import com.accounts.service.TransactionService;
import com.netflix.graphql.dgs.DgsDataLoader;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DgsDataLoader(name = "account")
public class TransactionAccountDataLoader  implements MappedBatchLoader<Integer, Account> {

    @Autowired
    TransactionService txnService;

    @Override
    public CompletionStage<Map<Integer, Account>> load(Set<Integer> keys) {

        Map<Integer, Account> accountMap = new HashMap<>();
        accountMap.put(12345, new Account (1005));
        return CompletableFuture.supplyAsync(() ->  accountMap);
        /*return CompletableFuture.supplyAsync(() ->  txnService.getTxnAccounts (keys));*/
    }
}

