package com.to1.mines.view.ui;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.table.TableLayout;

/**
 * Created by wizzard on 13.06.15.
 */
public class AppTitle extends Container implements ActionListener {

    private SimpleButton Back;
    private SimpleButton Home;
    private Label Title;

    public AppTitle( String label ) {
        super();

        setScrollableX(false);
        setScrollableY(false);

        Layout layout = new TableLayout( 1, 3 );
        setLayout( new BorderLayout() );

        Back = new SimpleButton("Powrót", "back.png");
        Home = new SimpleButton("Home", "home.png");
        Title = new Label( label );
        Title.getStyle().setBgTransparency(0);
        Title.getStyle().setAlignment(Component.CENTER);
        Title.getStyle().setFgColor( 0xffffff );
        Font f = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE );
        Title.getStyle().setFont( f );
        addComponent(BorderLayout.WEST, Back);

        addComponent(BorderLayout.CENTER, Title);
        addComponent( BorderLayout.EAST, Home );

        getStyle().setMargin(5,5,5,5);

        Home.addActionListener( this );
        Back.addActionListener( this );

        revalidate();
    }

    public void actionPerformed(ActionEvent evt)
    {
        SimpleButton b = ( SimpleButton ) evt.getComponent();
        if( b.equals( Home ) ) AppMenu.instance.Home();
        else if( b.equals( Back ) ) AppMenu.instance.Back();
        evt.consume();
        revalidate();
    }

    public void show()
    {
        if( AppMenu.instance.hasHistory() ){
            if( !contains( Back ) ){
                addComponent(BorderLayout.WEST, Back);
                Title.getStyle().setPadding(0,0,0,0);
            }
        }
        else {
            if( contains( Back ) ){
                Title.getStyle().setPadding(0,0,Back.getWidth(),0);
                removeComponent( Back );
            }
        }
    }
}
