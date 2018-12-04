package com.corey.sim.atm.entity;

import java.io.Serializable;
import java.util.Objects;

public class AuthGroupIdClass implements Serializable {

    private String name;
    private String accountNumber;

    public AuthGroupIdClass() {
    }

    public AuthGroupIdClass(String name, String accountNumber) {
        this.name = name;
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, accountNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AuthGroupIdClass) {
            AuthGroupIdClass that = (AuthGroupIdClass) obj;
            return Objects.equals(name, that.name)
                    && Objects.equals(accountNumber, that.accountNumber);
        }
        return false;
    }
}
