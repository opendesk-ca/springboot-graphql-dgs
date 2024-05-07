package com.accounts.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Client {
    private String id;
    private Integer accountId;
    private String firstName;
    private String middleName;
    private String lastName;
}
