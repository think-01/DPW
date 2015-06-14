package com.data.objects;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.security.Principal;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class UserInfo implements Principal {
    private final String name;


    public String getName() {
        return name;
    }

    public UserInfo(String name) {
        this.name = name;
    }
}