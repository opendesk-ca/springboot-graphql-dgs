package com.accounts.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionInput {
    private Integer accountId;
    private LocalDateTime transactionTimestamp;
    private String description;
    private TransactionStatus status;
    private Float amount;
    private TransactionType transactionType;
}
