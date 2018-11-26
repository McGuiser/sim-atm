package com.corey.sim.atm.datastore;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNTS")
@NamedQuery(name = "Account.selectByAccountNumber", query = "select a from Account a where a.accountNumber = :accountNumber")
public class Account implements Serializable {

    public static final int ACCOUNT_NUM_LENGTH = 9;
    public static final int EMAIL_MIN_LENGTH = 5;
    public static final int EMAIL_MAX_LENGTH = 60;
    public static final int PIN_LENGTH = 4;
    public static final int MIN_INIT_DEPOSIT = 20;
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = ACCOUNT_NUM_LENGTH, unique = true)
    private String accountNumber;
    
    @Column(length = 65)
    private String email;
    
    @Column(length = 9)
    private Float balance;
    
    @Column(length = 65)
    private String pin;

    public Long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Float getBalance() {
        System.out.println("Hello from account");
        return balance;
    }

    public void setBalance(Float balance) {
        System.out.println("Hello from account");
        this.balance = balance;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Account) {
            Account that = (Account) obj;
            return Objects.equals(id, that.id);
        }
        return false;
    }
}
