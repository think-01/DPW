package com.services;

import com.Config;
import com.graph.Artifact;
import com.graph.GraphFactory;
import com.pojo.request.FileUploadForm;
import com.pojo.response.FeedRecord;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.neo4j.gis.spatial.SpatialDatabaseService;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.api.exceptions.Status;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

/**
 * Created by wizzard on 08.06.15.
 */

@Path("/data/")
public class Data {
    @EJB
    GraphFactory graph;

    @Context
    private SecurityContext securityContext;

    @RolesAllowed("USER")
    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public String test()
    {
        return "test";
    }

    @RolesAllowed("MINES")
    @GET
    @Path("/hello")
    @Produces(MediaType.APPLICATION_JSON)
    public String hello()
    {
        return UUID.randomUUID().toString();
    }

    @RolesAllowed("MINES")
    @GET
    @Path("/t")
    @Produces(MediaType.APPLICATION_JSON)
    public String t()
    {
        return "OK";
    }

    @RolesAllowed("MINES")
    //@PermitAll
    @POST
    @Path("/feed/{size}/{from}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<FeedRecord> fetch(
        /*@FormParam("xxx") String xxx*/ /*Feed feed*/
        @PathParam("size") Integer size,
        @PathParam("from") Integer page
    )
    {

        List<String> murale = new Vector<String>();

        murale.add( "http://artelocal.eu/wp-content/uploads/2014/01/Streetart-Alice-Suspended-Mural-2014-Berlin-11.jpg" );
        murale.add( "http://riotsound.com/Graffiti/art-gallery/albums/Bieeto-Brasil/Street_Art_Mural_Brazil.sized.jpg" );
        murale.add( "http://40.media.tumblr.com/d85710394555d7c103028843debb9b07/tumblr_mk2ctuvQpf1r6wmavo1_1280.jpg" );
        murale.add( "http://www.brooklynstreetart.com/theblog/wp-content/uploads/2013/06/brooklyn-street-art-fin-angelina-mural-festival-daniel-esteban-rojas-montreal-06-13-web-2.jpg" );
        murale.add( "http://static.guim.co.uk/sys-images/Guardian/About/General/2012/2/24/1330108547463/A-mural-by-the-Russian-st-007.jpg" );
        murale.add( "http://streetartnyc.org/wp-content/uploads/2013/04/Shery-and-the-Yok-street-art-mural-at-the-Bushwick-Collective.jpg" );
        murale.add( "http://streetartnyc.org/wp-content/uploads/2012/07/Sonni-street-art-mural-in-Brooklyn-NYC.jpg" );
        murale.add( "http://untappedcities.com/wp-content/uploads/2013/07/Chris-Dyer_Montreal-MURAL-festival_Untapped-Cities_Lea-Plourde-Archer.jpg" );
        murale.add( "http://streetartnyc.org/wp-content/uploads/2013/10/Swoon-and-Groundswell-street-art-mural-Bpwery-NYC.jpg" );
        murale.add( "http://api.ning.com/files/kV4MbYiv7oQ5c525vHxmkeLJjzFdVEK1cZsfkW5VgGk22Rk50MFGJu*ukhZbMnzEmxET4XPTEJy1flWXv9zacUSvGWvOQtUS/1082074942.jpeg" );
        murale.add( "http://www.brooklynstreetart.com/theblog/wp-content/uploads/2013/06/brooklyn-street-art-le-bonnard-mural-festival-daniel-esteban-rojas-montreal-06-13-web.jpg" );
        murale.add( "http://www.graffitiday.com/wp-content/uploads/2010/08/toronto-street-art-mural.jpg" );

        Collection<FeedRecord> ret = new HashSet<FeedRecord>();

        for( int i = 0; i<size; i++ )
        {
            FeedRecord r = new FeedRecord();
            r.title = "Element #"+String.valueOf(i);
            r.description = UUID.randomUUID().toString();
            r.url = murale.get( Double.valueOf( Math.floor(Math.random()*murale.size()) ).intValue() );
            ret.add(r);
        }

        return ret;
    }

    @RolesAllowed("MINES")
    @POST
    @Path("/upload")
    @Consumes("multipart/form-data")
    public Response upload(
            @MultipartForm FileUploadForm form
    )
    {
        String ID = UUID.randomUUID().toString();
        byte[] image = form.getImage();
        try {
            if (image != null) {
                File file = new File(Config.UPLOAD_PATH + "/" + ID + "." + form.getType() );
                if (!file.exists()) file.createNewFile();
                FileOutputStream fop = new FileOutputStream(file);
                fop.write(image);
                fop.flush();
                fop.close();

                //BufferedImage img = ImageIO.read( Config.UPLOAD_PATH + "/" + ID + "." + form.getType() );
                //BufferedImage scaledImg = Scalr.resize(img, 150);
            }
        }
        catch( Exception e )
        {
            return Response.status(404).build();
        }

        GraphDatabaseService graphDb = graph.getGraphDB();
        SpatialDatabaseService db = graph.getSpatialGraphDB();

        try ( Transaction tx = graphDb.beginTx() ) {
            HashMap<String, String> props = new HashMap<String, String>();
            props.put( "desc", form.getDesc());
            props.put( "file", ID + "." + form.getType() );

            Artifact node = new Artifact(
                    db,
                    Double.valueOf( form.getLat() ),
                    Double.valueOf( form.getLng() ),
                    form.getLayer(),
                    props
            );
            tx.success();
        }

        return Response.status(200).entity("OK").build();
    }
}
