package com.accounts.service;

import com.accounts.domain.BankAccount;
import com.accounts.domain.Client;
import com.accounts.domain.Currency;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BankService {

    private static List<BankAccount> bankAccounts = Arrays.asList(
            new BankAccount(100, "C100", Currency.USD, 1500.00f, "Active"),
            new BankAccount(100, "C101", Currency.CAD, 3000.00f, "Active"),
            new BankAccount(102, "C102", Currency.EUR, 2500.00f, "Inactive"),
            new BankAccount(103, "C103", Currency.USD, 5000.00f, "Active"),
            new BankAccount(104, "C104", Currency.EUR, 7500.00f, "Active")
    );

    private static List<Client> clients = Arrays.asList(
            new Client("C100", 100, "John", "T.", "Doe"),
            new Client("C101", 101, "Emma", "B.", "Smith"),
            new Client("C102", 102, "James", "R.", "Brown"),
            new Client("C103", 103, "Olivia", "S.", "Johnson"),
            new Client("C104", 104, "William", "K.", "Jones")
    );

    public List<BankAccount> accounts() {
        return bankAccounts;
    }

    public Map<Integer, Client> getClients (List<Integer> accountIds) {
        Map<Integer, Client> accountToClients = new HashMap<>();

        for (Integer accountId : accountIds) {

            // Search for clients with the matching account ID and add them to the list
            for (Client client : clients) {
                if (client.getAccountId().intValue() == accountId.intValue()) {
                    accountToClients.put(accountId, client);
                }
            }
        }

        return accountToClients;
    }
}
