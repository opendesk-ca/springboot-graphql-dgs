package com.accounts.service;

import com.accounts.domain.TransactionInput;
import com.accounts.entity.Account;
import com.accounts.entity.Transaction;
import com.accounts.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TransactionService {

    @Autowired
    TransactionRepo repo;

    public List<Transaction>  getTransactions() {
        return repo.findAll();
    }

    public Boolean save (Transaction transaction) {
        repo.save(transaction);
        return false;
    }

    public Map<Integer, Account> getTxnAccounts(Set<Integer> keys) {
        Map<Integer, Account> accountMap = new HashMap<>();

        return null;
    }
}
