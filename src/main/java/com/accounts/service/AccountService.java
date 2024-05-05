package com.accounts.service;

import com.accounts.entity.Account;
import com.accounts.exceptions.AccountNotFoundException;
import com.accounts.repo.AccountRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class AccountService {
    @Autowired
    AccountRepo repo;

    public Boolean save(Account account) {
        if (!repo.findById(account.getAccountId()).isPresent()){
            repo.save(account);
            return true;
        }
        return false;
    }


    public List<Account> getAccounts() {
        return repo.findAll();
    }

    public Account accountById(Integer accountId) {
        Optional<Account> optionalAccount = repo.findById(accountId);
        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        } else {
            throw new AccountNotFoundException("Account Not found " + accountId + " Please add Account before adding Transactions");
        }
    }

    public Map<Integer, Account> getAccounts(List <Integer> accountIdList) {
        List<Account> accountList = getAccounts();

        Map<Integer, Account> resultMap = new HashMap<>();

        for (Account account : accountList) {
            if (accountIdList.contains(account.getAccountId())) {
                resultMap.put(account.getAccountId(), account);
            }
        }

        return resultMap;
    }
}