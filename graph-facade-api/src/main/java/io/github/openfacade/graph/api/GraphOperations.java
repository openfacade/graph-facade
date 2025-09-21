package io.github.openfacade.graph.api;

import io.github.openfacade.graph.schema.CreateEdgeRequest;
import io.github.openfacade.graph.schema.CreateEdgeSchemaRequest;
import io.github.openfacade.graph.schema.CreateNodeRequest;
import io.github.openfacade.graph.schema.CreateNodeSchemaRequest;
import org.jspecify.annotations.NonNull;


public interface GraphOperations {
    void createNode(@NonNull CreateNodeRequest createNodeRequest) throws GraphException;

    void createNodeSchema(@NonNull CreateNodeSchemaRequest createNodeSchemaRequest)  throws GraphException;

    void createEdge(@NonNull CreateEdgeRequest createEdgeRequest) throws GraphException;

    void createEdgeSchema(@NonNull CreateEdgeSchemaRequest createEdgeSchemaRequest)  throws GraphException;
}
