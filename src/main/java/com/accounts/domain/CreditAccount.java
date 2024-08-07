package com.accounts.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditAccount implements Account {
    private String id;
    private String clientId;
    private float creditLimit;
    private float outstandingBalance;
    private String status;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getClient() {
        return clientId;
    }

    @Override
    public String getCurrency() {
        return "N/A"; // CreditAccount may not have a currency field
    }

    @Override
    public double getBalance() {
        return creditLimit - outstandingBalance; // Balance is calculated as credit limit minus outstanding balance
    }

    @Override
    public String getStatus() {
        return status;
    }
}

