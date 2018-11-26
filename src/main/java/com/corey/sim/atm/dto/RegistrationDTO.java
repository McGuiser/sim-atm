package com.corey.sim.atm.dto;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegistrationDTO {

    private Long id;
    private String accountNumber;
    private String email;
    private Float balance;
    private String pin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        System.out.println("Hello from dto :" + balance);
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
