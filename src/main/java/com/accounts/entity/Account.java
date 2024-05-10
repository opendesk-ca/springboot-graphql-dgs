package com.accounts.entity;

import com.accounts.domain.AccountStatus;
import com.accounts.domain.AccountType;
import com.accounts.domain.DateTimeConverter;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Currency;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Account", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"accountId"})
})
public class Account {
    @Id
    @Column
    private  Integer accountId;
    @Column
    private AccountType accountType;
    @Column
    private String accountNumber;
    @Column
    private AccountStatus status;
    @Column
    private Float balance;
    @Column
    private Currency currency;
    @Column
    private LocalDateTime lastActivityDate = LocalDateTime.now();

    public OffsetDateTime getLastActivityDate() {
        return DateTimeConverter.toOffsetDateTime(this.lastActivityDate);
    }
}
