package com.accounts.dataloader;

import com.accounts.domain.Client;
import com.accounts.service.BankService;
import com.netflix.graphql.dgs.DgsDataLoader;
import org.dataloader.BatchLoaderEnvironment;
import org.dataloader.MappedBatchLoaderWithContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


@DgsDataLoader(name = "clientWithContext")
public class ClientDataLoaderWithContext implements MappedBatchLoaderWithContext<Integer, Client> {

    @Autowired
    BankService accountsService;

    @Override
    public CompletionStage<Map<Integer, Client>> load(Set<Integer> accounts, BatchLoaderEnvironment batchLoaderEnvironment) {
        return CompletableFuture.supplyAsync(() -> accountsService.getClients(new ArrayList<>(accounts)));
    }
}
