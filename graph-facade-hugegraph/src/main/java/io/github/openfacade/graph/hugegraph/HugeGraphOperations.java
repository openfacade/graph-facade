package io.github.openfacade.graph.hugegraph;

import io.github.openfacade.graph.api.DataType;
import io.github.openfacade.graph.api.GraphException;
import io.github.openfacade.graph.api.GraphOperations;
import lombok.RequiredArgsConstructor;
import org.apache.hugegraph.driver.HugeClient;
import org.apache.hugegraph.structure.graph.Vertex;
import org.jspecify.annotations.NonNull;

import java.util.Map;

import static io.github.openfacade.graph.hugegraph.ConvertUtil.toHugeGraphDataType;

@RequiredArgsConstructor
public class HugeGraphOperations implements GraphOperations {
    private final HugeClient hugeClient;

    @Override
    public void createNode(@NonNull String nodeId, String nodeSchema) throws GraphException {
        try {
            Vertex vertex = new Vertex(nodeSchema);
            vertex.id(nodeId);

            // add vertex to graph
            hugeClient.graph().addVertex(vertex);
        } catch (Exception e) {
            throw new GraphException("Failed to create node: " + nodeId, e);
        }
    }

    @Override
    public void createNodeSchema(String name, Map<String, DataType> propertyKeys) {
        try {
            if (checkVertexLabelExist(name)) {
                return;
            }

            // create vertex label if not exist
            hugeClient.schema().vertexLabel(name)
                    .useCustomizeStringId()
                    .ifNotExist()
                    .create();

            // create property keys if not exist
            propertyKeys.forEach((propertyName, dataType) ->
                    hugeClient.schema().propertyKey(propertyName)
                    .dataType(toHugeGraphDataType(dataType))
                    .ifNotExist()
                    .create());

            // bind properties to node
            if (!propertyKeys.isEmpty()) {
                String[] propertyNames = propertyKeys.keySet().toArray(new String[0]);
                hugeClient.schema().vertexLabel(name)
                        .nullableKeys(propertyNames)
                        .properties(propertyNames)
                        .append();
            }
        } catch (Exception e) {
            throw new GraphException("Failed to create node schema for name: " + name, e);
        }
    }

    private boolean checkVertexLabelExist(String name) {
        try {
            hugeClient.schema().getVertexLabel(name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
