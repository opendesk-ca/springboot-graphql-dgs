package com.accounts.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Embeddable
public class ClientAccountId implements Serializable {
    private Integer clientId;
    private Integer accountId;
}