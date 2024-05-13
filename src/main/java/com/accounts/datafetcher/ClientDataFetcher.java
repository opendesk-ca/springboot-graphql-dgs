package com.accounts.datafetcher;


import com.accounts.domain.Account;
import com.accounts.domain.ClientInput;
import com.accounts.entity.Client;
import com.accounts.service.ClientService;
import com.netflix.graphql.dgs.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@DgsComponent
@Slf4j
public class ClientDataFetcher {

    @Autowired
    ClientService clientService;

    @DgsQuery (field = "clients")
    public List<Client> getClients () {
        return clientService.getClients();
    }

    @DgsMutation
    public Client addClient (@InputArgument ("client") ClientInput client) {
        log.info("Adding clients.");
        return clientService.save (client);
    }

    @DgsMutation
    public Boolean deleteClient (@InputArgument ("clientId") Integer clientId,
                                 @InputArgument ("accountId") Integer accountId) {
        log.info("Deleting clients.");
        return clientService.delete (clientId, accountId);
    }

    @DgsData(parentType = "Account", field = "client")
    public List<Client> clients (DgsDataFetchingEnvironment dfe){
        Account account = dfe.getSource();

        log.info("Get Clients for Account ", account.getAccountId());

        List<Client> clients = clientService.getClients(account.getAccountId());
        return clients;
    }

    @DgsEntityFetcher(name = "Account")
    public Account account (Map<String, Object> values) {

        Object accountId =  values.get("accountId");

        if (accountId instanceof Number) {
            return new Account(((Number) accountId).intValue(), null);
        } else if (accountId instanceof String) {
            Account account = new Account(Integer.parseInt((String) accountId),
                    clientService.getClients(Integer.parseInt((String) accountId)));
            return account;
        }else {
            throw new IllegalArgumentException("Object is not a Number");
        }
    }
}

