package com.corey.sim.atm.ws;

import com.corey.sim.atm.dto.RegistrationDTO;
import com.corey.sim.atm.service.PinService;
import com.corey.sim.atm.service.ServiceException;
import com.corey.sim.atm.entity.Account;
import com.corey.sim.atm.entity.Transaction;
import com.corey.sim.atm.service.AccountService;
import com.corey.sim.atm.service.TransactionService;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("registration")
public class RegistrationWS {

    private static final Logger logger = Logger.getAnonymousLogger();
    @EJB
    private AccountService accountService;
    
    @EJB
    private TransactionService transactionService;

    @POST
    @Consumes("application/json")
    @Produces("text/plain")
    public Response create(RegistrationDTO dto) {
        try {
            Random random = new Random(System.nanoTime());
            String accountNumber = String.format("%09d", random.nextInt(1000000000));
            String email = dto.getEmail().trim().toLowerCase();
            String pin = dto.getPin().trim();
            Float deposit = dto.getBalance();
            if (email.length() < Account.EMAIL_MIN_LENGTH
                    || email.length() > Account.EMAIL_MAX_LENGTH
                    || pin.length() != Account.PIN_LENGTH) {
                return Response.status(Status.BAD_REQUEST).entity("Invalid pin or email").build();
            }
            if (deposit < 20) {
                return Response.status(Status.BAD_REQUEST).entity("Invalid initial deposit").build();
            }
            
            Account account = accountService.selectByAccountNumber(accountNumber);
            while (account != null) {
                accountNumber = String.format("%09d", random.nextInt(1000000000));
                account = accountService.selectByAccountNumber(accountNumber);
            }
            
            account = new Account();
            account.setAccountNumber(accountNumber);
            account.setEmail(email);
            account.setBalance(deposit);
            
            PinService pinService = new PinService();
            String encrypted = pinService.encryptPin(pin);
            account.setPin(encrypted);
            
            accountService.persist(account);
            
            String id = String.format("%06d", random.nextInt(1000000));
            Transaction transaction = transactionService.selectById(id);
            while (transaction != null) {
                id = String.format("%06d", random.nextInt(1000000));
                transaction = transactionService.selectById(id);
            }
            
            transaction = new Transaction();
            transaction.setId(id);
            transaction.setAccountNumber(accountNumber);
            transaction.setAmount(deposit);
            transaction.setTransactionType("deposit");
            transaction.setTransactionDate(new Date());
            transactionService.persist(transaction);
            
            return Response.status(Status.OK).entity(accountNumber).build();
        } catch (ServiceException e) {
            logger.log(Level.WARNING, "WS failed", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Registration failed").build();
        }
    }
}
