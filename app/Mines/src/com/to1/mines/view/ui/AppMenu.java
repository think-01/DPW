package com.to1.mines.view.ui;

import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.table.TableLayout;
import com.to1.mines.view.App;
import com.to1.mines.view.Sections.Section;

import java.util.*;

/**
 * Created by wizzard on 13.06.15.
 */
public class AppMenu extends Container implements ActionListener {
    public static AppMenu instance;
    private AlertContainer alert;

    private int Index;

    private List<TabButton> buttons;
    private List<Section> sections;
    private Vector<Integer> history;

    private TableLayout.Constraint constraint;
    ButtonGroup bg = new ButtonGroup();
    private int HomeIndex;

    public AppMenu( double cols ) {
        super();

        instance = this;

        buttons = new Vector<TabButton>();
        sections = new Vector<Section>();
        history = new Vector<Integer>();

        Layout layout = new TableLayout( 1, (int) cols );
        setLayout( layout );

        constraint = new TableLayout.Constraint();
        constraint.setWidthPercentage( (int) Math.floor(100/cols));

        alert = new AlertContainer();

        getStyle().setMargin(0,0,0,0);
        getStyle().setPadding(0,0,0,0);
    }

    public void paintBackground(Graphics g) {
        int h = ( int ) Math.ceil( ( getHeight() )/2 );
        g.setColor( 0x161616 );
        g.fillRect( getX(), getY(), getWidth() + 1, h );
        g.setColor( 0 );
        g.fillRect( getX(), getY()+h, getWidth() + 1, h+1 );
    }

    public void add( Section tab )
    {
        TabButton b = new TabButton();
        b.setN( buttons.size() );
        buttons.add( b );
        sections.add( tab );

        if( tab.getIcon() != null ) b.setIcon( tab.getIcon() );
        else if( tab.getText() != null ) b.setText( tab.getText() );

        if( getParent().contains( tab ) )
        {
            setIndex( buttons.size()-1 );
            HomeIndex = Index;
        }

        b.addActionListener( this );
        bg.add( b );
        addComponent( constraint, b );
        revalidate();
    }

    public int getIndex() {
        Container parent = getParent();
        Section current = sections.get(Index);
        return parent.contains(current) ? Index : -1;
    }

    public void setIndex(int index) {
        uodateIndex( index );

        if( history.size()==0 || history.get(history.size()-1) != Index ) {
            history.add(Index);
            while( history.size() > 40 ) history.removeElementAt(0);
        }

        Index = index;
    }

    public void uodateIndex(int index) {
        Container parent = getParent();
        Container current = parent.contains(alert) ? alert : sections.get(Index);
        Container next = sections.get(index);

        String msg = ( ( Section ) next ).show();
        if( msg != null )
        {
            alert.setText( msg );
            next = alert;
        }

        if( next != current )
            parent.replace(current, next, CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, index < Index, 200));

        for( int i = 0; i<buttons.size(); i++ )
            buttons.get(i).setSelected( index == i );
    }

    public void Back()
    {
        if( history.size() > 0 )
        {
            int i = history.get( history.size()-1 );
            history.removeElementAt( history.size()-1 );
            uodateIndex(i);
            Index = i;
        }
    }

    public void Home()
    {
        setIndex( HomeIndex );
    }

    public Boolean hasHistory()
    {
        return history.size() > 0;
    }

    public void actionPerformed(ActionEvent evt)
    {
        TabButton b = ( TabButton ) evt.getComponent();
        setIndex( b.getN() );
        evt.consume();
        revalidate();
    }
}
