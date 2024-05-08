package com.accounts.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class BankAccount {
    private  Integer id;
    private List<Client> client;
}
