package com.to1.mines.view.Sections;

import com.codename1.io.services.ImageDownloadService;
import com.codename1.ui.List;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListModel;
import com.to1.mines.api.APIPostRequest;
import com.to1.mines.view.renderers.FeedRenderer;
import com.to1.mines.view.renderers.ModelRecord;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wizzard on 13.06.15.
 */
public class Feed extends Section {
    private final List list;

    public Feed() {
        super(new BoxLayout(BoxLayout.Y_AXIS), "search.png", "Wykopalisko" );

        list = new List();

        list.setRenderer( new FeedRenderer() );
        list.setModel( new DefaultListModel() );
        container.addComponent( list );

        ImageDownloadService.setFastScale(true);
    }

    @Override
    public String show() {
        super.show();
        fetchMore(10);
        return null;
    }

    private void fetchMore( final int pageSize ) {
        final int pageNum = Double.valueOf( Math.floor( list.size() / (pageSize + 1) )+1 ).intValue();

        APIPostRequest<Collection<Map<String,String>>> r = new APIPostRequest<Collection<Map<String,String>>>( "/data/feed/" + pageSize + "/" + pageNum ){
            @Override
            protected void postResponse()
            {
                for( Map<String, String> q : response )
                {
                    ModelRecord r = ModelRecord.create(q);
                    r.put("distance","14.3km");
                    r.put("stats","124/1673");
                    r.put("author","wizzard");
                    r.put("description","Ala ma kota, a kot ma Alę, ale Ala nie boi się wcale kota - bo to idiota.");

                    ImageDownloadService.createImageToStorage(
                            r.get("url").toString(),
                            r.getListener("thumb"),
                            "thumb" + (list.size() - 1),
                            true
                    );

                    list.addItem(r);
                }

                Map<String,String> r = new HashMap<String, String>();
                r.put("page", String.valueOf(pageNum) );
                r.put("size", String.valueOf(pageSize) );
                list.addItem(r);
                revalidate();
            }
        };

        r.Send();
    }
}
