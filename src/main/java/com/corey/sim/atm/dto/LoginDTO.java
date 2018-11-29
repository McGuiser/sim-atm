package com.corey.sim.atm.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginDTO {

    private String accountNumber;
    private String pin;
    private String nextPage;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }
}
