package com.accounts.controller;

import com.accounts.domain.BankAccount;
import com.accounts.domain.Client;
import com.accounts.service.BankService;
import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.execution.ErrorType;
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
    public List<BankAccount> accounts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Getting Accounts for user: " + authentication.getName());
        return bankService.getAccounts();
    }

    @SchemaMapping(typeName = "BankAccount", field = "client")
    public Client getClient(BankAccount account) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Getting client for " + account.id() + " for user: " + authentication.getName());
        return bankService.getClientByAccountId(account.id());
    }

    @GraphQlExceptionHandler
    public GraphQLError handle(@NonNull Exception ex, @NonNull DataFetchingEnvironment environment) {
        return GraphQLError
                .newError()
                .errorType(ErrorType.BAD_REQUEST)
                .message(ex.getMessage())
                .path(environment.getExecutionStepInfo().getPath())
                .location(environment.getField().getSourceLocation())
                .build();
    }
}
