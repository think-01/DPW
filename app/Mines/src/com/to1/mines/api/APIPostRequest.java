package com.to1.mines.api;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.ImageIO;
import com.to1.mines.view.Sections.Upload;
import com.to1.mines.view.ui.ProgressBar;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Base64;
import com.to1.mines.Config;

import java.io.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by wizzard on 10.06.15.
 */
public class APIPostRequest<T> extends MultipartRequest {
    protected T response;

    public APIPostRequest(String url) {
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
        setPost(true);
        ProgressBar prg = new ProgressBar();
        Dialog dlg = prg.showInifiniteBlocking();
        setDisposeOnCompletion(dlg);
        NetworkManager.getInstance().addToQueue(this);
    }
}
