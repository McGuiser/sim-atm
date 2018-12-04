package com.corey.sim.atm.ws;

import com.corey.sim.atm.entity.Transaction;
import com.corey.sim.atm.service.AuthService;
import com.corey.sim.atm.entity.Account;
import com.corey.sim.atm.dto.TransactionDTO;
import com.corey.sim.atm.service.AccountService;
import com.corey.sim.atm.service.TransactionService;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("transaction")
public class TransactionWS {

    private static final Logger logger = Logger.getAnonymousLogger();
    
    @Context
    private HttpServletRequest request;
    
    @EJB
    private AuthService authService;
    
    @EJB
    private AccountService accountService;
    
    @EJB
    private TransactionService transactionService;

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    public Response read() {
        try {
            
            String accountNumber = authService.getAccountNumber();
            Account account = accountService.selectByAccountNumber(accountNumber);
            
            if (account == null) {
                return Response.status(Status.UNAUTHORIZED).build();
            }
            
            List<Transaction> transactions = transactionService.selectByAccountNumber(accountNumber);
            transactions.sort((t1,t2) -> t1.getTransactionDate().compareTo(t2.getTransactionDate()));
            TransactionDTO[] dtos = toDTOs(transactions);
            
            return Response.ok(dtos).build();
        } catch (Exception e) {
            logger.log(Level.WARNING, "WS failed", e);
            return Response.serverError().build();
        }
    }
    
    private TransactionDTO[] toDTOs(List<Transaction> transactions) throws ParseException {
        TransactionDTO[] dtos = new TransactionDTO[transactions.size()];
        for (int i = 0; i < dtos.length; i++) {
            Transaction transaction = transactions.get(i);
            dtos[i] = new TransactionDTO(transaction);
        }
        return dtos;
    }
    
    @POST
    @Consumes("application/json")
    @Produces("text/plain")
    public Response create(TransactionDTO transactionDTO) {
        try {
            String accountNumber = authService.getAccountNumber();
            Account account = accountService.selectByAccountNumber(accountNumber);
            
            if (account == null) {
                return Response.status(Status.UNAUTHORIZED).build();
            }
            Random random = new Random(System.nanoTime());
            String id = String.format("%06d", random.nextInt(1000000));
            Transaction transaction = transactionService.selectById(id);
            while (transaction != null) {
                id = String.format("%06d", random.nextInt(1000000));
                transaction = transactionService.selectById(id);
            }
            
            Float amount = transactionDTO.getAmount();
            
            String transactionType = transactionDTO.getTransactionType();
            
            transaction = new Transaction();
            transaction.setId(id);
            transaction.setAccountNumber(accountNumber);
            transaction.setAmount(amount);
            transaction.setTransactionType(transactionType);
            transaction.setTransactionDate(new Date());
            transactionService.persist(transaction);
            
            if(transactionType.equals("withdrawal")){
                amount *= -1;
            }
            float balance = account.getBalance() + amount;
            float minNew = account.getBalance() - 500;
            float maxNew = account.getBalance() + 10000;
            
            if(balance < account.getBalance()){
                if(!((minNew <= balance) && ((account.getBalance() - balance) % 20 == 0))){
                    return Response.status(Status.BAD_REQUEST).entity("Invalid withdrawal!!!").build();
                }
            }else{
                if(!(balance <= maxNew)){
                    return Response.status(Status.BAD_REQUEST).entity("Invalid deposit").build();
                }
            }
            
                account.setBalance(balance);
                accountService.merge(account);
            
            return Response.ok(account.getBalance()).build();
        } catch (Exception e) {
            logger.log(Level.WARNING, "WS failed", e);
            return Response.serverError().build();
        }
    }
}
