package com.accounts.repo;


import com.accounts.entity.Client;
import com.accounts.entity.ClientAccountId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepo extends JpaRepository <Client, ClientAccountId> {
    List<Client> findByClientIdAccountId(ClientAccountId clientAccountId);
    List<Client> findByClientIdAccountIdAccountId(Integer accountId);
}
