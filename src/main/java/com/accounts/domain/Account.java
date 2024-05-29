package com.accounts.domain;


public record Account(
        Integer id,
        String clientId,
        Currency currency,
        Float balance,
        String status
) {}
