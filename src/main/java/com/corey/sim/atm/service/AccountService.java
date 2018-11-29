package com.corey.sim.atm.service;

import com.corey.sim.atm.datastore.Account;
import com.corey.sim.atm.datastore.AuthGroup;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class AccountService {

    @PersistenceContext
    private EntityManager em;

    public Account selectById(Long id) {
        return em.find(Account.class, id);
    }

    public Account selectByAccountNumber(String accountNumber) {
        TypedQuery<Account> q = em.createNamedQuery("Account.selectByAccountNumber", Account.class);
        q.setParameter("accountNumber", accountNumber);
        List<Account> accounts = q.getResultList();
        if (accounts.isEmpty()) {
            return null;
        }
        return accounts.get(0);
    }

    public void persist(Account account) {
        em.persist(account);
        AuthGroup grp = new AuthGroup("account", account.getAccountNumber());
        em.persist(grp);
    }
    
    public void merge(Account account) {
        em.merge(account);
        em.flush();
    }
}

