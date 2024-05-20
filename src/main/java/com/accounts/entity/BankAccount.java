package com.accounts.entity;

import com.accounts.domain.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Account", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"accountId"})
})
public class BankAccount {
    @Id
    @Column (  name = "account_id" )
    private Integer id;

    @Column
    private Long clientId;

    @Column
    private Currency currency;

    @Column
    private Float balance;

    @Column
    private String status;
}
