package com.accounts.datafetcher;


import com.accounts.domain.BankAccount;
import com.accounts.service.BankService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@DgsComponent
@Slf4j
public class AccountsDataFetcher {

    @Autowired
    BankService accountsService;

    @DgsQuery
    public List<BankAccount> accounts()  {
        log.info("Getting Accounts");
        return accountsService.accounts();
    }
}
