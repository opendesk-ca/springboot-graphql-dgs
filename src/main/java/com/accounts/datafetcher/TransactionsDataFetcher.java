package com.accounts.datafetcher;


import com.accounts.entity.Account;
import com.accounts.entity.Transaction;
import com.accounts.service.TransactionService;
import com.netflix.graphql.dgs.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@DgsComponent
@Slf4j
public class TransactionsDataFetcher {

    @Autowired
    TransactionService transactionService;

    @DgsQuery
    public List<Transaction> getTransactions()  {
        log.info("Getting Transactions");
        List<Transaction> txns = transactionService.getTransactions();
        return txns;
    }

   @DgsData(parentType = "Transaction", field = "account")
    public List<Account> getAccount (DgsDataFetchingEnvironment dfe){
        //Because the account field is on Transaction, the getSource() method will return the Transaction instance.
        Transaction transaction = dfe.getSource();
        return transactionService.getAccounts(transaction.getTransactionId());
    }
}
