package com.corey.sim.atm;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.corey.sim.atm.ws.DashboardWS.class);
        resources.add(com.corey.sim.atm.ws.LoginWS.class);
        resources.add(com.corey.sim.atm.ws.LogoutWS.class);
        resources.add(com.corey.sim.atm.ws.RegistrationWS.class);
        resources.add(com.corey.sim.atm.ws.TransactionWS.class);
    }
}
