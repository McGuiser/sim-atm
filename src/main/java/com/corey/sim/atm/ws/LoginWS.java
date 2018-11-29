package com.corey.sim.atm.ws;

import com.corey.sim.atm.dto.LoginDTO;
import com.corey.sim.atm.service.PinService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("login")
public class LoginWS {

    private static final Logger logger = Logger.getAnonymousLogger();
    @Context
    private HttpServletRequest request;

    @POST
    @Consumes("application/json")
    @Produces("text/plain")
    public Response login(LoginDTO dto) {
        System.out.println("In response");
        try {
            if (dto.getAccountNumber() == null
                    || dto.getPin() == null) {
                return Response.status(Status.BAD_REQUEST).build();
            }
            if (request.getRemoteUser() != null) {
                request.logout();
            }
            String accountNumber = dto.getAccountNumber();
            request.login(accountNumber, dto.getPin());
            return Response.ok("").build();
        } catch (ServletException e) {
            System.out.println("In servlet exception");
            e.printStackTrace();
            return Response.status(Status.UNAUTHORIZED).build();
        } catch (Exception e) {
            System.out.println("In exception");
            logger.log(Level.WARNING, "WS failed", e);
            return Response.serverError().build();
        }
    }
}
