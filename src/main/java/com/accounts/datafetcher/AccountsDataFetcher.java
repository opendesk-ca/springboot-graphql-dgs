package com.accounts.datafetcher;


import com.accounts.dataloader.ClientDataLoader;
import com.accounts.dataloader.ClientDataLoaderWithContext;
import com.accounts.domain.BankAccount;
import com.accounts.domain.Client;
import com.accounts.service.BankService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsQuery;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@DgsComponent
@Slf4j
public class AccountsDataFetcher {

    @Autowired
    BankService accountsService;

    @DgsQuery
    public List<BankAccount> accounts()  {
        System.out.println("Inside accounts");
        return accountsService.accounts();
    }

    @DgsData(parentType = "BankAccount", field = "client")
    public CompletableFuture<Client> client (DgsDataFetchingEnvironment dfe){

        DataLoader<Integer, Client> clientsDataLoader = dfe.getDataLoader(ClientDataLoader.class);

        //Because the client field is on BankAccount, the getSource() method will return the Account instance.
        BankAccount account = dfe.getSource();

        //Load the reviews from the DataLoader. This call is async and will be batched by the DataLoader mechanism.
        return clientsDataLoader.load(account.getId());
    }
}
