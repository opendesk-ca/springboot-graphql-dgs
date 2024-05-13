package com.accounts.domain;

import com.accounts.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Account {
    private  Integer accountId;
    private List<Client> client;
}
