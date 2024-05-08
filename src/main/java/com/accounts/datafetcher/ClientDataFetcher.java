package com.accounts.datafetcher;


import com.accounts.domain.BankAccount;
import com.accounts.domain.Client;
import com.accounts.service.BankService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsEntityFetcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@DgsComponent
@Slf4j
public class ClientDataFetcher {

    @Autowired
    BankService accountsService;

    @DgsData(parentType = "BankAccount", field = "client")
    public List<Client> clients (DgsDataFetchingEnvironment dfe){
        BankAccount account = dfe.getSource();

        log.info("Get Clients for Account "+ account.getId());

        return accountsService.getClients(account.getId());
    }

    @DgsEntityFetcher(name = "BankAccount")
    public BankAccount account (Map<String, Object> values) {

        Object accountId =  values.get("id");

        if (accountId instanceof Number) {
            return new BankAccount(((Number) accountId).intValue(), null);
        } else if (accountId instanceof String) {
            return new BankAccount(Integer.parseInt((String) accountId), null);
        }else {
            throw new IllegalArgumentException("Object is not a Number");
        }
    }
}

