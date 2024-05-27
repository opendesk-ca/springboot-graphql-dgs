package com.accounts;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.util.Map;

import static graphql.Assert.assertFalse;
import static graphql.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureHttpGraphQlTester
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = SpringBootGraphQLApplication.class)

public class AccountsGraphQlTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    @Order(1)
    @Sql(scripts = "/sql/drop-and-create-tables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testAddAccountsMutation() {
        String addAccountMutation = """
            mutation {
                addAccount(account: {
                    accountId: "12345",
                    accountType: CHECKING,
                    accountNumber: "CHK987654321",
                    currency: "USD",
                    balance: 2500.00,
                    status: OPEN,
                    lastActivityDate: "2024-05-10T14:48:00.000Z"
                }) {
                    accountId
                    accountType
                    accountNumber
                    currency
                    balance
                    status
                    lastActivityDate
                }
            }
        """;

        graphQlTester.document(addAccountMutation)
                .execute()
                .path("addAccount")
                .entity(Map.class)
                .satisfies(account -> {
                    assertEquals("12345", account.get("accountId"));
                    assertEquals("CHECKING", account.get("accountType"));
                    assertEquals(2500.00, account.get("balance"));
                });
    }

    @Test
    @Order(2)
    void tesGetAccountsQuery() {
        String accountsQuery = """
        query Accounts {
          accounts {
            accountId
            accountNumber
            accountType
            balance
            currency
            lastActivityDate
            status
          }
        }
    """;

        graphQlTester.document(accountsQuery)
                .execute()
                .path("accounts")
                .entityList(Map.class)
                .satisfies(accounts -> {
                    assertFalse(accounts.isEmpty());
                    assertTrue(accounts.stream().anyMatch(account -> "12345".equals(account.get("accountId"))));
                    assertTrue(accounts.stream().anyMatch(account -> "OPEN".equals(account.get("status"))));
                });
    }

    @Test
    @Order(3)
    public void testDeleteFailInvalidAccountsMutation() {
        String deleteAccountMutation = """
           mutation {
                      deleteAccount(accountId: 9999)
                   }
        """;

        graphQlTester.document(deleteAccountMutation)
                .execute().equals(Boolean.FALSE);
    }

    @Test
    @Order(4)
    @Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDeleteSuccessValidAccountsMutation() {
        String deleteAccountMutation = """
           mutation {
                      deleteAccount(accountId: 12345)
                   }
        """;

        graphQlTester.document(deleteAccountMutation)
                .execute().equals(Boolean.TRUE);
    }
}