package com.accounts.controller;

import com.accounts.config.JwtUtils;
import com.accounts.domain.AuthPayload;
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

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountsControllerTest {

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private BankService bankService;

    @Mock
    private JwtUtils jwUtils;

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

    @Test
    void testLoginQueryTest () throws NoSuchFieldException, IllegalAccessException {
        String email = "admin@system.com";
        String password = "password123";
        String expectedToken = "valid-jwt-token";

        Field systemUserField = AccountsController.class.getDeclaredField("systemUser");
        systemUserField.setAccessible(true); // Allow access to private field
        systemUserField.set(accountsController, email); // Set value

        Field systemPasswordField = AccountsController.class.getDeclaredField("systemPassword");
        systemPasswordField.setAccessible(true); // Allow access to private field
        systemPasswordField.set(accountsController, password); // Set value

        when (jwUtils.generateJWTToken()).thenReturn(expectedToken);
        //Actual call

        AuthPayload payload = accountsController.loginQuery(email, password);

        assertNotNull(payload);
        assertEquals(payload.getToken(), expectedToken);
        assertEquals(email, payload.getUser().getEmail());
    }
}
