package com.accounts.repo;

import com.accounts.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountId(Integer accountId);
    boolean existsByAccountIdAndTransactionId(Integer accountId, Integer transactionId);
}
