package com.accounts.controller;

import com.accounts.domain.AuthPayload;
import com.accounts.domain.BankAccount;
import com.accounts.domain.Client;
import com.accounts.domain.User;
import com.accounts.service.BankService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class AccountsController {
    @Autowired
    BankService bankService;

    @Value("${app.jwt.secret}")
    String jwtSecret;

    @QueryMapping
    public Mono<List<BankAccount>> accounts() {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> {
                    Authentication authentication = context.getAuthentication();
                    log.info("Getting Accounts for user: " + authentication.getName());
                    return bankService.getAccounts();
                });
    }

    @MutationMapping
    public Mono<AuthPayload> login(@Argument String username, @Argument String password) {
        // Authenticate user (replace with your actual authentication logic)
        if ("user".equals(username) && "password".equals(password)) {
            String token = Jwts.builder()
                    .setSubject(username)
                    .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();

            User user = new User("1", username, "user@example.com"); // Example user
            AuthPayload payload = new AuthPayload(token, user);
            return Mono.just(payload);
        } else {
            return Mono.error(new RuntimeException("Invalid credentials"));
        }
    }

    @SchemaMapping(typeName = "BankAccount", field = "client")
    public Mono<Client> getClient(BankAccount account) {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(context -> {
                    Authentication authentication = context.getAuthentication();
                    log.info("Getting client for " + account.id() + " for user: " + authentication.getName());
                    return Mono.fromCallable(() -> bankService.getClientByAccountId(account.id()))
                            .subscribeOn(Schedulers.boundedElastic());
                });
    }
}
