package com.corey.sim.atm.dto;

import com.corey.sim.atm.entity.Transaction;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TransactionDTO {

    private String id;
    private String accountNumber;
    private String transactionType;
    private Float amount;
    private String transactionDate;

    public TransactionDTO() {
    }

    public TransactionDTO(Transaction transaction) throws ParseException {
        id = transaction.getId();
        accountNumber = transaction.getAccountNumber();
        transactionType = transaction.getTransactionType();
        amount = transaction.getAmount();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        transactionDate = formatter.format(transaction.getTransactionDate());
    }

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

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
    
}
