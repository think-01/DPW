package com.graph;

import com.Config;
import org.neo4j.gis.spatial.SpatialDatabaseService;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import javax.annotation.PostConstruct;
import javax.ejb.*;

/**
 * Created by wizzard on 14.06.15.
 */
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class GraphFactory {

    private GraphDatabaseService graphDb;
    private SpatialDatabaseService db;

    @Lock(LockType.READ)
    public GraphDatabaseService getGraphDB(){
        return graphDb;
    }

    @Lock(LockType.READ)
    public SpatialDatabaseService getSpatialGraphDB(){
        return db;
    }


    @PostConstruct
    public void Init()
    {
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( Config.DB_PATH );
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
        db = new SpatialDatabaseService(graphDb);
    }


}
