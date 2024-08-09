package com.accounts.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
     String id;
     Client client;
     Currency currency;
     Float balance;
     String status;
}
