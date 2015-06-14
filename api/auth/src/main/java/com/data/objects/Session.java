package com.data.objects;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.beans.Transient;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Session {
    @JsonIgnore
    public Device device;

    @JsonIgnore
    public Date updated;

    @JsonIgnore
    public String app;

    public static String USER = "USER";

    public String session;

    public static int timeout = 5;

    public Session( Device sys, String app ) {
        this.app = app;
        this.device = sys;
        this.updated = new Date();
        this.session = UUID.randomUUID().toString();
    }

    public String getSession() {
        Date now = new Date();
        if( TimeUnit.MILLISECONDS.toMinutes(now.getTime() - this.updated.getTime()) >= timeout )
            return null;

        this.updated = now;
        return session;
    }
}
