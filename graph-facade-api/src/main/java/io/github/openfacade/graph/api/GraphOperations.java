package io.github.openfacade.graph.api;

import org.jspecify.annotations.NonNull;

import java.util.Map;

public interface GraphOperations {
    void createNode(@NonNull String nodeId, String vertexLabel) throws GraphException;

    void createNodeSchema(@NonNull String name, Map<String, DataType> propertyKeys) throws GraphException;
}
