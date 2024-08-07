package com.accounts.domain;

public interface Account {
    String getId();
    String getClient();
    String getCurrency();
    double getBalance();
    String getStatus();
}
