package com.accounts.datafetcher;

import com.accounts.dataloader.TransactionAccountDataLoader;
import com.accounts.entity.Account;
import com.accounts.entity.Transaction;
import com.accounts.service.TransactionService;
import com.netflix.graphql.dgs.*;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public CompletableFuture<Account> getAccount (DgsDataFetchingEnvironment dfe){

        DataLoader<Integer, Account> transactionAccountDataLoader = dfe.getDataLoader(TransactionAccountDataLoader.class);

        //Because the account field is on Transaction, the getSource() method will return the Transaction instance.
        Transaction transaction = dfe.getSource();

        //Load the account from the DataLoader. This call is async and will be batched by the DataLoader mechanism.

        CompletableFuture<Account> account = CompletableFuture.completedFuture(new Account(transaction.getAccountId()));

        return transactionAccountDataLoader.load(transaction.getAccountId());
    }

    @DgsMutation
    public Boolean addTransaction(@InputArgument ("transaction") Transaction transaction) {
        transactionService.save(transaction);
        return true;
    }

    @DgsEntityFetcher(name = "Account")
    public Account account (Map<String, Object> values) {

        /*Optional<Account> account = accountsService.getAccounts()
                .stream().filter(a->a.getAccountId()==Integer.parseInt((String)values.get("accountId")))
                .findFirst();

        if (account.isPresent()) return account.get();*/
        return null;
    }
}
