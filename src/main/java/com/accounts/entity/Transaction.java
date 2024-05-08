package com.accounts.entity;

import com.accounts.domain.DateTimeConverter;
import com.accounts.domain.TransactionStatus;
import com.accounts.domain.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;

    private Integer account;

    private LocalDateTime transactionTimestamp;

    private String description;

    private TransactionStatus status;

    private Float amount;

    private TransactionType transactionType;

    public OffsetDateTime getTransactionTimestamp() {
        return DateTimeConverter.toOffsetDateTime(this.transactionTimestamp);
    }
}

