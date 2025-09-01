package io.github.openfacade.graph.neo4j;

import io.github.openfacade.graph.api.GraphException;
import io.github.openfacade.graph.api.GraphOperations;
import io.github.openfacade.graph.schema.CreateNodeRequest;
import io.github.openfacade.graph.schema.CreateNodeSchemaRequest;


public class Neo4jGraphOperations implements GraphOperations {
    @Override
    public void createNode(CreateNodeRequest createNodeRequest) throws GraphException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void createNodeSchema(CreateNodeSchemaRequest createNodeSchemaRequest)  throws GraphException {
        throw new UnsupportedOperationException();
    }
}
