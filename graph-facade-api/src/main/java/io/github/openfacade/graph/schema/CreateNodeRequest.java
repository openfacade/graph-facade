package io.github.openfacade.graph.schema;

import lombok.*;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Getter
@Setter
public class CreateNodeRequest {

    @NonNull
    private String nodeId;

    @NonNull
    private String nodeSchema;

    private Map<String, Object> properties = new HashMap<>();

    private CreateNodeRequest(@NonNull String nodeId, @NonNull String nodeSchema, Map<String, Object> properties) {
        this.nodeId = Objects.requireNonNull(nodeId, "nodeId must not be null");
        this.nodeSchema = Objects.requireNonNull(nodeSchema, "nodeSchema must not be null");
        this.properties = properties != null ? new HashMap<>(properties) : new HashMap<>();
    }

    @Builder
    public static CreateNodeRequest createNodeRequest(@NonNull String nodeId, @NonNull String nodeSchema, Map<String, Object> properties) {
        return new CreateNodeRequest(nodeId, nodeSchema, properties);
    }
}
