package com.accounts.domain;

public record BankAccount(Integer id, Currency currency, Float balance, String status) {}
