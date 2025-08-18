package io.github.openfacade.graph.api;

import org.jspecify.annotations.NonNull;

public interface GraphOperations {
    void createNode(@NonNull String nodeId) throws GraphException;
}
