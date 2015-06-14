package com.to1.mines.view.ui;

import com.codename1.ui.*;
import com.codename1.ui.Component;
import com.codename1.ui.Graphics;
import com.to1.mines.Config;

/**
 * Created by wizzard on 13.06.15.
 */
public class SimpleButton extends com.codename1.ui.Button {

    public int ID;

    protected final int COLOR_FROM = 0x880000;
    protected final int COLOR_TO = 0xbb0000;
    protected final int COLOR_LINE = 0xbb0000;

    public SimpleButton() {
        super();
        init();
    }

    public SimpleButton( String label ) {
        super( label );
        init();
    }

    public SimpleButton( String label, String icon ) {
        super();
        if( icon != null )
        {
            try {
                setIcon(Image.createImage("/" + icon).scaled(Config.BUTTON_ICON_SIZE, Config.BUTTON_ICON_SIZE));
            }
            catch( Exception e) {
                setText( label );
            }
        }
        init();
    }

    public SimpleButton( Image icon ) {
        super();
        setIcon( icon );
        init();
    }

    public SimpleButton( String label, Image icon ) {
        super( label );
        setIcon( icon );
        init();
    }

    public void init() {
        setUIID("___simple");

        getPressedStyle().setFgColor( 0xFFFFFF);
        getSelectedStyle().setFgColor( 0xFFFFFF);
        getUnselectedStyle().setFgColor(0xFFFFFF);
        getStyle().setFgColor(0xFFFFFF);

        int m =5;
        getPressedStyle().setMargin(m,m,m,m);
        getSelectedStyle().setMargin(m,m,m,m);
        getStyle().setMargin(m,m,m,m);
        getStyle().setMargin(m,m,m,m);

        int p = 10;
        getPressedStyle().setPadding(p, p, p, p);
        getSelectedStyle().setPadding(p, p, p, p);
        getStyle().setPadding(p, p, p, p);
        getStyle().setPadding(p, p, p, p);

        getPressedStyle().setAlignment( Component.CENTER );
        getSelectedStyle().setAlignment( Component.CENTER );
        getUnselectedStyle().setAlignment( Component.CENTER );
        getStyle().setAlignment( Component.CENTER );
    }

    public void paintBackground(Graphics g) {
        g.setColor( COLOR_LINE );
        g.fillLinearGradient( COLOR_TO, COLOR_FROM, getX(), getY(), getWidth() + 1, getHeight() + 1,false);
        g.drawRect(getX(), getY(), getWidth() - 1, getHeight() - 1, 1);
    }
}
