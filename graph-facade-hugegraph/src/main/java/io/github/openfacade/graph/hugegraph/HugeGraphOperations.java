package io.github.openfacade.graph.hugegraph;

import io.github.openfacade.graph.api.DataType;
import io.github.openfacade.graph.api.GraphException;
import io.github.openfacade.graph.api.GraphOperations;
import io.github.openfacade.graph.schema.CreateEdgeRequest;
import io.github.openfacade.graph.schema.CreateEdgeSchemaRequest;
import io.github.openfacade.graph.schema.CreateNodeRequest;
import io.github.openfacade.graph.schema.CreateNodeSchemaRequest;
import lombok.RequiredArgsConstructor;
import org.apache.hugegraph.driver.HugeClient;
import org.apache.hugegraph.structure.graph.Edge;
import org.apache.hugegraph.structure.graph.Vertex;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
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

    @Override
    public void createEdge(@NonNull CreateEdgeRequest createEdgeRequest) throws GraphException {
        String edgeName = createEdgeRequest.getEdgeName();
        String edgeSchemaName = createEdgeRequest.getEdgeSchemaName();
        Map<String, Object> properties = createEdgeRequest.getProperties();

        try {
            // Get edge label schema to validate properties
            Set<String> schemaProperties = hugeClient.schema().getEdgeLabel(edgeSchemaName).properties();

            // Check properties
            checkEdgeProperties(schemaProperties, properties);

            // Since we don't have source/target vertex IDs in the request,
            // we'll assume they are stored in the properties map with special keys
            Object sourceIdObj = properties.get("sourceId");
            Object targetIdObj = properties.get("targetId");

            if (sourceIdObj == null || targetIdObj == null) {
                throw new GraphException("Source and target vertex IDs are required to create an edge. Please provide them in the properties map with keys 'sourceId' and 'targetId'.");
            }

            String sourceId = sourceIdObj.toString();
            String targetId = targetIdObj.toString();

            // Remove sourceId and targetId from properties as they are not edge properties
            Map<String, Object> edgeProperties = new HashMap<>(properties);
            edgeProperties.remove("sourceId");
            edgeProperties.remove("targetId");

            // Create edge
            Edge edge = new Edge(edgeSchemaName);
            edge.sourceId(sourceId);
            edge.targetId(targetId);

            // Set properties
            edgeProperties.forEach(edge::property);

            // Add edge to graph
            hugeClient.graph().addEdge(edge);
        } catch (Exception e) {
            throw new GraphException("Failed to create edge: " + edgeName, e);
        }
    }

    private static void checkEdgeProperties(Set<String> schemaProperties, Map<String, Object> properties) {
        // Filter out sourceId and targetId as they are not edge properties
        List<String> invalidProperties = properties.keySet()
                .stream()
                .filter(propertyName -> !"sourceId".equals(propertyName) && !"targetId".equals(propertyName))
                .filter(propertyName -> !schemaProperties.contains(propertyName))
                .toList();

        if (!invalidProperties.isEmpty()) {
            throw new GraphException("invalid properties: [" + String.join(",", invalidProperties) + "]");
        }
    }

    @Override
    public void createEdgeSchema(@NonNull CreateEdgeSchemaRequest createEdgeSchemaRequest) throws GraphException {
        String name = createEdgeSchemaRequest.getName();
        String sourceNodeSchemaName = createEdgeSchemaRequest.getSourceNodeSchemaName();
        String targetNodeSchemaName = createEdgeSchemaRequest.getTargetNodeSchemaName();
        Map<String, DataType> propertyKeys = createEdgeSchemaRequest.getPropertyKeys();

        try {
            if (checkEdgeLabelExist(name)) {
                return;
            }

            // Create edge label if not exist
            hugeClient.schema().edgeLabel(name)
                    .sourceLabel(sourceNodeSchemaName)
                    .targetLabel(targetNodeSchemaName)
                    .ifNotExist()
                    .create();

            // Create property keys if not exist
            propertyKeys.forEach((propertyName, dataType) ->
                    hugeClient.schema().propertyKey(propertyName)
                            .dataType(toHugeGraphDataType(dataType))
                            .ifNotExist()
                            .create());

            // Bind properties to edge
            if (!propertyKeys.isEmpty()) {
                String[] propertyNames = propertyKeys.keySet().toArray(new String[0]);
                hugeClient.schema().edgeLabel(name)
                        .nullableKeys(propertyNames)
                        .properties(propertyNames)
                        .append();
            }
        } catch (Exception e) {
            throw new GraphException("Failed to create edge schema for name: " + name, e);
        }
    }

    @Override
    public void deleteNode(@NonNull String nodeId) throws GraphException {
        try {
            // Check if the node exists before attempting deletion
            Vertex vertex = hugeClient.graph().getVertex(nodeId);
            if (vertex == null) {
                // Considered idempotent - return silently if node doesn't exist
                return;
            }

            // In HugeGraph, removing a vertex automatically removes all its edges
            // This is the default behavior, but we should make it explicit in the code comment
            hugeClient.graph().removeVertex(nodeId);
        } catch (GraphException e) {
            // Re-throw our custom exceptions
            throw e;
        } catch (Exception e) {
            throw new GraphException("Failed to delete node: " + nodeId, e);
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

    private boolean checkEdgeLabelExist(String name) {
        try {
            hugeClient.schema().getEdgeLabel(name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
