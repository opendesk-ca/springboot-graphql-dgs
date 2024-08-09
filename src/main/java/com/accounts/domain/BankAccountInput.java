package com.accounts.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BankAccountInput {
    private String id;
    private ClientInput client;
    private Currency currency;
    private Float balance;
    private String status;
}
