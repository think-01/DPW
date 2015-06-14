package com.service;

        import com.data.managers.Authentication;
        import com.data.objects.*;

        import javax.annotation.security.DenyAll;
        import javax.annotation.security.PermitAll;
        import javax.annotation.security.RolesAllowed;
        import javax.ejb.EJB;
        import javax.ejb.Stateless;
        import javax.inject.Inject;
        import javax.ws.rs.*;
        import javax.ws.rs.core.*;
        import java.security.Principal;
        import java.util.Collection;


@Path("/")
public class User {
    @RolesAllowed("USER")
    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public String test()
    {
        return "test";
    }

    @RolesAllowed("MINES")
    @GET
    @Path("/test2")
    @Produces(MediaType.APPLICATION_JSON)
    public String test2()
    {
        return "test2";
    }
}
