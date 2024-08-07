package com.accounts.controller;

import com.accounts.domain.Account;
import com.accounts.domain.BankAccount;
import com.accounts.domain.Client;
import com.accounts.domain.CreditAccount;
import com.accounts.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Slf4j
public class AccountsController {
    @Autowired
    BankService bankService;

    @QueryMapping
    List<Account> accounts (){
        log.info("Getting Accounts ");
        return bankService.getAccounts();
    }

    @QueryMapping
    public Account accountById(@Argument("accountId") String id) {
        return bankService.getAccountById(id);
    }

    @SchemaMapping(typeName = "BankAccount", field = "client")
    public Client getClient(BankAccount account) {
        log.info("Getting client for {}", account.getId());
        return bankService.getClientByAccountId(account.getClient());
    }

    @SchemaMapping(typeName = "CreditAccount", field = "client")
    public Client getClient(CreditAccount account) {
        log.info("Getting client for %s".formatted(account.getId()));
        return bankService.getClientByAccountId(account.getClient());
    }
}
