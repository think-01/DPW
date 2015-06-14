package com.data.objects;

import javax.persistence.*;
import java.beans.Transient;
import java.io.Serializable;

@Entity
@NamedQueries(
        {
                @NamedQuery(name = "findAllTokens", query = "SELECT a FROM Auth a"),
                @NamedQuery(name = "findAllApps", query = "SELECT DISTINCT a.app FROM Auth a"),
                @NamedQuery(name = "findSecret", query = "SELECT a FROM Auth a WHERE a.name LIKE :name")
        }
)
public class Auth implements Serializable {

    @Id
    @GeneratedValue
    public int id;

    private String name;

    private String key;

    private Device system;

    private String app;

    public Auth() {
    }

    public Auth(String name, String key, Device sys, String app ) {
        this.name = name;
        this.key = key;
        this.system = sys;
        this.app = app;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Device getSystem() {
        return system;
    }

    public void setSystem(Device system) {
        this.system = system;
    }

    public String getApp() {
        return app.toUpperCase();
    }

    public void setApp(String app) {
        this.app = app;
    }
}
