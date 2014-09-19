Sample Neo4j transaction event handler
======================================

This is a transaction event handler.

1. Build it: 

        mvn clean package

2. Copy target/transaction-event-handler-template-1.0.jar to the plugins/ directory of your Neo4j server.

3. Load the event handler by adding a line to conf/neo4j-server.properties:

        org.neo4j.server.thirdparty_jaxrs_classes=org.neo4j.example.unmanagedextension=/dummyPath

   The path used does not matter, as the event handler extension will not be serving requests. If you piggyback
   the event handler on an actual extension JAR, then just configure it as you would with any extension.

4. Start the Neo4j server.

