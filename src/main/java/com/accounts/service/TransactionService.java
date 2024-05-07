package com.accounts.service;

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

    public Boolean save (Transaction transaction) {
        repo.save(transaction);
        return false;
    }
}
