package com.account.management.integrationtest;

import com.account.management.dto.AccountDto;
import com.account.management.dto.AddressDto;
import com.account.management.dto.CreateAccountCommand;
import com.account.management.dto.CreateAccountDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountManagementIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Container
    private static final KafkaContainer kafkaContainer = new KafkaContainer();

    @Container
    private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @BeforeAll
    public static void setUp() {
        kafkaContainer.start();
        System.setProperty("spring.kafka.bootstrap-servers", kafkaContainer.getBootstrapServers());

        mysqlContainer.start();
        System.setProperty("spring.datasource.url", mysqlContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", mysqlContainer.getUsername());
        System.setProperty("spring.datasource.password", mysqlContainer.getPassword());
    }

    @AfterAll
    public static void tearDown() {
        kafkaContainer.stop();
        mysqlContainer.stop();
    }

    @Test
    public void testCreateAccountEndpoint() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + port + "/api/v1/accounts-management/create";
        var createAccountCommand = new CreateAccountCommand();
        createAccountCommand.setCreateAccountDto(CreateAccountDto.builder()
                .withFirstName("John")
                .withLastName("Doe")
                .withNiNumber("12345")
                .withDepositInitialAmount(100.0)
                .withAddressDto(AddressDto.builder()
                        .withAddressLine1("123 Main St")
                        .withCounty("GB")
                        .withCountry("GB")
                        .withPostcode("GA12LN")
                        .build())
                .build());

        var accountDto = restTemplate.postForObject(url, createAccountCommand, AccountDto.class);
        assertEquals(createAccountCommand.getCreateAccountDto().getFirstName(), accountDto.getFirstName());
        assertEquals(createAccountCommand.getCreateAccountDto().getLastName(), accountDto.getLastName());
        assertTrue(null != accountDto.getAccountNumber());

    }
}
