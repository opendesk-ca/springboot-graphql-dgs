package com.accounts.service;

import com.accounts.domain.ClientInput;
import com.accounts.entity.Client;
import com.accounts.entity.ClientAccountId;
import com.accounts.exceptions.ClientAlreadyExistsException;
import com.accounts.exceptions.ClientNotFoundException;
import com.accounts.repo.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    ClientRepo repo;

    public List<Client> getClients() {
        List<Client> clients = repo.findAll();

        clients = clients.stream().map(c->{
            c.setClientId(c.getClientIdAccountId().getClientId());
            c.setAccountId(c.getClientIdAccountId().getAccountId());
            return c;
        }).collect(Collectors.toList());

        return clients;
    }

    public Client save(ClientInput clientInput) {
        ClientAccountId cIdAId = ClientAccountId.builder()
                .accountId(clientInput.getAccountId())
                .clientId(clientInput.getClientId()).build();

        Client client = Client.builder().clientIdAccountId(cIdAId)
                .clientId(clientInput.getClientId()).accountId(clientInput.getAccountId())
                .firstName(clientInput.getFirstName()).lastName(clientInput.getLastName())
                .middleName(clientInput.getMiddleName()).country(clientInput.getCountry())
                .build();

        if (repo.findByClientIdAccountId(cIdAId).isEmpty()){
            repo.save(client);
        }else{
            throw new ClientAlreadyExistsException("Client  " + clientInput.getClientId() + " Already exists for Account " + clientInput.getAccountId()) ;
        }
        return client;
    }

    public Boolean delete(Integer clientId, Integer accountId) {
        ClientAccountId cIdAId = ClientAccountId.builder()
                .accountId(accountId)
                .clientId(clientId).build();

        if (!repo.findByClientIdAccountId(cIdAId).isEmpty()){
            repo.delete(repo.findByClientIdAccountId(cIdAId).get(0));
            return true;
        }else{
            throw new ClientNotFoundException("Client  " + clientId + " not exists for Account " + accountId);
        }
    }
}
