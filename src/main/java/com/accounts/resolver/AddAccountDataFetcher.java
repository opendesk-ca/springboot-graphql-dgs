package com.accounts.resolver;

import com.accounts.domain.*;
import com.accounts.service.BankService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AddAccountDataFetcher implements DataFetcher<BankAccount> {

    private final BankService bankAccountService;

    public AddAccountDataFetcher(BankService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Override
    public BankAccount get(DataFetchingEnvironment environment) {
        // Fetching the input argument as a Map
        Map<String, Object> accountInputMap = environment.getArgument("account");

        // Manually converting the Map to BankAccountInput
        BankAccountInput accountInput = mapToBankAccountInput(accountInputMap);

        // Validate the status field
        validateStatus(accountInput.getStatus());

        // Creating the Client object
        Client client = new Client(
                accountInput.getClient().getId(),
                accountInput.getClient().getFirstName(),
                accountInput.getClient().getMiddleName(),
                accountInput.getClient().getLastName()
        );

        // Creating the BankAccount object
        BankAccount account = new BankAccount(
                accountInput.getId(),
                client,
                accountInput.getCurrency(),
                accountInput.getBalance(),
                accountInput.getStatus()
        );

        return bankAccountService.addAccount(account);
    }

    private void validateStatus(String status) {
        if (!status.matches("active|inactive|suspended")) {
            throw new IllegalArgumentException("Status must be one of: active, inactive, suspended");
        }
    }

    private BankAccountInput mapToBankAccountInput(Map<String, Object> map) {
        // Extracting client details
        Map<String, Object> clientMap = (Map<String, Object>) map.get("client");

        ClientInput clientInput = new ClientInput(
                (String) clientMap.get("id"),
                (String) clientMap.get("firstName"),
                (String) clientMap.get("middleName"),
                (String) clientMap.get("lastName")
        );

        // Creating BankAccountInput from map values
        return new BankAccountInput(
                (String) map.get("id"),
                clientInput,
                Currency.valueOf((String) map.get("currency")),
                ((Number) map.get("balance")).floatValue(),
                (String) map.get("status")
        );
    }
}