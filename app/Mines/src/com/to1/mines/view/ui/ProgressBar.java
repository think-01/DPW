package com.to1.mines.view.ui;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.plaf.Style;
import com.to1.mines.view.App;

/**
 * Created by wizzard on 14.06.15.
 */
public class ProgressBar extends InfiniteProgress {
    public static Image Roller;

    public ProgressBar() {
        super();

        setUIID("___my_progress");

        getStyle().setBgTransparency(0);
        getUnselectedStyle().setBgTransparency(0);
        getSelectedStyle().setBgTransparency(0);
        getPressedStyle().setBgTransparency(0);

        if( ProgressBar.Roller == null )
        {
            try {
                ProgressBar.Roller = Image.createImage("/103.png");
            }
            catch( Exception e )
            {

            }
        }
        if( ProgressBar.Roller != null ) setAnimation(ProgressBar.Roller);
    }
}
