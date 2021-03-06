package org.neo4j.example.unmanagedextension;

import com.sun.jersey.api.client.Client;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.index.Index;
import org.neo4j.server.NeoServer;
import org.neo4j.server.helpers.CommunityServerBuilder;
import org.neo4j.server.rest.JaxRsResponse;
import org.neo4j.server.rest.RestRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MyServiceFunctionalTest {

    private static final RelationshipType KNOWS = DynamicRelationshipType.withName("KNOWS");

    @Test
    public void shouldReturnFriends() throws IOException {
        NeoServer server = CommunityServerBuilder.server()
                .withThirdPartyJaxRsPackage("org.neo4j.example.unmanagedextension", "/dummy")
                .build();
        server.start();
        populateDb(server.getDatabase().getGraph());

        assertEquals((Long)4L, ((Long)new ExecutionEngine(server.getDatabase().getGraph())
                .execute("MATCH (n) WHERE n.lastModified > 0 RETURN count(n)")
                .iterator().next().get("count(n)")));

        server.stop();
    }

    private void populateDb(GraphDatabaseService db) {

        try (Transaction tx = db.beginTx())
        {
            Node personA = createPerson(db, "A");
            Node personB = createPerson(db, "B");
            Node personC = createPerson(db, "C");
            Node personD = createPerson(db, "D");
            personA.createRelationshipTo(personB, KNOWS);
            personB.createRelationshipTo(personC, KNOWS);
            personC.createRelationshipTo(personD, KNOWS);
            tx.success();
        }
    }

    private Node createPerson(GraphDatabaseService db, String name) {
        Node node = db.createNode();
        node.setProperty("name", name);
        return node;
    }

}
