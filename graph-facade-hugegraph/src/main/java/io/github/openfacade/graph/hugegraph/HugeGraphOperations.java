package io.github.openfacade.graph.hugegraph;

import io.github.openfacade.graph.api.DataType;
import io.github.openfacade.graph.api.GraphException;
import io.github.openfacade.graph.api.GraphOperations;
import io.github.openfacade.graph.schema.CreateNodeRequest;
import io.github.openfacade.graph.schema.CreateNodeSchemaRequest;
import lombok.RequiredArgsConstructor;
import org.apache.hugegraph.driver.HugeClient;
import org.apache.hugegraph.structure.graph.Vertex;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.github.openfacade.graph.hugegraph.ConvertUtil.toHugeGraphDataType;

@RequiredArgsConstructor
public class HugeGraphOperations implements GraphOperations {
    private final HugeClient hugeClient;

    @Override
    public void createNode(CreateNodeRequest createNodeRequest) throws GraphException {
        String nodeId = createNodeRequest.getNodeId();
        String nodeSchema = createNodeRequest.getNodeSchema();
        Map<String, Object> properties = createNodeRequest.getProperties();
        try {
            Vertex vertex = new Vertex(nodeSchema);
            vertex.id(nodeId);

            Set<String> schemaProperties = hugeClient.schema().getVertexLabel(nodeSchema).properties();
            // check properties
            checkNodeProperties(schemaProperties, properties);

            // set properties
            properties.forEach(vertex::property);

            // add vertex to graph
            hugeClient.graph().addVertex(vertex);
        } catch (Exception e) {
            throw new GraphException("Failed to create node: " + nodeId, e);
        }
    }

    private static void checkNodeProperties(Set<String> schemaProperties, Map<String, Object> properties) {
        List<String> invalidProperties = properties.keySet()
                .stream()
                .filter(propertyName -> !schemaProperties.contains(propertyName))
                .toList();

        if (!invalidProperties.isEmpty()) {
            throw new GraphException("invalid properties: [" + String.join(",", invalidProperties) + "]");
        }
    }

    @Override
    public void createNodeSchema(CreateNodeSchemaRequest createNodeSchemaRequest) {
        String name = createNodeSchemaRequest.getName();
        Map<String, DataType> propertyKeys = createNodeSchemaRequest.getPropertyKeys();
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
