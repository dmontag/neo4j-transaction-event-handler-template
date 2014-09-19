package org.neo4j.example.unmanagedextension;

import javax.ws.rs.Path;

/**
 * This file is necessary for the extension load mechanism to work properly.
*/
@Path("/")
public class DummyResource {
}
