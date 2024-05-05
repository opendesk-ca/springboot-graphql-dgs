package com.accounts.entity;

import com.accounts.domain.AccountStatus;
import com.accounts.domain.AccountType;
import com.accounts.domain.DateTimeConverter;
import com.accounts.domain.InterestRateType;
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
    private Float interestRate;
    @Column
    private InterestRateType interestRateType;
    @Column
    private Currency currency;
    @Column
    private Float currentBalance;
    @Column
    private LocalDateTime maturityDate;
    @Column
    private LocalDateTime lastActivityDate;

    public OffsetDateTime getMaturityDate() {
        return DateTimeConverter.toOffsetDateTime(this.maturityDate);
    }
    public OffsetDateTime getLastActivityDate() {
        return DateTimeConverter.toOffsetDateTime(this.lastActivityDate);
    }
}
