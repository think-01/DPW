package com.to1.mines;

import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Tabs;
import com.codename1.ui.layouts.BorderLayout;
import com.to1.mines.view.App;

/**
 * Created by wizzard on 30.05.15.
 */
public class Mines {
   
    private Form current;

    public void init(Object context) {
    }

    public void start() {
        current = new App();

        if(current != null){
            current.show();
            return;
        }
        //new StateMachine("/theme");
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }
    
    public void destroy() {
    }
}
