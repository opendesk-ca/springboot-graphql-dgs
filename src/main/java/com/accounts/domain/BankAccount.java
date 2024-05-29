package com.accounts.domain;

import java.util.List;

public record BankAccount(Integer id, List<Client> client) {}