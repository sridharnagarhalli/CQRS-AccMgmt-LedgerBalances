Feature: Account Management Service

  Scenario: Creating a new account
    Given the account management service is running
    When a new account is created with valid data
    Then the account should be saved in the database
    And a message should be sent to the Kafka topic

  Scenario: Creating an account with invalid data
    Given the account management service is running
    When a new account is created with invalid data
    Then an error response should be returned
    And no message should be sent to the Kafka topic
