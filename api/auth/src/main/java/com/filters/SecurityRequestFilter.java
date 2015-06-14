package com.filters;

import com.data.managers.Authentication;
import com.data.objects.Auth;
import com.data.objects.Session;
import com.data.objects.UserInfo;
import org.apache.commons.codec.binary.Base64;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class SecurityRequestFilter implements ContainerRequestFilter {
    @Context
    private ResourceInfo resourceInfo;

    private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource", 401, new Headers<Object>());;
    private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this resource", 403, new Headers<Object>());;
    private static final ServerResponse SESSION_EXPIRED = new ServerResponse("Session has expired, please login again.", 440, new Headers<Object>());;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        SecurityContext ss =  containerRequestContext.getSecurityContext();
        Method method = resourceInfo.getResourceMethod();

        if(method.isAnnotationPresent(PermitAll.class))
            return;

        if(method.isAnnotationPresent(DenyAll.class))
        {
            containerRequestContext.abortWith( ACCESS_FORBIDDEN );
            return;
        }

        try {
            Pattern pattern = Pattern.compile("^\\s*Think01\\s+([A-Za-z0-9\\-_]+):(\\d+):([A-Za-z0-9+/]+[=]*).*$", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
            Matcher matcher = pattern.matcher( containerRequestContext.getHeaders().get("Authorization").get(0) );
            if (matcher.find()) {
                String login = matcher.group(1);
                String stamp = matcher.group(2);
                String token = matcher.group(3);

                long tstamp1 = (new Date()).getTime();
                long tstamp2 = Long.parseLong( stamp );

                if( tstamp2 - tstamp1 < 1000 ) {
                    Auth auth = Authentication.tokens.get( login );
                    String secret = auth.getKey();

                    Mac mac = Mac.getInstance("HmacSHA1");
                    mac.init(new SecretKeySpec(secret.getBytes(), "HmacSHA1"));
                    byte[] data = (stamp + "+" + secret).getBytes();
                    mac.update(data, 0, data.length);
                    byte[] rawHmac = mac.doFinal();
                    String hash = new String(Base64.encodeBase64(rawHmac));

                    if( hash.equals( token ) )
                    {
                        if(method.isAnnotationPresent(RolesAllowed.class))
                        {
                            RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                            Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

                            if( rolesSet.contains( auth.getApp() ) || rolesSet.contains( Session.USER ) )
                            {
                                containerRequestContext.setSecurityContext(new RestSecurityContext(matcher.group(1), auth.getApp() ) );
                                return;
                            }
                        }
                    }
                }
            }
        }
        catch( Exception e )
        {
            System.err.println( "*** Auth Exception: "+e.getMessage() );
            e.printStackTrace();
        }
        containerRequestContext.abortWith( ACCESS_DENIED );
        return;
    }
}

class RestSecurityContext implements SecurityContext
{
    private String userName;
    private List<String> roles;
    private int accessLevel;

    public RestSecurityContext(String userName, String role) {
        this.userName = userName;
        this.roles = new Vector<String>();
        this.roles.add( Session.USER );
        this.roles.add(role);
        this.accessLevel = 1;
    }

    @Override
    public Principal getUserPrincipal() {
        return new UserInfo(userName);
    }

    @Override
    public boolean isUserInRole(String _role) {
        return this.roles.contains( _role );
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}