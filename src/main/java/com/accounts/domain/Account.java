package com.accounts.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Account {
    private  Integer id;
    private Currency currency;
    private Float balance;
    private String status;
}
