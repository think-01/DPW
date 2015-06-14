package com.to1.mines.view.Sections;

import com.codename1.location.Location;
import com.codename1.location.LocationManager;
import com.codename1.ui.*;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.layouts.BorderLayout;
import com.to1.mines.Config;
import com.to1.mines.api.APIPostRequest;
import com.to1.mines.view.Sections.UploadSteps.UploadCapture;
import com.to1.mines.view.Sections.UploadSteps.UploadShowPhoto;
import com.to1.mines.view.ui.AlertDialog;
import com.to1.mines.view.ui.ProgressBar;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wizzard on 14.06.15.
 */
public class Upload extends Section {
    public static final int PHOTO = 1;
    private final AlertDialog alert;
    private Container current;

    private UploadCapture CaptureFrame;
    private final UploadShowPhoto ShowPhotoFrame;

    private Location location;
    private String FileName;
    private int FileType;

    public Upload() {
        super(new BorderLayout(),"post.png","Dodaj artefakt");

        CaptureFrame = new UploadCapture( this );
        ShowPhotoFrame = new UploadShowPhoto( this );

        current = CaptureFrame;
        container.addComponent( BorderLayout.CENTER, CaptureFrame );

        alert = new AlertDialog();
    }

    @Override
    public String show()
    {
        super.show();

        LocationManager lm = LocationManager.getLocationManager();

        try
        {
            if( lm.isGPSDetectionSupported() && lm.isGPSEnabled() ==  false ) throw new Exception();
            if( lm.getStatus() == LocationManager.OUT_OF_SERVICE ) throw new Exception();
            if( lm.getStatus() == LocationManager.TEMPORARILY_UNAVAILABLE ) {
                ProgressBar prg = new ProgressBar();
                Dialog dlg = prg.showInifiniteBlocking();
                location = lm.getCurrentLocationSync(25000);
                if( lm.getStatus() == LocationManager.AVAILABLE ) throw new Exception();
                dlg.dispose();
            }
            else
                location = lm.getCurrentLocation();
        }
        catch( Exception e )
        {
            return "Wlacz lokalizacje GPS w swoim urzadzeniu.";
        }

        return null;
    }

    public void ShowPhoto(String i) {
        FileName = i;
        FileType = PHOTO;
        container.replace(current, ShowPhotoFrame, CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 200));
        current = ShowPhotoFrame;
        ShowPhotoFrame.setPhoto(i);
    }

    public void ShowSending(String t) {
        APIPostRequest<String> r = new APIPostRequest<String>( "/data/upload" ){
            @Override
            protected void postResponse()
            {
                container.replace(current, CaptureFrame, CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 200));
                current = CaptureFrame;
            }

            @Override
            protected void readResponse( InputStream input ) throws IOException
            {
                response = input.toString();
            }

            @Override
            protected void handleException( Exception e )
            {
                alert.show( e.getMessage() );
            }

            @Override
            protected void handleErrorResponseCode(int code, String message)
            {
                alert.show( message );
            }
        };

        r.addArgument( "lat", String.valueOf( location.getLatitude() ) );
        r.addArgument( "lng", String.valueOf( location.getLongitude() ) );
        r.addArgument( "desc", t );
        r.addArgument( "uid", Config.UID );
        r.addArgument( "type", "jpg" );
        r.addArgument( "layer", "images" );

        try{
            switch( FileType )
            {
                case PHOTO:
                    r.addData( "image", FileName, "image/jpeg" );
                    break;
                default:
                    throw( new Exception() );
            }
            r.Send();
        }
        catch( Exception e )
        {
        }
    }
}
