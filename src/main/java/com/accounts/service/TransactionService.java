package com.accounts.service;

import com.accounts.domain.TransactionInput;
import com.accounts.entity.Transaction;
import com.accounts.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepo repo;

    public List<Transaction>  getTransactions() {
        return repo.findAll();
    }

    public Boolean save (Object transaction) {
//        Transaction entity = Transaction.builder().transactionId(transaction.getTransactionId())
//                        .transactionType(transaction.getTransactionType())
//                                .amount(transaction.getAmount())
//                                        .build();
//        repo.save(entity);
        return false;
    }
}
