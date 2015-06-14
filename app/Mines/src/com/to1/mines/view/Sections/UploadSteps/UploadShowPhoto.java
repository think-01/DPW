package com.to1.mines.view.Sections.UploadSteps;

import com.codename1.capture.Capture;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.to1.mines.view.Sections.Upload;
import com.to1.mines.view.ui.AlertDialog;
import com.to1.mines.view.ui.BoxButton;

/**
 * Created by wizzard on 14.06.15.
 */
public class UploadShowPhoto extends Container implements ActionListener {

    private final Upload Root;
    private final TextArea Description;
    private final BoxButton Send;
    private final AlertDialog alert;
    private Label PhotoContainer;

    public UploadShowPhoto( Upload p ) {
        super( new BorderLayout() );
        Root = p;

        PhotoContainer = new Label("");

        Description = new TextArea();
        Description.setUIID("Label");

        Send = new BoxButton("Wyslij","picture.png");
        Send.addActionListener( this );

        addComponent(BorderLayout.NORTH, PhotoContainer );
        addComponent( BorderLayout.SOUTH, Send );
        addComponent( BorderLayout.CENTER, Description );

        alert = new AlertDialog();
    }

    public void setPhoto( String file )
    {
        try {
            Image Photo = Image.createImage(file);

            double scale = getWidth() / Photo.getWidth();
            int h = (int) Math.round(Photo.getHeight() * scale);
            int w = (int) Math.round(Photo.getWidth() * scale);
            int hm = (int) Math.round(getHeight()/3);
            Photo = Photo.scaled(w, hm > h ? h : hm );
            PhotoContainer.setIcon(Photo);
        }
        catch( Exception e )
        {
            PhotoContainer.setIcon(null);
        }
    }

    public void actionPerformed(ActionEvent evt)
    {
        String t = Description.getText();
        if( t.length() < 3 )
        {
            alert.show("opis jest zbyt krotki, wpisz co najmniej 10 liter");
            return;
        }
        if( t.length() > 100 )
        {
            alert.show("opis jest zbyt dlugi, wpisz nie wiecej niz 100 liter");
            return;
        }

        Root.ShowSending(t);
    }
}
