package com.data.managers;

import com.data.objects.*;
import org.apache.http.impl.cookie.DateUtils;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.*;

@Stateless
@Startup
public class Authentication {
    @PersistenceContext
    EntityManager em;
    public static Map<String,Session> sessions = new TreeMap<String,Session>();

    public static Map<String,Auth> tokens;

    @Schedule(second="*/30", minute="*",hour="*", persistent=false)
    @PostConstruct
    public void reindexTokens() {
        List<Auth> auths = getTokens();
        tokens = new HashMap<String,Auth>();
        for( Auth auth : auths ) tokens.put( auth.getName(), auth );
    }

    public List<Auth> getTokens() {
        TypedQuery<Auth> query = em.createNamedQuery("findAllTokens", Auth.class);
        return query.getResultList();
    }

    public List<String> getApps() {
        TypedQuery<String> query = em.createNamedQuery("findAllApps", String.class);
        return query.getResultList();
    }

    public void killSession( String session ) {
        sessions.remove( session );
    }

    public Session getSession( String token )
    {
        Session ses;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Auth> q = cb.createQuery(Auth.class);
            Root<Auth> auth = q.from(Auth.class);
            Predicate actives = cb.equal( auth.<String>get("key"), token );
            q.select(auth).where(actives);
            Auth a = em.createQuery(q).getSingleResult();

            ses = new Session( a.getSystem(), a.getApp().toUpperCase() );
            sessions.put(ses.session, ses);
        }
        catch ( Exception e ){
            return null;
        }
        return ses;
    }
}
