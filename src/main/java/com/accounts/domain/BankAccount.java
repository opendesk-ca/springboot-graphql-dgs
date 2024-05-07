package com.accounts.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BankAccount {
    private  Integer id;
    private String clientId;
    private Currency currency;
    private Float balance;
    private String status;
}
