package com.accounts.datafetcher;


import com.accounts.domain.Account;
import com.accounts.service.AccountService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@DgsComponent
@Slf4j
public class AccountsDataFetcher {

    @Autowired
    AccountService accountsService;

    @DgsQuery
    public List<Account> accounts()  {
        log.info("Getting Accounts");
        return accountsService.accounts();
    }
}
