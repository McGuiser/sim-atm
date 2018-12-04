package com.corey.sim.atm.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TRANSACTIONS")
@NamedQuery(name = "Transaction.selectByAccountNumber", query = "select t from Transaction t where t.accountNumber = :accountNumber")
public class Transaction implements Serializable {

    public static final int ID_LENGTH = 10;
    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    private String accountNumber;
    
    @Column(length = 65)
    private String transactionType;
    
    @Column(length = 9)
    private Float amount;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Transaction) {
            Transaction that = (Transaction) obj;
            return Objects.equals(id, that.id);
        }
        return false;
    }
}
