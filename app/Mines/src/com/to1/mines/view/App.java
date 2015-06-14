package com.to1.mines.view;

import com.codename1.io.Storage;
import com.codename1.io.Util;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.mig.PlatformDefaults;
import com.codename1.ui.plaf.Style;
import com.to1.mines.Config;
import com.to1.mines.api.APIGetRequest;
import com.to1.mines.api.APIPostRequest;
import com.to1.mines.view.Sections.*;
import com.to1.mines.view.ui.AppMenu;
import sun.misc.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wizzard on 13.06.15.
 */
public class App extends Form {

    private AppMenu tabs;

    public App() {
        Object uid = Storage.getInstance().readObject("___city_miners_uuid");

        System.err.println( uid );

        if( uid == null )
        {
            new APIGetRequest<String>( "/data/hello" ){
                @Override
                protected void readResponse( InputStream input ) throws IOException
                {
                    Config.UID = Util.readToString( input );
                    Storage.getInstance().writeObject( "___city_miners_uuid", Config.UID );
                }
            }.Send();
        }
        else Config.UID = uid.toString();

        Config.DPI = PlatformDefaults.getDefaultDPI();

        Config.TAB_ICON_SIZE = 18;
        if( Config.DPI > 100 ) Config.TAB_ICON_SIZE = 28;
        if( Config.DPI > 200 ) Config.TAB_ICON_SIZE = 38;
        if( Config.DPI > 300 ) Config.TAB_ICON_SIZE = 48;

        Config.BUTTON_ICON_SIZE = 10;
        if( Config.DPI > 100 ) Config.BUTTON_ICON_SIZE = 20;
        if( Config.DPI > 200 ) Config.BUTTON_ICON_SIZE = 30;
        if( Config.DPI > 300 ) Config.BUTTON_ICON_SIZE = 40;

        Config.THUMB_SIZE = 60;
        if( Config.DPI > 100 ) Config.THUMB_SIZE = 90;
        if( Config.DPI > 200 ) Config.THUMB_SIZE = 120;
        if( Config.DPI > 300 ) Config.THUMB_SIZE = 150;

        setLayout(new BorderLayout());

        getStyle().setMargin(0, 0, 0, 0);
        getStyle().setPadding(0, 0, 0, 0);
        getStyle().setBackgroundGradientEndColor(0x880000);
        getStyle().setBackgroundGradientStartColor(0xcc0000);
        getStyle().setBackgroundGradientRelativeX((float) 0.4);
        getStyle().setBackgroundGradientRelativeY((float) 0.5);
        getStyle().setBackgroundGradientRelativeSize((float) 1.5);
        getStyle().setBackgroundType(Style.BACKGROUND_GRADIENT_RADIAL);

        setScrollableX(false);
        setScrollableY(false);

        Section t = new Home();
        getContentPane().addComponent(BorderLayout.CENTER, t );

        tabs = new AppMenu(6);
        getContentPane().addComponent(BorderLayout.SOUTH, tabs );

        tabs.add( t );
        tabs.add( new Feed() );
        tabs.add( new Maps() );
        tabs.add( new Users() );
        tabs.add( new Paths() );
        tabs.add( new Upload() );
    }
}
