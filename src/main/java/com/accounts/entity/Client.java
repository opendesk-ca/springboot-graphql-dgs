package com.accounts.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class Client {

    @EmbeddedId
    private ClientAccountId clientIdAccountId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String country;
}
