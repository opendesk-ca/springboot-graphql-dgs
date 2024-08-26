package com.accounts.controller;

import com.accounts.domain.BankAccount;
import com.accounts.domain.Client;
import com.accounts.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Controller
@Slf4j
public class AccountsController {
    @Autowired
    BankService bankService;

    @QueryMapping
    public Mono<List<BankAccount>> accounts() {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> {
                    Authentication authentication = context.getAuthentication();
                    log.info("Getting Accounts for user: " + authentication.getName());
                    return bankService.getAccounts();
                });
    }

    @SchemaMapping(typeName = "BankAccount", field = "client")
    public Mono<Client> getClient(BankAccount account) {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(context -> {
                    Authentication authentication = context.getAuthentication();
                    log.info("Getting client for " + account.id() + " for user: " + authentication.getName());
                    return Mono.fromCallable(() -> bankService.getClientByAccountId(account.id()))
                            .subscribeOn(Schedulers.boundedElastic());
                });
    }
}
