package com.account.management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "ACCOUNT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private Long id;
    private String accountNumber;
    private String niNumber;
    private String firstName;
    private String lastName;
    private double balance;
    private Instant accountCreatedTimestamp;
    private Instant lastTransactionTimestamp;
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
