package com.to1.mines.view.Sections;

import com.codename1.ui.Button;
import com.codename1.ui.layouts.BorderLayout;

/**
 * Created by wizzard on 13.06.15.
 */
public class Maps extends Section {
    public Maps() {
        super(new BorderLayout(),"map.png","Mapa");
        container.addComponent(BorderLayout.SOUTH,  new Button("bbbbbbbbbbb") );
    }
}
