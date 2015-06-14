package com.to1.mines.view.Sections.UploadSteps;

import com.codename1.capture.Capture;
import com.codename1.ui.Container;
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
public class UploadCapture extends Container implements ActionListener {
    private final BoxButton GetPhoto;
    private final BoxButton GetSound;
    private final BoxButton GetMovie;

    private final int PHOTO_BUTTON = 1;
    private final int SOUND_BUTTON = 2;
    private final int MOVIE_BUTTON = 3;
    private final AlertDialog alert;
    private final Upload Root;

    public UploadCapture( Upload p ) {
        super( new BoxLayout(BoxLayout.Y_AXIS) );
        Root = p;

        GetPhoto = new BoxButton("zrob zdjecie","picture.png");
        GetSound = new BoxButton("nagraj dzwiek","voice.png");
        GetMovie = new BoxButton("nagraj film","movie.png");

        addComponent(GetPhoto);
        addComponent(GetSound);
        addComponent(GetMovie);

        GetPhoto.addActionListener( this );
        GetSound.addActionListener( this );
        GetMovie.addActionListener( this );

        GetPhoto.ID = PHOTO_BUTTON;
        GetSound.ID = SOUND_BUTTON;
        GetMovie.ID = MOVIE_BUTTON;

        getStyle().setPadding(20,20,20,20);

        alert = new AlertDialog();
    }

    public void actionPerformed(ActionEvent evt)
    {
        BoxButton b = ( BoxButton ) evt.getComponent();
        switch( b.ID )
        {
            case PHOTO_BUTTON:
                String i = Capture.capturePhoto();
                if( i!= null ) Root.ShowPhoto( i );
                //OutputStream save = FileSystemStorage.getInstance().openOutputStream("test.jpg");
                //ImageIO.getImageIO().save( i, save,  ImageIO.FORMAT_JPEG, 0.85f);
                break;
            case SOUND_BUTTON:
                alert.show("akcja nieobslugiwana");
                break;
            case MOVIE_BUTTON:
                alert.show("akcja nieobslugiwana");
                break;
        }
    }
}
