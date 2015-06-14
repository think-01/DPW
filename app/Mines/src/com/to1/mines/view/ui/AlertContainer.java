package com.to1.mines.view.ui;

import com.codename1.ui.*;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;

/**
 * Created by wizzard on 14.06.15.
 */
public class AlertContainer extends Container {

    private TextArea Body;

    public AlertContainer() {
        super( new BorderLayout() );

        try {
            Image bkg = Image.createImage("/background.png");
            getStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
            getStyle().setBgImage( bkg );
        }
        catch( Exception e)
        {
        }

        Body = new TextArea();
        Body.setUIID("___alert_text");
        Body.setRows(-1);
        Body.setColumns(-1);
        Body.setGrowByContent(true);
        Body.setDefaultValign(Component.BOTTOM);
        Body.setVerticalAlignment(Component.BOTTOM);

        Font f = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE );

        Body.getStyle().setFont(f);
        Body.getStyle().setBgTransparency(0);
        Body.getStyle().setBorder(Border.createEmpty());
        Body.getStyle().setAlignment(Component.CENTER);
        Body.getStyle().setFgColor(0xffcccc);

        Body.getUnselectedStyle().setFont(f);
        Body.getUnselectedStyle().setBgTransparency(0);
        Body.getUnselectedStyle().setBorder(Border.createEmpty());
        Body.getUnselectedStyle().setAlignment(Component.CENTER);
        Body.getUnselectedStyle().setFgColor(0xffcccc);

        Body.getSelectedStyle().setFont(f);
        Body.getSelectedStyle().setBgTransparency(0);
        Body.getSelectedStyle().setBorder(Border.createEmpty());
        Body.getSelectedStyle().setAlignment(Component.CENTER);
        Body.getSelectedStyle().setFgColor(0xffcccc);

        Body.setEditable(false);

        getStyle().setPadding( 20,20,20,20 );

        setScrollableX(false);
        setScrollableY(false);

        addComponent(BorderLayout.CENTER, Body);
    }

    public void setText( String s )
    {
        Body.setText( s );
    }
}
