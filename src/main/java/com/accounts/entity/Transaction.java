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
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;

    @ElementCollection
    @CollectionTable(name = "transaction_accounts", joinColumns = @JoinColumn(name = "transaction_id"))
    @Column(name = "account_id")
    private List<Integer> accountId;

    private LocalDateTime transactionTimestamp;

    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private Float amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    public OffsetDateTime getTransactionTimestamp() {
        return DateTimeConverter.toOffsetDateTime(this.transactionTimestamp);
    }
}

