package com.accounts;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureHttpGraphQlTester
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = SpringBootGraphQLApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FederatedGraphQLTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private static HttpHeaders headers;

    @BeforeAll
    public static void setUp() {
        headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
    }

    @Test
    @Order(1)
    @Sql(scripts = "/sql/drop-and-create-tables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testAddAccountMutation() {

        String graphqlPayload = getGraphqlPayload("""
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
                """);
        HttpEntity<String> request = new HttpEntity<>(graphqlPayload, headers);

        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:4000/graphql").build().toUri();
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, request, String.class);

        // Assert the response
        assertEquals(200, response.getStatusCodeValue());
        // Additional assertions on response body
    }

    @Test
    @Order(2)
    void testAddClientMutation() {

        String graphqlPayload = getGraphqlPayload("""
                        mutation {
                            addClient(client: {
                                clientId: 5000,
                                accountId: 12345,
                                firstName: "John",
                                middleName: "Quincy",
                                lastName: "Doe",
                                country: "US"
                            }) {
                                clientId
                                accountId
                                firstName
                                middleName
                                lastName
                                country
                            }
                        }
                """);
        HttpEntity<String> request = new HttpEntity<>(graphqlPayload, headers);

        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:4000/graphql").build().toUri();
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, request, String.class);

        // Assert the response
        assertEquals(200, response.getStatusCodeValue());
        // Additional assertions on response body
    }


    @Test
    @Order(3)
    @Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testFederatedQuery() {
        String graphqlPayload = getGraphqlPayload("""
                    {
                      accounts {
                        accountId
                        client {
                          clientId
                          firstName
                          lastName
                        }
                        accountNumber
                        accountType
                        balance
                        currency
                        lastActivityDate
                        status
                      }
                    }
                """);
        HttpEntity<String> request = new HttpEntity<>(graphqlPayload, headers);

        ResponseEntity<String> response = restTemplate.exchange("/graphql", HttpMethod.POST, request, String.class);
        System.out.println(response);
        // Assert the response
        assertEquals(200, response.getStatusCodeValue());
        // Additional assertions on response body
    }

    private static String getGraphqlPayload(String query) {
        return String.format("{\"query\":\"%s\"}", query.replace("\"", "\\\"").replace("\n", ""));
    }
}
