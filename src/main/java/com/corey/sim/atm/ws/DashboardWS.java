package com.corey.sim.atm.ws;

import com.corey.sim.atm.service.AuthService;
import com.corey.sim.atm.datastore.Account;
import com.corey.sim.atm.dto.DashboardDTO;
import com.corey.sim.atm.service.AccountService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("dashboard")
public class DashboardWS {

    private static final Logger logger = Logger.getAnonymousLogger();
    
    @Context
    private HttpServletRequest request;
    
    @EJB
    private AuthService authService;
    
    @EJB
    private AccountService accountService;

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
            
            DashboardDTO dashDTO = new DashboardDTO(account);
            
            return Response.ok(dashDTO).build();
        } catch (Exception e) {
            logger.log(Level.WARNING, "WS failed", e);
            return Response.serverError().build();
        }
    }
    
    @PUT
    @Consumes("application/json")
    @Produces("text/plain")
    public Response update(DashboardDTO dashDTO) {
        try {
            String accountNumber = authService.getAccountNumber();
            Account account = accountService.selectByAccountNumber(accountNumber);
            
            if (account == null) {
                return Response.status(Status.UNAUTHORIZED).build();
            }
            
            float amount = dashDTO.getBalance();
            account.setBalance(amount);
            
            accountService.merge(account);
            dashDTO = new DashboardDTO(account);
            
            return Response.ok(dashDTO.getBalance()).build();
        } catch (Exception e) {
            logger.log(Level.WARNING, "WS failed", e);
            return Response.serverError().build();
        }
    }

    @DELETE
    @Produces("text/plain")
    public Response deleteAll() {
        try {
            String accountNumber = authService.getAccountNumber();
            Account account = accountService.selectByAccountNumber(accountNumber);
            
            if (account == null) {
                return Response.status(Status.UNAUTHORIZED).build();
            }
            
            accountService.deleteByAccountNumber(accountNumber);
            request.getSession();
            request.logout();
            
            return Response.ok("").build();
        } catch (ServletException e) {
            logger.log(Level.WARNING, "WS failed", e);
            return Response.serverError().build();
        }
    }
}
