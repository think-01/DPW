package com.to1.mines.view.ui;

import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextArea;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

/**
 * Created by wizzard on 14.06.15.
 */
public class AlertDialog extends Dialog implements ActionListener {

    private final TextArea popupBody;
    private final SimpleButton Close;

    public AlertDialog() {
        super();

        setLayout( new BoxLayout( BoxLayout.Y_AXIS ));

        getStyle().setPadding(5,5,5,5);

        popupBody = new TextArea("This is the body of the popup", 3, 10);
        popupBody.setUIID("Label");
        popupBody.setEditable(false);
        popupBody.getStyle().setAlignment(Component.CENTER);
        popupBody.getUnselectedStyle().setAlignment(Component.CENTER);
        popupBody.getSelectedStyle().setAlignment(Component.CENTER);
        popupBody.getPressedStyle().setAlignment(Component.CENTER);

        addComponent(popupBody);
        setTransitionInAnimator(CommonTransitions.createEmpty());
        setTransitionOutAnimator(CommonTransitions.createFade(300));

        Close = new SimpleButton( "OK" );
        Close.getStyle().setMargin(15, 15, 15, 15);
        Close.getUnselectedStyle().setMargin(15, 15, 15, 15);
        Close.getSelectedStyle().setMargin(15, 15, 15, 15);
        Close.getPressedStyle().setMargin(15, 15, 15, 15);

        Close.addActionListener( this );
        addComponent(Close);

    }

    public void show( String t )
    {
        popupBody.setText( t );
        super.showPacked(BorderLayout.CENTER, true);
    }

    public void actionPerformed(ActionEvent evt)
    {
        super.dispose();
    }
}
