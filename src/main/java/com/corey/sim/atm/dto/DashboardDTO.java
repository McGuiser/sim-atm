package com.corey.sim.atm.dto;

import com.corey.sim.atm.datastore.Account;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DashboardDTO {

    private String accountNumber;
    private String email;
    private Float balance;
    
    public DashboardDTO() {
    }

    public DashboardDTO(Account a) {
        accountNumber = a.getAccountNumber();
        email = a.getEmail();
        balance = a.getBalance();
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
}
