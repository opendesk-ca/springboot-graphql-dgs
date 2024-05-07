package com.accounts.dataloader;

import com.accounts.domain.Client;
import com.accounts.service.BankService;
import com.netflix.graphql.dgs.DgsDataLoader;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


@DgsDataLoader(name = "client")
@Slf4j
public class ClientDataLoader implements MappedBatchLoader<Integer, Client> {

    @Autowired
    BankService accountsService;

    @Override
    public CompletionStage<Map<Integer, Client>> load(Set<Integer> keys) {
        log.info("Using Client Data Loader");
        return CompletableFuture.supplyAsync(() ->  accountsService.getClients(new ArrayList<>(keys)));
    }
}
