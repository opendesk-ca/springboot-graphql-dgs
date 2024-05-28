package com.accounts;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        String mutation = """
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

        String graphqlPayload = getGraphqlPayload(mutation);
        HttpEntity<String> request = new HttpEntity<>(graphqlPayload, headers);

        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:4000/graphql").build().toUri();
        ResponseEntity<Map> response = restTemplate.exchange(uri, HttpMethod.POST, request, Map.class);

        // Assert the response
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());

        Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
        Map<String, Object> addAccount = (Map<String, Object>) data.get("addAccount");

        assertEquals("12345", addAccount.get("accountId"));
        assertEquals("CHECKING", addAccount.get("accountType"));
        assertEquals("CHK987654321", addAccount.get("accountNumber"));
        assertEquals("USD", addAccount.get("currency"));
        assertEquals(2500, addAccount.get("balance"));
        assertEquals("OPEN", addAccount.get("status"));
    }

    @Test
    @Order(2)
    void testAddClientMutation() {
        String mutation = """
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
        """;

        String graphqlPayload = getGraphqlPayload(mutation);
        HttpEntity<String> request = new HttpEntity<>(graphqlPayload, headers);

        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:4000/graphql").build().toUri();
        ResponseEntity<Map> response = restTemplate.exchange(uri, HttpMethod.POST, request, Map.class);

        // Assert the response
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());

        Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
        Map<String, Object> addClient = (Map<String, Object>) data.get("addClient");

        assertEquals("5000", addClient.get("clientId"));
        assertEquals(12345, addClient.get("accountId"));
        assertEquals("John", addClient.get("firstName"));
        assertEquals("Quincy", addClient.get("middleName"));
        assertEquals("Doe", addClient.get("lastName"));
        assertEquals("US", addClient.get("country"));
    }

    @Test
    @Order(3)
    @Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testFederatedQuery() {
        String query = """
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
        """;

        String graphqlPayload = getGraphqlPayload(query);
        HttpEntity<String> request = new HttpEntity<>(graphqlPayload, headers);

        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:4000/graphql").build().toUri();
        ResponseEntity<Map> response = restTemplate.exchange(uri, HttpMethod.POST, request, Map.class);

        // Log response body for debugging
        System.out.println(response.getBody());

        // Assert the response
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());

        Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
        assertNotNull(data.get("accounts"));
    }

    private static String getGraphqlPayload(String query) {
        return String.format("{\"query\":\"%s\"}", query.replace("\"", "\\\"").replace("\n", ""));
    }
}
