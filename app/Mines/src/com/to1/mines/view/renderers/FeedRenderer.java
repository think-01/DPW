package com.to1.mines.view.renderers;

import com.codename1.io.services.ImageDownloadService;
import com.codename1.ui.*;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.ListCellRenderer;
import com.codename1.ui.plaf.Border;

import java.util.Collection;
import java.util.Map;

/**
 * Created by wizzard on 11.06.15.
 */
public class FeedRenderer extends Container implements ListCellRenderer {
    private Button more = new Button("");
    private Label thumb = new Label("");
    private TextArea description = new TextArea();
    private Label author = new Label("");
    private Label stats = new Label("");
    private Label distance = new Label("");
    private Container cnt1;

    private Boolean loading = false;

    public FeedRenderer() {
        setLayout( new BorderLayout() );

        //addComponent( BorderLayout.NORTH, more );

        //addComponent( BorderLayout.WEST, thumb );

        cnt1 = new Container( new BorderLayout() );
        cnt1.addComponent(BorderLayout.NORTH, description );

        Container cnt2 = new Container( new BoxLayout(BoxLayout.X_AXIS) );
        cnt2.addComponent( author );
        cnt2.addComponent( stats );
        cnt2.addComponent( distance );
        cnt1.addComponent( BorderLayout.SOUTH, cnt2 );

        //addComponent(BorderLayout.CENTER, cnt1);

        thumb.getStyle().setBgTransparency(0);
        description.getStyle().setBgTransparency(0);
        more.getStyle().setBgTransparency(0);
        author.getStyle().setBgTransparency(0);
        stats.getStyle().setBgTransparency(0);
        distance.getStyle().setBgTransparency(0);

        description.getStyle().setBorder(Border.createEmpty());
        description.setColumns(-1);
        description.setRows(3);

        Font f = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL );

        description.getStyle().setFont(f);
        author.getStyle().setFont(f);
        stats.getStyle().setFont(f);
        distance.getStyle().setFont(f);

        f = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_LARGE );
        more.getStyle().setFont(f);

        author.getStyle().setFgColor(0xff0000);
        stats.getStyle().setFgColor(0xff0000);
        distance.getStyle().setFgColor(0xff0000);
        description.getStyle().setFgColor(0xffffff);

        more.getStyle().setFgColor(0x000000);
        more.getStyle().setAlignment( Component.CENTER );
        more.getStyle().setBorder( Border.createEmpty());
        more.getStyle().setOpacity(150);

        cnt1.getStyle().setBgTransparency(0);
        cnt2.getStyle().setBgTransparency(0);
        getStyle().setBgTransparency(100);
        getStyle().setBgColor( 0x000000 );

        cnt1.getStyle().setMargin(0,0,0,0);
        cnt1.getStyle().setMargin(0,0,0,0);
        getStyle().setMargin(0,0,0,0);
    }

    public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {
        Map<String, Object> artifact = (Map<String, Object>) value;

        if (value == null) return this;

        if( artifact.get("page") == null ) {
            String sex = (String) artifact.get("sex");
            if (sex == null) sex = "m";

            //author.setIcon(sex.toLowerCase().equals("m") ? ProjectResources.ManPNG : ProjectResources.WomanPNG);
            //stats.setIcon(ProjectResources.RankPNG);
            //distance.setIcon(ProjectResources.PlacePNG);

            author.setText((String) artifact.get("author"));
            stats.setText((String) artifact.get("stats"));
            distance.setText((String) artifact.get("distance"));
            description.setText((String) artifact.get("description"));
            more.setText("");

            if (artifact.get("thumb") != null)
                thumb.setIcon((Image) artifact.get("thumb"));

            if( contains(more ) ) removeComponent(more);
            if( !contains(thumb ) ) addComponent( BorderLayout.WEST, thumb );
            if( !contains(cnt1 ) ) addComponent(BorderLayout.CENTER, cnt1);
        }
        else
        {
            more.setText("POKAŻ WIĘCEJ");
            author.setText("");
            stats.setText("");
            distance.setText("");
            description.setText("");
            thumb.setIcon(null);

            if( !contains(more ) ) addComponent(BorderLayout.CENTER, more);
            if( contains(thumb ) ) removeComponent(thumb);
            if( contains(cnt1 ) ) removeComponent(cnt1);
        }
        return this;
    }

    public Component getListFocusComponent(List list) {
        return null;
    }
}
