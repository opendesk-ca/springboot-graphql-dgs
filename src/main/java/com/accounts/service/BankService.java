package com.accounts.service;

import com.accounts.domain.BankAccount;
import com.accounts.domain.Client;
import com.accounts.domain.Currency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class BankService {

    // Mutable lists for bank accounts and clients
    private final List<BankAccount> bankAccounts = new ArrayList<>(List.of(
            new BankAccount("A100", new Client("C100", "Elena", "Maria", "Gonzalez"), Currency.USD, 106.00f, "active"),
            new BankAccount("A101", new Client("C200", "James", "Robert", "Smith"), Currency.CAD, 250.00f, "active"),
            new BankAccount("A102", new Client("C300", "Aarav", "Kumar", "Patel"), Currency.CAD, 333.00f, "inactive"),
            new BankAccount("A103", new Client("C400", "Linh", "Thi", "Nguyen"), Currency.EUR, 4000.00f, "active"),
            new BankAccount("A104", new Client("C500", "Olivia", "Grace", "Johnson"), Currency.EUR, 4000.00f, "active")
    ));
    private final List<Client> clients = new ArrayList<>(List.of(
            new Client("C100", "Elena", "Maria", "Gonzalez"),
            new Client("C200", "James", "Robert", "Smith"),
            new Client("C300", "Aarav", "Kumar", "Patel"),
            new Client("C400", "Linh", "Thi", "Nguyen"),
            new Client("C500", "Olivia", "Grace", "Johnson")
    ));

    // Method to get all bank accounts
    public List<BankAccount> getAccounts() {
        return bankAccounts;
    }

    // Method to get client by account ID
    public Client getClientByAccountId(String accountId) {
        return bankAccounts.stream()
                .filter(account -> account.getId().equals(accountId))
                .map(BankAccount::getClient)
                .findFirst()
                .orElse(null);
    }

    // Method to add a new bank account
    public BankAccount addAccount(BankAccount account) {
        bankAccounts.add(account);
        return account;
    }
}
