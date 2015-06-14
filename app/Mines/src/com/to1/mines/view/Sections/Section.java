package com.to1.mines.view.Sections;

import com.codename1.ui.Container;
import com.codename1.ui.Image;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.to1.mines.Config;
import com.to1.mines.view.ui.AlertContainer;
import com.to1.mines.view.ui.AppMenu;
import com.to1.mines.view.ui.AppTitle;

/**
 * Created by wizzard on 13.06.15.
 */
public class Section extends Container  {
    private final AppTitle title;

    protected final Container container;

    private Image icon;
    private String label;

    public String getText()
    {
        return label;
    }

    public Image getIcon()
    {
        return icon;
    }

    public Section(Layout layout, String icon, String label ) {
        super( new BorderLayout());

        setScrollableX(false);
        setScrollableY(false);

        title = new AppTitle( label );
        addComponent(BorderLayout.NORTH, title);

        container = new Container( layout );
        container.setScrollableX(true);
        container.setScrollableY(false);

        try {
            Image bkg = Image.createImage("/background.png");
            getStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
            getStyle().setBgImage( bkg );
        }
        catch( Exception e)
        {
        }

        addComponent(BorderLayout.CENTER, container);

        this.label = label;
        if( icon != null ) {
            try {
                this.icon = Image.createImage("/" + icon).scaled(Config.TAB_ICON_SIZE, Config.TAB_ICON_SIZE);
            } catch (Exception e) {
                this.icon = null;
            }
        }
    }

    public String show()
    {
        title.show();
        return null;
    }
}
