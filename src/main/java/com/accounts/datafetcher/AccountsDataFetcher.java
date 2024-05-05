package com.accounts.datafetcher;


import com.accounts.entity.Account;
import com.accounts.service.AccountService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@DgsComponent
@Slf4j
public class AccountsDataFetcher {

    @Autowired
    AccountService accountsService;

    @DgsQuery
    public List<Account> getAccounts()  {
        log.info("Getting Accounts");
        return accountsService.getAccounts();
    }

    @DgsMutation
    public Boolean addAccount(@InputArgument ("account") Account account) {
        accountsService.save(account);
        return true;
    }
}
