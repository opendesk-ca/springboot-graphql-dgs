package com.accounts.service;

import com.accounts.entity.Account;
import com.accounts.entity.Transaction;
import com.accounts.exceptions.AccountNotFoundException;
import com.accounts.exceptions.TransactionExistsException;
import com.accounts.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionService {

    @Autowired
    TransactionRepo transactionRepository;

    @Autowired
    AccountService service;

    public Boolean save(Transaction transaction) {
        if (Objects.isNull(service.accountById(transaction.getAccountId()))){
            throw new AccountNotFoundException("Account Not fount " + transaction.getAccountId() + ". Please add the Account before adding Transactions.");
        }

        if (transactionRepository.existsByAccountIdAndTransactionId(transaction.getAccountId(), transaction.getTransactionId())){
            throw new TransactionExistsException("Transaction already exists  Account Id : " + transaction.getAccountId() +
                    ". Transaction Id " + transaction.getTransactionId());
        }

        transactionRepository.save(transaction);
        return true;
    }

    public List<Transaction>  getTransactions() {
            return transactionRepository.findAll();
    }

    public Map<Transaction, Account> getTxnAccountMap(List<Transaction> transactions) {
        Map<Transaction, Account> depTxnAccountMap = new HashMap<>();

        for (Transaction txn : transactions) {
            Account account = service.accountById(txn.getAccountId());

            if (!Objects.isNull(account)) {
                depTxnAccountMap.put(txn, account);
            }
        }

        return depTxnAccountMap;
    }
}
