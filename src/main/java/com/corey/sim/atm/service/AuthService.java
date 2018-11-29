package com.corey.sim.atm.service;

import java.security.Principal;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

@Stateless
public class AuthService {

    @Resource
    private SessionContext ctx;

    public String getAccountNumber() {
        Principal p = ctx.getCallerPrincipal();
        return p.getName();
    }
}
