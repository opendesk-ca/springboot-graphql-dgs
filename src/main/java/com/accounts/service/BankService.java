package com.accounts.service;

import com.accounts.domain.Client;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankService {

    private static List<Client> clients = Arrays.asList(
            new Client("C100", 100, "John", "T.", "Doe"),
            new Client("C101", 101, "Emma", "B.", "Smith"),
            new Client("C102", 102, "James", "R.", "Brown"),
            new Client("C103", 103, "Olivia", "S.", "Johnson"),
            new Client("C104", 104, "William", "K.", "Jones")
    );

    public List<Client> getClients(Integer accountIds) {
        return clients.stream().filter(c->c.getAccountId().equals(accountIds)).collect(Collectors.toList());
    }
}
