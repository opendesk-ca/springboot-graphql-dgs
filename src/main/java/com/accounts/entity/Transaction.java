package com.accounts.entity;

import com.accounts.domain.DateTimeConverter;
import com.accounts.domain.TransactionStatus;
import com.accounts.domain.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Transaction {

    private	Integer	                transactionId	;
    private List<Account> account;
    private LocalDateTime transactionTimestamp;
    private	String	                description	;
    private TransactionStatus       status	;
    private	Float	                amount	;
    private TransactionType transactionType	;

    public OffsetDateTime getTransactionTimestamp () {
        return DateTimeConverter.toOffsetDateTime(this.transactionTimestamp);
    }
}
