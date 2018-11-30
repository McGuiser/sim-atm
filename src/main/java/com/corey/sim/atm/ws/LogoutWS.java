package com.corey.sim.atm.ws;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("logout")
public class LogoutWS {

    private static final Logger logger = Logger.getAnonymousLogger();
    @Context
    private HttpServletRequest request;

    @GET
    @Produces("text/plain")
    public Response logout() {
        try {
            if (request.getRemoteUser() != null) {
                request.logout();
            }
            return Response.ok("").build();
        } catch (Exception e) {
            logger.log(Level.WARNING, "WS failed", e);
            return Response.serverError().build();
        }
    }
}
