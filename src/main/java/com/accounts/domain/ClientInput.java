package com.accounts.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientInput {
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
}