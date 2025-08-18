package io.github.openfacade.graph.neo4j;

import io.github.openfacade.graph.api.GraphException;
import io.github.openfacade.graph.api.GraphOperations;
import org.jspecify.annotations.NonNull;

public class Neo4jGraphOperations implements GraphOperations {
    @Override
    public void createNode(@NonNull String nodeId) throws GraphException {
        throw new UnsupportedOperationException();
    }
}
