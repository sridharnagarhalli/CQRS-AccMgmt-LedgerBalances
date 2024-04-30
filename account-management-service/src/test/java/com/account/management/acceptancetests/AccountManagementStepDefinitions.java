package com.account.management.acceptancetests;

import com.account.management.dto.AddressDto;
import com.account.management.dto.CreateAccountCommand;
import com.account.management.dto.CreateAccountDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountManagementStepDefinitions {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;

    private ResponseEntity<String> response;

    @Given("the account management service is running")
    public void theAccountManagementServiceIsRunning() {
        // Nothing to do here, SpringBootTest annotation takes care of starting the application
    }

    @When("a new account is created with valid data")
    public void aNewAccountIsCreatedWithValidData() {
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
        response = restTemplate.postForEntity(url, createAccountCommand, String.class);
    }

    @Then("the account should be saved in the database")
    public void theAccountShouldBeSavedInTheDatabase() {
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Then("a message should be sent to the Kafka topic")
    public void aMessageShouldBeSentToTheKafkaTopic() {
        // TODO
    }

    @When("a new account is created with invalid data")
    public void aNewAccountIsCreatedWithInvalidData() {
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
        response = restTemplate.postForEntity(url, createAccountCommand, String.class);
    }

    @Then("an error response should be returned")
    public void anErrorResponseShouldBeReturned() {
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Then("no message should be sent to the Kafka topic")
    public void noMessageShouldBeSentToTheKafkaTopic() {
        //TODO
    }
}
