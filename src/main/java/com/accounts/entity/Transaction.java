package com.accounts.entity;

import com.accounts.domain.DateTimeConverter;
import com.accounts.domain.TransactionStatus;
import com.accounts.domain.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Transaction", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"accountId", "transactionId"})
})
public class Transaction {

    @Id
    @Column
    private	Integer	                transactionId	;

    @Column
    private	Integer	                accountId	;

    @Column
    private LocalDateTime transactionTimestamp;

    @Column
    private	String	                description	;

    @Column
    private TransactionStatus       status	;

    @Column
    private	Float	                amount	;

    @Column
    private TransactionType transactionType	;

    @Column
    private	String	                payee	;

    @Column
    private	Integer	                checkNumber	;

    public OffsetDateTime getTransactionTimestamp () {
        return DateTimeConverter.toOffsetDateTime(this.transactionTimestamp);
    }
}
