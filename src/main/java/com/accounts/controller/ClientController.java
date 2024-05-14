package com.accounts.controller;

import com.accounts.domain.Account;
import com.accounts.domain.ClientInput;
import com.accounts.entity.Client;
import com.accounts.service.ClientService;
import com.netflix.graphql.dgs.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@DgsComponent
@Controller
@Slf4j
public class ClientController {

    @Autowired
    ClientService clientService;

    @QueryMapping (name = "clients")
    public List<Client> getClients () {
        log.info("Getting clients.");
        return clientService.getClients();
    }

    @MutationMapping
    public Client addClient (@InputArgument("client") ClientInput client) {
        log.info("Adding clients.");
        return clientService.save (client);
    }

    @MutationMapping
    public Boolean deleteClient (@InputArgument ("clientId") Integer clientId,
                                 @InputArgument ("accountId") Integer accountId) {
        log.info("Deleting clients.");
        return clientService.delete (clientId, accountId);
    }

    @DgsData(parentType = "Account", field = "client")
    public List<Client> clients (DgsDataFetchingEnvironment dfe){
        Account account = dfe.getSource();

        log.info("Get Clients for Account " + account.getAccountId());

        List<Client> clients = clientService.getClients(account.getAccountId());
        return clients;
    }

    @DgsEntityFetcher(name = "Account")
    public Account account (Map<String, Object> values) {

        log.info("Fetching Account entities from Client Side : "  + values );
        Object accountId =  values.get("accountId");

        if (accountId instanceof Number) {
            return new Account(((Number) accountId).intValue(), null);
        } else if (accountId instanceof String) {
            return new Account(Integer.parseInt((String) accountId),null);
        }else {
            throw new IllegalArgumentException("Object is not a Number");
        }
    }
}
