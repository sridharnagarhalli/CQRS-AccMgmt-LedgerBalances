# Account Management and Ledger Balance Services using CQRS Design Pattern 

### Account Management Service
### Swagger
```
http://localhost:9291/swagger-ui/index.html
```
### Curl command to create an account
```
curl --location --request POST 'localhost:9291/api/v1/accounts-management/create' \
--header 'Content-Type: application/json' \
--data-raw '{
  "createAccountDto": {
    "firstName": "Sridhar",
    "lastName": "Nagarhalli",
    "niNumber": "Test123",
    "depositInitialAmount": 100.00,
    "addressDto": {
      "addressLine1": "100 ",
      "addressLine2": "Doughlas Street",
      "postcode": "DG11DG",
      "county": "Berkshire",
      "country": "UK"
    }
  }
}'
```
### Curl command to deposit to account

```
curl --location --request POST 'localhost:9291/api/v1/accounts-management/transact' \
--header 'Content-Type: application/json' \
--data-raw '{
  "transactionType": "DEPOSIT",
  "transactionDto": {
    "transactionAmount": 232.66,
    "accountNumber": "9618dd56-7781-4f26-89a9-f1b08d0245a2"
  }
}'
```

### Curl command to withdraw from account

```
curl --location --request POST 'localhost:9291/api/v1/accounts-management/transact' \
--header 'Content-Type: application/json' \
--data-raw '{
  "transactionType": "WITHDRAW",
  "transactionDto": {
    "transactionAmount": 32.66,
    "accountNumber": "9618dd56-7781-4f26-89a9-f1b08d0245a2"
  }
}'
```

### Ledger balances query service
### Swagger
```
http://localhost:8291/swagger-ui/index.html
```

### Curl command to execute the endpoint
```
curl --location --request GET 
'http://localhost:8291/api/v1/ledger/balances?timestamp=2024-04-30T17:40:12'
```