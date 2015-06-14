package com.ejb;

import com.data.managers.Authentication;
import com.data.objects.Auth;
import com.data.objects.Device;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class StartupBean {
    @PersistenceContext EntityManager em;

    @PostConstruct
    public void applicationStartup() {
        Auth a;
        em.persist( new Auth( "UID", "TOKEN", Device.IOS, "MINES" ) );
        em.persist( new Auth( "UID", "TOKEN", Device.ANDROID, "MINES" ) );
        em.persist( new Auth( "UID", "TOKEN", Device.WINDOWS, "MINES" ) );
    }
}
