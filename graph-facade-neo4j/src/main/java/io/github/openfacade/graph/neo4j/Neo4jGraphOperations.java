package io.github.openfacade.graph.neo4j;

import io.github.openfacade.graph.api.DataType;
import io.github.openfacade.graph.api.GraphException;
import io.github.openfacade.graph.api.GraphOperations;
import org.jspecify.annotations.NonNull;

import java.util.Map;

public class Neo4jGraphOperations implements GraphOperations {
    @Override
    public void createNode(@NonNull String nodeId, String vertexLabel) throws GraphException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void createNodeSchema(@NonNull String name, Map<String, DataType> propertyKeys) throws GraphException {
        throw new UnsupportedOperationException();
    }
}
