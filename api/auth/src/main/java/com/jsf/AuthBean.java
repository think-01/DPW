package com.jsf;

import com.data.managers.Authentication;
import com.data.objects.Auth;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ManagedBean( name="AuthBean" )
@RequestScoped
public class AuthBean implements Serializable {

    private List<Auth> tokens;
    private List<String> apps;

    @EJB
    Authentication auth;

    public List<String> getApps() {
        System.err.println("--------------------");
        try {
            Pattern pattern = Pattern.compile("^\\s*Think01\\s+([A-Za-z0-9\\-_]+):(\\d+):([A-Za-z0-9+/]+[=]*).*$", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
            Matcher matcher = pattern.matcher("HmacSHA1 wizzard1:1432975589421:iyRrRzmc9EwM9/BRwK3CSqk+geI=");
            if (matcher.find()) {
                String login = matcher.group(1).toLowerCase();
                String stamp = matcher.group(2).toLowerCase();
                String hmac = matcher.group(3);

                long tstamp1 = (new Date()).getTime();
                long tstamp2 = Long.parseLong( stamp );

                long diff = tstamp2 - tstamp1;  //miliseconds

                String secret = "827ccb0e-ea8a-306c-8c34-a16891f84e7a";

                Mac mac = Mac.getInstance("HmacSHA1");
                mac.init( new SecretKeySpec( secret.getBytes(), "HmacSHA1") );
                byte[] data = ( stamp+"+"+secret ).getBytes();
                mac.update( data, 0, data.length);
                byte[] rawHmac = mac.doFinal();
                String result = new String(Base64.encodeBase64(rawHmac));

                System.err.println(hmac);
                System.err.println(result);
                System.err.println("=================");
            }
        }
        catch( Exception e )
        {
            System.err.println("*******************");
            System.err.println( e.getMessage() );
        }
        apps = auth.getApps();
        return apps;
    }

    public List<Auth> getTokens() {
        tokens = auth.getTokens();
        return tokens;
    }
}
