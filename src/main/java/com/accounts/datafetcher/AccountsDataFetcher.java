package com.accounts.datafetcher;


import com.accounts.entity.Account;
import com.accounts.service.AccountService;

import com.netflix.graphql.dgs.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;


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

    @DgsMutation
    public Account addAccount(@InputArgument("account") Account account) {
        accountsService.save(account);
        return account;
    }

    @DgsMutation
    public Boolean deleteAccount (@InputArgument("accountId") Integer accountId) {
        accountsService.deleteAccount (accountId);
        return Boolean.TRUE;
    }

    @DgsEntityFetcher(name = "Account")
    public Account account (Map<String, Object> values) {

        Optional<Account> account = accountsService.accounts()
                .stream().filter(a->a.getAccountId()==Integer.parseInt((String)values.get("accountId")))
                .findFirst();

        if (account.isPresent()) return account.get();
        return null;
    }
}
