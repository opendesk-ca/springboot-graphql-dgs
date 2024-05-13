package com.accounts.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientInput {
    private Integer clientId;
    private Integer accountId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String country;
}