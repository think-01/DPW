package com.to1.mines.view.renderers;

import com.codename1.io.NetworkEvent;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.to1.mines.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wizzard on 14.06.15.
 */
public class ModelRecord extends HashMap<String,Object> implements ActionListener {

    public void actionPerformed(ActionEvent evt)
    {
        NetworkEvent n = (NetworkEvent) evt;
        put("thumb", (Image) n.getMetaData());
    }

    public static ModelRecord create( Map<String, String> _m ) {
        ModelRecord m = new ModelRecord();
        for( String k : _m.keySet() )
            m.put( k, _m.get(k) );
        return m;
    }

    public ActionListener getListener( final String key )
    {
        return new ActionListener(){

            public void actionPerformed(ActionEvent evt)
            {
                NetworkEvent n = (NetworkEvent) evt;
                put( key, ( (Image) n.getMetaData() ).scaled(Config.THUMB_SIZE,Config.THUMB_SIZE));
            }

        };
    }
}
