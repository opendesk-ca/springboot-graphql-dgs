package com.accounts.service;

import com.accounts.domain.Account;
import com.accounts.domain.Currency;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AccountService {

    private static List<Account> accounts = Arrays.asList(
            new Account(100,  Currency.USD, 1500.00f, "Active"),
            new Account(102,  Currency.EUR, 2500.00f, "Inactive"),
            new Account(103,  Currency.USD, 5000.00f, "Active"),
            new Account(104,  Currency.EUR, 7500.00f, "Active")
    );

    public List<Account> accounts() {
        return accounts;
    }
}
