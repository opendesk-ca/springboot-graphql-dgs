package com.accounts.service;

import com.accounts.entity.Account;
import com.accounts.exceptions.AccountNotFoundException;
import com.accounts.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepo repo;

    public Boolean save(com.accounts.entity.Account account) {
        if (!repo.findById(account.getAccountId()).isPresent()){
            repo.save(account);
            return true;
        }
        return false;
    }


    public List<Account> accounts() {
        return repo.findAll();
    }

    public com.accounts.entity.Account accountById(Integer accountId) {
        Optional<com.accounts.entity.Account> optionalAccount = repo.findById(accountId);
        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        } else {
            throw new AccountNotFoundException("Account Not fount " + accountId + " Please Account before adding Transactions");
        }
    }
}
