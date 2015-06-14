package com.to1.mines.view.ui;

import com.codename1.ui.Component;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.to1.mines.Config;

/**
 * Created by wizzard on 14.06.15.
 */
public class BoxButton extends SimpleButton {

    public BoxButton( String label, String icon ) {
        super( "", icon );
        setText(" " + label.toUpperCase());
    }

    @Override
    public void init() {
        setUIID("___box");

        getPressedStyle().setFgColor( 0xFFFFFF);
        getSelectedStyle().setFgColor( 0xFFFFFF);
        getUnselectedStyle().setFgColor(0xFFFFFF);
        getStyle().setFgColor(0xFFFFFF);

        int m =5;
        getPressedStyle().setMargin(m,m,m,m);
        getSelectedStyle().setMargin(m,m,m,m);
        getStyle().setMargin(m,m,m,m);
        getStyle().setMargin(m,m,m,m);

        int p = 15;
        getPressedStyle().setPadding(p, p, p, p);
        getSelectedStyle().setPadding(p, p, p, p);
        getStyle().setPadding(p, p, p, p);
        getStyle().setPadding(p, p, p, p);

        getStyle().setBgTransparency(0);
        getSelectedStyle().setBgTransparency(0);
        getPressedStyle().setBgTransparency(0);
        getUnselectedStyle().setBgTransparency(0);

        getStyle().setBorder(Border.createEmpty());
        getSelectedStyle().setBorder(Border.createEmpty());
        getPressedStyle().setBorder(Border.createEmpty());
        getUnselectedStyle().setBorder(Border.createEmpty());

        getStyle().setBackgroundType(Style.BACKGROUND_NONE);
        getSelectedStyle().setBackgroundType(Style.BACKGROUND_NONE);
        getPressedStyle().setBackgroundType(Style.BACKGROUND_NONE);
        getUnselectedStyle().setBackgroundType(Style.BACKGROUND_NONE);

        getStyle().setBgColor( COLOR_FROM );
        getSelectedStyle().setBgColor( COLOR_FROM );
        getPressedStyle().setBgColor( COLOR_FROM );
        getUnselectedStyle().setBgColor( COLOR_FROM );

        getPressedStyle().setAlignment( Component.CENTER );
        getSelectedStyle().setAlignment( Component.CENTER );
        getUnselectedStyle().setAlignment( Component.CENTER );
        getStyle().setAlignment( Component.CENTER );
    }

    @Override
    public void paintBackground(Graphics g) {
        g.setColor( 0 );
        g.fillRect(getX(), getY(), getWidth() , getHeight() , (byte) 50);

        g.setColor( COLOR_FROM );
        g.drawRect(getX(), getY(), getWidth()-1 , getHeight()-1 );
    }
}
