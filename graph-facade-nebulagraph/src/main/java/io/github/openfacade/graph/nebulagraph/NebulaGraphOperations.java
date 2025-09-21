package io.github.openfacade.graph.nebulagraph;

import io.github.openfacade.graph.api.GraphException;
import io.github.openfacade.graph.api.GraphOperations;
import io.github.openfacade.graph.schema.CreateEdgeRequest;
import io.github.openfacade.graph.schema.CreateEdgeSchemaRequest;
import io.github.openfacade.graph.schema.CreateNodeRequest;
import io.github.openfacade.graph.schema.CreateNodeSchemaRequest;
import org.jspecify.annotations.NonNull;

/**
 * NebulaGraph implementation of GraphOperations interface.
 * Provides operations for interacting with NebulaGraph database.
 */
public class NebulaGraphOperations implements GraphOperations {

    @Override
    public void createNode(CreateNodeRequest createNodeRequest) throws GraphException {
        throw new UnsupportedOperationException("NebulaGraph createNode operation not yet implemented");
    }

    @Override
    public void createNodeSchema(CreateNodeSchemaRequest createNodeSchemaRequest) throws GraphException {
        throw new UnsupportedOperationException("NebulaGraph createNodeSchema operation not yet implemented");
    }

    @Override
    public void createEdge(@NonNull CreateEdgeRequest createEdgeRequest) throws GraphException {
        throw new UnsupportedOperationException("NebulaGraph createEdgeRequest operation not yet implemented");
    }

    @Override
    public void createEdgeSchema(@NonNull CreateEdgeSchemaRequest createEdgeSchemaRequest) throws GraphException {
        throw new UnsupportedOperationException("NebulaGraph createEdgeSchemaRequest operation not yet implemented");
    }
}
