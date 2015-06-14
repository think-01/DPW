package com.to1.mines.api;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.to1.mines.Config;
import com.to1.mines.view.ui.ProgressBar;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * Created by wizzard on 14.06.15.
 */
public class APIGetRequest<T> extends ConnectionRequest {
    protected T response;

    public APIGetRequest( String url ) {
        super();
        setUrl(Config.JBoss + url);

        String stamp = String.valueOf((new Date()).getTime());
        Digest digest = new SHA1Digest();
        HMac hmac = new HMac(digest);
        hmac.init( new KeyParameter( Config.SECRET.getBytes() ) );
        byte[] data = ( stamp+"+"+Config.SECRET ).getBytes();
        hmac.update( data, 0, data.length);
        byte[]  resBuf = new byte[digest.getDigestSize()];
        hmac.doFinal(resBuf, 0);
        String hash = new String(Base64.encode(resBuf));

        addRequestHeader("Authorization", "Think01 " + Config.ID + ":" + stamp + ":" + hash);
    }

    @Override
    protected void handleIOException(IOException err) {
        System.err.println(err.getMessage());
    }

    @Override
    protected void handleErrorResponseCode(int code, String message) {
        System.err.println("err "+code+" "+message);
    }

    @Override
    protected void readResponse( InputStream input ) throws IOException
    {
        JSONParser p = new JSONParser();
        response = (T) p.parseJSON( new InputStreamReader(input) ).get("root");
    }

    public void Send()
    {
        setPost(false);
        ProgressBar prg = new ProgressBar();
        Dialog dlg = prg.showInifiniteBlocking();
        setDisposeOnCompletion( dlg );
        NetworkManager.getInstance().addToQueue(this);
    }
}
