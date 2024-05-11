package com.accounts.service;

import com.accounts.domain.ClientInput;
import com.accounts.entity.Client;
import com.accounts.entity.ClientAccountId;
import com.accounts.repo.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    ClientRepo repo;

    public List<Client> getClients(Integer accountId) {
        return repo.findAll().stream()
                .filter(c->c.getClientIdAccountId().getAccountId().equals(accountId))
                    .collect(Collectors.toList());
    }

    public List<Client> getClients() {
        return repo.findAll();
    }

    public Client save(ClientInput clientInput) {
        ClientAccountId clientAccount = ClientAccountId.builder()
                .clientId(clientInput.getClientId()).accountId(clientInput.getAccountId()).build();

        Client client = Client.builder().clientIdAccountId(clientAccount)
                .clientId(clientInput.getClientId()).accountId(clientInput.getAccountId())
                .firstName(clientInput.getFirstName()).lastName(clientInput.getLastName())
                .middleName(clientInput.getMiddleName()).country(clientInput.getCountry()).build();
         repo.save(client);

         return client;
    }
}
