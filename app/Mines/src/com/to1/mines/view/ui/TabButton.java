package com.to1.mines.view.ui;

import com.codename1.ui.*;

/**
 * Created by wizzard on 13.06.15.
 */
public class TabButton extends RadioButton {
    private int N;

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public TabButton() {
        super();
        init();
    }

    public TabButton(String text) {
        super(text);
        init();
    }

    public void init() {
        setUIID("___tabs");
        setToggle(true);
        setSelected(false);

        //wybrany
        getSelectedStyle().setBgTransparency(255);

        //niewybrany
        getStyle().setBgTransparency(0);
        getUnselectedStyle().setBgTransparency(0);
        getPressedStyle().setBgTransparency(0);

        getPressedStyle().setAlignment( Component.CENTER );
        getSelectedStyle().setAlignment( Component.CENTER );
        getUnselectedStyle().setAlignment( Component.CENTER );
        getStyle().setAlignment( Component.CENTER );

        getPressedStyle().setFgColor( 0xFFFFFF);
        getSelectedStyle().setFgColor( 0xFFFFFF);
        getUnselectedStyle().setFgColor(0xFFFFFF);
        getStyle().setFgColor(0xFFFFFF);

        getPressedStyle().setMargin(0,0,0,0);
        getSelectedStyle().setMargin(0, 0, 0, 0);
        getStyle().setMargin(0, 0, 0, 0);
        getStyle().setMargin(0, 0, 0, 0);

        int p = 10;
        getPressedStyle().setPadding(p, p, p, p);
        getSelectedStyle().setPadding(p, p, p, p);
        getStyle().setPadding(p, p, p, p);
        getStyle().setPadding(p, p, p, p);
    }

    public void paintBackground(Graphics g) {
        if( isSelected() ) {
            g.fillLinearGradient(0x880000,0xbb0000,getX(), getY(), getWidth() + 1, getHeight() + 1,false);
        }
        else
            super.paintBackground(g);
    }
}
