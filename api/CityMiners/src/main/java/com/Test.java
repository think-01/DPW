package com;

import com.graph.Artifact;

import org.neo4j.gis.spatial.*;
import org.neo4j.gis.spatial.encoders.SimplePointEncoder;
import org.neo4j.gis.spatial.pipes.GeoPipeFlow;
import org.neo4j.gis.spatial.pipes.GeoPipeline;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by wizzard on 06.06.15.
 */
@Singleton
@Startup
public class Test {
    private GraphDatabaseService graphDb;

    @PostConstruct
    public void init() {
        /*
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( "D:/dev/a6" );
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });

        Long id;

        SpatialDatabaseService db = new SpatialDatabaseService(graphDb);
        try ( Transaction tx = graphDb.beginTx() ) {
            Artifact jolka = new Artifact(db, 52.361979, 21.228282, "users");
            Artifact janusz = new Artifact(db, 52.361979, 21.228282, "users");
            janusz.Like( jolka );

            id = janusz.getId();
            tx.success();
        }

        try ( Transaction tx = graphDb.beginTx() ) {
            Node n = graphDb.getNodeById(id);
            Iterator<String> i = n.getPropertyKeys().iterator();
            while( i.hasNext() ) System.err.println("---- "+i.next());

            EditableLayer layer = (EditableLayer) db.getOrCreateLayer(
                    "users",
                    SimplePointEncoder.class,
                    EditableLayerImpl.class
            );


            Artifact januszek = new Artifact( db, n, "users" );

            List<GeoPipeFlow> closests =
                    GeoPipeline
                            .startNearestNeighborLatLonSearch(layer, januszek.getCoordinate(), 100)
                            .sort("OrthodromicDistance")
                            .getMin("OrthodromicDistance")
                            .toList();

            Stream<GeoPipeFlow> nd = closests.stream().filter(p -> Integer.parseInt(p.getId()) != januszek.getId());

            tx.success();
            */
        //}

        /*
        try ( Transaction tx = graphDb.beginTx() ) {

            SimplePointLayer layer = db.createSimplePointLayer("neo-text-1");
            //EditableLayer runningLayer = (EditableLayer) spatialDb.getOrCreateLayer("running", SimplePointEncoder.class, EditableLayerImpl.class, "lon:lat");

            Coordinate coord = new Coordinate( 52.4080796, 21.0387675 );

            SpatialDatabaseRecord n = layer.add(new Coordinate(52.4080796, 21.0387675));
            layer.add( new Coordinate( 52.361979,   21.228282 ) );
            layer.add( new Coordinate( 52.4048308,  20.9395954 ) );
            layer.add( new Coordinate( 52.4767911,  21.0265852 ) );

            List<GeoPipeFlow> results =
                    layer.findClosestPointsTo( new Coordinate(52.3990944, 21.046645), 1000.0);

            for( GeoPipeFlow r : results )
                System.out.println(r.getRecord().toString() );

            Node firstNode = graphDb.createNode();
            firstNode.setProperty("message", "Hello, ");

            Node secondNode = graphDb.createNode();
            secondNode.setProperty("message", "World!");

            Relationship relationship = firstNode.createRelationshipTo(secondNode, RelTypes.KNOWS);
            relationship.setProperty("message", "brave Neo4j ");

            System.out.print(firstNode.getProperty("message"));
            System.out.print(relationship.getProperty("message"));
            System.out.print(secondNode.getProperty("message"));
            tx.success();
        }
        */
    }

    @PreDestroy
    public void kill() {
    }
}
