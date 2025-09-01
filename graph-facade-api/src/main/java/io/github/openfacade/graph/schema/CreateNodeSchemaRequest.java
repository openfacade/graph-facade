package io.github.openfacade.graph.schema;

import io.github.openfacade.graph.api.DataType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class CreateNodeSchemaRequest {

    @NonNull
    private String name;

    private Map<String, DataType> propertyKeys = new HashMap<>();

    private CreateNodeSchemaRequest(@NonNull String nodeId, Map<String, DataType> propertyKeys) {
        this.name = Objects.requireNonNull(nodeId, "nodeId must not be null");
        this.propertyKeys = propertyKeys != null ? new HashMap<>(propertyKeys) : new HashMap<>();
    }

    @Builder
    public static CreateNodeSchemaRequest createNodeSchemaRequest(@NonNull String name, Map<String, DataType> propertyKeys) {
        return new CreateNodeSchemaRequest(name, propertyKeys);
    }
}
