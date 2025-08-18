package io.github.openfacade.graph.hugegraph;

import io.github.openfacade.graph.api.GraphException;
import io.github.openfacade.graph.api.GraphOperations;
import lombok.RequiredArgsConstructor;
import org.apache.hugegraph.driver.HugeClient;
import org.jspecify.annotations.NonNull;

@RequiredArgsConstructor
public class HugeGraphOperations implements GraphOperations {
    private final HugeClient hugeClient;

    @Override
    public void createNode(@NonNull String nodeId) throws GraphException {
        throw new UnsupportedOperationException();
    }
}
