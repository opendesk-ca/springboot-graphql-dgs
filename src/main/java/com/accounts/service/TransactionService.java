package com.accounts.service;

import com.accounts.domain.TransactionStatus;
import com.accounts.domain.TransactionType;
import com.accounts.entity.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TransactionService {


    public List<Transaction>  getTransactions() {

        List<Transaction> txns = new ArrayList<>();
        List <Integer> accountList1 = Arrays.asList(1001, 1002, 1001);
        List <Integer> accountList2 = Arrays.asList(1001);

        // Adding 5 transaction records
        txns.add(new Transaction(1, accountList1, LocalDateTime.now(), "Payment Received", TransactionStatus.PENDING, 150.00f, TransactionType.CREDIT));
        txns.add(new Transaction(2, accountList2, LocalDateTime.now().minusDays(1), "Online Purchase", TransactionStatus.POSTED, 75.25f, TransactionType.DEBIT));
        txns.add(new Transaction(3, accountList1, LocalDateTime.now().minusWeeks(1), "ATM Withdrawal", TransactionStatus.POSTED, 200.00f, TransactionType.DEBIT));
        txns.add(new Transaction(4, accountList2, LocalDateTime.now().minusMonths(1), "Deposit", TransactionStatus.PENDING, 500.00f, TransactionType.CREDIT));
        txns.add(new Transaction(5, accountList1, LocalDateTime.now().minusDays(2), "Utility Bill", TransactionStatus.PENDING, 100.00f, TransactionType.DEBIT));

        return txns;
    }
}
