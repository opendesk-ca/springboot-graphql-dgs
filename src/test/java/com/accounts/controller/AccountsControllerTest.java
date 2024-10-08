package com.accounts.controller;

import com.accounts.domain.BankAccount;
import com.accounts.domain.Currency;
import com.accounts.service.BankService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AccountsControllerTest {

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private BankService bankService;

    @InjectMocks
    private AccountsController accountsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testAccounts() {

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when (authentication.isAuthenticated()).thenReturn(true);
        List<BankAccount> accountsMock = Arrays.asList(new BankAccount("A100", "C100", Currency.CAD, 100, "A" ));

        when (bankService.getAccounts()).thenReturn(accountsMock);

        //Actual call
        List<BankAccount> accounts = accountsController.accounts();

        assertEquals (accounts, accountsMock);
        verify (bankService, times(1)).getAccounts();
    }
}
