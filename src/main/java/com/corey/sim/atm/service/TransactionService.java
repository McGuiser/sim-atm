package com.corey.sim.atm.service;

import com.corey.sim.atm.entity.Transaction;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class TransactionService {

    @PersistenceContext
    private EntityManager em;

    public Transaction selectById(String id) {
        return em.find(Transaction.class, id);
    }
    
    public List<Transaction> selectByAccountNumber(String accountNumber) {
        TypedQuery<Transaction> q = em.createNamedQuery("Transaction.selectByAccountNumber", Transaction.class);
        q.setParameter("accountNumber", accountNumber);
        List<Transaction> accounts = q.getResultList();
        
        if (accounts.isEmpty()) {
            return null;
        }
        
        return accounts;
    }

    public void persist(Transaction trans) {
        em.persist(trans);
    }
}
