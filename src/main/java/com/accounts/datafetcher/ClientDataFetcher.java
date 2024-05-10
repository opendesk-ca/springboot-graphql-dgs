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
        return clientService.save (client);
    }

    @DgsData(parentType = "Account", field = "client")
    public List<Client> clients (DgsDataFetchingEnvironment dfe){
        Account account = dfe.getSource();

        log.info("Get Clients for Account ", account.getId());

        return clientService.getClients(account.getId());
    }

    @DgsEntityFetcher(name = "Account")
    public Account account (Map<String, Object> values) {

        Object accountId =  values.get("id");

        if (accountId instanceof Number) {
            return new Account(((Number) accountId).intValue(), null);
        } else if (accountId instanceof String) {
            return new Account(Integer.parseInt((String) accountId), null);
        }else {
            throw new IllegalArgumentException("Object is not a Number");
        }
    }
}

