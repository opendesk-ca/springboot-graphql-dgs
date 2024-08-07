package com.accounts.service;

import com.accounts.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class BankService {

    private static final List<BankAccount> bankAccounts = List.of(
            BankAccount.builder().id("A100").clientId("C100").currency(Currency.USD).balance(106.00f).status("A").build(),
            BankAccount.builder().id("A101").clientId("C200").currency(Currency.CAD).balance(250.00f).status("A").build(),
            BankAccount.builder().id("A102").clientId("C300").currency(Currency.CAD).balance(333.00f).status("I").build(),
            BankAccount.builder().id("A103").clientId("C400").currency(Currency.EUR).balance(4000.00f).status("A").build(),
            BankAccount.builder().id("A104").clientId("C500").currency(Currency.EUR).balance(4000.00f).status("A").build()
    );

    private static final List<CreditAccount> creditAccounts = List.of(
            CreditAccount.builder().id("CA100").clientId("C100").creditLimit(5000.0f).outstandingBalance(1500.0f).status("Active").build(),
            CreditAccount.builder().id("CA101").clientId("C200").creditLimit(3000.0f).outstandingBalance(1200.0f).status("Active").build(),
            CreditAccount.builder().id("CA102").clientId("C300").creditLimit(7000.0f).outstandingBalance(2500.0f).status("Inactive").build(),
            CreditAccount.builder().id("CA103").clientId("C400").creditLimit(4000.0f).outstandingBalance(1800.0f).status("Active").build(),
            CreditAccount.builder().id("CA104").clientId("C500").creditLimit(6000.0f).outstandingBalance(2000.0f).status("Active").build()
    );

    private static final List<Client> clients = List.of(
            Client.builder().id("C100").firstName("John").middleName("A").lastName("Doe").build(),
            Client.builder().id("C200").firstName("Jane").middleName("B").lastName("Smith").build(),
            Client.builder().id("C300").firstName("Alice").middleName("C").lastName("Johnson").build(),
            Client.builder().id("C400").firstName("Bob").middleName("D").lastName("Brown").build(),
            Client.builder().id("C500").firstName("Charlie").middleName("E").lastName("Davis").build()
    );

    public List<Account> getAccounts() {
        return Stream.concat(bankAccounts.stream(), creditAccounts.stream())
                .collect(Collectors.toList());
    }


    public Client getClientByAccountId(String clientId) {
        // Find the client by clientId from the clients list
        return clients.stream()
                .filter(client -> client.getId().equals(clientId))
                .findFirst()
                .orElse(null);
    }

    public Account getAccountById(String accountId) {
        Optional<BankAccount> bankAccount = bankAccounts.stream()
                .filter(account -> account.getId().equals(accountId))
                .findFirst();
        if (bankAccount.isPresent()) {
            return bankAccount.get();
        }

        Optional<CreditAccount> creditAccount = creditAccounts.stream()
                .filter(account -> account.getId().equals(accountId))
                .findFirst();
        return creditAccount.orElse(null);
    }
}
