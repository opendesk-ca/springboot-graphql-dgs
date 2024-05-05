package com.accounts.dataloader;

import com.accounts.entity.Account;
import com.accounts.service.AccountService;
import com.netflix.graphql.dgs.DgsDataLoader;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DgsDataLoader(name = "account")
public class TransactionAccountDataLoader implements MappedBatchLoader<Integer, Account> {

    @Autowired
    AccountService accountService;

    @Override
    public CompletionStage<Map<Integer, Account>> load(Set<Integer> keys) {
        return CompletableFuture.supplyAsync(() ->  accountService.getAccounts(new ArrayList<>(keys)));
    }
}
