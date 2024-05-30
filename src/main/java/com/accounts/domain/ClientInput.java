package com.accounts.domain;


public record ClientInput(
        Integer clientId,
        Integer accountId,
        String firstName,
        String middleName,
        String lastName,
        String country
) {}
