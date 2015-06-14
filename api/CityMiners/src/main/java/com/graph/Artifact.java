package com.graph;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import org.neo4j.gis.spatial.EditableLayer;
import org.neo4j.gis.spatial.EditableLayerImpl;
import org.neo4j.gis.spatial.SpatialDatabaseRecord;
import org.neo4j.gis.spatial.SpatialDatabaseService;
import org.neo4j.gis.spatial.encoders.SimplePointEncoder;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import java.util.Collection;
import java.util.Map;

/**
 * Created by wizzard on 07.06.15.
 */
public class Artifact {

    private SpatialDatabaseService db;
    private EditableLayer layer;
    private Node node;
    private Coordinate coordinate;
    private Geometry geometry;

    public Artifact(SpatialDatabaseService db, Node n, String layer ) {
        this.db = db;
        this.layer = (EditableLayer) db.getOrCreateLayer(
                layer,
                SimplePointEncoder.class,
                EditableLayerImpl.class
        );

        node = n;
    }

    public Artifact(SpatialDatabaseService db, Double lat, Double lng, String layer) {
        this.db = db;
        this.layer = (EditableLayer) db.getOrCreateLayer(
                layer,
                SimplePointEncoder.class,
                EditableLayerImpl.class
        );

        node = this.layer.add(
                    this.layer
                            .getGeometryFactory()
                            .createPoint(
                                    new Coordinate(lng, lat)
                            )
                ).getGeomNode();
    }

    public Artifact(SpatialDatabaseService db, Double lat, Double lng, String layer, Map<String,String> props ) {
        this.db = db;
        this.layer = (EditableLayer) db.getOrCreateLayer(
                layer,
                SimplePointEncoder.class,
                EditableLayerImpl.class
        );

        SpatialDatabaseRecord rec = this.layer.add(
                this.layer
                        .getGeometryFactory()
                        .createPoint(
                                new Coordinate(lng, lat)
                        )
        );

        for( String i : props.keySet() )
            rec.setProperty( i, props.get(i) );

        node = rec.getGeomNode();
    }

    public Coordinate getCoordinate() {
        try {
            Double lat = ( Double ) node.getProperty("latitude");
            Double lng = ( Double ) node.getProperty("longitude");
            this.coordinate = new Coordinate( lng, lat );
        }
        catch( Exception e ){}
        return coordinate;
    }

    public Long getId()
    {
        return node.getId();
    }

    public Node getNode() {
        return node;
    }

    public Integer getLikes() {
        return node.hasProperty( Property.LIKES ) ? ( Integer ) node.getProperty( Property.LIKES ) : 0;
    }

    public void setLikes( Integer l ) {
        node.setProperty( Property.LIKES, l );
    }

    public void Like(Artifact a) {
        node.createRelationshipTo( a.getNode(), Link.LIKE );
        a.setLikes( a.getLikes()+1 );
    }

    public void UnLike(Artifact a) {
        Node n = a.getNode();
        ( ( Collection<Relationship> ) node.getRelationships(Direction.OUTGOING, Link.LIKE) )
                .stream()
                .filter( r -> r.getEndNode() == n )
                .findFirst()
                .get()
                .delete();
        a.setLikes( a.getLikes()+1 );
    }
}

