package com.corey.sim.atm.datastore;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@IdClass(AuthGroupIdClass.class)
@Table(name = "AUTH_GROUP")
@NamedQuery(name = "AuthGroup.selectByAccountNumber", query = "select g from AuthGroup g where g.accountNumber = :accountNumber")
public class AuthGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "GROUP_NAME", length = 20)
    private String name;
    @Id
    private String accountNumber;

    public AuthGroup() {
    }

    public AuthGroup(String name, String username) {
        this.name = name;
        this.accountNumber = username;
    }

    public String getName() {
        return name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
