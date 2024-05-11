package com.accounts.repo;


import com.accounts.entity.Client;
import com.accounts.entity.ClientAccountId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository <Client, ClientAccountId> {
}
